package dataSource;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public final class Base64 {
 public static final Charset CODE = StandardCharsets.UTF_8;

 private Base64(){
    }

 public  static String encode(final String value){
     if (null==value){
         return null;
     }
     else
         return new String(java.util.Base64.getEncoder().encode(value.getBytes(CODE)));
 }

    public  static String decode(final String value){
        if (null==value){
            return null;
        }
        else
            return new String(java.util.Base64.getDecoder().decode(value.getBytes(CODE)));
    }

}
