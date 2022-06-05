package Client;

import Client.InformationGathering.System.InfoObject;
import Client.Tree.Tree;
import Client.InformationGathering.System.SystemInformation;
import Client.InformationGathering.System.SystemNetworkInformation;

import java.io.*;
import java.net.*;

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
                        File[] files = File.listRoots();
                        String[] rutas = new String[files.length];
                        for (int i = 0; i < files.length; i++) {
                            rutas[i] = files[i].toString();
                        }
                        output.writeObject(rutas);
                    } else if (reader instanceof InfoObject e && e.getCommand().equals("TREE")) {
                        System.out.println("Path recibido "+e.getPath());
                        new Tree(e.getPath(), output).start();
                    } else if (reader instanceof String e && e.equals("SYS_DETAILS")) {
                        output.writeObject(new Object[]{new SystemNetworkInformation(), new SystemInformation()});
                    } else if (reader instanceof File file){
                        FileInputStream fileInputStream = new FileInputStream(file);
                        byte[]fileContentBytes = new byte[(int) file.length()];
                        int length = fileInputStream.read(fileContentBytes);
                        fileInputStream.close();
                        dataOutput.writeInt(length);
                        dataOutput.write(fileContentBytes);
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