package Server.ServerGUI;

import Server.ServerConnections.Server;
import Server.ServerGUI.TableUtils.StateColumnRenderer;
import Server.ServerGUI.TableUtils.TableModel;
import Server.ServerGUI.TableUtils.TablePopUpListener;
import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;


public class jRatGUI {

    private final String[] column = {"IP", "Country", "Tag", "Username", "Operating System", "Status"};
    private JPanel panel;
    private JTable connectionTable;
    private JFrame frame;
    private JPopupMenu popupMenu;
    private Server server;


    public jRatGUI(Server server) {
        this.server=server;
        loadStyle();
        setUpFrame();
        addComps();
    }


    private void setUpFrame() {
        frame = new JFrame("jRat Interface");
        frame.setSize(new Dimension(800, 400));
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
    }

    private void loadStyle() {
        FlatDarkLaf.setup();
        UIManager.put("Component.focusedBorderColor", new Color(55, 55, 55));
    }


    private void addJMenu(){
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("size");
        JMenuItem menuItem = new JMenuItem("size");
        menu.add(menuItem);
        menuBar.add(menu);
        frame.setJMenuBar(menuBar);
    }

    private void addJPanel(){
        panel = new JPanel();
        panel.setLayout(null);
    }

    private void addComps() {
        // Menu
        addJMenu();
        //
        // PopUp Menu
        addPopUpMenu();
        //
        // Panel
        addJPanel();
        //
        // JTable
        setupTable();
        //
        frame.setVisible(true);
    }

    private JMenu browserMenu;

    private void addPopUpMenu(){
        popupMenu = new JPopupMenu();
        browserMenu = new JMenu("File Manager");
        popupMenu.add(browserMenu);

    }

    private void addTable() {
        TableModel tableModel = new TableModel(null, column);
        connectionTable = new JTable(tableModel);
        JScrollPane tableScroll = new JScrollPane(connectionTable);
        connectionTable.setFocusable(false);
        tableScroll.setBounds(0, 0, 784, 780);
        connectionTable.setComponentPopupMenu(popupMenu);
        connectionTable.addMouseListener(new TablePopUpListener(connectionTable, browserMenu,server));
        panel.add(tableScroll);
        frame.add(panel);
    }

    private void setupTable() {
        addTable();
        styleTable();
    }

    private void styleTable() {
        StateColumnRenderer columnRenderer = new StateColumnRenderer();

        DefaultTableCellRenderer alignRenderer = new DefaultTableCellRenderer();
        alignRenderer.setHorizontalAlignment(JLabel.CENTER);

        for (int x = 0; x < connectionTable.getColumnCount(); x++) {
            if (x == connectionTable.getColumnCount() - 1) {
                connectionTable.getColumnModel().getColumn(x).setCellRenderer(columnRenderer);
            } else {
                connectionTable.getColumnModel().getColumn(x).setCellRenderer(alignRenderer);
            }
        }

    }


    public JTable getConnectionTable() {
        return connectionTable;
    }



}