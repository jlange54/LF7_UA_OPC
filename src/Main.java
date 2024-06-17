import Database.GetSensorOutputs;

public class Main {
    public static void main(String[] args) throws Exception {
        GetSensorOutputs.execute(500, 10000);
//        Database.GetSensorOutputs.outputsToDatabase(Station.BF ,500, 5000);
//        Local.GetSensorOutputs.execute(500, 5000);
//        System.out.println(OpcDatabase.getLastIdFromDataValueTable());
    }
}
