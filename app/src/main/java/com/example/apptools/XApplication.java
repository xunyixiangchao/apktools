package com.example.apptools;

import android.app.Application;


public class XApplication extends Application {
    private static XApplication a;

    @Override
    public void onCreate() {
        super.onCreate();

        a = this;
    }

    public static XApplication b() {
        return a;
    }
}
