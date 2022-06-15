package Client.InformationGathering.System;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ReadJSON {

    JSONObject jsonObject;

    public ReadJSON(String path) throws IOException {
        jsonObject = readJsonFromUrl(path);
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        try (InputStream is = new URL(url).openStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String jsonText = readAll(rd);
            return new JSONObject(jsonText);
        }
    }

    public String get(String item) {
        if (item.equals("proxy")) return Boolean.toString(jsonObject.getBoolean(item));
        return jsonObject.getString(item);
    }


}
