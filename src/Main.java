import de.judge.opc_ets.OPCClientETS;
import de.judge.opc_ets.Station;
import sun.security.jca.GetInstance;

import java.io.InputStream;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        for(Station station : Station.values()){
//            System.out.println(station.name());
            if (station.name() != "Controller"){
                System.out.println("Getting output from: " + station.name());
                GetOutputFromStation.execute(station);
            }
        }
    }
}
