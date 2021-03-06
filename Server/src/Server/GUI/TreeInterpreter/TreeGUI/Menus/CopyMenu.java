package Server.GUI.TreeInterpreter.TreeGUI.Menus;


import Server.Connections.Streams;
import Server.GUI.TreeInterpreter.DirectoryTreeGUI.DirectoryDiskMenu;
import Server.GUI.TreeInterpreter.TreeUtils.Action;
import Server.GUI.TreeInterpreter.TreeUtils.TreeMenu;

import javax.swing.*;
import javax.swing.event.MenuEvent;


public class CopyMenu extends TreeMenu {

    private final JDialog dialog;
    private final JMenu copyItem;

    public CopyMenu(JTree tree, Streams stream, JDialog dialog, JMenu copyItem) {
        super(tree, stream);
        this.dialog = dialog;
        this.copyItem = copyItem;
    }

    @Override
    public void menuSelected(MenuEvent e) {
        copyItem.removeAll();
        getStream().executor.submit(new DirectoryDiskMenu(copyItem, getStream(), dialog, getSelectedPaths(), Action.COPY));
    }


}
