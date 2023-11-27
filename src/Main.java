import de.judge.opc_ets.Station;

public class Main {
    public static void main(String[] args) {
        for(Station station : Station.values()){
//            System.out.println(station.name());
            if (station.name() != "Controller"){
                System.out.println("Getting output from: " + station.name());
                OPCClient.getOutput(station);
            }
        }
    }
}
