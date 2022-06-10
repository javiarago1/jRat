package Client.InformationGathering.System;

import java.io.File;
import java.io.Serializable;
import java.util.List;

public class InfoObject implements Serializable {
    private File path, destination;
    private final String command;
    private List<File> filesArray;
    private List<File> directories;

    public InfoObject(List<File> fileArray, String command) {
        this.filesArray = fileArray;
        this.command = command;
    }

    public InfoObject(File route, String command) {
        this.path = route;
        this.command = command;
    }

    public InfoObject(List<File> fileArray, List<File> directories, String command) {
        this.filesArray = fileArray;
        this.directories = directories;
        this.command = command;
    }

    public InfoObject(List<File> fileArray, File destination, String command) {
        this.filesArray = fileArray;
        this.destination = destination;
        this.command = command;
    }

    public File getDestination() {
        return destination;
    }

    public List<File> getDirectories() {
        return directories;
    }

    public List<File> getFilesArray() {
        return filesArray;
    }

    public File getPath() {
        return path;
    }

    public String getCommand() {
        return command;
    }

}