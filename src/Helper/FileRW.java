package Helper;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class FileRW {

    public static void write(String directory, List<String> input) throws IOException {
        Iterator<String> inputIterator = input.iterator();
        FileWriter writer = new FileWriter(directory);
        while(inputIterator.hasNext()) {
            try {
                writer.append(inputIterator.next()+"\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        writer.close();
    }

    public static void writeWithIS (String directory, InputStream input,  int queries) throws IOException {
        Scanner in = new Scanner(input);
        FileWriter writer = new FileWriter(directory);
        int lines = 0;
        int loops = 0;
        String firstSensor = null;

        while (in.hasNext()) {
            try {
                String line = in.nextLine();
                if (lines == 0) {
                    firstSensor = Regex.substitution(line, "Sensor:.*(\".*\").*>.*VALUE:",1);
                } else if (firstSensor.equals(Regex.substitution(line, "Sensor:.*(\".*\").*>.*VALUE:",1))) {
                    loops++;
                }
                if (loops < queries) {
                    writer.append(line + "\n");
                }
                lines++;

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        writer.close();
        in.close();
    }

    public static List<String> read (String directory) throws IOException {
        File file = new File(directory);
        if (file.isFile()) {
            List<String> result = new ArrayList<String>();
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(directory)));
            boolean lineEmpty = false;

            while (lineEmpty == false) {
                String line = reader.readLine();
                if (line != null) {
                    result.add(line);
                } else {
                    lineEmpty = true;
                }
            }

            reader.close();
            return result;
        } else {
         return null;
        }
    }
}
