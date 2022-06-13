package Server.GUI.TreeInterpreter.TreeUtils;

import Server.Connections.Streams;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public abstract class Manager implements Runnable {
    private final List<File> filesArray;
    private final Streams stream;
    private List<File> directories;
    private File directory;

    public Manager(List<File> filesArray, List<File> directories, Streams stream) {
        this.filesArray = new ArrayList<>(filesArray);
        this.directories = new ArrayList<>(directories);
        this.stream = stream;
    }

    public Manager(List<File> filesArray, File directory, Streams stream) {
        this.filesArray = new ArrayList<>(filesArray);
        this.directory = directory;
        this.stream = stream;
    }

    public Manager(List<File> filesArray, Streams stream) {
        this.filesArray = new ArrayList<>(filesArray);
        this.stream = stream;
    }

    @Override
    public abstract void run();

    public List<File> getFilesArray() {
        return filesArray;
    }

    public List<File> getDirectories() {
        return directories;
    }

    public File getDirectory() {
        return directory;
    }

    public Streams getStream() {
        return stream;
    }
}
