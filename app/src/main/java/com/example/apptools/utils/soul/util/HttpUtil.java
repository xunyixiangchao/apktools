package com.example.apptools.utils.soul.util;

import java.util.List;
import java.util.Map;

import okhttp3.Request;

public class HttpUtil {
    public static final String FIELD_DELIMITER = "&";
    public static final String KEY_VALUE_DELIMITER = "=";


    public static void sendRequest(String url,String method, Map<String,Object> map){
        String genUrl = genRequestUrl(url, map);

        Request.Builder builder = new Request.Builder();

    }




    private static String genRequestUrl(String str, Map<String, Object> map) {
        boolean z11 = true;
        StringBuilder sb2 = new StringBuilder(str);
        for (String str2 : map.keySet()) {
            if (str2 != null && map.get(str2) != null) {
                Object obj = map.get(str2);
                if (obj instanceof List) {
                    for (Object obj2 : (List) obj) {
                        if (z11) {
                            sb2.append("?");
                            z11 = false;
                        } else {
                            sb2.append("&");
                        }
                        sb2.append(str2);
                        sb2.append(KEY_VALUE_DELIMITER);
                        sb2.append(obj2);
                    }
                } else {
                    if (z11) {
                        sb2.append("?");
                        z11 = false;
                    } else {
                        sb2.append("&");
                    }
                    sb2.append(str2);
                    sb2.append(KEY_VALUE_DELIMITER);
                    sb2.append(obj);
                }
            }
        }
        return sb2.toString();
    }
}
