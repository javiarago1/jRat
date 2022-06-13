package Server.GUI.TreeInterpreter.TreeGUI.Menus.Chooser;

import Server.Connections.Streams;

import Server.GUI.TreeInterpreter.TreeUtils.Upload;

import javax.swing.*;


public class UploadFileAction extends Upload {
    public UploadFileAction(JTree tree, Streams stream, JDialog dialog) {
        super(tree, stream, dialog);
        getFileChooser().setDialogTitle("Select files");
        getFileChooser().setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
    }

}
