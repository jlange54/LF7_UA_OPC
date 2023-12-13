import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class File {

    public static boolean write(String directory, List<String> input) throws IOException {
        boolean completed_successfully = false;

        Iterator<String> inputIterator = input.iterator();
        java.io.FileWriter writer = new java.io.FileWriter(directory);
        while(inputIterator.hasNext()) {
            try {
                writer.append(inputIterator.next()+"\n");
                completed_successfully = true;
            } catch (IOException e) {
                completed_successfully = false;
            }
        }
        writer.close();
        return completed_successfully;
    }

    public static List<String> read (String directory) throws IOException {
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
    }
}
