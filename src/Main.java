import de.judge.opc_ets.OPCClientETS;
import de.judge.opc_ets.Station;
import sun.security.jca.GetInstance;

import java.io.InputStream;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {


        try {
            OPCClientETS.getInstance().connectToMachine(Station.BF);
            OPCClientETS.getInstance().browseOPCServer();

            InputStream input = OPCClientETS.getInstance().getInputStream();

            Scanner in = new Scanner(input);

            while (in.hasNextLine()) {
                String line = in.nextLine();
                
                System.out.println(line);
            }


            OPCClientETS.getInstance().disconnect();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
