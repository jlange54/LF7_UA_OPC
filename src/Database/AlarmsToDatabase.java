package Database;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import Api.ApiConnector;
import Object.AlarmMainStructure;


public class AlarmsToDatabase {
    public static void insertAlarmsToDatabase() throws IOException, InterruptedException {
       AlarmMainStructure mainStructure = AlarmMainStructure.mapFromJson(ApiConnector.getAllAlarms().body());
       for (AlarmMainStructure.Alarm alarm : mainStructure.getAlarms()) {
           for (AlarmMainStructure.Alarm.Error error : alarm.getErrors()) {
               String alarmSql = "Insert INTO alarm (StationId, fehlerCode, fehlerMsg, anlagenId) VALUES (?, ?, ?, ?)";
               try (PreparedStatement preparedStatement = OpcDatabase.getConnection().prepareStatement(alarmSql)) {
                   preparedStatement.setInt(1, OpcDatabase.getStationIdFromName(alarm.getModuleId()));
                   preparedStatement.setInt(2, error.getCode());
                   preparedStatement.setString(3, error.getMsg());
                   preparedStatement.setInt(4,1);

                   preparedStatement.execute();
               } catch (SQLException e) {
                   e.printStackTrace();
               }
           }
       }
    }
}
