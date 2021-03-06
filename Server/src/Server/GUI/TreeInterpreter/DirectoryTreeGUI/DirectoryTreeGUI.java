package Server.GUI.TreeInterpreter.DirectoryTreeGUI;


import Server.Connections.Streams;
import Server.GUI.TreeInterpreter.DirectoryTreeGUI.Menus.MoveAction;
import Server.GUI.TreeInterpreter.DirectoryTreeGUI.Menus.PasteAction;
import Server.GUI.TreeInterpreter.DirectoryTreeListener;
import Server.GUI.TreeInterpreter.TreeGUI.TreeGUI;
import Server.GUI.TreeInterpreter.TreeUtils.Action;

import javax.swing.*;
import javax.swing.tree.TreeSelectionModel;
import java.io.File;
import java.util.List;


public class DirectoryTreeGUI extends TreeGUI {

    public DirectoryTreeGUI(File rootName, Streams stream, JDialog dialog, List<File> filesToCopy, Action action) {
        super(rootName, stream, dialog, filesToCopy, action);
    }



    @Override
    protected void createPopUpMenu() {
        JPopupMenu popupMenu = new JPopupMenu();
        switch (getAction()) {
            case COPY -> {
                JMenuItem pasteItem = new JMenuItem("Paste");
                popupMenu.add(pasteItem);
                pasteItem.addActionListener(new PasteAction(getTree(), getStream(), getFilesArray()));
            }
            case MOVE -> {
                getTree().getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
                JMenuItem moveItem = new JMenuItem("Move");
                popupMenu.add(moveItem);
                moveItem.addActionListener(new MoveAction(getTree(), getStream(), getFilesArray()));
            }
        }
        JMenuItem exitItem = new JMenuItem("Exit");
        popupMenu.add(exitItem);
        exitItem.addActionListener(e -> getDialog().dispose());
        getTree().addMouseListener(new DirectoryTreeListener(getTree(), popupMenu));
    }


}
