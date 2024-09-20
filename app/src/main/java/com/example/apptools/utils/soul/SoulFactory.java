package com.example.apptools.utils.soul;

import java.util.HashMap;
import java.util.Map;

public class SoulFactory {
    private static Map<Integer, String> urlMap = new HashMap<>();
    public static Integer URL_BUBBLE = 1;
    static {
        urlMap.put(URL_BUBBLE, "/bubbling/list");
    }
    private static Map<String, SoulService> serviceMap = new HashMap<>();

    static {
        serviceMap.put(urlMap.get(URL_BUBBLE), new SoulBubbleService());
    }

    public static SoulService getService(String path) {
        return serviceMap.get(path);
    }
}
