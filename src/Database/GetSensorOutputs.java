package Database;

import Object.Crawl;
import Helper.Regex;
import de.judge.opc_ets.OPCClientETS;
import de.judge.opc_ets.SensorList;
import de.judge.opc_ets.Station;

import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GetSensorOutputs {

    public static void execute(int queryInterval, int queryDuration) throws Exception {
        for (Station station : OpcDatabase.getStationsFromDB(OpcDatabase.getConnection())) {
            if (!station.name().equals("Controller")) {
                System.out.println("Getting output from: " + station.name());
                outputsToDatabase(station, queryInterval, queryDuration);
            }
        }
    }


    public static void outputsToDatabase(Station station, int queryInterval, int queryDuration) throws Exception {
        OPCClientETS.getInstance().connectToMachine(station);
        OPCClientETS.getInstance().setCrawlOffset(queryInterval);
        OPCClientETS.getInstance().browseOPCServer(buildSensorList(station));

        System.out.println("Building crawlList for station: " + station.name());
        List<Crawl> crawlList = buildCrawlList(OPCClientETS.getInstance().getInputStream(), queryDuration);
//        Debug List
//        List<Crawl> crawlList = Debug.generateCrawlerTest();
        fillCrawlTable(crawlList, station);

    }

    private static List<Crawl> buildCrawlList(InputStream input, int queryDuration)  {
        Scanner in = new Scanner(input);
        List<Crawl> crawlList = new ArrayList<>();
        long startTime = System.currentTimeMillis();

        while (in.hasNext() && System.currentTimeMillis() <= startTime+Long.valueOf(queryDuration)) {
            String line = in.nextLine();
            System.out.println(line);
            String raw = line;
            String sensor = Regex.substitution(line, "Sensor:.*(\".*\").*VALUE:",1);
            String value = Regex.substitution(line, "DataValue.*value=(.*),.*statusCode",1);
            String sourceTimestamp = Regex.substitution(line, "sourceTimestamp=(.*),.*sourcePicoseconds",1);
            String serverTimestamp = Regex.substitution(line, "serverTimestamp=(.*),.*serverPicoseconds",1);
            Crawl crawlLine = new Crawl(raw, sensor, value, sourceTimestamp, serverTimestamp);
            crawlList.add(crawlLine);
        }

        return crawlList;
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
        List<String> sensors = OpcDatabase.getSensorsFromDB(OpcDatabase.getConnection(), station.name());
        for (String sensor : sensors) {
            String sensorLine[] = sensor.split(";");
            sensorList.addSensor(Integer.parseInt(sensorLine[0]), sensorLine[1]);
        }

        return sensorList;
    }

    private static void fillCrawlTable (List<Crawl> crawlList, Station station) throws SQLException {
        for (Crawl crawl : crawlList) {
            System.out.println(crawl);
            String datavalueSql = "INSERT INTO datavalue (RawValue, CalculatedValue, Valid) VALUES (?,?,?)";
            try (PreparedStatement preparedStatement = OpcDatabase.getConnection().prepareStatement(datavalueSql)) {
                preparedStatement.setString(1, crawl.getRaw());
                preparedStatement.setString(2, crawl.getValue());
                int valid = 0;
                if (crawl.getValue() !=" " && crawl.getValue()!=null) {
                    valid = 1;
                }
                preparedStatement.setString(3, Integer.toString(valid));
                preparedStatement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            String datacrawlSql = "INSERT INTO datacrawl (sourceTimestamp, serverTimestamp, Anlagenname, StationId, OrderId, DataValueId, SensorId) VALUES (?,?,?,?,?,?,?)";
            try (PreparedStatement preparedStatement = OpcDatabase.getConnection().prepareStatement(datacrawlSql)) {
                preparedStatement.setString(1, crawl.getSourceTimestamp());
                preparedStatement.setString(2, crawl.getServerTimestamp());
                preparedStatement.setString(3, "LF7");
                preparedStatement.setString(4, Integer.toString(OpcDatabase.getStationIdToSensor(crawl.getSensor())));
                preparedStatement.setString(5, "1");
                preparedStatement.setString(6, Integer.toString(OpcDatabase.getLastIdFromDataValueTable()));
                preparedStatement.setString(7, Integer.toString(OpcDatabase.getSensorId(crawl.getSensor())));
                preparedStatement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
