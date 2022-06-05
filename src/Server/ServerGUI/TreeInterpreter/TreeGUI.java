package Server.ServerGUI.TreeInterpreter;

import Server.ServerConnections.Streams;
import Server.ServerGUI.MainClass;
import com.formdev.flatlaf.FlatDarculaLaf;

import javax.swing.*;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.util.Arrays;

public class TreeGUI {


    private final JDialog dialog;
    private final JTree tree;
    private Streams stream;


    public TreeGUI(JTree tree, String identifier, Streams stream) {
        this.stream=stream;
        this.tree=tree;
        dialog= new JDialog(MainClass.gui.getFrame(),"Tree Directory -"+identifier);
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
        tree.setShowsRootHandles(true);
        createMenuBar();
        createPopUpMenu();
        JScrollPane scrollPane = new JScrollPane(tree);
        dialog.add(scrollPane);

    }

    private void createPopUpMenu(){

        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem downloadItem = new JMenuItem("Download");
        JMenuItem copyItem = new JMenuItem("Copy");
        JMenuItem moveItem = new JMenuItem("Move");

        downloadItem.addActionListener(new MenuItemListener(tree,stream));

        popupMenu.add(downloadItem);
        popupMenu.add(copyItem);
        popupMenu.add(moveItem);




        tree.addMouseListener(new PopUpTreeListener(tree, popupMenu));
    }

    private void createMenuBar(){
        JMenuBar menu_bar = new JMenuBar();
        JMenu menu = new JMenu("size");
        JMenuItem size = new JMenuItem("size");
        menu.add(size);
        menu_bar.add(menu);
        dialog.setJMenuBar(menu_bar);
    }




}