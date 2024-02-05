import Helper.Runtime;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
//        getAllSensors.execute();  // This method can be called if the saved sensor file has to be generated or updated. It is not necessary to run this everytime
        getSensorOutputs.execute(100 ,5000);
    }
}
