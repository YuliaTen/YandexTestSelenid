package apiUtils.modelMessage;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

// класс корневой для распарсивания приходящего json для списка писем
@JsonIgnoreProperties(ignoreUnknown = true)
public class RootMessage {
    public List<Model> models;

    @Override
    public String toString() {
        return "RootMessage{" +
                "models=" + models +
                '}';
    }

    public List<Model> getModels() {
        return models;
    }

    public void setModels(List<Model> models) {
        this.models = models;
    }



}
