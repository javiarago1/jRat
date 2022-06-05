package Server.ServerGUI.TreeInterpreter;

import javax.swing.*;
import javax.swing.tree.TreePath;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class PopUpTreeListener implements MouseListener {

    private final JTree tree;
    private final JPopupMenu popupMenu;

    public PopUpTreeListener(JTree tree,JPopupMenu popupMenu){
        this.tree=tree;
        this.popupMenu=popupMenu;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (SwingUtilities.isRightMouseButton(e)) {
            TreePath[] treePath = tree.getSelectionPaths();
                if (treePath != null && treePath.length==1){
                    int row = tree.getClosestRowForLocation(e.getX(), e.getY());
                    tree.setSelectionRow(row);
                }
                popupMenu.show(e.getComponent(), e.getX(), e.getY());
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

}
