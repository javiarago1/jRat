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
    protected void readTree(File file) {
        File[] directoriesArray = file.listFiles(fileFilter);
        if (directoriesArray != null) {
            if (directoriesArray.length == 0) getNodesArray().add(new DefaultMutableTreeNode("<NO MORE FOLDERS>"));
            else {
                for (File e : directoriesArray) {
                    System.out.println(file);
                    DefaultMutableTreeNode fatherNode = new DefaultMutableTreeNode(e.getName());
                    fatherNode.add(new DefaultMutableTreeNode("<LOADING DIRECTORY>"));
                    getNodesArray().add(fatherNode);
                }
            }
        }

    }


}
