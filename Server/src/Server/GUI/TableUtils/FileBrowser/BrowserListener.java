package Server.GUI.TableUtils.FileBrowser;

import Server.Connections.Streams;
import Server.GUI.TableUtils.Configuration.GetSYS;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

public class BrowserListener implements MenuListener {

    private final JMenu browserMenu;
    private final JTable table;
    private final ConcurrentHashMap<Socket, Streams> map;

    public BrowserListener(JTable table, JMenu browserMenu, ConcurrentHashMap<Socket, Streams> map) {
        this.browserMenu = browserMenu;
        this.table = table;
        this.map = map;
    }

    @Override
    public void menuSelected(MenuEvent e) {
        Streams stream = GetSYS.getStream(map,table);
        browserMenu.removeAll();
        assert stream != null;
        stream.executor.submit(new DiskMenu(browserMenu, stream));
    }

    @Override
    public void menuDeselected(MenuEvent e) {

    }

    @Override
    public void menuCanceled(MenuEvent e) {

    }
}
