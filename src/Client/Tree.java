package Client;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Tree implements Runnable{
    private final File rootPath;
    private final DefaultMutableTreeNode rootNode;
    private final JTree JT;
    private final Socket socket;


    public Tree(File rootPath, Socket socket){
        this.socket=socket;
        this.rootPath=rootPath;
        this.rootNode=new DefaultMutableTreeNode(rootPath.getName());
        this.JT=new JTree(rootNode);
    }

    private void executeFileRecursion(File file,DefaultMutableTreeNode treeNode){
        File[]arrayFiles= file.listFiles();
        if (arrayFiles==null)return;
        for (File e:arrayFiles){
            if (e.isDirectory()){
                System.out.println(file);
                DefaultMutableTreeNode fatherNode = new DefaultMutableTreeNode(e.getName());
                treeNode.add(fatherNode);
                executeFileRecursion(e,fatherNode);
            }
            else {
                treeNode.add(new DefaultMutableTreeNode(e.getName()));
                System.out.println(e.getName());
            }
        }

    }


    private void sendObject(){
        try {
            ObjectOutputStream objectWriter = new ObjectOutputStream(socket.getOutputStream());
            objectWriter.writeObject(JT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        executeFileRecursion(rootPath, rootNode);
        sendObject();

    }
}