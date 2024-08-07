package Helper;

import Object.Crawl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Debug {
    public static void printList (List<String> inputList) {
        Iterator<String> inputListIterator = inputList.listIterator();
        while (inputListIterator.hasNext()) {
            System.out.println(inputListIterator.next());
        }
    }


    public static List<String> generateTestList(int listLength) {
        List<String> testList = new ArrayList<String>();
        for (int i = 0; i<listLength; i++) {
            String lorem = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec at vestibulum nulla. Ut nec tempor est, sed eleifend nulla. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Donec tristique luctus ultricies. Proin in velit euismod, luctus velit vitae, pulvinar arcu. Cras eget cursus mi. Suspendisse nulla nulla, volutpat a sagittis ac, aliquam vel ligula. Proin quis ex eget mauris maximus ornare in laoreet dolor. Vestibulum vel libero scelerisque, vulputate metus vitae, imperdiet magna. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Nunc vulputate tempus turpis quis tempus. Duis sapien elit, lacinia non urna et, condimentum imperdiet arcu. ";
            String line = i +" // "+ lorem;
            testList.add(line);
        }
        return testList;
    }

    public static List<Crawl> generateCrawlerTest () throws IOException {
        List<String> testCrawl = FileRW.read("./data/sensorDataOutputs/2024-05-13_ExportVikoria.txt");
        List<Crawl> crawlList = new ArrayList<>();

        for (String line : testCrawl) {
            System.out.println(line);
            String raw = line;
            String sensor = Regex.substitution(line, "Sensor:.*(\".*\").*VALUE:",1);
            String value = Regex.substitution(line, "DataValue.*value=(.*),.*statusCode",1);
            String sourceTimestamp = Regex.substitution(line, "sourceTimestamp=(.*),.*sourcePicoseconds",1);
            String serverTimestamp = Regex.substitution(line, "serverTimestamp=(.*),.*serverPicoseconds",1);
            Crawl crawlLine = new Crawl(raw, sensor, value, sourceTimestamp, serverTimestamp);
            crawlList.add(crawlLine);
        }

        return crawlList;
    }
}
