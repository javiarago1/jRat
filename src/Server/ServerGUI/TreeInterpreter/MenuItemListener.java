package Server.ServerGUI.TreeInterpreter;

import Server.ServerConnections.Streams;

import javax.swing.*;
import javax.swing.tree.TreePath;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

public class MenuItemListener implements ActionListener {


    private final JTree tree;
    private final Streams stream;
    private final ArrayList<File> filesArray = new ArrayList<>();

    public MenuItemListener(JTree tree, Streams stream){
        this.tree=tree;
        this.stream=stream;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (tree.getSelectionPaths()!=null) {
            filesArray.clear();
            TreePath[] treePath = tree.getSelectionPaths();
            for (TreePath a : treePath) {
                StringBuilder value = new StringBuilder();
                Object[] elements = a.getPath();
                for (Object element : elements) {
                    value.append(element).append("\\");
                }
                filesArray.add(new File(value.toString()));
            }
            stream.executor.submit(new FileManager(filesArray,stream,Action.DOWNLOAD));
        }
    }
}
