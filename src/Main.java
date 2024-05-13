import Database.opcDatabase;
import de.judge.opc_ets.Station;

public class Main {
    public static void main(String[] args) throws Exception {
        Database.getSensorOutputs.outputsToDatabase(Station.BF ,500, 5000);
//        Local.getSensorOutputs.execute(500, 5000);
    }
}
