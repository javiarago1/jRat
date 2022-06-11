package Server.GUI.TableUtils.FileBrowser;


import Client.InformationGathering.System.InfoObject;
import Server.Connections.Streams;
import Server.GUI.Main;
import Server.GUI.TableUtils.Bar.ProgressBar;
import Server.GUI.TreeInterpreter.TreeGUI.TreeGUI;


import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;


import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class DiskMenu implements Runnable {
    private final JMenu browserMenu;
    private final Streams stream;
    private File[] disksArray;
    private final String[] defaultPaths = new String[]{"Downloads\\", "Documents\\", "Desktop\\", "Pictures\\", "Music\\", "Videos\\"};
    private final Path userPath;
    private final Path diskWindows;


    public DiskMenu(JMenu browserMenu, Streams stream) {
        this.browserMenu = browserMenu;
        this.stream = stream;
        userPath = Paths.get(stream.getTempSystemInformation().getUSER_HOME());
        diskWindows = userPath.getRoot();
    }

    public void createMenu() {
        createFileBrowserOptions();
    }


    protected ActionListener generateActionListener(String path) {
        return e -> new TreeGUI(new File(path), stream, Main.gui.getFrame());
    }

    private void createFileBrowserOptions() {
        System.out.println(Arrays.toString(disksArray));
        for (File s : disksArray) {
            if (s.toString().equals(diskWindows.toString())) {
                JMenu windowsDiskMenu = new JMenu(stream.getTempSystemInformation().getOPERATING_SYSTEM());
                for (String e : defaultPaths) {
                    e = e.substring(0, e.length() - 1);
                    JMenuItem item = new JMenuItem(e);
                    item.addActionListener(generateActionListener(userPath + "\\" + e));
                    windowsDiskMenu.add(item);
                }
                browserMenu.add(windowsDiskMenu);
            }
            JMenuItem tempMenuItem = new JMenuItem(s.toString());
            tempMenuItem.addActionListener(generateActionListener(s.toString()));
            browserMenu.add(tempMenuItem);
        }

    }

    public Streams getStream() {
        return stream;
    }

    public File[] requestDisks() {
        stream.sendObject("DISKS");
        return (File[]) stream.readObject();
    }

    @Override
    public void run() {
        disksArray = requestDisks();
        createMenu();
    }
}
