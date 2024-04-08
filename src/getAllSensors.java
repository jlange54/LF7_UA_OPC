import Helper.FileRW;
import Helper.Regex;
import de.judge.opc_ets.OPCClientETS;
import de.judge.opc_ets.SensorList;
import de.judge.opc_ets.Station;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class getAllSensors {

    public static void getDataFromSensorsFromStation (Station station, SensorList sensorList) {
        try {
            OPCClientETS.getInstance().connectToMachine(station);
            OPCClientETS.getInstance().browseOPCServer(sensorList);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * This Method executes getAllSensorsFromStation for all stations expect "Controller"
     */
    public static void execute () {
        for(Station station : Station.values()){
            if (!station.name().equals("Controller")){
                System.out.println("Getting output from: " + station.name());
                getAllSensorsFromStation(station);
            }
        }
    }


    /**
     * This method browses a specified station and writes the Sensor Strings into a specified file directory
     *
     * @param station
     */
    private static void getAllSensorsFromStation (Station station) {
        try {
            OPCClientETS.getInstance().connectToMachine(station);
            OPCClientETS.getInstance().browseOPCServer();

            System.out.println("Get InputStream from: " + station.name());
            InputStream input = OPCClientETS.getInstance().getInputStream();

            System.out.println("writing file for: "+ station.name());
            FileRW.write("./data/sensors/data/sensors_"+station.name()+".txt",filterForSensor(input));
            OPCClientETS.getInstance().disconnect();
            System.out.println("Completed instance: "+station.name());
        }catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * This method returns a List of type String. It requires an InputStream that is filtered with regex to get the ns and s values for each sensor. The method terminates when a line in the InputStream contains "END DATASET"
     *
     * @param input
     * @return
     */
    public static List<String> filterForSensor (InputStream input) {
        List<String> result = new ArrayList<>();
        Scanner in = new Scanner(input);

        while(in.hasNextLine()){
            String line = in.nextLine();
            if (line.contains("+")){
                String nodeID_ns = Regex.substitution(line, ".*ns=(\\d);s=(\"\\+.*)\".*BrowseName.*", 1);
                String nodeID_s = Regex.substitution(line, ".*ns=(\\d);s=(\"\\+.*)\".*BrowseName.*", 2);
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
