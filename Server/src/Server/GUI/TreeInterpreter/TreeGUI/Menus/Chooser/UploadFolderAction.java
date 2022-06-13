package Server.GUI.TreeInterpreter.TreeGUI.Menus.Chooser;

import Server.Connections.Streams;
import Server.GUI.TreeInterpreter.TreeUtils.Upload;

import javax.swing.*;


public class UploadFolderAction extends Upload {

    public UploadFolderAction(JTree tree, Streams stream, JDialog dialog) {
        super(tree, stream, dialog);
        getFileChooser().setDialogTitle("Select folders");
        getFileChooser().setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    }

}
