import de.judge.opc_ets.OPCClientETS;
import de.judge.opc_ets.Station;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OPCClient {

    public static void getOutput (Station station) {
        try {
            OPCClientETS.getInstance().connectToMachine(station);
            OPCClientETS.getInstance().browseOPCServer();

            InputStream input = OPCClientETS.getInstance().getInputStream();

            System.out.println("writing CSV for "+ station.name());
            CSV.write("./CSV_Exports/export_"+station.name()+".csv", filterForSensor(input));

            OPCClientETS.getInstance().disconnect();
            System.out.println("Completed");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<String> filterForSensor (InputStream input) {
        List<String> result = new ArrayList<>();
        int loop = 0;
        String firstSensor = new String();
        String currentSensor = new String();
        Scanner in = new Scanner(input);

        while(in.hasNextLine() && firstSensor != currentSensor){
            String line = in.nextLine();
            currentSensor = Regex.substitution(line,".*DisplayName=\".*\\+(.*)\".*NodeClass.*", 1);
            if (loop == 0) {
                firstSensor = currentSensor;
            }

            if (line.contains("+")){
                result.add(line);
                System.out.println(line);
            }
            loop++;
        }
        return result;
    }
}
