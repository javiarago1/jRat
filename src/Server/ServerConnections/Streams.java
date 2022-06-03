package Server.ServerConnections;


import Client.InformationGathering.SystemInformation;
import Client.InformationGathering.SystemNetworkInformation;
import Server.ServerGUI.MainClass;
import Server.ServerGUI.TreeInterpreter.TreeGUI;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Streams {
    private final ObjectOutputStream output;
    private final ObjectInputStream input;


    private TreeGUI treeGUI;

    private boolean isWorking;

    private SystemInformation tempSystemInformation;
    private SystemNetworkInformation tempSystemNetworkInformation;

    public ExecutorService executor = Executors.newSingleThreadExecutor();

    public Streams(Socket socket) throws IOException {
        if (socket == null) throw new IllegalArgumentException();
        output = new ObjectOutputStream(socket.getOutputStream());
        input = new ObjectInputStream(socket.getInputStream());

    }

    public void sendObject(Object object) {
        try {
            output.writeObject(object);
        } catch (Exception ignored) {
        }
    }


    public Object readObject() {
        try {
            return input.readObject();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            System.out.println("Ocurre CLASS NOT FOUND");
            return null;
        }

    }



    public void openTreeGUI(JTree tree) {
        treeGUI = new TreeGUI(MainClass.gui.getFrame(),tree);
    }

    public SystemInformation getTempSystemInformation() {
        return tempSystemInformation;
    }

    public void setTempSystemInformation(SystemInformation tempSystemInformation) {
        this.tempSystemInformation = tempSystemInformation;
    }

    public SystemNetworkInformation getTempSystemNetworkInformation() {
        return tempSystemNetworkInformation;
    }

    public void setTempSystemNetworkInformation(SystemNetworkInformation tempSystemNetworkInformation) {
        this.tempSystemNetworkInformation = tempSystemNetworkInformation;
    }

    public boolean isWorking() {
        return isWorking;
    }

    public void setWorking(boolean working) {
        isWorking = working;
    }
}