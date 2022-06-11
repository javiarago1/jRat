package Server.GUI.TreeInterpreter.TreeGUI;

import Client.InformationGathering.System.InfoObject;
import Server.Connections.Streams;
import Server.GUI.TreeInterpreter.TreeUtils.Folder;

import javax.swing.*;
import java.io.File;
import java.util.List;

public class FolderRequest extends Folder {
    public FolderRequest(JTree tree, Streams stream) {
        super(tree, stream);
    }

    @Override
    public List<?> requestTree(String value) {
        getStream().sendObject(new InfoObject(new File(value), "TREE"));
        return (List<?>) getStream().readObject();
    }
}
