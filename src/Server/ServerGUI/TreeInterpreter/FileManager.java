package Server.ServerGUI.TreeInterpreter;

import Server.ServerConnections.Streams;

import java.io.*;

import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;

public class FileManager implements Runnable{


    private ArrayList<File>filesArray;
    private Streams stream;
    private Action action;

    public FileManager(ArrayList<File> filesArray, Streams stream, Action action){
        this.filesArray=filesArray;
        this.stream=stream;
        this.action=action;
    }

    @Override
    public void run() {
        if (action.equals(Action.DOWNLOAD)) {
            File downloadDirectory = new File("Downloaded Files");
            if (!downloadDirectory.exists()){
                downloadDirectory.mkdirs();
            }

            for (File e:filesArray) {
                stream.sendObject(e);
                try (FileOutputStream fileToCreate = new FileOutputStream("Downloaded Files/"+e.getName())) {
                    byte[] array = stream.readFile();
                    fileToCreate.write(array);
                } catch (IOException error) {
                    error.printStackTrace();
                }
            }
        }

    }
}
