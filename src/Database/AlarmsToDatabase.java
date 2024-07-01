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
              insertAlarmToDatabase(alarm.getModuleId(), error.getCode(), error.getMsg(), 1);
           }
       }
    }

    public static void insertAlarmToDatabase(String stationId, int fehlerCode, String fehlerMsg, int anlagenId) throws IOException, InterruptedException {
        String alarmSql = "Insert INTO alarm (StationId, fehlerCode, fehlerMsg, anlagenId) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = OpcDatabase.getConnection().prepareStatement(alarmSql)) {
            preparedStatement.setInt(1, OpcDatabase.getStationIdFromName(stationId));
            preparedStatement.setInt(2, fehlerCode);
            preparedStatement.setString(3, fehlerMsg);
            preparedStatement.setInt(4,anlagenId);

            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
