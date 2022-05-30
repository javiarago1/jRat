 package Server;

 import InformationGathering.SystemNetworkInformation;
 import ServerGUI.MainClass;
 import com.sun.tools.javac.Main;

 import javax.swing.*;
 import javax.swing.table.DefaultTableModel;
 import java.net.ServerSocket;
 import java.net.Socket;
 import java.util.concurrent.ConcurrentHashMap;
 import java.util.concurrent.ExecutorService;

 public class SingleShot implements Runnable {
     private ServerSocket server;
     private ExecutorService executor;
     private ConcurrentHashMap <Socket, Streams> dialog;

     public SingleShot(ServerSocket server, ExecutorService executor, ConcurrentHashMap<Socket, Streams> dialog) {
         if (server == null || executor == null || dialog == null) throw new IllegalArgumentException();
         this.server = server;
         this.executor = executor;
         this.dialog = dialog;
     }

     @Override
     public void run() {
         try {
             Socket socket = server.accept();
             if (dialog.putIfAbsent(socket, new Streams(socket)) == null){
                 System.out.println("Connected to: " + socket.getRemoteSocketAddress());
                 SwingUtilities.invokeLater(() -> {
                     DefaultTableModel model = (DefaultTableModel) MainClass.gui.j.getModel();
                     dialog.get(socket).sendMsg("INITIAL_DETAILS");
                     SystemNetworkInformation a = (SystemNetworkInformation) dialog.get(socket).readObject();
                     System.out.println(a);
                     model.addRow(new Object[]{"Column 1", "Column 2", "Column 3"});


                 });
             }
             executor.submit(new SingleShot(server, executor, dialog));
         }
         catch (Exception e) { executor.submit(new SingleShot(server, executor, dialog)); }
     }

 }