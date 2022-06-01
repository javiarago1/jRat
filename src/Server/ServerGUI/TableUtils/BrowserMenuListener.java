package Server.ServerGUI.TableUtils;

import Server.ServerConnections.Server;
import Server.ServerConnections.Streams;
import Server.ServerGUI.GetSYS;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class BrowserMenuListener implements MenuListener {

    private final JTable table;
    private final Server server;
    private final JMenu browserMenu;

    public BrowserMenuListener(JTable table, JMenu browserMenu , Server server){
        this.table=table;
        this.server=server;
        this.browserMenu=browserMenu;
    }

    @Override
    public void menuSelected(MenuEvent e) {
        String selectedSocket = table.getValueAt(table.getSelectedRow(),0).toString();
        Streams stream = GetSYS.getStream(server.getMap(),selectedSocket);
        browserMenu.removeAll();
        assert stream != null;
        new DiskMenuCreator(browserMenu,stream).createMenu();
    }

    @Override
    public void menuDeselected(MenuEvent e) {

    }

    @Override
    public void menuCanceled(MenuEvent e) {

    }
}
