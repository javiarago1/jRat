 package Server.ServerConnections;

 import Client.InformationGathering.SystemInformation;
 import Client.InformationGathering.SystemNetworkInformation;
 import Server.ServerGUI.MainClass;

 import javax.swing.*;
 import javax.swing.table.DefaultTableModel;
 import java.net.ServerSocket;
 import java.net.Socket;
 import java.util.concurrent.ConcurrentHashMap;
 import java.util.concurrent.ExecutorService;

 public class SingleShot implements Runnable {
     private final ServerSocket server;
     private final ExecutorService executor;
     private final ConcurrentHashMap <Socket, Streams> dialog;

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


                 dialog.get(socket).sendMsg("SYS_DETAILS");
                 Object[] informationArray = (Object[]) dialog.get(socket).readObject();
                 SystemNetworkInformation tempNetwork = (SystemNetworkInformation) informationArray[0];
                 SystemInformation tempSystem = (SystemInformation) informationArray[1];
                 dialog.get(socket).setTempSystemInformation(tempSystem);
                 dialog.get(socket).setTempSystemNetworkInformation(tempNetwork);

                 String[]row=new String[]{socket.getInetAddress().toString(),tempNetwork.getUSER_COUNTRY(),"User",tempSystem.getUSER_NAME(), tempSystem.getOPERATING_SYSTEM(),"Connected"};

                 SwingUtilities.invokeLater(() -> {
                     DefaultTableModel model = (DefaultTableModel) MainClass.gui.getConnectionTable().getModel();
                     model.addRow(row);
                 });


             }
             executor.submit(new SingleShot(server, executor, dialog));
         }
         catch (Exception e) { executor.submit(new SingleShot(server, executor, dialog)); }
     }

 }