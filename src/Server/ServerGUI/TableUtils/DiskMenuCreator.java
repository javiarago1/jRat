package Server.ServerGUI.TableUtils;


import Server.ServerConnections.Streams;
import Server.ServerGUI.MainClass;
import Server.ServerGUI.Progressing.ProgressingBar;
import Server.ServerGUI.TreeInterpreter.TreeGUI;


import javax.swing.*;


import java.awt.event.ActionListener;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;


public class DiskMenuCreator {
    private final JMenu browserMenu;
    private final Streams stream;
    private final String[] disksArray;
    private final String[] defaultPaths = new String[]{"Downloads", "Documents", "Desktop", "Pictures", "Music", "Videos"};
    private final Path userPath;
    private final Path diskWindows;


    public DiskMenuCreator(JMenu browserMenu, Streams stream) {
        this.browserMenu = browserMenu;
        this.stream = stream;
        disksArray = requestDisks();
        userPath = Paths.get(stream.getTempSystemInformation().getUSER_HOME());
        diskWindows = userPath.getRoot();
    }

    public void createMenu() {
        createFileBrowserOptions();
    }

    private String[] requestDisks() {
        stream.sendObject("DISKS");
        return (String[]) stream.readObject();

    }

    private ActionListener generateActionListener(String path){
        return e -> {
            ProgressingBar progressingGUI = new ProgressingBar(stream.getIdentifier());
            stream.setWorking(true);
            SwingWorker<Void,Void> swingWorker = new SwingWorker<>() {
                JTree tempTree;
                @Override
                protected Void doInBackground() {
                    System.out.println(Thread.currentThread().getName());
                    progressingGUI.executeProgression();
                    stream.sendObject("TREE" + path);
                    tempTree = (JTree) stream.readObject();
                    System.out.println("Object -> "+tempTree);
                    return null;
                }


                @Override
                protected void done() {
                    stream.setWorking(false);
                    progressingGUI.closeDialog(tempTree);
                }
            };
            stream.executor.submit(swingWorker);
        };
    }

    private void createFileBrowserOptions() {
        System.out.println(Arrays.toString(disksArray));
        for (String s : disksArray) {
            if (s.equals(diskWindows.toString())) {
                JMenu windowsDiskMenu = new JMenu(stream.getTempSystemInformation().getOPERATING_SYSTEM());
                for (String e : defaultPaths) {
                    JMenuItem item = new JMenuItem(e);
                    item.addActionListener(generateActionListener(userPath+"\\"+e));
                    windowsDiskMenu.add(item);
                }
                browserMenu.add(windowsDiskMenu);
            }
            JMenuItem tempMenuItem = new JMenuItem(s);
            tempMenuItem.addActionListener(generateActionListener(s));

            browserMenu.add(tempMenuItem);


        }

    }

}
