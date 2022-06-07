package Server.ServerGUI.TreeInterpreter;

import Client.InformationGathering.System.InfoObject;
import Server.ServerConnections.Streams;
import net.lingala.zip4j.ZipFile;

import java.io.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FileManager implements Runnable{


    private final ArrayList<File> filesArray;
    private final Streams stream;
    private final Action action;

    public FileManager(ArrayList<File> filesArray, Streams stream, Action action){
        this.filesArray=new ArrayList<>(filesArray);
        this.stream=stream;
        this.action=action;
    }

    @Override
    public void run() {
        if (action.equals(Action.DOWNLOAD)) {
            System.out.println("Archivos que voy a enviar -> "+filesArray);
            stream.sendObject(new InfoObject(filesArray,"DOWNLOAD"));
            File sessionDirectory = new File("Downloaded Files"+"\\"+stream.getIdentifier()+"\\"+getTime());
            sessionDirectory.mkdirs();
                try (FileOutputStream fileToCreate = new FileOutputStream(sessionDirectory+"/temp.zip")) {
                    byte[] array = stream.readFile();
                    fileToCreate.write(array);
                    System.out.println("Downloaded correctly");
                    new ZipFile(sessionDirectory+"/temp.zip").extractAll(sessionDirectory.toString());
                    System.out.println("Extraction finished properly");
                } catch (IOException error) {
                    error.printStackTrace();
                }
        }

    }

    private String getTime(){
        SimpleDateFormat formatter= new SimpleDateFormat("[yyyy-MM-dd] - [HH-mm-ss]");
        Date date = new Date(System.currentTimeMillis());
        return formatter.format(date);
    }

}
