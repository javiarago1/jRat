package Server.GUI.TreeInterpreter.DirectoryTreeGUI;

import Client.InformationGathering.System.InfoObject;
import Server.Connections.Streams;
import Server.GUI.TreeInterpreter.TreeUtils.Folder;

import javax.swing.*;
import java.io.File;
import java.util.List;


public class DirectoryFolderRequest extends Folder {
    public DirectoryFolderRequest(JTree tree, Streams stream) {
        super(tree, stream);
    }

    @Override
    public List<?> requestTree(String value) {
        getStream().sendObject(new InfoObject(new File(value), "TREE_DIRECTORIES"));
        return (List<?>) getStream().readObject();
    }
}
