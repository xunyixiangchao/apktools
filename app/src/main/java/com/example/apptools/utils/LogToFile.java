package com.example.apptools.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.example.apptools.utils.soul.SoulUserInfoService;
import com.example.apptools.utils.soul.bean.user.UserData;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LogToFile {
    private static final String LOG_FILE = "Slog-%s.txt";
    private static final String BUBBLE_FILE_TAG = "Sbubble-%s.txt";
    private static final String USER_FILE_TAG = "SUser-%s.txt";
    private static final String DEFAULT_PATH = "/storage/emulated/0/apptools";
    private static final String BUBBLE_PATH = "/storage/emulated/0/apptools/bubble";
    private static final String USER_PATH = "/storage/emulated/0/apptools/user";

    /**
     * 1.不带tag
     * invoke-static {v2}, Lcom/example/apptools/utils/LogToFile;->write(Ljava/lang/String;)V
     * 2.带tag
     * invoke-static {v1,v2}, Lcom/example/apptools/utils/LogToFile;->write(Ljava/lang/String;Ljava/lang/String;)V
     */
    public static void write(String content) {
        write(null, content);
    }

    /**
     * 1.不带tag
     * invoke-static {v2}, Lcom/example/apptools/utils/LogToFile;->write(Ljava/lang/String;)V
     * 2.带tag
     * invoke-static {v1,v2}, Lcom/example/apptools/utils/LogToFile;->write(Ljava/lang/String;Ljava/lang/String;)V
     */
    public static void write(String tag, String content) {
        try {
            Date now = new Date(System.currentTimeMillis());
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String date = format.format(now);
            Format format1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
            String textData = format1.format(now);
            String fileName = String.format(LOG_FILE, date);
            File logFile = new File(DEFAULT_PATH, fileName);
            if (!logFile.exists()) {
                File parentDir = logFile.getParentFile();
                if (parentDir != null && !parentDir.exists()) {
                    boolean dirsCreated = parentDir.mkdirs();
                }
                logFile.createNewFile();
            }
            FileWriter fw = new FileWriter(logFile, true);
            fw.write(textData + "-->" + (tag != null ? tag + ": " : ": ") + content + "\n");
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeBubble(String content) {
        try {
            Date now = new Date(System.currentTimeMillis());
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String date = format.format(now);
            Format format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String textData = format1.format(now);
            String fileName = String.format(BUBBLE_FILE_TAG, date);
            File logFile = new File(BUBBLE_PATH, fileName);
            if (!logFile.exists()) {
                File parentDir = logFile.getParentFile();
                if (parentDir != null && !parentDir.exists()) {
                    boolean dirsCreated = parentDir.mkdirs();
                }
                logFile.createNewFile();
            }
            FileWriter fw = new FileWriter(logFile, true);
            fw.write(textData + "-->" + content + "\n");
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> readBubble(String date) {
        List<String> list = new ArrayList<>();
        try {
            Date now = new Date(System.currentTimeMillis());
            if (TextUtils.isEmpty(date)) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                date = format.format(now);
            }
            String fileName = String.format(BUBBLE_FILE_TAG, date);
            File logFile = new File(BUBBLE_PATH, fileName);
            try (BufferedReader br = new BufferedReader(new FileReader(logFile))) {
                String line;
                while ((line = br.readLine()) != null) {
                    // 处理每一行
                    Log.i("LogToFile",line);
                    list.add(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void writeUser(Context context, String fileName) {
        if (SoulUserInfoService.userData == null) {
            XToast.showToast(context, "没有要保存的用户信息");
            return;
        }
        if (TextUtils.isEmpty(fileName)) {
            fileName = "default";
        }
        try {
            fileName = String.format(USER_FILE_TAG, fileName);
            File logFile = new File(USER_PATH, fileName);
            if (!logFile.exists()) {
                File parentDir = logFile.getParentFile();
                if (parentDir != null && !parentDir.exists()) {
                    boolean dirsCreated = parentDir.mkdirs();
                }
                logFile.createNewFile();
            }
            UserData userData = SoulUserInfoService.userData;
            FileWriter fw = new FileWriter(logFile, true);
            fw.write(userData.getUserIdEcpt() + "-->" + GsonUtil.build().toJson(userData) + "\n");
            fw.close();
            SoulUserInfoService.userData = null;
            XToast.showToast(context, "保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            XToast.showToast(context, "保存异常" + e.toString());
        }
    }
}
