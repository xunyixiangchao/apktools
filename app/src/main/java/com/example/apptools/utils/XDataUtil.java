package com.example.apptools.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.text.Editable;
import android.text.Selection;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.apptools.service.FloatingWindowService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.soul.android.component.SoulRouter;

public class XDataUtil {

    private static String TAG = "XDataUtil";
    public static Map<Integer, String> typeMap = new HashMap<>();
    //石头剪刀
    public static Integer GAME_FINGER = 1;
    //骰子
    public static Integer GAME_DICE = 2;

    public static Integer CHECK = 3;

    public static Integer RECALL = 4;

    public static Integer NET_CHECK = 5;

    public static Integer BUBBLE = 6;

    public static Integer IS_CHECK = 7;

    public static Integer BUBBLE_SIZE = 8;

    static {
        typeMap.put(GAME_FINGER, "FINGER");
        typeMap.put(GAME_DICE, "DICE");
        typeMap.put(CHECK, "CHECK");
        typeMap.put(RECALL, "RECALL");
        typeMap.put(NET_CHECK, "NET_CHECK");
        typeMap.put(BUBBLE, "BUBBLE");
        typeMap.put(IS_CHECK, "IS_CHECK");
        typeMap.put(BUBBLE_SIZE, "BUBBLE_SIZE");
    }

    public static String getXDataValue(Context context, int type) {
        SharedPreferences sp = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        String value = sp.getString(typeMap.get(type), "");
        return value;
    }


    public static int getXDataIntValue(Context context, int type) {
        return getXDataIntValue(context, type, null);
    }

    public static int getXDataIntValue(Context context, int type, Map<String, String> content) {
        SharedPreferences sp = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        String value = sp.getString(typeMap.get(type), "0");
        StringBuilder stringBuilder = new StringBuilder();
        if (type == RECALL) {
            if (isRecall(context)) {
                XToast.showToast(context, value + "已为您拦截一个撤回消息");
            }
            if (content != null) {
                for (Map.Entry<String, String> entry : content.entrySet()) {
                    stringBuilder.append(entry.getKey())
                            .append("=")
                            .append(entry.getValue())
                            .append(",");
                }
            }
            LogToFile.write(typeMap.get(RECALL), value + stringBuilder);
        }
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

    public static boolean isChecked(Context context) {
        return "1".equals(XDataUtil.getXDataValue(context, XDataUtil.IS_CHECK));
    }

    public static boolean checkData(Context context, String checkCode, boolean isNeedCheck) {
        if (isNeedCheck && !isChecked(context)) {
            XDataUtil.showToast(context, "请先完成验证！");
            XDataUtil.defaultAll(context);
            return false;
        }
        if ("666999".equals(checkCode)) {
            XDataUtil.setXDataValue(context, XDataUtil.CHECK, checkCode);
            XDataUtil.setXDataValue(context, XDataUtil.IS_CHECK, "1");
            return true;
        }
        String checkUrl = "http://67.218.158.220/curl/xconfig.txt";
        new NetAsyncUtil(context, typeMap.get(NET_CHECK)).execute(checkUrl);
        String result = XDataUtil.getXDataValue(context, typeMap.get(NET_CHECK));
        // 获取当前日期
        Date currentDate = new Date();
        List<String> list = new ArrayList<>();
        if (!"".equals(result)) {
            list = Arrays.asList(result.split(","));
        }
        for (String item : list) {
            List<String> items = Arrays.asList(item.split("#"));
            String newCheckCode;
            if (checkCode.contains("#")) {
                newCheckCode = checkCode;
                checkCode = newCheckCode.split("#")[0];
            }
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
                    showToast(context, "验证已过期");
                    XDataUtil.defaultAll(context);
                    return false;
                }
                XDataUtil.setXDataValue(context, XDataUtil.CHECK, item);
                return true;
            }
        }
        XDataUtil.showToast(context, "请先完成验证！");
        XDataUtil.defaultAll(context);
        return false;
    }

    public static void defaultAll(Context context) {
        SharedPreferences sp = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        for (String value : typeMap.values()) {
            if (typeMap.get(RECALL).equals(value)) {
                edit.putString(value, "9");
                edit.apply();//提交修改
                continue;
            }
            edit.putString(value, "");
            edit.apply();//提交修改
        }
    }

    public static void recall(Context context) {
        if (!XDataUtil.checkData(context, XDataUtil.getXDataValue(context, XDataUtil.CHECK), true)) {
            return;
        }
        XDataUtil.setXDataValue(context, XDataUtil.RECALL, isRecall(context) ? "9" : "23");
        if (!isRecall(context)) {
            XToast.showToast(context, "防撤已关闭");
        } else {
            XToast.showToast(context, "防撤已开启");
        }
    }

    public static boolean isRecall(Context context) {
        String recallValue = XDataUtil.getXDataValue(context, XDataUtil.RECALL);
        return "23".equals(recallValue);
    }

    public static void saveUser(Context context) {
        if (!XDataUtil.checkData(context, XDataUtil.getXDataValue(context, XDataUtil.CHECK), true)) {
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        EditText editText = new EditText(context);

        Editable editable = editText.getText();
        if (editable != null) {
            Selection.setSelection(editable, editable.length());
        }
        builder.setTitle("保存文件名");
        builder.setView(editText);
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("确定",
                (dialog, which) -> XThread.runOnMain(() ->
                        LogToFile.writeUser(context, editable.toString()))
        );
        AlertDialog dialog = builder.create();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
        } else {
            dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        }
        dialog.show();
    }
}
