package ServerGUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import Server.Streams;

import javax.swing.*;

public class Connections {
    private final Streams selectedStreams;
    public Connections(Streams selectedStreams) {
        if (selectedStreams == null)
            throw new IllegalArgumentException();
        this.selectedStreams = selectedStreams;
    }

    public void execute() {
        while(true) {
            System.out.println("Select an operation to execute:");
            System.out.println("a - Show device's name");
            System.out.println("b - Show device's remote IP");
            System.out.println("c - Show device's operating system");
            System.out.println("d - Show file directory");
            System.out.println("e - Back to menu");
            try {
                String selected = new BufferedReader(new InputStreamReader(System.in)).readLine();
                switch(selected) {
                    case "a":	selectedStreams.sendMsg("PC_NAME");
                        System.out.println("Device's name is: " + selectedStreams.readMsg());
                        break;
                    case "b":	selectedStreams.sendMsg("IP");
                        System.out.println("Device's IP is: " + selectedStreams.readMsg());
                        break;
                    case "c":	selectedStreams.sendMsg("OS");
                        System.out.println("Device's OS is: " + selectedStreams.readMsg());
                        break;
                    case "d": selectedStreams.sendMsg("TREE");
                        JTree tempTree = selectedStreams.readObject();
                        SwingUtilities.invokeLater(() -> new TreeGUI(tempTree));
                        break;
                    case "e":	return;
                    default: 	break;
                }
            } catch (IOException e) {
                System.err.println("Exception!");
                e.printStackTrace();
                return;
            }
        }
    }
}