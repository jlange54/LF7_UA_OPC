import Helper.Runtime;
import de.judge.opc_ets.Station;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Runtime.start();
//        getAllSensors.execute();
        getSensorOutputs.execute(Station.BF, 100 ,1);
        System.out.println("Program completed in " + Runtime.end() + " seconds");


    }
}
