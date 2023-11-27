import jdk.internal.util.xml.impl.Input;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FilterSensor {
    public static List<String> execute (InputStream input) {
        List<String> result = new ArrayList<>();
        Scanner in = new Scanner(input);
        while(in.hasNextLine()){
            String line = in.nextLine();
            if (line.contains("+")){
                result.add(line);
                System.out.println(line);
            }
        }

        return result;
    }
}
