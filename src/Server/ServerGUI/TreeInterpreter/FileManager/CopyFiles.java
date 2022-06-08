package Server.ServerGUI.TreeInterpreter.FileManager;


import Client.InformationGathering.System.InfoObject;
import Server.ServerConnections.Streams;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CopyFiles implements Runnable {

    private List<File>filesArray;
    private Streams stream;
    private File destination;

    public CopyFiles(List<File> arrayList,File destination,Streams stream){
        this.filesArray=new ArrayList<>(arrayList);
        this.stream=stream;
        this.destination=destination;
    }


    @Override
    public void run() {
        stream.sendObject(new InfoObject(filesArray, destination,"COPY"));
    }
}
