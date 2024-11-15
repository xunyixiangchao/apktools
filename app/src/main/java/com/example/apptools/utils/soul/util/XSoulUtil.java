package com.example.apptools.utils.soul.util;

import android.content.Context;

import cn.soulapp.android.component.home.api.user.user.a;
import cn.soulapp.android.component.planet.soulmatch.robot.bean.SoulMatchParams;
import hi.g;


public class XSoulUtil {
    public static void sign(Context service) {
        sf.a.a.c(null);
    }

    public static void getRegisterAvatars(Context service) {
        a.M(null);
    }

    public static void startMatch(Context service){
        g x = new g();
        SoulMatchParams params = new SoulMatchParams();
        params.setGender("UNKNOWN");
        params.setX(0.0f);
        params.setY(0.0f);
        params.setStartMatchType("0");
        x.startMatch(params);
    }
}
