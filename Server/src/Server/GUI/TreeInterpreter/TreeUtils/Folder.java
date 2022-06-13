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
        SwingWorker<Void, Void> requestWorker = new SwingWorker<>() {
            private final TreePath treePath = event.getPath();
            private final DefaultMutableTreeNode selectedFolder = (DefaultMutableTreeNode) treePath.getLastPathComponent();
            private final DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
            private List<?> receivedList;

            @Override
            protected Void doInBackground() {
                File path = TreeMenu.getSelectedPath(treePath);
                receivedList = requestTree(path.toString());
                return null;
            }

            @Override
            protected void done() {
                selectedFolder.removeAllChildren();
                selectedFolder.add(new DefaultMutableTreeNode("<LOADING DIRECTORY>"));
                model.reload(selectedFolder);
                DefaultMutableTreeNode blankNode = (DefaultMutableTreeNode) selectedFolder.getFirstChild();
                for (Object e : receivedList) {
                    model.insertNodeInto((DefaultMutableTreeNode) e, selectedFolder, selectedFolder.getChildCount());
                }
                if (blankNode.toString().equals("<LOADING DIRECTORY>")) {
                    model.removeNodeFromParent(blankNode);
                }

                System.out.println("Cargando archivos " + receivedList);
            }
        };
        stream.executor.submit(requestWorker);

    }

    @Override
    public void treeWillCollapse(TreeExpansionEvent event) {

    }

    public Streams getStream() {
        return stream;
    }
}
