package Server.ServerGUI.TreeInterpreter;

import Server.ServerConnections.Streams;

import java.io.DataInputStream;
import java.io.File;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

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
                int filenamelength = inputStream.readInt();
                System.out.println("de aki pasa 2");
                System.out.println(filenamelength);

                if (filenamelength>0){

                    byte[]filenamebytes = new byte[filenamelength];
                    inputStream.readFully(filenamebytes,0,filenamelength);
                    String filename = new String(filenamebytes);

                    System.out.println("Nombre de archvo"+filename);
                    int filecontentlength = inputStream.readInt();

                    byte[]filecontent=null;
                    if (filecontentlength>0){
                        filecontent=new byte[filecontentlength];
                        inputStream.readFully(filecontent,0,filecontentlength);

                    }



                    File toDonwload = new File(filename);
                    FileOutputStream filetocreate = new FileOutputStream(toDonwload);
                    assert filecontent != null;
                    filetocreate.write(filecontent);
                    filetocreate.close();


                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
