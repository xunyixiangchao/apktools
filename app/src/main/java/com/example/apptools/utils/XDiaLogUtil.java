package com.example.apptools.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.text.Editable;
import android.text.InputType;
import android.text.Selection;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import cn.soul.android.component.SoulRouter;

import com.example.apptools.service.FloatingWindowService;
import com.example.apptools.utils.soul.bean.bubble.BubblingListItem;
import com.example.apptools.utils.soul.util.BubbleUtil;
import com.example.apptools.utils.soul.util.XSocket;
import com.example.apptools.utils.soul.util.XSoulUtil;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class XDiaLogUtil {

    private static Map<String, String[]> list = new HashMap<>();

    static {
        list.put("游戏", new String[]{"剪刀石头布", "骰子"});
        list.put("BUBBLE", new String[]{"BUBBLE列表", "获取BUBBLE列表", "发送BUBBLE"});
        list.put("其他", new String[]{"跳转", "保存", "验证", "关闭", "签到","连接"});
    }

    public static void showGame(Context context, Integer type) {
        if (!XDataUtil.checkData(context, XDataUtil.getXDataValue(context, XDataUtil.CHECK), true)) {
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        EditText editText = new EditText(context);
        // 设置输入类型为数字
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);

        editText.setText(XDataUtil.getXDataValue(context, type));
        Editable editable = editText.getText();
        if (editable != null) {
            Selection.setSelection(editable, editable.length());
        }
        builder.setTitle(type.equals(XDataUtil.GAME_FINGER) ? "布1，剪刀2，锤头3" : "1-6");
        builder.setView(editText);
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("确定", (dialog, which) ->
                XDataUtil.setXDataValue(context, type, editText.getText().toString()));
        AlertDialog dialog = builder.create();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
        } else {
            dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        }
        dialog.show();
    }

    public static void showCheck(FloatingWindowService context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        EditText editText = new EditText(context);
        // 设置输入类型为数字
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);

        Editable editable = editText.getText();
        if (editable != null) {
            Selection.setSelection(editable, editable.length());
        }
        builder.setTitle("输入验证码");
        builder.setView(editText);
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("确定", (dialog, which) -> {
                    if (XDataUtil.checkData(context, editable.toString(), false)) {
                        XDataUtil.setXDataValue(context, XDataUtil.IS_CHECK, "1");
                        XDataUtil.showToast(context, "验证成功！");
                    } else {
                        XDataUtil.showToast(context, "验证失败！");
                        XDataUtil.setXDataValue(context, XDataUtil.CHECK, "");
                    }
                }
        );
        AlertDialog dialog = builder.create();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
        } else {
            dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        }
        dialog.show();
    }

    public static void jumpUser(Context context) {
        if (!XDataUtil.checkData(context, XDataUtil.getXDataValue(context, XDataUtil.CHECK), true)) {
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        EditText editText = new EditText(context);

        Editable editable = editText.getText();
        if (editable != null) {
            Selection.setSelection(editable, editable.length());
        }
        builder.setTitle("输入用户Id");
        builder.setView(editText);
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("确定",
                (dialog, which) -> XThread.runOnMain(() ->
                        SoulRouter.i().e("/chat/conversationActivity").w("userIdEcpt", editable.toString()).d())
        );
        AlertDialog dialog = builder.create();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
        } else {
            dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        }
        dialog.show();
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

    public static void avatar(Context context) {
        if (!XDataUtil.checkData(context, XDataUtil.getXDataValue(context, XDataUtil.CHECK), true)) {
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        EditText editText = new EditText(context);
        editText.setText(XDataUtil.getXDataValue(context, XDataUtil.AVATAR));
        Editable editable = editText.getText();
        if (editable != null) {
            Selection.setSelection(editable, editable.length());
        }
        builder.setTitle("头像");
        builder.setView(editText);
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("确定",
                (dialog, which) -> XThread.runOnMain(() ->
                        XDataUtil.setXDataValue(context, XDataUtil.AVATAR, editable.toString()))
        );
        AlertDialog dialog = builder.create();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
        } else {
            dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        }
        dialog.show();
    }

    public static void sendBubble(Context context) {
        if (!XDataUtil.checkData(context, XDataUtil.getXDataValue(context, XDataUtil.CHECK), true)) {
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        EditText editText = new EditText(context);
        Editable editable = editText.getText();
        if (editable != null) {
            Selection.setSelection(editable, editable.length());
        }
        builder.setTitle("发个Bubble");
        builder.setView(editText);
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("确定",
                (dialog, which) -> XThread.runOnMain(() ->
                        BubbleUtil.sendBubble(context, editable.toString())
                ));
        AlertDialog dialog = builder.create();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
        } else {
            dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        }
        dialog.show();
    }

    public static void showListDialog(FloatingWindowService service, String item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(service);
        String[] items = list.get(item);
        builder.setItems(items, (dialog, which) -> {
            switch (items[which]) {
                case "BUBBLE列表":
                    service.list = LogToFile.readBubble(null);
                    if (service.list.size() > 0) {
                        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                                WindowManager.LayoutParams.WRAP_CONTENT,
                                WindowManager.LayoutParams.WRAP_CONTENT,
                                Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ?
                                        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY :
                                        WindowManager.LayoutParams.TYPE_PHONE,
                                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                                PixelFormat.TRANSLUCENT);

                        int bubbleSize = XDataUtil.getXDataIntValue(service, XDataUtil.BUBBLE_SIZE);
//                    if (bubbleSize == 0 || bubbleSize != list.size()) {
                        String data = service.list.get(service.list.size() - 1);
                        String[] split = data.split("-->");
                        String dataString = split[1];
                        Type listType = new TypeToken<List<BubblingListItem>>() {
                        }.getType();
//                // 将JSON字符串转换为List集合
                        List<BubblingListItem> bubblingList = GsonUtil.build().fromJson(dataString, listType);
                        bubblingList.get(0).setTopDate(split[0]);
                        service.adapter.setData(bubblingList, 0);
                        service.recyclerView.scrollToPosition(0);
                        service.scrollPosition = 0;
                        XDataUtil.setXDataValue(service, XDataUtil.BUBBLE_SIZE, String.valueOf(service.list.size()));
                        service.windowManager.addView(service.recyLayout, params);
                        service.handler.postDelayed(service.runnable, service.delayMillis);
//                    }
                    } else {
                        XToast.showToast(service, "还没有BUBBLE数据");
                    }
                    break;
                case "获取BUBBLE列表":
                    BubbleUtil.requestBubbleList(service);
                    break;
                case "发送BUBBLE":
                    XDiaLogUtil.sendBubble(service);
                    break;
                case "剪刀石头布":
                    // 剪刀石头布
                    XDiaLogUtil.showGame(service, XDataUtil.GAME_FINGER);
                    break;
                case "骰子":
                    //
                    XDiaLogUtil.showGame(service, XDataUtil.GAME_DICE);
                    break;
                case "跳转":
                    XDiaLogUtil.jumpUser(service);
//                Request request = new Request.Builder().url("https://api-user.soulapp.cn/v3/update/user/info?pageId=HomePage_AvatarChoice").build();
//                XOkHttpUtil.soulInterceptor(request, null);
                    break;
                case "保存":
                    XDiaLogUtil.saveUser(service);
                    break;
                case "头像":
                    XToast.showToast(service, "暂不可用");
//                XDiaLogUtil.avatar(this);
                    break;
                case "验证":
                    // 验证
                    XDiaLogUtil.showCheck(service);
                    break;
                case "关闭":
                    // 关闭
                    service.stopService(new Intent(service, FloatingWindowService.class));
                    break;
                case "签到":
                    XSoulUtil.click(service);
                case "连接":
                    Socket soulSocket = XSocket.getSoulSocket("114.55.211.197", 8180);
                    break;
                default:
                    break;
            }
        });
        AlertDialog dialog = builder.create();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
        } else {
            dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        }
        dialog.show();
    }

    public static void showHintDialog(Context context, String hint) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        TextView textView = new TextView(context);
        textView.setHint(hint);
        int padding =30;
        textView.setPadding(padding,padding,padding,padding);
        builder.setTitle("提示");
        builder.setCancelable(false);
        builder.setView(textView);
        builder.setPositiveButton("确定",
                (dialog, which) -> XThread.runOnMain(() ->
                        context.stopService(new Intent(context, FloatingWindowService.class))
                ));
        AlertDialog dialog = builder.create();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
        } else {
            dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        }
        dialog.show();
    }
}
