import de.judge.opc_ets.OPCClientETS;
import de.judge.opc_ets.Station;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OPCClient {

    public static void start () {
        for(Station station : Station.values()){
            if (station.name() != "Controller"){
                System.out.println("Getting output from: " + station.name());
                getOutput(station);
            }
        }
    }

    public static void getOutput (Station station) {
        try {
            OPCClientETS.getInstance().connectToMachine(station);
            OPCClientETS.getInstance().browseOPCServer();

            System.out.println("Get InputStream from" + station.name());
            InputStream input = OPCClientETS.getInstance().getInputStream();

            System.out.println("Filtering for Sensor lines");
            List<String> result = filterForSensor(input);
            System.out.println("Result List building complete");
//            Debug.printList(result);
            System.out.println("writing file for "+ station.name());
            File.write("./Exports/export_"+station.name()+".txt",result);
            OPCClientETS.getInstance().disconnect();
            System.out.println("Completed");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<String> filterForSensor (InputStream input) {
        List<String> result = new ArrayList<>();
        Scanner in = new Scanner(input);

        while(in.hasNextLine()){
            String line = in.nextLine();
//            System.out.println(line);
            if (line.contains("+")){
                String nodeID_ns = Regex.substitution(line, ".*ns=(\\d);s=\"\\+(.*)\".*BrowseName.*", 1);
                String nodeID_s = Regex.substitution(line, ".*ns=(\\d);s=\"\\+(.*)\".*BrowseName.*", 2);
                String resultLine = nodeID_ns+","+nodeID_s;
                result.add(resultLine);
                System.out.println(resultLine);
            }
            if (line.contains("END DATASET")) {
                System.out.println(line);
                break;
            }
        }
        in.close();
        return result;
    }
}
