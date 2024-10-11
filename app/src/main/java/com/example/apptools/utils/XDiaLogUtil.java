package com.example.apptools.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.text.Editable;
import android.text.InputType;
import android.text.Selection;
import android.view.WindowManager;
import android.widget.EditText;

import cn.soul.android.component.SoulRouter;

import com.example.apptools.service.FloatingWindowService;
import com.example.apptools.utils.soul.bean.bubble.BubblingListItem;
import com.example.apptools.utils.soul.util.BubbleUtil;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;


public class XDiaLogUtil {

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

    public static void showBubble(FloatingWindowService service) {
        AlertDialog.Builder builder = new AlertDialog.Builder(service);
        String[] items = {"BUBBLE列表", "获取BUBBLE列表", "发送BUBBLE"};
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
                        XDataUtil.setXDataValue(service, XDataUtil.BUBBLE_SIZE, String.valueOf(service.list.size()));
                        service.windowManager.addView(service.recyLayout, params);
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
}
