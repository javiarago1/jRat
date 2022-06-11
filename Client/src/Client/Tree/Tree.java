package Client.Tree;


import javax.swing.tree.DefaultMutableTreeNode;
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Tree {
    private final File rootPath;
    private final ArrayList<DefaultMutableTreeNode> nodesArray = new ArrayList<>();

    private final FileFilter directoryFilter;
    private final FileFilter fileFilter;

    public Tree(File rootPath) {
        this.rootPath = rootPath;
        this.directoryFilter = File::isDirectory;
        this.fileFilter = File::isFile;
    }

    protected void readTree(File file) {
        File[] filesArray = file.listFiles(directoryFilter);
        File[] directoryArray = file.listFiles(fileFilter);
        if (filesArray != null) {
            if (Objects.requireNonNull(file.listFiles()).length == 0)
                nodesArray.add(new DefaultMutableTreeNode("<EMPTY FOLDER>"));
            else {
                for (File e : filesArray) {
                    System.out.println(file);
                    DefaultMutableTreeNode fatherNode = new DefaultMutableTreeNode(e.getName());
                    fatherNode.add(new DefaultMutableTreeNode("<LOADING DIRECTORY>"));
                    nodesArray.add(fatherNode);
                }
            }
        }
        if (directoryArray != null) {
            for (File e : directoryArray) {
                nodesArray.add(new DefaultMutableTreeNode(e.getName()));
                System.out.println(e.getName());
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