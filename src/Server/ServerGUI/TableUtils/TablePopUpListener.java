package Server.ServerGUI.TableUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TablePopUpListener extends MouseAdapter {

    private final JTable table;

    public TablePopUpListener(JTable table){
        this.table=table;

    }
    @Override
    public void mousePressed(MouseEvent e) {
        Point point = e.getPoint();
        int currentRow = table.rowAtPoint(point);
        table.setRowSelectionInterval(currentRow, currentRow);

    }





}
