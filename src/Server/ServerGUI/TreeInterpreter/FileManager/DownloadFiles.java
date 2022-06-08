package Server.ServerGUI.TreeInterpreter.FileManager;

import Client.InformationGathering.System.InfoObject;
import Server.ServerConnections.Streams;
import net.lingala.zip4j.ZipFile;

import java.io.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DownloadFiles implements Runnable {


    private final List<File> filesArray;
    private final Streams stream;

    public DownloadFiles(List<File> filesArray, Streams stream) {
        this.filesArray = new ArrayList<>(filesArray);
        this.stream = stream;

    }


    @Override
    public void run() {
        System.out.println("Archivos que voy a enviar -> " + filesArray);
        stream.sendObject(new InfoObject(filesArray, "DOWNLOAD"));
        File sessionDirectory = new File("Downloaded Files" + "\\" + stream.getIdentifier() + "\\" + getTime());
        sessionDirectory.mkdirs();
        try (FileOutputStream fileToCreate = new FileOutputStream(sessionDirectory + "/temp.zip")) {
            byte[] array = stream.readFile();
            fileToCreate.write(array);
            System.out.println("Downloaded correctly");
            new ZipFile(sessionDirectory + "/temp.zip").extractAll(sessionDirectory.toString());
            System.out.println("Extraction finished properly");
        } catch (IOException error) {
            error.printStackTrace();
        }
    }


    private String getTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("[yyyy-MM-dd] - [HH-mm-ss]");
        Date date = new Date(System.currentTimeMillis());
        return formatter.format(date);
    }

}

