package Client.Tree;


import javax.swing.tree.DefaultMutableTreeNode;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class Tree {
    private final File rootPath;
    private final ArrayList<DefaultMutableTreeNode> nodesArray = new ArrayList<>();

    public Tree(File rootPath) {
        this.rootPath = rootPath;
    }

    protected void readTree(File file) {
        File[] arrayFiles = file.listFiles();
        if (arrayFiles != null) {
            if (arrayFiles.length == 0) nodesArray.add(new DefaultMutableTreeNode("[EMPTY FOLDER]"));
            else {
                for (File e : arrayFiles) {
                    if (e.isDirectory()) {
                        System.out.println(file);
                        DefaultMutableTreeNode fatherNode = new DefaultMutableTreeNode(e.getName());
                        fatherNode.add(new DefaultMutableTreeNode(""));
                        nodesArray.add(fatherNode);
                    } else {
                        nodesArray.add(new DefaultMutableTreeNode(e.getName()));
                        System.out.println(e.getName());
                    }
                }
            }
        }
    }

    public List<DefaultMutableTreeNode> getTree() {
        readTree(rootPath);
        return nodesArray;
    }

    public ArrayList<DefaultMutableTreeNode> getNodesArray() {
        return nodesArray;
    }
}