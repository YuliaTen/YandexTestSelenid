package utils.apiUtils;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.qameta.allure.Step;
import java.util.ArrayList;

//  класс создания json для запроса списка писем
public class JsonRequestMessageClass {
    @JsonProperty("models")
    private ArrayList<Model> models;
    @JsonProperty("_ckey")
    private String ckey;

    private void setModels(ArrayList<Model> models) {
        this.models = models;
    }

    private void setCkey(String ckey) {
        this.ckey = ckey;
    }

    @Step("Создаем json для запроса списка писем")
    public JsonRequestMessageClass getJsonClass(String ckey, String numFolder) {
        Params params = new Params();
        params.setCurrent_folder(numFolder);
        params.setSort_type("date");
        params.setWith_pins("yes");
        Model model = new Model();
        model.setName("messages");
        model.setParams(params);
        ArrayList<Model> mods = new ArrayList<>();
        mods.add(model);
        JsonRequestMessageClass jsonClass = new JsonRequestMessageClass();
        jsonClass.setCkey(ckey);
        jsonClass.setModels(mods);
        return jsonClass;
    }


    private class Model {
        @JsonProperty
        String name;
        @JsonProperty
        Params params;

        void setName(String name) {
            this.name = name;
        }

        void setParams(Params params) {
            this.params = params;
        }
    }

    private class Params {
        @JsonProperty
        String current_folder;
        @JsonProperty
        String sort_type;
        @JsonProperty
        String with_pins;

        void setCurrent_folder(String current_folder) {
            this.current_folder = current_folder;
        }

        void setSort_type(String sort_type) {
            this.sort_type = sort_type;
        }

        void setWith_pins(String with_pins) {
            this.with_pins = with_pins;
        }
    }

}
