package Server.GUI.TreeInterpreter.TreeGUI.Menus;

import Server.Connections.Streams;
import Server.GUI.TreeInterpreter.DirectoryTreeGUI.DirectoryDiskMenu;
import Server.GUI.TreeInterpreter.TreeUtils.Action;
import Server.GUI.TreeInterpreter.TreeUtils.TreeMenu;

import javax.swing.*;
import javax.swing.event.MenuEvent;


public class MoveMenu extends TreeMenu {
    private final JMenu moveMenu;
    private final JDialog dialog;

    public MoveMenu(JTree tree, Streams stream, JDialog dialog, JMenu moveMenu) {
        super(tree, stream);
        this.dialog = dialog;
        this.moveMenu = moveMenu;
    }

    @Override
    public void menuSelected(MenuEvent e) {
        moveMenu.removeAll();
        getStream().executor.submit(new DirectoryDiskMenu(moveMenu, getStream(), dialog, getSelectedPaths(), Action.MOVE));
    }

}
