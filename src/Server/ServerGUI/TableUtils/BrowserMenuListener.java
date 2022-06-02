package Server.ServerGUI.TableUtils;

import Server.ServerConnections.Streams;
import Server.ServerGUI.GetSYS;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

public class BrowserMenuListener implements MenuListener {

    private final JMenu browserMenu;
    private final JTable table;
    private final ConcurrentHashMap<Socket,Streams> map;

    public BrowserMenuListener(JTable table, JMenu browserMenu , ConcurrentHashMap<Socket,Streams> map){
        this.browserMenu=browserMenu;
        this.table=table;
        this.map=map;

    }

    @Override
    public void menuSelected(MenuEvent e) {
        Streams stream = GetSYS.getStream(map,table);
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
