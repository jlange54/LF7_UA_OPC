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

    public static void writeWithIS (String directory, InputStream input) throws IOException {
        Scanner in = new Scanner(input);
        FileWriter writer = new FileWriter(directory);
        while (in.hasNext()) {
            try {
                writer.append(in.next()+"\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        writer.close();
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
