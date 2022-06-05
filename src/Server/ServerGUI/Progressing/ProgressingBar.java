package Server.ServerGUI.Progressing;

import Server.ServerConnections.Streams;
import Server.ServerGUI.MainClass;
import Server.ServerGUI.TreeInterpreter.TreeGUI;
import com.formdev.flatlaf.FlatDarculaLaf;


import javax.swing.*;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class ProgressingBar{


    private final JDialog dialog;
    private JPanel panel;
    private JProgressBar progressBar;
    private JLabel processingLabel;
    private JLabel requestLabel;
    private Timer timer;
    private String identifier;


    public ProgressingBar(String identifier) {
        this.identifier=identifier;
        dialog= new JDialog(MainClass.gui.getFrame(),"Processing - "+identifier);
        loadStyle();
        addFrame();
        addComponents();
        startDialog();
    }

    public void executeProgression(){
        requestLabel.setText("Request tree ✓ Done");
        ActionListener taskPerformer = new ActionListener() {
            int timeBar;
            final String dot=".";
            final StringBuilder dotSum = new StringBuilder(dot);
            int dotCounter=0;
            @Override
            public void actionPerformed(ActionEvent evt) {
                System.out.println("Still working"+dotSum);
                processingLabel.setText("Processing files"+dotSum);
                dotSum.append(dot);
                dotCounter++;
                if (dotCounter==3){
                    dotSum.setLength(0);
                    dotSum.append(dot);
                    dotCounter=0;
                }
                if (timeBar==2){

                    progressBar.setValue(progressBar.getValue()+5);
                    if (progressBar.getValue()==100) progressBar.setValue(0);
                    timeBar=0;
                }
                timeBar++;
            }
        };
        timer = new Timer(500 ,taskPerformer);
        timer.setInitialDelay(0);
        timer.start();


    }


    private void startDialog(){
        dialog.setVisible(true);
    }

    private void loadStyle(){
        FlatDarculaLaf.setup();
    }

    private void addFrame() {
        dialog.setSize(new Dimension(300, 150));
        dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dialog.setLocationRelativeTo(null);
        dialog.setResizable(false);
        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                timer.stop();
            }
        });
    }



    private void addJPanel(){
        panel = new JPanel();
        panel.setLayout(null);
    }

    private void addJLabel(){
        processingLabel=new JLabel();
        processingLabel.setBounds(20,27,200,30);
        panel.add(processingLabel);
        requestLabel=new JLabel();
        requestLabel.setBounds(20,5,200,30);
        panel.add(requestLabel);
    }

    private void addComponents() {
        addJPanel();
        addJLabel();
        addProgressionBar();
    }

    public void closeDialog(JTree tree, Streams stream){
        dialog.dispose();
        new TreeGUI(tree,identifier,stream);
    }

    private void addProgressionBar(){
        progressBar=new JProgressBar(0,100);
        progressBar.setBounds(20,60,240,30);
        progressBar.setValue(0);
        panel.add(progressBar);
        dialog.add(panel);
    }


}