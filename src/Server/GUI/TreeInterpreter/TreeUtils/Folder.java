package Server.GUI.TreeInterpreter.TreeUtils;

import Server.Connections.Streams;

import javax.swing.*;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
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
        StringBuilder value = new StringBuilder();
        Object[] elements = treePath.getPath();
        for (Object element : elements) {
            value.append(element).append("\\");
        }

        DefaultMutableTreeNode node = (DefaultMutableTreeNode) treePath.getLastPathComponent();
        DefaultMutableTreeNode blankNode = (DefaultMutableTreeNode) node.getFirstChild();
        DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
        if (blankNode.toString().equals("")) {
            model.removeNodeFromParent(blankNode);
        }

        List<?> receivedList = requestTree(value.toString());

        System.out.println(receivedList);

        for (Object e : receivedList) {
            node.add((DefaultMutableTreeNode) e);
        }

        System.out.println("Other way" + node);


    }

    @Override
    public void treeWillCollapse(TreeExpansionEvent event) {

    }

    public Streams getStream() {
        return stream;
    }
}
