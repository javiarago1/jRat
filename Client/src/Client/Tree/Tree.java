package Client.Tree;


import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.io.File;


public class Tree {
    private final File rootPath;
    private final DefaultMutableTreeNode rootNode;
    private final JTree tree;


    public Tree(File rootPath) {
        this.rootPath = rootPath;
        this.rootNode = new DefaultMutableTreeNode(rootPath.toString());
        tree = new JTree(rootNode);
    }

    protected void executeFileRecursion(File file, DefaultMutableTreeNode treeNode) {
        File[] arrayFiles = file.listFiles();
        if (arrayFiles != null) {
            if (arrayFiles.length == 0) treeNode.add(new DefaultMutableTreeNode("[EMPTY FOLDER]"));
            else {
                for (File e : arrayFiles) {
                    if (e.isDirectory()) {
                        System.out.println(file);
                        DefaultMutableTreeNode fatherNode = new DefaultMutableTreeNode(e.getName());
                        treeNode.add(fatherNode);
                        executeFileRecursion(e, fatherNode);
                    } else {
                        treeNode.add(new DefaultMutableTreeNode(e.getName()));
                        System.out.println(e.getName());
                    }
                }
            }
        }
    }

    public JTree getTree() {
        executeFileRecursion(rootPath, rootNode);
        return tree;
    }


}