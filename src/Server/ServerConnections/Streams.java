package Server.ServerConnections;



import Client.InformationGathering.SystemInformation;
import Client.InformationGathering.SystemNetworkInformation;
import jdk.swing.interop.SwingInterOpUtils;

import java.io.*;
import java.net.Socket;

public class Streams {
    private PrintWriter writer;
    private BufferedReader reader;
    private Socket socket;

    private SystemInformation tempSystemInformation;
    private SystemNetworkInformation tempSystemNetworkInformation;


    public Streams(Socket socket) throws IOException {
        if (socket == null) throw new IllegalArgumentException();
        this.socket = socket;
        writer = new PrintWriter(socket.getOutputStream());
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

    }

    public boolean sendMsg(String msg){
        try {
            writer.println(msg);
            writer.flush();
        }
        catch (Exception e) { return false; }
        return true;
    }

    public String readMsg(){
        try {
            return reader.readLine();
        }
        catch (Exception e) { return null; }
    }


    public Object readObject (){
        try {
            return  new ObjectInputStream(socket.getInputStream()).readObject();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            System.out.println("Ocurre CLASSNOTFOUND");
            return null;
        }

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
}