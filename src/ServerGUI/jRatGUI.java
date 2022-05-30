package ServerGUI;

import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;


public class jRatGUI {
    private JPanel panel;
    public JTable j;

    private DefaultTableModel model;

    public jRatGUI(){
        JFrame frame = new JFrame("jRat Interface");
        frame.setSize(new Dimension(800,400));
        FlatDarkLaf.setup();
        UIManager.put( "Component.focusedBorderColor", new Color(55, 55, 55) );

        frame.setResizable(false);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JMenuBar menubar = new JMenuBar();
        JMenu menu = new JMenu("size");
        JMenuItem size = new JMenuItem("size");
        menu.add(size);
        menubar.add(menu);
        frame.setJMenuBar(menubar);
        panel = new JPanel();
        panel.setLayout(null);

        frame.add(panel);


        String[] column = { "Country", "IP", "Tag","Username","Operating System","Status" };

        model = new DefaultTableModel(null,column);
        j = new JTable(model);

        panel.add(j);
        JScrollPane scroll = new JScrollPane(j);

        scroll.setBounds(0,0,784,780);

        panel.add(scroll);
        frame.add(panel);
        frame.setVisible(true);
    }




}
