package Server.ServerGUI.TreeInterpreter;

import Server.ServerConnections.Streams;

import java.io.*;

import java.util.ArrayList;
import java.util.Arrays;

public class FileManager implements Runnable{


    private ArrayList<File>filesArray;
    private Streams stream;
    private Action action;

    public FileManager(ArrayList<File> filesArray, Streams stream, Action action){
        this.filesArray=filesArray;
        this.stream=stream;
        this.action=action;
    }

    @Override
    public void run() {
        if (action.equals(Action.DOWNLOAD)){
            System.out.println(Action.DOWNLOAD);
            stream.sendObject(filesArray.get(0));
            System.out.println("nopasa");
            DataInputStream inputStream = stream.getDataInputStream();
            System.out.println("de aki pasa");
            try {





                    byte[]array = stream.readInt();


                    File toDonwload = new File("file.png");
                    FileOutputStream filetocreate = new FileOutputStream(toDonwload);

                    filetocreate.write(array);
                    filetocreate.close();



            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
