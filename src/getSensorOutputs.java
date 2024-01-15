import Helper.FileRW;
import de.judge.opc_ets.OPCClientETS;
import de.judge.opc_ets.SensorList;
import de.judge.opc_ets.Station;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.*;

public class getSensorOutputs {

    public static void execute (Station station) throws IOException {
        getDataFromSensorsFromStation(station, buildSensorListFromStation (station));

    }

    public static void getDataFromSensorsFromStation (Station station, SensorList sensorList) {
        try {
            OPCClientETS.getInstance().connectToMachine(station);
            OPCClientETS.getInstance().browseOPCServer(sensorList);

            System.out.println("Get InputStream from" + station.name());
            InputStream input = OPCClientETS.getInstance().getInputStream();
            String time = LocalDateTime.now().toString().replace(":","-").replace(".","-");
            System.out.println("Writing sensor output for station: " + station.name());
            FileRW.writeWithIS("./data/sensorOutputs/"+ time +"/export_"+station.name()+".txt",input);

            OPCClientETS.getInstance().disconnect();
            System.out.println("Completed instance: "+station.name());
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static SensorList buildSensorListFromStation (Station station) throws IOException {
        String directory = "./Exports/export_"+station.name()+".txt";
        List <String> sensorsFromExport = FileRW.read(directory);
        Iterator<String> sensorsFromExportIterator = sensorsFromExport.iterator();
        SensorList result = new SensorList();

        while (sensorsFromExportIterator.hasNext()) {
            String[] parts = sensorsFromExportIterator.next().split(",");
            int s = Integer.parseInt(parts[0]);
            String  ns = parts[1];
            result.addSensor(s, ns);
        }
        return result;
    }

}
