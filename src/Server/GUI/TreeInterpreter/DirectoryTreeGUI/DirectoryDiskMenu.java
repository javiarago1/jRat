package Server.GUI.TreeInterpreter.DirectoryTreeGUI;

import Client.InformationGathering.System.InfoObject;

import Server.Connections.Streams;

import Server.GUI.TableUtils.Bar.ProgressBar;
import Server.GUI.TableUtils.FileBrowser.DiskMenu;
import Server.GUI.TreeInterpreter.TreeUtils.Action;


import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class DirectoryDiskMenu extends DiskMenu {

    private final Streams stream;
    private final JDialog dialog;
    private final List<File> filesToCopy;
    private final Action action;

    public DirectoryDiskMenu(JMenu browserMenu, Streams stream, JDialog dialog, List<File> filesToCopy, Action action) {
        super(browserMenu, stream);
        this.dialog = dialog;
        this.stream = stream;
        this.filesToCopy = new ArrayList<>(filesToCopy);
        this.action = action;
    }

    @Override
    protected ActionListener generateActionListener(String path) {
        return e -> {
            ProgressBar progressingGUI = new ProgressBar(stream.getIdentifier());
            SwingWorker<Void, Void> swingWorker = new SwingWorker<>() {
                JTree tempTree;

                @Override
                protected Void doInBackground() {
                    stream.setWorking(true);
                    System.out.println(Thread.currentThread().getName());
                    progressingGUI.executeProgression();
                    System.out.println(path);
                    stream.sendObject(new InfoObject(new File(path), "TREE_DIRECTORIES"));
                    tempTree = (JTree) stream.readObject();
                    System.out.println("Object -> " + tempTree);
                    return null;
                }

                @Override
                protected void done() {
                    stream.setWorking(false);
                    progressingGUI.closeDialog();
                    new DirectoryTreeGUI(tempTree, stream, dialog, filesToCopy, action);
                }
            };
            stream.executor.submit(swingWorker);
        };
    }

}
