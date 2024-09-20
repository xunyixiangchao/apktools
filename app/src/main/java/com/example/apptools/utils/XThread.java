package com.example.apptools.utils;

import android.os.Handler;
import android.os.Looper;

public class XThread {
    private static Handler mHandler = new Handler(Looper.getMainLooper());


    public static void runOnMain(Runnable runnable){
        mHandler.post(runnable);
    }
}
