package cn.soulapp.lib.basic.app;

import android.app.Application;

public class MartianApp extends Application {

    private static MartianApp a;

    @Override
    public void onCreate() {
        super.onCreate();
        a = this;
    }

    public static MartianApp b() {
        return a;
    }

}
