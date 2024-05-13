package Object;

public class Crawl {
    private String sensor;
    private String value;
    private String sourceTimestamp;
    private String serverTimestamp;

    public Crawl(String sensor, String value, String sourceTimestamp, String serverTimestamp) {
        this.sensor = sensor;
        this.value = value;
        this.sourceTimestamp = sourceTimestamp;
        this.serverTimestamp = serverTimestamp;
    }

    public String getSensor() {
        return sensor;
    }

    public void setSensor(String sensor) {
        this.sensor = sensor;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getSourceTimestamp() {
        return sourceTimestamp;
    }

    public void setSourceTimestamp(String sourceTimestamp) {
        this.sourceTimestamp = sourceTimestamp;
    }

    public String getServerTimestamp() {
        return serverTimestamp;
    }

    public void setServerTimestamp(String serverTimestamp) {
        this.serverTimestamp = serverTimestamp;
    }

    @Override
    public String toString() {
        return "Crawl{" +
                "sensor='" + sensor + '\'' +
                ", value='" + value + '\'' +
                ", sourceTimestamp='" + sourceTimestamp + '\'' +
                ", serverTimestamp='" + serverTimestamp + '\'' +
                '}';
    }
}
