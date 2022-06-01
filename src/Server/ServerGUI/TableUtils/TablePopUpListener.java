package Server.ServerGUI.TableUtils;

import Server.ServerConnections.Server;
import Server.ServerConnections.Streams;
import Server.ServerGUI.GetSYS;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

public class TablePopUpListener extends MouseAdapter {

    private JTable table;
    private Server server;
    private JMenu browserMenu;
    public TablePopUpListener(JTable table,JMenu browserMenu ,Server server){
        this.table=table;
        this.server=server;
        this.browserMenu=browserMenu;
    }
    @Override
    public void mousePressed(MouseEvent e) {
        Point point = e.getPoint();
        int currentRow = table.rowAtPoint(point);
        table.setRowSelectionInterval(currentRow, currentRow);
        String selectedSocket = table.getValueAt(currentRow,0).toString();
        Streams stream = GetSYS.getStream(server.getMap(),selectedSocket);
        new DiskMenuCreator(browserMenu,stream).requestDisks();


    }





}
