import de.judge.opc_ets.OPCClientETS;
import de.judge.opc_ets.Station;

import java.io.InputStream;
import java.util.Scanner;

public class GetOutputFromStation {

    public static void execute (Station station) {
        try {
            OPCClientETS.getInstance().connectToMachine(station);
            OPCClientETS.getInstance().browseOPCServer();

            InputStream input = OPCClientETS.getInstance().getInputStream();

            System.out.println("writing CSV for "+ station.name());
            CSV.write("./CSV_Exports/export_"+station.name()+".csv", FilterSensor.execute(input));

            OPCClientETS.getInstance().disconnect();
            System.out.println("Completed");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
