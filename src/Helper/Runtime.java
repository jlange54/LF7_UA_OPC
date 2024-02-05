package Helper;

/**
 * This class can be used to get the overall runtime of a program or process in ms, seconds or minutes
 * This is only in here because i was bored and wanted to test some stuff
 */
public class Runtime {

    private static long startTime;

    private static long currentTime () {
        return System.currentTimeMillis();
    }

    public static void start () {
        startTime = currentTime();
    }

    public static void end (timeUnit timeUnit, type type) {
        long endTime = currentTime();
        long duration = endTime - startTime;

        switch (timeUnit) {
            case seconds:
                System.out.println(type.toString()+" finished in: "+duration/1000L+ " seconds");
                break;
            case minutes:
                System.out.println(type.toString()+" finished in: "+duration/60000L+ " minutes");
                break;
            case ms:
            default:
                System.out.println(type.toString()+" finished in: "+duration+ " ms");
                break;
        }
    }

    public enum timeUnit {
        ms, seconds, minutes
    }

    public enum type {
        program, process
    }
}
