package Server.GUI.TreeInterpreter.DirectoryTreeGUI.Menus;

import Server.Connections.Streams;
import Server.GUI.TreeInterpreter.FileManager.CopyFiles;
import Server.GUI.TreeInterpreter.TreeUtils.TreeMenu;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PasteAction extends TreeMenu {

    private final List<File> filesToCopy;

    public PasteAction(JTree tree, Streams stream, List<File> filesToCopy) {
        super(tree, stream);
        this.filesToCopy = new ArrayList<>(filesToCopy);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        getStream().executor.submit(new CopyFiles(filesToCopy, getSelectedPaths(), getStream()));
    }
}
