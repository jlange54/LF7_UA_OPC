import Helper.FileRW;
import Helper.Regex;
import de.judge.opc_ets.OPCClientETS;
import de.judge.opc_ets.Sensor;
import de.judge.opc_ets.SensorList;
import de.judge.opc_ets.Station;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.*;

public class getSensorOutputs {

    public static void execute (Station station, int queryInterval, int queries) throws IOException {
        getDataFromSensorsFromStation(station, buildSensorListFromStation (station), queryInterval, queries);

    }

    public static void getDataFromSensorsFromStation (Station station, SensorList sensorList, int queryInterval, int queries) {
        try {
            OPCClientETS.getInstance().connectToMachine(station);
            OPCClientETS.getInstance().setCrawlOffset(queryInterval);
            OPCClientETS.getInstance().browseOPCServer(sensorList);

            System.out.println("Get InputStream from " + station.name());
            InputStream input = OPCClientETS.getInstance().getInputStream();
            String time = LocalDateTime.now().toString().replace(":", "-").replace(".", "-");
            System.out.println("Writing sensor output for station: " + station.name());
            FileRW.writeWithIS("./data/sensorDataOutputs/" + time + "_export_" + station.name() + ".txt", input, queries);

            OPCClientETS.getInstance().disconnect();
            System.out.println("Completed instance: "+station.name());
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static SensorList buildSensorListFromStation (Station station) throws IOException {
        String directory = "./data/sensors/export_"+station.name()+".txt";
        List <String> sensorsFromExport = FileRW.read(directory);
        Iterator<String> sensorsFromExportIterator = sensorsFromExport.iterator();
        SensorList result = new SensorList();

        while (sensorsFromExportIterator.hasNext()) {
            String[] parts = sensorsFromExportIterator.next().split(",");
            result.addSensor(Integer.parseInt(parts[0]), parts[1]);
        }
        return result;
    }
}
