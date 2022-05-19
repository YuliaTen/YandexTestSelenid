package utils.apiUtils.modelMessage;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Data {
    public ArrayList<MessageModels> message;

    @Override
    public String toString() {
        return "Data{" +
                "message=" + message +
                '}';
    }

    public ArrayList<MessageModels> getMessage() {
        return message;
    }

    public void setMessage(ArrayList<MessageModels> message) {
        this.message = message;
    }
}
