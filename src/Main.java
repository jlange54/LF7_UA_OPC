import Database.migration;
import Database.opcDatabase;

import java.sql.Connection;
import java.sql.DriverManager;

public class Main {
    public static void main(String[] args) throws Exception {
//        Local.getAllSensors.execute();  // This method can be called if the saved sensor file has to be generated or updated. It is not necessary to run this everytime
//        Local.getSensorOutputs.execute(100 ,5000);
        migration.fillStationsSensors();
    }
}
