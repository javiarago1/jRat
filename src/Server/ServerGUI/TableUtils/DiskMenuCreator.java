package Server.ServerGUI.TableUtils;

import Server.ServerConnections.Streams;

import javax.swing.*;
import java.io.File;
import java.util.Arrays;


public class DiskMenuCreator {
    private JMenu browserMenu;
    private Streams stream;


    public DiskMenuCreator(JMenu browserMenu, Streams stream){
        this.browserMenu=browserMenu;
        this.stream=stream;
    }


    public void requestDisks(){
        stream.sendMsg("DISKS");
        System.out.println(Arrays.toString((String[])stream.readObject()));



    }

}
