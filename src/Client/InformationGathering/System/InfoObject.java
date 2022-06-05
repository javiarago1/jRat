package Client.InformationGathering.System;

import java.io.File;
import java.io.Serializable;

public class InfoObject implements Serializable {
    File path;
    String command;

    public InfoObject(File route, String command) {
        this.path = route;
        this.command = command;
    }

    public File getPath() {
        return path;
    }

    public String getCommand() {
        return command;
    }
}
