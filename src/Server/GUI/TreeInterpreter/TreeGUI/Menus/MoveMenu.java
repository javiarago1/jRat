package Server.GUI.TreeInterpreter.TreeGUI.Menus;

import Server.Connections.Streams;
import Server.GUI.TreeInterpreter.TreeUtils.Action;
import Server.GUI.TreeInterpreter.DirectoryTreeGUI.DirectoryDiskMenu;
import Server.GUI.TreeInterpreter.TreeUtils.TreeMenu;

import javax.swing.*;
import javax.swing.event.MenuEvent;


public class MoveMenu extends TreeMenu {
    private final JMenu moveMenu;
    private final JDialog dialog;
    private final Streams stream;

    public MoveMenu(JTree tree, Streams stream, JDialog dialog, JMenu moveMenu) {
        super(tree);
        this.dialog = dialog;
        this.stream = stream;
        this.moveMenu = moveMenu;
    }

    @Override
    public void menuSelected(MenuEvent e) {
        moveMenu.removeAll();
        stream.executor.submit(new DirectoryDiskMenu(moveMenu, stream, dialog, getSelectedPaths(), Action.MOVE));
    }

}
