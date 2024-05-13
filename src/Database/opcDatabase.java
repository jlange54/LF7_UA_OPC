package Database;

import de.judge.opc_ets.Sensor;
import de.judge.opc_ets.Station;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class opcDatabase {

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/lf7_ua_opc", "root", "");
    }

    /**
     * This method checks if a specified table is empty and returns a boolean
     *
     * @param connection
     * @param table
     * @return
     * @throws SQLException
     */
    public static boolean isTableFilled(Connection connection, String table) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM " + table);
        if (resultSet.next()) {
            return true;
        }
        return false;
    }

    /**
     * This method deletes all the entries from a selected table and resets the autoincrement counter
     *
     * @param connection
     * @param table
     * @throws SQLException
     */
    public static void clearTable(Connection connection, String table) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate("DELETE FROM " + table);
        statement.executeUpdate("ALTER Table " + table + " AUTO_INCREMENT = 1");
    }

    public static List<Station> getStationsFromDB(Connection connection) throws SQLException {
        List<Station> result = new ArrayList<>();

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM lf7_ua_opc.station");
        while (resultSet.next()) {
            for (Station station : Station.values()) {
                if (station.name().equals(resultSet.getString(2)) && station.getURL().equals(resultSet.getString(3))) {
                    result.add(station);
                }
            }
        }
        return result;
    }

    public static List<String> getSensorsFromDB(Connection connection, String station) throws SQLException {
        List<String> result = new ArrayList<>();
        String sqlQuery = "SELECT lf7_ua_opc.sensor.ns, lf7_ua_opc.sensor.s FROM lf7_ua_opc.sensor INNER JOIN lf7_ua_opc.station ON sensor.StationId = station.Id WHERE station.Name = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        preparedStatement.setString(1, station);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            String ns = resultSet.getString(1);
            String s = resultSet.getString(2);
            result.add(ns + ";" +s);
        }
        return result;
    }

}