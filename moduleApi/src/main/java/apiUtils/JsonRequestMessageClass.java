package apiUtils;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.qameta.allure.Step;
import java.util.ArrayList;
import java.util.List;

//  класс создания json для запроса списка писем
public class JsonRequestMessageClass {
    @JsonProperty("models")
    private List<Model> models;
    @JsonProperty("_ckey")
    private String ckey;

    public JsonRequestMessageClass(List<Model> models, String ckey) {
        this.models = models;
        this.ckey = ckey;
    }

    @Step("Создаем json для запроса списка писем")
    public static JsonRequestMessageClass getJsonClass(String ckey, String numFolder) {
        JsonRequestMessageClass.Params params = new JsonRequestMessageClass.Params(numFolder,"date","yes");
        JsonRequestMessageClass.Model model = new JsonRequestMessageClass.Model("messages", params);
        List<Model> mods = new ArrayList<>();
        mods.add(model);
        return new JsonRequestMessageClass(mods, ckey);
    }

    private static class Model {
        @JsonProperty
        String name;
        @JsonProperty
        Params params;

        public Model(String name, Params params) {
            this.name = name;
            this.params = params;
        }
    }

    private static class Params {
        @JsonProperty
         String current_folder;
        @JsonProperty
         String sort_type;
        @JsonProperty
         String with_pins;

        public Params(String current_folder, String sort_type, String with_pins) {
            this.current_folder = current_folder;
            this.sort_type = sort_type;
            this.with_pins = with_pins;
        }
    }

}
