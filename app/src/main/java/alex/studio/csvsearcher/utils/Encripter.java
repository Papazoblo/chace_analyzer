package alex.studio.csvsearcher.utils;

import android.util.Base64;

public class Encripter {
    /**
     * Перевод строки в Base64
     * @param val
     * @return
     */
    public static String encript(String val){
        if(val == null || val.isEmpty()){
            return "";
        }else{
            return new String(Base64.encode(val.getBytes(), Base64.DEFAULT));
        }
    }

    /**
     * Перевод Base64 строку
     * @param val
     * @return
     */
    public static String decript(String val){
        if(val == null || val.isEmpty()){
            return "";
        }else{
            return new String(Base64.decode(val.getBytes(), Base64.DEFAULT));
        }
    }
}
