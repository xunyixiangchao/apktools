package com.example.apptools.utils;

import android.content.Context;
import android.widget.Toast;

public class XToast {

    public static void showToast(Context context, String message) {
        XThread.runOnMain(() -> Toast.makeText(context, message, Toast.LENGTH_SHORT).show());
    }
}
