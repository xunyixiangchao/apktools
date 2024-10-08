package com.example.apptools.utils.soul;

import java.util.HashMap;
import java.util.Map;

public class SoulFactory {
    private static Map<Integer, String> urlMap = new HashMap<>();
    public static Integer URL_BUBBLE = 1;
    public static Integer URL_USER = 2;
    static {
        urlMap.put(URL_BUBBLE, "/bubbling/list");
        urlMap.put(URL_USER, "/v2/user/info");
    }
    private static Map<String, SoulService> serviceMap = new HashMap<>();

    static {
        serviceMap.put(urlMap.get(URL_BUBBLE), new SoulBubbleService());
        serviceMap.put(urlMap.get(URL_USER),new SoulUserInfoService());
    }

    public static SoulService getService(String path) {
        return serviceMap.get(path);
    }
}
