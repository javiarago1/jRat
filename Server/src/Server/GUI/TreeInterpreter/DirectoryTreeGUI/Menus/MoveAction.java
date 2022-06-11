package Server.GUI.TreeInterpreter.DirectoryTreeGUI.Menus;

import Server.Connections.Streams;
import Server.GUI.TreeInterpreter.FileManager.MoveFiles;
import Server.GUI.TreeInterpreter.TreeUtils.TreeMenu;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MoveAction extends TreeMenu {

    private final List<File> filesToMove;

    public MoveAction(JTree tree, Streams stream, List<File> filesToMove) {
        super(tree, stream);
        this.filesToMove = new ArrayList<>(filesToMove);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        getStream().executor.submit(new MoveFiles(filesToMove, getSelectedPath(), getStream()));
    }
}
