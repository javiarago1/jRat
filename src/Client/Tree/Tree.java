package Client.Tree;


import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Objects;


public class Tree {
    private final File rootPath;
    private final DefaultMutableTreeNode rootNode;
    private final JTree tree;
    private final ObjectOutputStream output;



    public Tree(File rootPath, ObjectOutputStream output){
        this.output=output;
        this.rootPath=rootPath;
        // Quito el slash con substring para la lectura de JTree
        this.rootNode=new DefaultMutableTreeNode(rootPath.toString());
        tree=new JTree(rootNode);
    }

    private void executeFileRecursion(File file,DefaultMutableTreeNode treeNode){
        File[]arrayFiles= file.listFiles();
        if (arrayFiles==null)return;
        for (File e:arrayFiles){
            if (e.isDirectory()){

                System.out.println(file);
               // if (e.getName().equals("Center"))break;
                DefaultMutableTreeNode fatherNode = new DefaultMutableTreeNode(e.getName());
                treeNode.add(fatherNode);
                executeFileRecursion(e,fatherNode);
            }
            else {

                treeNode.add(new DefaultMutableTreeNode(e.getName()));
                System.out.println(e.getName());
                if (e.getName().equals("Center"))break;
            }
        }

    }

    public void start(){
        executeFileRecursion(rootPath, rootNode);
        sendObject();
    }


    private void sendObject(){
        try {
            output.writeObject(tree);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}