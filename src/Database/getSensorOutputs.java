package Database;

import Object.Crawl;
import Helper.Regex;
import de.judge.opc_ets.OPCClientETS;
import de.judge.opc_ets.SensorList;
import de.judge.opc_ets.Station;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class getSensorOutputs {

    public static void outputsToDatabase(Station station, SensorList sensorList, int queryInterval, int queryDuration) throws Exception {
        OPCClientETS.getInstance().connectToMachine(station);
        OPCClientETS.getInstance().setCrawlOffset(queryInterval);
        OPCClientETS.getInstance().browseOPCServer(sensorList);

        InputStream input = OPCClientETS.getInstance().getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        List<Crawl> crawlList = new ArrayList<>();

        while (reader.ready()) {
            String line = reader.readLine();
            String sensor = Regex.substitution(line, "Sensor:.*(\".*\").*VALUE:",1);
            String value = Regex.substitution(line, "DataValue.*value=(.*),.*statusCode",1);
            String sourceTimestamp = Regex.substitution(line, "sourceTimestamp=(.*),.*sourcePicoseconds",1);
            String serverTimestamp = Regex.substitution(line, "serverTimestamp=(.*),.*serverPicoseconds",1);
            Crawl crawlLine = new Crawl(sensor, value, sourceTimestamp, serverTimestamp);
            crawlList.add(crawlLine);
        }
    }

    /**
     * This method returns a SensorList from the database for the provided station.
     *
     * @param station
     * @return
     * @throws SQLException
     */
    private static SensorList buildSensorList(Station station) throws SQLException {
        SensorList sensorList = new SensorList();
        List<String> sensors = opcDatabase.getSensorsFromDB(opcDatabase.getConnection(), station.name());
        for (String sensor : sensors) {
            String sensorLine[] = sensor.split(";");
            sensorList.addSensor(Integer.parseInt(sensorLine[0]), sensorLine[1]);
        }
        return sensorList;
    }
}
