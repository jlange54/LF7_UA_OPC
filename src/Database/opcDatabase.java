package Database;

import Helper.FileRW;
import de.judge.opc_ets.Station;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class opcDatabase {


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
       ResultSet resultSet = statement.executeQuery("SELECT * FROM "+ table);
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
       statement.executeUpdate("DELETE FROM "+ table);
       statement.executeUpdate("ALTER Table "+ table + " AUTO_INCREMENT = 1");
   }

   public static List<Station> getStationsFromDB(Connection connection) throws SQLException {
       List<Station> result = new ArrayList<>();

       Statement statement = connection.createStatement();
       ResultSet resultSet = statement.executeQuery("SELECT * FROM lf7_ua_opc.station");
       while (resultSet.next()) {
           for(Station station : Station.values()) {
               if (station.name().equals(resultSet.getString(2)) && station.getURL().equals(resultSet.getString(3))) {
                   result.add(station);
               }
           }
       }
       return result;
    }

}
