package Server.GUI;

import Server.Connections.Server;

import javax.swing.*;

public class Main {

    static public jRatGUI gui;

    public static void main(String[] args) {
        try {
            Server server = new Server(3055);
            SwingUtilities.invokeLater(() -> gui = new jRatGUI(server));
            server.startServer();
        } catch (Exception e) {
            System.err.println("Server exception!\n");
            e.printStackTrace();
        }

    }

}