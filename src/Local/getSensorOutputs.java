package Local;

import Helper.FileRW;
import de.judge.opc_ets.OPCClientETS;
import de.judge.opc_ets.SensorList;
import de.judge.opc_ets.Station;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.*;

public class getSensorOutputs {

    /**
     * This method executes getDataFromSensorsFromStationToLocal for all Stations except "Controller"
     * It requires a queryInterval which defines the time between the data logging form the specific station and queryDuration which defines the overall time the station if queried for
     **/
    public static void execute(int queryInterval, int queryDuration) throws IOException {
        for (Station station : Station.values()) {
            if (!station.name().equals("Controller")) {
                System.out.println("Getting output from: " + station.name());
                getDataFromSensorsFromStationToLocalStorage(station, buildSensorListForStationFromLocal(station), queryInterval, queryDuration);
            }
        }
    }

    /**
     * This method browses the specified station with the provided sensorList and provides an Inputstream to the writer which writes the log files into the specified local directory
     *
     * @param station
     * @param sensorList
     * @param queryInterval
     * @param queryDuration
     */
    public static void getDataFromSensorsFromStationToLocalStorage(Station station, SensorList sensorList, int queryInterval, int queryDuration) {
        try {
            OPCClientETS.getInstance().connectToMachine(station);
            OPCClientETS.getInstance().setCrawlOffset(queryInterval);
            OPCClientETS.getInstance().browseOPCServer(sensorList);

            System.out.println("Get InputStream from " + station.name());
            InputStream input = OPCClientETS.getInstance().getInputStream();
            String time = LocalDateTime.now().toString().replace(":", "-").replace(".", "-");
            System.out.println("Writing sensor output for station: " + station.name());
            FileRW.writeWithIS("./data/sensorDataOutputs/" + time + "_export_" + station.name() + ".txt", input, queryDuration);

            OPCClientETS.getInstance().disconnect();
            System.out.println("Completed instance: " + station.name());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method returns a SensorList from local storage file for the provided station. It reads the previously generated file that contains all sensors for a station.
     *
     * @param station
     * @return
     * @throws IOException
     */
    private static SensorList buildSensorListForStationFromLocal(Station station) throws IOException {
        String directory = "./data/sensors/export_" + station.name() + ".txt";
        List<String> sensorsFromExport = FileRW.read(directory);
        Iterator<String> sensorsFromExportIterator = sensorsFromExport.iterator();
        SensorList result = new SensorList();

        while (sensorsFromExportIterator.hasNext()) {
            String[] parts = sensorsFromExportIterator.next().split(",");
            result.addSensor(Integer.parseInt(parts[0]), parts[1]);
        }
        return result;
    }



}
