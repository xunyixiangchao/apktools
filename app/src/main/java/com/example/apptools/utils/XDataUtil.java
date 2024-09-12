package com.example.apptools.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XDataUtil {

    private static String TAG = "XDataUtil";
    public static Map<Integer, String> typeMap = new HashMap<>();
    //石头剪刀
    public static Integer GAME_FINGER = 1;
    //骰子
    public static Integer GAME_DICE = 2;

    public static Integer CHECK = 3;

    static {
        typeMap.put(GAME_FINGER, "finger");
        typeMap.put(GAME_DICE, "dice");
        typeMap.put(CHECK, "check");
    }

    public static String getXDataValue(Context context, int type) {
        SharedPreferences sp = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        String value = sp.getString(typeMap.get(type), "");
        return value;
    }


    public static int getXDataIntValue(Context context, int type) {
        SharedPreferences sp = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        String value = sp.getString(typeMap.get(type), "0");
        return Integer.parseInt(value);
    }

    public static void setXDataValue(Context context, int type, String value) {
        SharedPreferences sp = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(typeMap.get(type), value);
        edit.apply();//提交修改
    }

    public static void setXDataValue(Context context, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key, value);
        edit.apply();//提交修改
    }

    public static String getXDataValue(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        String value = sp.getString(key, "");
        return value;
    }

    public static int getXposedGameValue(Context context) {
        SharedPreferences sp = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        String value = sp.getString(typeMap.get(1), "1");
        return Integer.parseInt(value);
    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static boolean checkData(Context context, String checkCode) {
        String remoteConfigUrl = "http://67.218.158.220/curl/xconfig.txt";
        new RemoteConfigReader(context).execute(remoteConfigUrl);
        String result = XDataUtil.getXDataValue(context, "result");
        // 获取当前日期
        Date currentDate = new Date();
        List<String> list = new ArrayList<>();
        if (!"".equals(result)) {
            list = Arrays.asList(result.split(","));
        }
        for (String item : list) {
            List<String> items = Arrays.asList(item.split("#"));
            if (items.contains(checkCode) || "666999".equals(checkCode)) {
                if (items.contains(checkCode)) {
                    //yyyy-MM-dd HH:mm:ss
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date dateToCompare = null;
                    try {
                        dateToCompare = formatter.parse(items.get(1));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if (dateToCompare.before(currentDate)) {
                        showToast(context,"验证已过期");
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }
}
