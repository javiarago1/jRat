package Server.GUI.TreeInterpreter.FileManager;

import Client.InformationGathering.System.InfoObject;
import Server.Connections.Streams;
import Server.GUI.TreeInterpreter.TreeUtils.Manager;

import java.io.File;
import java.util.List;

public class DeleteFiles extends Manager {
    public DeleteFiles(List<File> filesArray, Streams stream) {
        super(filesArray, stream);
    }

    @Override
    public void run() {
        getStream().sendObject(new InfoObject(getFilesArray(), "DELETE"));
    }
}
