package Client.InformationGathering.System;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

public class InfoObject implements Serializable {
    private File path;
    private String command;
    private ArrayList<File>fileArray;


    public InfoObject(ArrayList<File>fileArray,String command){
        this.fileArray=fileArray;
        this.command=command;
    }

    public InfoObject(File route, String command) {
        this.path = route;
        this.command = command;
    }

    public ArrayList<File> getFileArray() {
        return fileArray;
    }

    public File getPath() {
        return path;
    }

    public String getCommand() {
        return command;
    }

}