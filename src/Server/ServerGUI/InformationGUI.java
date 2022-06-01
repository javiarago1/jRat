package Server.ServerGUI;

import Client.InformationGathering.SystemInformation;
import Client.InformationGathering.SystemNetworkInformation;
import Server.ServerConnections.Streams;

public class InformationGUI {

    private SystemNetworkInformation network;
    private SystemInformation system;

    public InformationGUI(){

    }

    private void getInformation(Streams streams){
       // streams.sendMsg()
    }


    public SystemNetworkInformation getNetwork() {
        return network;
    }

    public void setNetwork(SystemNetworkInformation network) {
        this.network = network;
    }

    public SystemInformation getSystem() {
        return system;
    }

    public void setSystem(SystemInformation system) {
        this.system = system;
    }
}
