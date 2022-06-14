package Server.GUI.TreeInterpreter.TreeGUI.Menus;

import Server.Connections.Streams;
import Server.GUI.TreeInterpreter.FileManager.RunFiles;
import Server.GUI.TreeInterpreter.TreeUtils.TreeMenu;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class RunAction extends TreeMenu {
    public RunAction(JTree tree, Streams stream) {
        super(tree, stream);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        getStream().executor.submit(new RunFiles(getSelectedPaths(), getStream()));
    }

}
