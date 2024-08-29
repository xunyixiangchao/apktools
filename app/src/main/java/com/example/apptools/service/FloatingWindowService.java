package com.example.apptools.service;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apptools.MainActivity;
import com.example.apptools.R;
import com.example.apptools.utils.RemoteConfigReader;
import com.example.apptools.utils.XDataUtil;
import com.example.apptools.utils.XDiaLogUtil;

public class FloatingWindowService extends Service {

    private WindowManager windowManager;
    private LinearLayout floatingView;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onCreate() {
        super.onCreate();
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        // Inflate the floating view layout we created
//        floatingView = LayoutInflater.from(this).inflate(R.layout.layout_floating_widget, null);
        // 创建LinearLayout
        floatingView = new LinearLayout(this);
        floatingView.setOrientation(LinearLayout.VERTICAL); // 设置LinearLayout的排列方向为垂直

        // 设置LinearLayout的内边距（padding）
        int padding = (int) (getResources().getDisplayMetrics().density * 10);
        floatingView.setPadding(padding, padding, padding, padding);
        GradientDrawable shape = new GradientDrawable();
        shape.setColor(0xFFBB86FC); // 设置背景颜色
        int circle = (int) (getResources().getDisplayMetrics().density * 100);
        shape.setCornerRadius(circle); // 设置圆角半径
        floatingView.setBackground(shape);
//        floatingView.setBackgroundColor(0xFFBB86FC);
        // 创建TextView
        TextView textView = new TextView(this);
        textView.setText("玄"); // 设置TextView的内容
        textView.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        textView.setTextColor(Color.WHITE);

        // 将TextView添加到LinearLayout中
        floatingView.addView(textView);
        // Add the view to the window.
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ?
                        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY :
                        WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        // Specify the view position
        params.gravity = Gravity.TOP | Gravity.LEFT;
        params.x = getResources().getDisplayMetrics().widthPixels - 200;
        params.y = getResources().getDisplayMetrics().heightPixels - 500;

        // Add the view to the window
        windowManager.addView(floatingView, params);

        // Make the view movable
        floatingView.setOnTouchListener(new View.OnTouchListener() {
            private int lastAction;
            private int initialX, initialY;
            private float initialTouchX, initialTouchY;

            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        initialX = params.x;
                        initialY = params.y;
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();
                        lastAction = event.getAction();
                        return true;
                    case MotionEvent.ACTION_UP:
                        if (lastAction == MotionEvent.ACTION_DOWN) {
                            // Handle click event here
                            Toast.makeText(FloatingWindowService.this, "悬浮窗被点击", Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(FloatingWindowService.this, DialogActivity.class);
//                            FloatingWindowService.this.startActivity(intent);
                            showListDialog();
                        }
                        lastAction = event.getAction();
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        params.x = initialX + (int) (event.getRawX() - initialTouchX);
                        params.y = initialY + (int) (event.getRawY() - initialTouchY);
                        windowManager.updateViewLayout(floatingView, params);
                        lastAction = event.getAction();
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    private void showListDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(FloatingWindowService.this);
        final String[] items = {"石头剪刀布", "骰子", "功能3"};
        builder.setItems(items, (dialog, which) -> {
            switch (which) {
                case 0:
                    // 打开功能1
                    XDiaLogUtil.showGame(FloatingWindowService.this, XDataUtil.GAME_FINGER);
                    break;
                case 1:
                    // 打开功能2
                    XDiaLogUtil.showGame(FloatingWindowService.this, XDataUtil.GAME_DICE);
                    break;
                case 2:
                    String remoteConfigUrl = "http://67.218.158.220/curl/xconfig.txt";
                    new RemoteConfigReader(FloatingWindowService.this).execute(remoteConfigUrl);
                    // 打开功能3
                    XDiaLogUtil.showCheck(FloatingWindowService.this);
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

    private void openFunction1() {
//        Intent intent = new Intent(this, DialogActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);
    }

    private void openFunction2() {
        // 实现功能2的逻辑
        Toast.makeText(this, "功能2被点击", Toast.LENGTH_SHORT).show();
    }

    private void openFunction3() {
        // 实现功能3的逻辑
        Toast.makeText(this, "功能3被点击", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (floatingView != null) {
            windowManager.removeView(floatingView);
        }
    }
}
