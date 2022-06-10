package Server.GUI.TreeInterpreter.TreeGUI;


import javax.swing.*;
import javax.swing.tree.TreePath;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class TreeListener implements MouseListener {

    private final JTree tree;
    private final JPopupMenu popupMenu;

    public TreeListener(JTree tree, JPopupMenu popupMenu) {
        this.tree = tree;
        this.popupMenu = popupMenu;

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (SwingUtilities.isRightMouseButton(e) && tree.getSelectionPaths() != null) {
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


    protected boolean checkEmpty(TreePath[] treePath) {
        for (TreePath e : treePath) {
            Object[] elements = e.getPath();
            if (elements[elements.length - 1].toString().equals("[EMPTY FOLDER]")) {
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

    public JTree getTree() {
        return tree;
    }


}
