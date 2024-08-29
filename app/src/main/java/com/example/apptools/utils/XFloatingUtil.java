package com.example.apptools.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.widget.Toast;

import com.example.apptools.service.FloatingWindowService;

public class XFloatingUtil {


    public static void init(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(context)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + context.getPackageName()));
                ((Activity) context).startActivityForResult(intent, 111);
                Toast.makeText(context, "需要悬浮窗权限才能继续", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(context, FloatingWindowService.class);
                context.startService(intent);
            }
        }
        Intent intent = new Intent(context, FloatingWindowService.class);
        context.startService(intent);
    }
}
