package Server.Connections;



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
    private final DataOutputStream dataOutput;

    private SystemInformation tempSystemInformation;
    private SystemNetworkInformation tempSystemNetworkInformation;

    public ExecutorService executor = Executors.newSingleThreadExecutor();

    public Streams(Socket socket) throws IOException {
        if (socket == null) throw new IllegalArgumentException();
        output = new ObjectOutputStream(socket.getOutputStream());
        input = new ObjectInputStream(socket.getInputStream());
        dataInput = new DataInputStream(socket.getInputStream());
        dataOutput = new DataOutputStream(socket.getOutputStream());

    }

    public void sendFile(File file) {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] fileContentBytes = new byte[(int) file.length()];
            int length = fileInputStream.read(fileContentBytes);
            fileInputStream.close();
            dataOutput.writeInt(length);
            dataOutput.write(fileContentBytes);
        } catch (IOException error) {
            error.printStackTrace();
        }
    }

    public byte[] readFile() {
        int length = 0;
        try {
            length = dataInput.readInt();
        } catch (IOException e) {
            e.printStackTrace();
        }

        byte[]byteContent;
        if (length>0){
            byteContent=new byte[length];
            try {
                dataInput.readFully(byteContent,0,length);
                return byteContent;
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

}