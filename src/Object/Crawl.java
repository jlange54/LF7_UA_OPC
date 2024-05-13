package Object;

public class Crawl {
    private String raw;
    private String sensor;
    private String value;
    private String sourceTimestamp;
    private String serverTimestamp;

    public Crawl(String raw, String sensor, String value, String sourceTimestamp, String serverTimestamp) {
        this.raw = raw;
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

    public void setRaw(String raw) {
        this.raw = raw;
    }

    public String getRaw() {
        return raw;
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
                "raw='" + raw + '\'' +
                ", sensor='" + sensor + '\'' +
                ", value='" + value + '\'' +
                ", sourceTimestamp='" + sourceTimestamp + '\'' +
                ", serverTimestamp='" + serverTimestamp + '\'' +
                '}';
    }
}
