package Server;



import javax.swing.*;
import java.io.ObjectInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Streams {
    private PrintWriter writer;
    private BufferedReader reader;
    private ObjectInputStream objectReader;
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

    public JTree readObject (){
        try {
            return (JTree) new ObjectInputStream(socket.getInputStream()).readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }

    }

}