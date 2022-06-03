package Server.ServerGUI.TableUtils;

import Server.ServerConnections.Streams;
import Server.ServerGUI.GetSYS;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

public class TablePopUpListener extends MouseAdapter {

    private final JTable table;
    private final ConcurrentHashMap<Socket,Streams> map;
    private final JMenu browserMenu;


    public TablePopUpListener(JTable table, ConcurrentHashMap<Socket,Streams> map,JMenu browserMenu){
        this.browserMenu=browserMenu;
        this.table=table;
        this.map=map;


    }
    @Override
    public void mousePressed(MouseEvent e) {
        Point point = e.getPoint();
        int currentRow = table.rowAtPoint(point);
        table.setRowSelectionInterval(currentRow, currentRow);
        Streams stream = GetSYS.getStream(map, table);
        assert stream != null;
        browserMenu.setEnabled(!stream.isWorking());

    }





}
