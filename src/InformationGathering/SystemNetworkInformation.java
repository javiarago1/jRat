package InformationGathering;


import java.io.IOException;
import java.io.Serializable;

public class SystemNetworkInformation implements Serializable {

    private final String IP;
    private final String INTERNET_COMPANY_NAME;
    private final String USER_CONTINENT;
    private final String USER_COUNTRY;
    private final String USER_REGION;
    private final String USER_CITY;
    private final String USER_POSTAL;
    private final String USER_ZONE;
    private final String USER_CURRENCY;
    private final String USER_PROXY;

    public SystemNetworkInformation() throws IOException {
        ReadJSON jsonURL = new ReadJSON("http://ip-api.com/json/?fields=9629695");
        IP = jsonURL.get("query");
        INTERNET_COMPANY_NAME = jsonURL.get("isp");
        USER_CONTINENT = jsonURL.get("continent");
        USER_COUNTRY = jsonURL.get("country");
        USER_REGION = jsonURL.get("regionName");
        USER_CITY = jsonURL.get("city");
        USER_POSTAL = jsonURL.get("zip");
        USER_ZONE = jsonURL.get("timezone");
        USER_CURRENCY = jsonURL.get("currency");
        USER_PROXY= jsonURL.get("proxy");
    }

    public String getIP() {
        return IP;
    }

    public String getINTERNET_COMPANY_NAME() {
        return INTERNET_COMPANY_NAME;
    }

    public String getUSER_CONTINENT() {
        return USER_CONTINENT;
    }

    public String getUSER_COUNTRY() {
        return USER_COUNTRY;
    }

    public String getUSER_REGION() {
        return USER_REGION;
    }

    public String getUSER_CITY() {
        return USER_CITY;
    }

    public String getUSER_POSTAL() {
        return USER_POSTAL;
    }

    public String getUSER_ZONE() {
        return USER_ZONE;
    }

    public String getUSER_CURRENCY() {
        return USER_CURRENCY;
    }

    public String getUSER_PROXY() {
        return USER_PROXY;
    }


    @Override
    public String toString() {
        return "SystemNetworkInformation{" +
                "IP='" + IP + '\'' +
                ", INTERNET_COMPANY_NAME='" + INTERNET_COMPANY_NAME + '\'' +
                ", USER_CONTINENT='" + USER_CONTINENT + '\'' +
                ", USER_COUNTRY='" + USER_COUNTRY + '\'' +
                ", USER_REGION='" + USER_REGION + '\'' +
                ", USER_CITY='" + USER_CITY + '\'' +
                ", USER_POSTAL='" + USER_POSTAL + '\'' +
                ", USER_ZONE='" + USER_ZONE + '\'' +
                ", USER_CURRENCY='" + USER_CURRENCY + '\'' +
                ", USER_PROXY='" + USER_PROXY + '\'' +
                '}';
    }
}
