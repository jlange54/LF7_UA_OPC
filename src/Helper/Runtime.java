package Helper;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.Duration;

public class Runtime {

    private static long startTime;
    private static long endTime;

    public static long currentTime () {
        long unixTime = System.currentTimeMillis() / 1000L;
        return unixTime;
    }

    public static void start () {
       startTime = currentTime();
    }

    public static long end () {
        endTime = currentTime();
        long duration = endTime - startTime;
        return duration;
    }
}
