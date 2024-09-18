package com.example.apptools.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

public class XToast {
    private static Handler mHandler = new Handler(Looper.getMainLooper());

    public static void showToast(Context context, String message) {
        mHandler.post(() -> Toast.makeText(context, message, Toast.LENGTH_SHORT).show());
    }
}
