package com.example.apptools.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
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
import java.util.Random;

public class XDataUtil {

    private static String VERSION = "1.0.1";

    private static String ON = "1";

    private static String OFF = "0";

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

    public static Integer HIDE_AD = 9;

    public static Integer AVATAR = 10;

    public static Integer LOCAL_RECALL = 11;

    public static Integer CLOSE_WATER = 12;

    public static Integer CLOSE_CHAT_LIMIT = 13;

    public static Integer NET_CONFIG = 14;

    public static Integer SEND_BUBBLE = 15;

    public static Integer AUTO_SEND_BUBBLE = 16;

    public static Integer IS_SIGN = 17;

    public static Integer NET_SWITCH = 18;

    public static Integer URL_FILTER=19;


    public static String CONFIG_URL = "http://67.218.158.220/curl/xconfig.txt";
    public static String CHECK_USER_URL = "http://67.218.158.220/curl/checkUser.txt";

    static {
        typeMap.put(GAME_FINGER, "FINGER");
        typeMap.put(GAME_DICE, "DICE");
        typeMap.put(CHECK, "CHECK");
        typeMap.put(RECALL, "RECALL");
        typeMap.put(NET_CHECK, "NET_CHECK");
        typeMap.put(BUBBLE, "BUBBLE");
        typeMap.put(IS_CHECK, "IS_CHECK");
        typeMap.put(BUBBLE_SIZE, "BUBBLE_SIZE");
        typeMap.put(AVATAR, "AVATAR");
        typeMap.put(LOCAL_RECALL, "LOCAL_RECALL");
        typeMap.put(CLOSE_WATER, "CLOSE_WATER");
        typeMap.put(CLOSE_CHAT_LIMIT, "CLOSE_CHAT_LIMIT");
        typeMap.put(NET_CONFIG, "NET_CONFIG");
        typeMap.put(SEND_BUBBLE, "SEND_BUBBLE");
        typeMap.put(AUTO_SEND_BUBBLE, "AUTO_SEND_BUBBLE");
        typeMap.put(IS_SIGN, "IS_SIGN");
        typeMap.put(NET_SWITCH, "NET_SWITCH");
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
        if (TextUtils.isEmpty(value)) {
            value = "0";
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
        return isSwitch(context,XDataUtil.IS_CHECK);
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

        new NetAsyncUtil(context, typeMap.get(NET_CHECK)).execute(CHECK_USER_URL);
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

    public static void hideAd(Context context) {
        commonSwitch(context,XDataUtil.HIDE_AD);
    }

    /**
     * false是关闭广告
     *
     * @param context
     * @return
     */
    public static boolean isHideAd(Context context) {
        return isSwitch(context,XDataUtil.HIDE_AD);
    }


    public static void localRecall(Context context) {
        commonSwitch(context,XDataUtil.LOCAL_RECALL);
    }

    public static boolean isLocalRecall(Context context) {
        return isSwitch(context,XDataUtil.LOCAL_RECALL);
    }

    public static void closeWater(Context context) {
        commonSwitch(context,XDataUtil.CLOSE_WATER);
    }

    public static boolean isCloseWater(Context context) {
        return isSwitch(context,XDataUtil.CLOSE_WATER);
    }


    public static void closeChatLimit(Context context) {
        commonSwitch(context,XDataUtil.CLOSE_CHAT_LIMIT);
    }

    public static boolean isCloseChatLimit(Context context) {
        return isSwitch(context,XDataUtil.CLOSE_CHAT_LIMIT);
    }

    public static void checkConfig(Context context) {
        String result = XDataUtil.getXDataValue(context, typeMap.get(NET_CONFIG));
        if (TextUtils.isEmpty(result) || !VERSION.equals(result.split("#")[0])) {
            defaultAll(context);
            if (!TextUtils.isEmpty(result)) {
                XToast.showToast(context, result.split("#")[1]);
                XDiaLogUtil.showHintDialog(context, result.split("#")[1]);
            }
        }
    }

    public static boolean isAutoBubble(Context context) {
        return isSwitch(context,XDataUtil.AUTO_SEND_BUBBLE);
    }

    public static void autoBubble(FloatingWindowService context) {
        if (!XDataUtil.checkData(context, XDataUtil.getXDataValue(context, XDataUtil.CHECK), true)) {
            return;
        }
        XDataUtil.setXDataValue(context, XDataUtil.AUTO_SEND_BUBBLE, isAutoBubble(context) ? "0" : "1");
        if (!isAutoBubble(context)) {
            context.clearPost(context.autoBubbleRun);
            XToast.showToast(context, "自动BUBBLE已关闭");
        } else {
            context.delayPost(context.autoBubbleRun, context.autoDelayTime + new Random().nextInt(context.radomTime));
            XToast.showToast(context, "自动BUBBLE已开启");
        }
    }

    public static void commonSwitch(Context context, Integer type) {
        if (!XDataUtil.checkData(context, XDataUtil.getXDataValue(context, XDataUtil.CHECK), true)) {
            return;
        }
        XDataUtil.setXDataValue(context, type, isSwitch(context, type) ? OFF : ON);
        if (!isSwitch(context, type)) {
            XToast.showToast(context, "开关已关闭");
        } else {
            XToast.showToast(context, "开关已开启");
        }
    }

    public static boolean isSwitch(Context context, Integer type) {
        String recallValue = XDataUtil.getXDataValue(context, type);
        return ON.equals(recallValue);
    }

    public static void sign(FloatingWindowService service) {
        Date now = new Date(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(now);
        XDataUtil.setXDataValue(service, XDataUtil.IS_SIGN, date);
        XToast.showToast(service, "今日已签到！");
    }

    public static boolean isSigned(Context context) {
        String xDataValue = XDataUtil.getXDataValue(context, XDataUtil.IS_SIGN);
        Date now = new Date(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(now);
        if (!TextUtils.isEmpty(xDataValue) && date.equals(xDataValue)) {
            return true;
        } else {
            return false;
        }
    }
}
