import Helper.Runtime;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Runtime.start();
//        getAllSensors.execute();
        getSensorOutputs.execute(100 ,5000);
        Runtime.end(Runtime.timeUnit.seconds, Runtime.type.program);


    }
}
