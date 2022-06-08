package Server.ServerGUI.TreeInterpreter.PopUp;

import Server.ServerConnections.Streams;
import Server.ServerGUI.TreeInterpreter.FileManager.DownloadFiles;

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
    private final Action action;

    public MenuItemListener(JTree tree, Streams stream, Action action){
        this.action=action;
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
            for (File ex:filesArray){
                System.out.println(ex);
            }
            if (action== Action.DOWNLOAD){
                stream.executor.submit(new DownloadFiles(filesArray,stream));
            } else if (action== Action.COPY || action== Action.MOVE){


            }

        }
    }
}