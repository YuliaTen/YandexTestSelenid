package utils.apiUtils.modelMessage;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

// класс корневой для распарсивания приходящего json для списка писем
@JsonIgnoreProperties(ignoreUnknown = true)
public class RootMessage {
    public ArrayList<Model> models;

    @Override
    public String toString() {
        return "RootMessage{" +
                "models=" + models +
                '}';
    }

    public ArrayList<Model> getModels() {
        return models;
    }

    public void setModels(ArrayList<Model> models) {
        this.models = models;
    }



}
