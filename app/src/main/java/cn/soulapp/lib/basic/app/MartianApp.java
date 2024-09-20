package cn.soulapp.lib.basic.app;

import android.app.Application;

public class MartianApp extends Application {

    private static MartianApp a;

    public static MartianApp b() {
        return a;
    }

}
