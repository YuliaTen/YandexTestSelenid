package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

// класс для хранения настроек из файла myConf.properties
public class ConfProperties {
    protected static Properties PROPERTIES;

    static {
        //указание  файла с настройками
        String path = "myConf.properties";
        try (InputStream inputStream = ConfProperties.class.getClassLoader().getResourceAsStream(path)){
            PROPERTIES = new Properties();
            PROPERTIES.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * метод для возврата строки со значением из файла с настройками
     */
    public static String getProperty(String key) {
        return PROPERTIES.getProperty(key);
    }
}
