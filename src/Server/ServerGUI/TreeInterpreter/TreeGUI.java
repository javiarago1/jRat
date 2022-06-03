package Server.ServerGUI.TreeInterpreter;

import Server.ServerGUI.MainClass;
import com.formdev.flatlaf.FlatDarculaLaf;
import com.sun.tools.javac.Main;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

import java.awt.*;


public class TreeGUI {


    private JDialog dialog;
    private JTree JT;

    public TreeGUI(JTree tree) {
        JT=tree;
        dialog= new JDialog(MainClass.gui.getFrame(),"Tree Directory");
        loadStyle();
        addFrame();
        addComponents();
        startDialog();
    }

    public void startDialog(){
        dialog.setVisible(true);
    }

    private void loadStyle(){
        FlatDarculaLaf.setup();
    }

    private void addFrame() {
        dialog.setSize(new Dimension(400, 300));
        dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dialog.setLocationRelativeTo(null);

    }



    private void addComponents() {
        JT.setShowsRootHandles(true);
        createListener();
        createMenuBar();
        JScrollPane scrollPane = new JScrollPane(JT);
        dialog.add(scrollPane);

    }

    private void createMenuBar(){
        JMenuBar menu_bar = new JMenuBar();
        JMenu menu = new JMenu("size");
        JMenuItem size = new JMenuItem("size");
        menu.add(size);
        menu_bar.add(menu);
        dialog.setJMenuBar(menu_bar);
    }

    private void createListener() {
        JT.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                    JT.getLastSelectedPathComponent();
            if (node == null) return;
            Object nodeInfo = node.getUserObject();
            System.out.println(nodeInfo);
        });
    }


}