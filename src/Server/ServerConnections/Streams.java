package Server.ServerConnections;


import Client.InformationGathering.System.SystemInformation;
import Client.InformationGathering.System.SystemNetworkInformation;


import java.io.*;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Streams {
    private final ObjectOutputStream output;
    private final ObjectInputStream input;
    private final DataInputStream dataInput;

    private boolean isWorking;

    private SystemInformation tempSystemInformation;
    private SystemNetworkInformation tempSystemNetworkInformation;

    public ExecutorService executor = Executors.newSingleThreadExecutor();

    public Streams(Socket socket) throws IOException {
        if (socket == null) throw new IllegalArgumentException();
        output = new ObjectOutputStream(socket.getOutputStream());
        input = new ObjectInputStream(socket.getInputStream());
        dataInput= new DataInputStream(socket.getInputStream());



    }



    public byte[] readFile(){
        int length=0;
        try {
            length = dataInput.readInt();
        } catch (IOException e) {
            e.printStackTrace();
        }


        byte[]filecontent;
        if (length>0){
            filecontent=new byte[length];
            try {
                dataInput.readFully(filecontent,0,length);
                return filecontent;
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
        return null;
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





    public String getIdentifier(){
        return tempSystemNetworkInformation.getIP()+" - "+tempSystemInformation.getUSER_NAME();
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