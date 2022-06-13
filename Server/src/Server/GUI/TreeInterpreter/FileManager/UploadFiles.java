package Server.GUI.TreeInterpreter.FileManager;

import Client.InformationGathering.System.InfoObject;
import Server.Connections.Streams;
import Server.GUI.TreeInterpreter.TreeUtils.Manager;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;

import java.io.File;
import java.util.List;

public class UploadFiles extends Manager {

    public UploadFiles(List<File> filesArray, List<File> directories, Streams stream) {
        super(filesArray, directories, stream);
    }

    @Override
    public void run() {
        ZipFile tempZip = new ZipFile("temp.zip");
        try {
            for (File a : getFilesArray()) {
                if (a.isDirectory()) {
                    tempZip.addFolder(a);
                } else {
                    tempZip.addFile(a);
                }
            }
            getStream().sendObject(new InfoObject(getDirectories(), "UPLOAD"));
            getStream().sendFile(tempZip.getFile());
        } catch (ZipException e) {
            e.printStackTrace();
        }

    }
}
