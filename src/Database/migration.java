package Database;

import Helper.FileRW;
import de.judge.opc_ets.Station;

import java.sql.*;
import java.util.List;


public class migration {
    public static void fillStationsSensors() throws Exception {
        Connection opcDatabaseConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/lf7_ua_opc", "root", "");

        opcDatabase.clearTable(opcDatabaseConnection, "lf7_ua_opc.sensor");
        opcDatabase.clearTable(opcDatabaseConnection, "lf7_ua_opc.station");
        fillStations(opcDatabaseConnection);
        fillSensors(opcDatabaseConnection);
    }


    /**
     * Only call this method when the table station is empty!!! Risk of duplicate entries otherwise
     * This method fills the Database.opcDatabase table station with all stations except "Controller"
     * @param connection
     * @throws SQLException
     */
    private static void fillStations(Connection connection) throws Exception {
        if (opcDatabase.isTableFilled(connection, "lf7_ua_opc.station")) {
            throw new Exception("Table is already filled");
        }

        for(Station station : Station.values()){
            if (!station.name().equals("Controller")){
                Statement statement = connection.createStatement();
                statement.execute("INSERT INTO station (Name, url) " +
                        "VALUES (" + '"' + station.name() + '"' +  "," + '"' +  station.getURL() + '"' +  ");");
            }
        }
    }

    /**
     * Only call this method when the table sensor is empty!!! Risk of duplicate entries otherwise
     * This method fills the Database.opcDatabase table sensor with all sensors according to their station
     * @param connection
     * @throws Exception
     */
    private static void fillSensors(Connection connection) throws Exception {
        if (opcDatabase.isTableFilled(connection, "lf7_ua_opc.sensor")) {
            throw new Exception("Table is already filled");
        }

        List<Station> stationList = opcDatabase.getStationsFromDB(connection);
        String stationID = null;

        for (Station station : stationList) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT Id FROM lf7_ua_opc.station WHERE Name = " + '"' +station.name() + '"');
            while (resultSet.next()) {
                stationID = resultSet.getString(1);
            }

            String directory = "./data/sensors/export_"+station.name()+".txt";
            List<String> sensorList = FileRW.read(directory);
            for (String line : sensorList) {
                String splitLine[] = line.split(",");
                String ns = splitLine[0];
                String s = splitLine[1];

                String sql = "INSERT INTO lf7_ua_opc.sensor (ns, s, StationId) VALUES (?,?,?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setString(1, ns);
                    preparedStatement.setString(2, s);
                    preparedStatement.setString(3, stationID);
                    preparedStatement.execute();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
