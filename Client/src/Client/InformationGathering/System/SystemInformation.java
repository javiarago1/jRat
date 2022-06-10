package Client.InformationGathering.System;


import java.io.File;
import java.io.Serializable;


public class SystemInformation implements Serializable {

    private final String OPERATING_SYSTEM;
    private final String USER_HOME;
    private final String USER_NAME;
    private final File[] USER_DISKS;


    public SystemInformation()  {
        OPERATING_SYSTEM=getOperatingSystem();
        USER_HOME=getUserHome();
        USER_NAME=getUserName();
        USER_DISKS=getUserDisks();

    }




    private File[] getUserDisks(){
         return File.listRoots();
    }

    private String getOperatingSystem(){
        return System.getProperty("os.name");
    }

    private String getUserHome(){
        return System.getProperty("user.home");
    }

    private String getUserName(){
        return System.getProperty("user.name");
    }


    public String getOPERATING_SYSTEM() {
        return OPERATING_SYSTEM;
    }

    public String getUSER_HOME() {
        return USER_HOME;
    }

    public String getUSER_NAME() {
        return USER_NAME;
    }



    public File[] getUSER_DISKS() {
        return USER_DISKS;
    }
}
