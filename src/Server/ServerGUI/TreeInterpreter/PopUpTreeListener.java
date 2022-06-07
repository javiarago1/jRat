package Server.ServerGUI.TreeInterpreter;


import javax.swing.*;
import javax.swing.tree.TreePath;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


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
            assert treePath != null;
            boolean emptyFolder = checkEmpty(treePath);
            if (!emptyFolder) {
                if (treePath.length == 1) {
                    int row = tree.getClosestRowForLocation(e.getX(), e.getY());
                    tree.setSelectionRow(row);
                }
                popupMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        }
    }

    private boolean checkEmpty(TreePath[] treePath){
        for (TreePath e:treePath){
            Object[]elements = e.getPath();
            if (elements[elements.length-1].toString().equals("[EMPTY FOLDER]")){
                return true;
            }
        }
        return false;
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
