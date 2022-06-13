package Server.GUI.TreeInterpreter.TreeUtils;


import Server.Connections.Streams;
import Server.GUI.TreeInterpreter.FileManager.UploadFiles;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Arrays;


public abstract class Upload extends TreeMenu {

    private final JDialog dialog;
    private final JFileChooser fileChooser = new JFileChooser();

    public Upload(JTree tree, Streams stream, JDialog dialog) {
        super(tree, stream);
        this.dialog = dialog;
        this.fileChooser.setMultiSelectionEnabled(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int returnVal = fileChooser.showOpenDialog(dialog);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File[] selectedFiles = fileChooser.getSelectedFiles();
            if ((selectedFiles == null)) {
                System.out.println("File exception");
            } else {
                System.out.println("Lista de selección -> " + Arrays.toString(selectedFiles));
                getStream().executor.submit(new UploadFiles(Arrays.asList(selectedFiles), getSelectedPaths(), getStream()));
            }
        }
    }

    public JFileChooser getFileChooser() {
        return fileChooser;
    }
}
