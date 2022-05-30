package Server;



import InformationGathering.SystemNetworkInformation;

import javax.swing.*;
import java.io.*;
import java.net.Socket;

public class Streams {
    private PrintWriter writer;
    private BufferedReader reader;
    private Socket socket;


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
            String msg = reader.readLine();
            return msg;
        }
        catch (Exception e) { return null; }
    }


    public Object readObject (){
        try {
            return  new ObjectInputStream(socket.getInputStream()).readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }

    }

}