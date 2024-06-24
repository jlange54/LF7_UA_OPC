package Object;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

public class AlarmMainStructure {
    @JsonProperty("system_id")
    private String systemId;
    @JsonProperty("alarms")
    private List<Alarm> alarms;

    public List<Alarm> getAlarms() {
        return alarms;
    }

    public void setAlarms(List<Alarm> alarms) {
        this.alarms = alarms;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public static AlarmMainStructure mapFromJson(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(json, AlarmMainStructure.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static class Alarm {
        @JsonProperty("system_id")
        private String systemId;
        @JsonProperty("module_id")
        private String moduleId;
        @JsonProperty("errors")
        private List<Error> errors;

        public String getSystemId() {
            return systemId;
        }

        public void setSystemId(String systemId) {
            this.systemId = systemId;
        }

        public String getModuleId() {
            return moduleId;
        }

        public void setModuleId(String moduleId) {
            this.moduleId = moduleId;
        }

        public List<Error> getErrors() {
            return errors;
        }

        public void setErrors(List<Error> errors) {
            this.errors = errors;
        }

        public static class Error {
            @JsonProperty("code")
            private int code;
            @JsonProperty("msg")
            private String msg;

            public int getCode() {
                return code;
            }

            public void setCode(int code) {
                this.code = code;
            }

            public String getMsg() {
                return msg;
            }

            public void setMsg(String msg) {
                this.msg = msg;
            }

            @Override
            public String toString() {
                return "Error{" +
                        "code=" + code +
                        ", msg='" + msg + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "Alarm{" +
                    ", moduleId='" + moduleId + '\'' +
                    ", errors=" + errors +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "AlarmMainStructure{" +
                "systemId='" + systemId + '\'' +
                ", alarms=" + alarms +
                '}';
    }
}
