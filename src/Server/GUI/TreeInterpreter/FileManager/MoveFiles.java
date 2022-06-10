package Server.GUI.TreeInterpreter.FileManager;

import Client.InformationGathering.System.InfoObject;
import Server.Connections.Streams;
import Server.GUI.TreeInterpreter.TreeUtils.Manager;

import java.io.File;
import java.util.List;

public class MoveFiles extends Manager {


    public MoveFiles(List<File> filesToCopy, File directories, Streams stream) {
        super(filesToCopy, directories, stream);
    }

    @Override
    public void run() {
        getStream().sendObject(new InfoObject(getFilesArray(), getDirectory(), "MOVE"));
    }
}
