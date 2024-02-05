package Helper;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class FileRW {

    /**
     * This method writes a List of Strings into a file specified by directory
     *
     * @param directory
     * @param input
     * @throws IOException
     */
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

    /**
     * This method writes an Inputstream into a file specified by directory
     *
     * @param directory
     * @param input
     * @param queryDuration
     * @throws IOException
     */
    public static void writeWithIS (String directory, InputStream input, int queryDuration) throws IOException {
        Scanner in = new Scanner(input);
        FileWriter writer = new FileWriter(directory);
        long startTime = System.currentTimeMillis();

        while (in.hasNext() && System.currentTimeMillis() <= startTime+Long.valueOf(queryDuration)) {
            try {
                String line = in.nextLine();
                System.out.println(line);
                writer.append(line + "\n");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        writer.close();
        in.close();
    }


    /**
     * This method returns a List of type String from a file from a specified directory
     *
     * @param directory
     * @return
     * @throws IOException
     */
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
