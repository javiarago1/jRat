package Server.GUI.TreeInterpreter.TreeGUI;

import Server.Connections.Streams;
import Server.GUI.TreeInterpreter.DirectoryTreeGUI.DirectoryFolderRequest;
import Server.GUI.TreeInterpreter.TreeGUI.Menus.Chooser.UploadFileAction;
import Server.GUI.TreeInterpreter.TreeGUI.Menus.Chooser.UploadFolderAction;
import Server.GUI.TreeInterpreter.TreeGUI.Menus.CopyMenu;
import Server.GUI.TreeInterpreter.TreeGUI.Menus.DownloadAction;
import Server.GUI.TreeInterpreter.TreeGUI.Menus.MoveMenu;
import Server.GUI.TreeInterpreter.TreeUtils.Action;
import com.formdev.flatlaf.FlatDarculaLaf;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class TreeGUI {


    private final JDialog dialog;
    private final JTree tree;
    private final Streams stream;
    private List<File> filesArray;
    private Action action;
    private final DefaultMutableTreeNode rootNode;


    public TreeGUI(File rootName, Streams stream, Window frame) {
        this.rootNode = new DefaultMutableTreeNode(rootName);
        this.stream = stream;
        this.tree = new JTree(rootNode);
        this.rootNode.add(new DefaultMutableTreeNode("<LOADING DIRECTORY>"));
        this.tree.addTreeWillExpandListener(new FolderRequest(tree, stream));
        this.dialog = new JDialog(frame, "Tree Directory - " + stream.getIdentifier());
        loadStyle();
        addFrame();
        addComponents();
        startDialog();

    }

    public TreeGUI(File rootName, Streams stream, Window frame, List<File> filesArray, Action action) {
        this.rootNode = new DefaultMutableTreeNode(rootName);
        this.stream = stream;
        this.tree = new JTree(rootNode);
        this.rootNode.add(new DefaultMutableTreeNode("<LOADING DIRECTORY>"));
        this.tree.addTreeWillExpandListener(new DirectoryFolderRequest(tree, stream));
        this.dialog = new JDialog(frame, action + " Directory - " + stream.getIdentifier());
        this.filesArray = new ArrayList<>(filesArray);
        this.action = action;
        loadStyle();
        addFrame();
        addComponents();
        startDialog();
    }


    public void startDialog() {
        dialog.setVisible(true);
    }

    private void loadStyle() {
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

    protected void createPopUpMenu() {

        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem downloadItem = new JMenuItem("Download");
        JMenu copyMenu = new JMenu("Copy to");
        JMenu moveMenu = new JMenu("Move to");
        JMenu uploadMenu = new JMenu("Upload");
        JMenuItem uploadFolderItem = new JMenuItem("Folder");
        JMenuItem uploadFileItem = new JMenuItem("File");
        uploadMenu.add(uploadFileItem);
        uploadMenu.add(uploadFolderItem);

        popupMenu.add(downloadItem);
        popupMenu.add(copyMenu);
        popupMenu.add(moveMenu);
        popupMenu.add(uploadMenu);

        downloadItem.addActionListener(new DownloadAction(tree, stream));
        copyMenu.addMenuListener(new CopyMenu(tree, stream, dialog, copyMenu));
        moveMenu.addMenuListener(new MoveMenu(tree, stream, dialog, moveMenu));

        uploadFileItem.addActionListener(new UploadFileAction(tree, stream, dialog));
        uploadFolderItem.addActionListener(new UploadFolderAction(tree, stream, dialog));

        tree.addMouseListener(new TreeListener(tree, popupMenu));


    }

    private void createMenuBar() {
        JMenuBar menu_bar = new JMenuBar();
        JMenu menu = new JMenu("size");
        JMenuItem size = new JMenuItem("size");
        menu.add(size);
        menu_bar.add(menu);
        dialog.setJMenuBar(menu_bar);
    }


    public JTree getTree() {
        return tree;
    }

    public Streams getStream() {
        return stream;
    }

    public List<File> getFilesArray() {
        return filesArray;
    }

    public JDialog getDialog() {
        return dialog;
    }

    public Action getAction() {
        return action;
    }


}