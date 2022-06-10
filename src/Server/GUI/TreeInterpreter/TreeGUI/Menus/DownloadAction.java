package Server.GUI.TreeInterpreter.TreeGUI.Menus;

import Server.Connections.Streams;
import Server.GUI.TreeInterpreter.FileManager.DownloadFiles;
import Server.GUI.TreeInterpreter.TreeUtils.TreeMenu;

import javax.swing.*;
import java.awt.event.ActionEvent;


public class DownloadAction extends TreeMenu {

    private final Streams stream;

    public DownloadAction(JTree tree, Streams stream) {
        super(tree);
        this.stream = stream;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        stream.executor.submit(new DownloadFiles(getSelectedPaths(), stream));
    }


}
