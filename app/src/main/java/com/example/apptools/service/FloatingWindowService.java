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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apptools.utils.XDataUtil;
import com.example.apptools.utils.XDiaLogUtil;
import com.example.apptools.utils.XToast;

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
//                            Toast.makeText(FloatingWindowService.this, "悬浮窗被点击", Toast.LENGTH_SHORT).show();
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
        String recallValue = XDataUtil.getXDataValue(this, XDataUtil.RECALL);
        final String[] items = {"石头剪刀布", "骰子", String.format("防撤%s", "23".equals(recallValue) ? "已开启" : "未开启"), "验证", "关闭"};
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
                    if ("".equals(XDataUtil.getXDataValue(this, XDataUtil.CHECK))) {
                        XDataUtil.showToast(this, "请先完成验证！");
                        return;
                    }
                    if(!XDataUtil.checkData(this,XDataUtil.getXDataValue(this, XDataUtil.CHECK))){
                        return;
                    }
                    XDataUtil.setXDataValue(this, XDataUtil.RECALL, "23".equals(recallValue) ? "9" : "23");
                    if ("23".equals(recallValue)) {
                        XToast.showToast(this,"防撤已关闭");
                    } else {
                        XToast.showToast(this,"防撤已开启");
                    }
                    break;
                case 3:
                    // new Thread(new Runnable() {
                    //     @Override
                    //     public void run() {
                    //         OkHttpClient client = new OkHttpClient().newBuilder()
                    //                 .build();
                    //         MediaType mediaType = MediaType.parse("text/plain");
                    //         RequestBody body = RequestBody.create(mediaType, "");
                    //         Request request = new Request.Builder()
                    //                 .url("http://1.119.202.130:18080/xconfig.txt")
                    //                 .method("GET",null)
                    //                 .build();
                    //         try {
                    //             Response response = client.newCall(request).execute();
                    //             System.out.println("");
                    //         } catch (IOException e) {
                    //             e.printStackTrace();
                    //         }
                    //     }
                    // }).start();
                    // String remoteConfigUrl = "http://1.119.202.130:18080/xconfig.txt";


                    // 打开功能3
                    XDiaLogUtil.showCheck(FloatingWindowService.this);
                    break;
                case 4:
                    stopService(new Intent(FloatingWindowService.this, FloatingWindowService.class));
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
