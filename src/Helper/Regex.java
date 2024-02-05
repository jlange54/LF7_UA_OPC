package Helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {

    /**
     * This method returns a String after matching a specified regular expression to a specified line
     *
     * @param line
     * @param regex
     * @param returnGroup
     * @return
     */
    public static String substitution(String line, String regex, int returnGroup) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()){
            return matcher.group(returnGroup);
        }
        return null;
    }
}
