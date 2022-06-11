package Server.GUI.TreeInterpreter.FileManager;

import Client.InformationGathering.System.InfoObject;
import Server.Connections.Streams;
import Server.GUI.TreeInterpreter.TreeUtils.Manager;
import net.lingala.zip4j.ZipFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DownloadFiles extends Manager {

    public DownloadFiles(List<File> filesArray, Streams stream) {
        super(filesArray, stream);
    }


    @Override
    public void run() {
        System.out.println("Archivos que voy a enviar -> " + getFilesArray());
        getStream().sendObject(new InfoObject(getFilesArray(), "DOWNLOAD"));
        File sessionDirectory = new File("Downloaded Files" + "\\" + getStream().getIdentifier() + "\\" + getTime());
        sessionDirectory.mkdirs();
        try (FileOutputStream fileToCreate = new FileOutputStream(sessionDirectory + "/temp.zip")) {
            byte[] array = getStream().readFile();
            fileToCreate.write(array);
            System.out.println("Downloaded correctly");
            new ZipFile(sessionDirectory + "/temp.zip").extractAll(sessionDirectory.toString());
            System.out.println("Extraction finished properly");
        } catch (IOException error) {
            error.printStackTrace();
        }
    }


    private String getTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("[yyyy-MM-dd] - [HH-mm-ss]");
        Date date = new Date(System.currentTimeMillis());
        return formatter.format(date);
    }

}

