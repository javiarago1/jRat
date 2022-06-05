package Server.ServerGUI.TreeInterpreter;

import Server.ServerConnections.Streams;

import java.io.*;

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
        if (action.equals(Action.DOWNLOAD)){
            System.out.println(Action.DOWNLOAD);
            stream.sendObject(filesArray.get(0));
            try(FileOutputStream fileToCreate = new FileOutputStream(filesArray.get(0).getName())) {
                    byte[]array = stream.readFile();
                    fileToCreate.write(array);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
