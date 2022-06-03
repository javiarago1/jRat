package Server.ServerGUI.TreeInterpreter;

import com.formdev.flatlaf.FlatDarculaLaf;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

import java.awt.*;


public class TreeGUI {

    private final JTree JT;
    private JDialog dialog;


    public TreeGUI(JFrame frame,JTree tree) {
        dialog= new JDialog(frame,"Tree Directory");
        JT = tree;
        loadStyle();
        addFrame();
        addComponents();
        startFrame();
    }

    private void startFrame(){
        dialog.setVisible(true);
    }

    private void loadStyle(){
        FlatDarculaLaf.setup();
    }

    private void addFrame() {
        dialog.setPreferredSize(new Dimension(400, 300));
        dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dialog.pack();
        dialog.setLocationByPlatform(true);

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