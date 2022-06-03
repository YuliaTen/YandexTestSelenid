package apiUtils.modelMessage;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Data {
    public List<Message> message;

    @Override
    public String toString() {
        return "Data{" +
                "message=" + message +
                '}';
    }

    public List<Message> getMessage() {
        return message;
    }

    public void setMessage(List<Message> message) {
        this.message = message;
    }
}
