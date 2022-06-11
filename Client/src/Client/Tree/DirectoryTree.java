package Client.Tree;


import javax.swing.tree.DefaultMutableTreeNode;
import java.io.File;
import java.io.FileFilter;
import java.util.Objects;

public class DirectoryTree extends Tree {

    private final FileFilter fileFilter;

    public DirectoryTree(File rootPath) {
        super(rootPath);
        fileFilter = File::isDirectory;
    }


    @Override
    protected void readTree(File file) {
        File[] arrayFiles = file.listFiles(fileFilter);
        if (arrayFiles != null) {
            if (arrayFiles.length == 0) getNodesArray().add(new DefaultMutableTreeNode("[NO MORE FOLDERS]"));
            else {
                for (File e : arrayFiles) {
                    System.out.println(file);
                    DefaultMutableTreeNode fatherNode = new DefaultMutableTreeNode(e.getName());
                    fatherNode.add(new DefaultMutableTreeNode(""));
                    getNodesArray().add(fatherNode);
                }
            }
        }
    }


}
