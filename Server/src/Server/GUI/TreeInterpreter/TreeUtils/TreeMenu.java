package Server.GUI.TreeInterpreter.TreeUtils;

import Server.Connections.Streams;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.tree.TreePath;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public abstract class TreeMenu implements ActionListener, MenuListener {

    private final JTree tree;
    private final List<File> filesArray = new ArrayList<>();
    private final Streams stream;

    public TreeMenu(JTree tree, Streams stream) {
        this.tree = tree;
        this.stream = stream;
    }


    protected static File getSelectedPath(TreePath treePath) {
        if (treePath != null) {
            StringBuilder value = new StringBuilder();
            Object[] elements = treePath.getPath();
            for (Object element : elements) {
                value.append(element).append("\\");
            }
            return new File(value.toString());
        }
        return null;
    }

    protected File getSelectedPath() {
        if (tree.getSelectionPath() != null) {
            TreePath treePath = tree.getSelectionPath();
            StringBuilder value = new StringBuilder();
            Object[] elements = treePath.getPath();
            for (Object element : elements) {
                value.append(element).append("\\");
            }
            return new File(value.toString());
        }
        return null;
    }

    protected List<File> getSelectedPaths() {
        if (tree.getSelectionPaths() != null) {
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
            return filesArray;
        }
        return null;
    }


    @Override
    public void menuSelected(MenuEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void menuDeselected(MenuEvent e) {

    }

    @Override
    public void menuCanceled(MenuEvent e) {

    }

    public Streams getStream() {
        return stream;
    }
}
