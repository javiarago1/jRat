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


        SwingWorker<Void, Void> swingWorker = new SwingWorker<>() {
            List<?> receivedList;

            @Override
            protected Void doInBackground() {
                File path = TreeMenu.getSelectedPath(treePath);
                receivedList = requestTree(path.toString());
                return null;
            }

            @Override
            protected void done() {
                for (Object e : receivedList) {
                    selectedFolder.add((DefaultMutableTreeNode) e);
                }
                if (blankNode.toString().equals("<LOADING DIRECTORY>")) {
                    model.removeNodeFromParent(blankNode);
                }
                model.reload(selectedFolder);
                System.out.println("Cargando archivos " + receivedList);
            }
        };
        stream.executor.submit(swingWorker);

    }

    @Override
    public void treeWillCollapse(TreeExpansionEvent event) {
        TreePath treePath = event.getPath();
        DefaultMutableTreeNode selectedFolder = (DefaultMutableTreeNode) treePath.getLastPathComponent();
        selectedFolder.removeAllChildren();
        selectedFolder.add(new DefaultMutableTreeNode("<LOADING DIRECTORY>"));
    }

    public Streams getStream() {
        return stream;
    }
}
