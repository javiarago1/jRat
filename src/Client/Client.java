package Client;

import Client.InformationGathering.System.InfoObject;
import Client.Tree.Tree;
import Client.InformationGathering.System.SystemInformation;
import Client.InformationGathering.System.SystemNetworkInformation;
import net.lingala.zip4j.ZipFile;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class Client {
    private static final String IP = "192.168.1.133";
    private static final int PORT = 3055;

    public static void main(String[] args) {

        while (true) {
            try {
                System.out.println("Conectando...");

                Socket s = new Socket(IP, PORT);

                System.out.println("Conectado");


                ObjectOutputStream output = new ObjectOutputStream(s.getOutputStream());
                ObjectInputStream input = new ObjectInputStream(s.getInputStream());
                DataOutputStream dataOutput = new DataOutputStream(s.getOutputStream());


                Object reader;
                do {
                    reader = input.readObject();

                    if (reader instanceof Object[]) {
                        output.writeObject(new Object[]{new SystemNetworkInformation(), new SystemInformation()});
                    } else if (reader instanceof String e && e.equals("DISKS")) {
                        output.writeObject(File.listRoots());

                    } else if (reader instanceof InfoObject e && e.getCommand().equals("TREE")) {
                        System.out.println("Path recibido " + e.getPath());
                        new Tree(e.getPath(), output).start();
                    } else if (reader instanceof String e && e.equals("SYS_DETAILS")) {
                        output.writeObject(new Object[]{new SystemNetworkInformation(), new SystemInformation()});
                    } else if (reader instanceof InfoObject e && e.getCommand().equals("DOWNLOAD")) {
                        ArrayList<File>fileArray = new ArrayList<>(e.getFileArray());
                        System.out.println(fileArray);
                        ZipFile zipFile = new ZipFile("fichero.zip");
                        for (File a : fileArray) {
                            if (a.isDirectory()) {
                                zipFile.addFolder(a);
                            } else {
                                zipFile.addFile(a);
                            }
                        }
                        File zipToSend = zipFile.getFile();
                        FileInputStream fileInputStream = new FileInputStream(zipToSend);
                        byte[] fileContentBytes = new byte[(int) zipToSend.length()];
                        int length = fileInputStream.read(fileContentBytes);
                        fileInputStream.close();
                        dataOutput.writeInt(length);
                        dataOutput.write(fileContentBytes);
                        zipToSend.delete();

                    }

                } while (reader != null);
            } catch (ConnectException e) {
                System.out.println("Connection refused");
            } catch (SocketException ignored) {
                System.out.println("Connection reset");
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        }

    }

}