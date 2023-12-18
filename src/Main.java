import de.judge.opc_ets.Station;

import java.io.IOException;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) throws IOException {
        Runtime.start();
//        getAllSensors.execute();
        getSensorOutputs.execute(Station.BF);
        System.out.println("Program completed in " + Runtime.end() + " seconds");


    }
}
