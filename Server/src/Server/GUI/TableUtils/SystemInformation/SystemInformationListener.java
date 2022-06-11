package Server.GUI.TableUtils.SystemInformation;

import Server.Connections.Streams;
import Server.GUI.TableUtils.Configuration.GetSYS;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

public class SystemInformationListener implements ActionListener {

    private JTable table;
    private ConcurrentHashMap<Socket, Streams> map;

    public SystemInformationListener(JTable table, ConcurrentHashMap<Socket, Streams> map) {
        this.table = table;
        this.map = map;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Streams stream = GetSYS.getStream(map, table);
        assert stream != null;
        stream.executor.submit(() -> {
            System.out.println(Thread.currentThread().getName());
            stream.sendObject("SYS_DETAILS");
            Object[] informationArray = (Object[]) stream.readObject();
            System.out.println(informationArray[0]);
        });



    }


}
