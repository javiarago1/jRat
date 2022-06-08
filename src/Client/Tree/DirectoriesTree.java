package Client.Tree;

import Server.ServerGUI.TreeInterpreter.TreeGUI;

import javax.swing.tree.DefaultMutableTreeNode;
import java.io.File;
import java.io.FileFilter;

public class DirectoriesTree extends Tree {

    private final FileFilter fileFilter;

    public DirectoriesTree(File rootPath) {
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

    public static void main(String[] args) {
        new TreeGUI(new DirectoriesTree(new File("C:\\Users\\JAVIER\\Desktop")).getTree(), "Identificador");
    }


}
