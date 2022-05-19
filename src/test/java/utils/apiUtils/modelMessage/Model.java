package utils.apiUtils.modelMessage;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Model {
    public String name;
    public String status;
    public Data data;

    @Override
    public String toString() {
        return "Model{" +
                "name='" + name + '\'' +
                ", status='" + status + '\'' +
                ", data=" + data +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
