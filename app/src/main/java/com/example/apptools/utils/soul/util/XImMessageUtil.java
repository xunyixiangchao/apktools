package com.example.apptools.utils.soul.util;

import android.util.Log;

import com.example.apptools.utils.GsonUtil;
import com.example.apptools.utils.LogToFile;
import com.soul.im.protos.CommandGroup;

import cn.soulapp.imlib.msg.ImMessage;

public class XImMessageUtil {
    private static String TAG = XImMessageUtil.class.getSimpleName();

    public static void logMessage(ImMessage imMessage) {
        Log.i(TAG, "发送：" + imMessage.toString());
        Log.i(TAG, "发送：" + GsonUtil.build().toJson(imMessage));
        LogToFile.writeTag(TAG, "发送：" + GsonUtil.build().toJson(imMessage));
    }

    public static void recvMessage(CommandGroup commandGroup) {
        Log.i(TAG, "接收：" + commandGroup.toString());
        Log.i(TAG, "接收：" + GsonUtil.build().toJson(commandGroup));
        LogToFile.writeTag(TAG, "接收：" + GsonUtil.build().toJson(GsonUtil.build().toJson(commandGroup)));
    }
}
