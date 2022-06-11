package Server.GUI.TreeInterpreter.TreeUtils;

import Server.Connections.Streams;

import javax.swing.*;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.io.File;
import java.util.List;

public abstract class Folder implements TreeWillExpandListener {

    private final Streams stream;
    private final JTree tree;

    public Folder(JTree tree, Streams stream) {
        this.tree = tree;
        this.stream = stream;
    }

    public abstract List<?> requestTree(String value);

    @Override
    public void treeWillExpand(TreeExpansionEvent event) {
        TreePath treePath = event.getPath();

        DefaultMutableTreeNode selectedFolder = (DefaultMutableTreeNode) treePath.getLastPathComponent();
        DefaultMutableTreeNode blankNode = (DefaultMutableTreeNode) selectedFolder.getFirstChild();
        DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
        if (blankNode.toString().equals("")) {
            model.removeNodeFromParent(blankNode);
        }

        File path = TreeMenu.getSelectedPath(treePath);
        List<?> receivedList = requestTree(path.toString());

        System.out.println("Here -> " + receivedList);

        for (Object e : receivedList) {
            selectedFolder.add((DefaultMutableTreeNode) e);
        }

    }

    @Override
    public void treeWillCollapse(TreeExpansionEvent event) {

    }

    public Streams getStream() {
        return stream;
    }
}
