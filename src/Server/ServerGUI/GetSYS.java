package Server.ServerGUI;

import Server.ServerConnections.Streams;

import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

public class GetSYS {

    public static Streams getStream(ConcurrentHashMap<Socket, Streams> map,String address){
        for (Socket a:map.keySet()){
            if (a.getInetAddress().toString().equals(address)){
                return map.get(a);
            }
        }
        return null;
    }

}

