package utils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

// класс для кодирования данных в формате MyBase64
public final class MyBase64 {
    public static final Charset CODE = StandardCharsets.UTF_8;

    private MyBase64() {
    }

    //закодировать
    public static String encode(final String value) {
        if (null == value) {
            return null;
        } else
            return new String(java.util.Base64.getEncoder().encode(value.getBytes(CODE)));
    }

    //раскодировать
    public static String decode(final String value) {
        if (null == value) {
            return null;
        } else
            return new String(java.util.Base64.getDecoder().decode(value.getBytes(CODE)));
    }

}
