package com.example.apptools.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.text.InputType;
import android.text.Selection;
import android.view.WindowManager;
import android.widget.EditText;

import cn.soul.android.component.SoulRouter;

import com.example.apptools.service.FloatingWindowService;


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
                        XDataUtil.setXDataValue(context, XDataUtil.CHECK, editable.toString());
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
                        SoulRouter.i().e("/account/userHomepage").w("KEY_USER_ID_ECPT", editable.toString()).d())
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
