package ServerGUI;

import com.formdev.flatlaf.FlatDarculaLaf;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import java.awt.*;


public class TreeGUI {

    private final JTree JT;
    private final JFrame frame = new JFrame("Tree Directory");
    private StringBuilder value;

    public TreeGUI(JTree tree) {
        JT = tree;
        loadStyle();
        addFrame();
        addComponents();
        startFrame();
    }

    private void startFrame(){
        frame.setVisible(true);
    }

    private void loadStyle(){
        FlatDarculaLaf.setup();
        UIManager.put( "Tree.background", Color.RED );
        DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) JT.getCellRenderer();
        Icon closedIcon = new ImageIcon("src/ServerGUI/TreeIMG/folder.png");
        Icon openIcon = new ImageIcon("src/ServerGUI/TreeIMG/folder.png");
        Icon leafIcon = new ImageIcon("src/ServerGUI/TreeIMG/file.png");
        renderer.setClosedIcon(closedIcon);
        renderer.setOpenIcon(openIcon);
        renderer.setLeafIcon(leafIcon);


    }

    private void addFrame() {
        frame.setPreferredSize(new Dimension(400, 300));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationByPlatform(true);

    }

    private void addComponents() {
        JT.setShowsRootHandles(true);
        createListener();
        JScrollPane scrollPane = new JScrollPane(JT);
        frame.add(scrollPane);

    }

    private void createListener() {
        JT.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                    JT.getLastSelectedPathComponent();
            if (node == null) return;
            Object nodeInfo = node.getUserObject();
        });
    }


}