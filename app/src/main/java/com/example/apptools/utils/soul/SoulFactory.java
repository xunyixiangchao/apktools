package com.example.apptools.utils.soul;

import java.util.HashMap;
import java.util.Map;

public class SoulFactory {
    private static Map<Integer, String> urlMap = new HashMap<>();
    public static Integer URL_BUBBLE = 1;
    public static Integer URL_USER = 2;
    public static Integer URL_SIGN = 3;
    public static Integer URL_AVATAR = 4;
    static {
        urlMap.put(URL_BUBBLE, "/bubbling/list");
        urlMap.put(URL_USER, "/v2/user/info");
        urlMap.put(URL_SIGN, "/increase/sign/userSign");
        urlMap.put(URL_AVATAR, "/cuteface/getRegisterRecAvatar");
    }
    private static Map<String, SoulService> serviceMap = new HashMap<>();

    static {
        serviceMap.put(urlMap.get(URL_BUBBLE), new SoulBubbleService());
        serviceMap.put(urlMap.get(URL_USER),new SoulUserInfoService());
        serviceMap.put(urlMap.get(URL_SIGN),new SoulSignService());
        serviceMap.put(urlMap.get(URL_AVATAR),new SoulAvatarService());
    }

    public static SoulService getService(String path) {
        return serviceMap.get(path);
    }
}
