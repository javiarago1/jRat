package Server.ServerGUI.TableUtils;


import Client.InformationGathering.System.InfoObject;
import Server.ServerConnections.Streams;
import Server.ServerGUI.Progressing.ProgressingBar;



import javax.swing.*;


import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;


public class DiskMenuCreator {
    private final JMenu browserMenu;
    private final Streams stream;
    private final File[] disksArray;
    private final String[] defaultPaths = new String[]{"Downloads\\", "Documents\\", "Desktop\\", "Pictures\\", "Music\\", "Videos\\"};
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

    private File[] requestDisks() {
        stream.sendObject("DISKS");
        return (File[])stream.readObject();

    }

    private ActionListener generateActionListener(String path){
        return e -> {
            ProgressingBar progressingGUI = new ProgressingBar(stream.getIdentifier());
            SwingWorker<Void,Void> swingWorker = new SwingWorker<>() {
                JTree tempTree;
                @Override
                protected Void doInBackground() {
                    stream.setWorking(true);
                    System.out.println(Thread.currentThread().getName());
                    progressingGUI.executeProgression();
                    System.out.println(path);
                    stream.sendObject(new InfoObject(new File(path),"TREE"));
                    tempTree = (JTree) stream.readObject();
                    System.out.println("Object -> "+tempTree);
                    return null;
                }

                @Override
                protected void done() {
                    stream.setWorking(false);
                    progressingGUI.closeDialog(tempTree,stream);
                }
            };
            stream.executor.submit(swingWorker);
        };
    }

    private void createFileBrowserOptions() {
        System.out.println(Arrays.toString(disksArray));
        for (File s : disksArray) {
            if (s.toString().equals(diskWindows.toString())) {
                JMenu windowsDiskMenu = new JMenu(stream.getTempSystemInformation().getOPERATING_SYSTEM());
                for (String e : defaultPaths) {
                    e=e.substring(0,e.length()-1);
                    JMenuItem item = new JMenuItem(e);
                    item.addActionListener(generateActionListener(userPath+"\\"+e));
                    windowsDiskMenu.add(item);
                }
                browserMenu.add(windowsDiskMenu);
            }
            JMenuItem tempMenuItem = new JMenuItem(s.toString());
            tempMenuItem.addActionListener(generateActionListener(s.toString()));

            browserMenu.add(tempMenuItem);


        }

    }

}
