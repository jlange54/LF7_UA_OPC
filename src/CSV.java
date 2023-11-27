import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

public class CSV {

    public static boolean write(String directory, List<String> input) throws IOException {
        boolean completed_successfully = false;

        FileWriter writer = new FileWriter(directory);
        while(input.iterator().hasNext()) {
            try {
                writer.append(input.iterator().next()+"\n");
                completed_successfully = true;
            } catch (IOException e) {
                completed_successfully = false;
            }
        }
        writer.close();
        return completed_successfully;
    }
}
