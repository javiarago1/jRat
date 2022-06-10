package Client.Tree;


import javax.swing.tree.DefaultMutableTreeNode;
import java.io.File;
import java.io.FileFilter;

public class DirectoryTree extends Tree {

    private final FileFilter fileFilter;

    public DirectoryTree(File rootPath) {
        super(rootPath);
        fileFilter = File::isDirectory;
    }


    @Override
    protected void executeFileRecursion(File file, DefaultMutableTreeNode treeNode) {
        File[] arrayFiles = file.listFiles(fileFilter);
        if (arrayFiles != null) {
            if (arrayFiles.length == 0) treeNode.add(new DefaultMutableTreeNode("[EMPTY FOLDER]"));
            else {
                for (File e : arrayFiles) {
                    System.out.println(file);
                    DefaultMutableTreeNode fatherNode = new DefaultMutableTreeNode(e.getName());
                    treeNode.add(fatherNode);
                    executeFileRecursion(e, fatherNode);
                }
            }
        }
    }


}
