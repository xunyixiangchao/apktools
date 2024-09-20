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
import com.example.apptools.utils.XOkHttpUtil;

import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

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
        final String[] items = {"石头剪刀布", "骰子", String.format("防撤%s", XDataUtil.isRecall(this) ? "已开启" : "未开启"),
                "验证", "关闭","跳转","url"};
        builder.setItems(items, (dialog, which) -> {
            switch (which) {
                case 0:
                    // 剪刀石头布
                    XDiaLogUtil.showGame(FloatingWindowService.this, XDataUtil.GAME_FINGER);
                    break;
                case 1:
                    // 骰子
                    XDiaLogUtil.showGame(FloatingWindowService.this, XDataUtil.GAME_DICE);
                    break;
                case 2:
                    // 防撤回
                    XDataUtil.recall(this);
                    break;
                case 3:
                    // 验证
                    XDiaLogUtil.showCheck(FloatingWindowService.this);
                    break;
                case 4:
                    // 关闭
                    stopService(new Intent(FloatingWindowService.this, FloatingWindowService.class));
                    break;
                case 5:
                    XDiaLogUtil.jumpUser(this);
                    break;
                case 6:
                    Request request = new Request.Builder().url("https://api-user.soulapp.cn/bubbling/list?bi=%5B%221920e28e8b8%22%2C%22%22%2C%22Xiaomi%22%2C%22Android%22%2C28%2C9%2C%22MIX2%22%2C%22Xiaomi%22%2C440%2C%221080*2030%22%2C%22xiaomi%22%2C%22WIFI%22%2C%22zh_CN%22%5D&bik=32755&pageId=ChatList_PaoPao&pageIndex=1").build();
                    ResponseBody responseBody = ResponseBody.create(
                            MediaType.get("application/json; charset=utf-8"),
                            "{\"code\":10001,\"message\":\"success\",\"data\":{\"haveBubbling\":true,\"pageEnd\":false,\"bubblingList\":[{\"userId\":-1,\"userIdEcpt\":\"OUM0VmdlYkloNHR1WUNwTVhMR0V3QT09\",\"version\":35,\"avatarName\":\"avatar-1634544776581-00623\",\"avatarColor\":\"HeaderColor_Default\",\"signature\":\"影子\",\"originSignature\":\"影子\",\"stateTip\":\"找吃鸡搭子\",\"mood\":\"https://china-img.soulapp.cn/bubbling/icon/cjdd.png\",\"moodTip\":\"平静\",\"createStr\":\"刚刚\",\"followType\":0,\"desc\":\"v区\",\"bubblingType\":1,\"consecutiveTimes\":1,\"hasPicked\":false,\"hasExp\":false},{\"userId\":-1,\"userIdEcpt\":\"Y0VOdllaTHMzUUpYRnBhV05La1orUT09\",\"version\":25,\"avatarName\":\"avatar-1632831302794-01671\",\"avatarColor\":\"HeaderColor_Default\",\"signature\":\"！！\",\"originSignature\":\"！！\",\"stateTip\":\"找连麦搭子\",\"mood\":\"https://china-img.soulapp.cn/bubbling/icon/xlm.png\",\"moodTip\":\"平静\",\"createStr\":\"刚刚\",\"followType\":0,\"desc\":\"想 连麦 聊完删\",\"bubblingType\":1,\"consecutiveTimes\":1,\"hasPicked\":false,\"hasExp\":false},{\"userId\":-1,\"userIdEcpt\":\"aHM5L1ZnNmJOV09odUl5MEpNOUVkQT09\",\"version\":103,\"avatarName\":\"avatar-1641184022598-00421\",\"avatarColor\":\"HeaderColor_Default\",\"signature\":\"嚸烟\uD83D\uDEAC抽寂寞\",\"originSignature\":\"嚸烟\uD83D\uDEAC抽寂寞\",\"stateTip\":\"找连麦搭子\",\"mood\":\"https://china-img.soulapp.cn/bubbling/icon/xlm.png\",\"moodTip\":\"平静\",\"createStr\":\"刚刚\",\"followType\":0,\"desc\":\"撩一会 速度\",\"bubblingType\":1,\"consecutiveTimes\":1,\"hasPicked\":false,\"hasExp\":false},{\"userId\":-1,\"userIdEcpt\":\"aEE3MXF1dm8rRDIvZ29OU1VteVFpUT09\",\"version\":2940,\"avatarName\":\"avatar-1707643010491-01756\",\"avatarColor\":\"HeaderColor_Default\",\"signature\":\"只想简简单单爱\",\"originSignature\":\"只想简简单单爱\",\"stateTip\":\"看电影\",\"mood\":\"https://china-img.soulapp.cn/bubbling/icon/kdy.png\",\"moodTip\":\"平静\",\"createStr\":\"刚刚\",\"followType\":0,\"desc\":\"看电影，看电影，看电影\",\"bubblingType\":3,\"consecutiveTimes\":397,\"hasPicked\":false,\"hasExp\":false},{\"userId\":-1,\"userIdEcpt\":\"aWdkUi9YbWpqOW5WVlgvZUh1NEExdz09\",\"version\":1371,\"avatarName\":\"avatar-1713449412589-01426\",\"avatarColor\":\"HeaderColor_Default\",\"signature\":\"清尘\",\"originSignature\":\"清尘\",\"stateTip\":\"找连麦搭子\",\"mood\":\"https://china-img.soulapp.cn/bubbling/icon/xlm.png\",\"moodTip\":\"平静\",\"createStr\":\"刚刚\",\"followType\":0,\"desc\":\"有和叔叔聊天的丫头吗\",\"bubblingType\":1,\"consecutiveTimes\":1,\"hasPicked\":false,\"hasExp\":false},{\"userId\":-1,\"userIdEcpt\":\"Z3l1RlRIMTZVZWJWVlgvZUh1NEExdz09\",\"version\":13,\"avatarName\":\"portrait_1_new\",\"avatarColor\":\"HeaderColor_1\",\"signature\":\"大先生\",\"originSignature\":\"大先生\",\"stateTip\":\"找聊天搭子\",\"mood\":\"https://china-img.soulapp.cn/bubbling/icon/xlt.png\",\"moodTip\":\"平静\",\"createStr\":\"刚刚\",\"followType\":0,\"desc\":\"无聊 有说话的吗\",\"skinUrl\":\"https://china-img.soulapp.cn/bubbling/skin/xingguangchibang.png\",\"bubblingType\":1,\"consecutiveTimes\":1,\"hasPicked\":false,\"hasExp\":false},{\"userId\":-1,\"userIdEcpt\":\"enNrY0c2M2xTWkhnQkp2UDhiV2VUdz09\",\"version\":1,\"avatarName\":\"avatar-1642052997196-00945\",\"avatarColor\":\"HeaderColor_Default\",\"signature\":\"劫.\",\"originSignature\":\"劫.\",\"stateTip\":\"找聊天搭子\",\"mood\":\"https://china-img.soulapp.cn/bubbling/icon/xlt.png\",\"moodTip\":\"平静\",\"createStr\":\"刚刚\",\"followType\":0,\"desc\":\"好无聊\",\"bubblingType\":1,\"consecutiveTimes\":1,\"hasPicked\":false,\"hasExp\":false},{\"userId\":-1,\"userIdEcpt\":\"NjdyRk1NUHZsY21odUl5MEpNOUVkQT09\",\"version\":1,\"avatarName\":\"avatar-1636955089206-00883\",\"avatarColor\":\"HeaderColor_Default\",\"signature\":\"观香\",\"originSignature\":\"观香\",\"stateTip\":\"找聊天搭子\",\"mood\":\"https://china-img.soulapp.cn/bubbling/icon/xlt.png\",\"moodTip\":\"平静\",\"createStr\":\"刚刚\",\"followType\":0,\"desc\":\"找个宝宝当闺蜜，有什么事\",\"bubblingType\":1,\"consecutiveTimes\":1,\"hasPicked\":false,\"hasExp\":false},{\"userId\":-1,\"userIdEcpt\":\"Wmt0eXVEYmt5b2NxVVNCSWZSZjNlUT09\",\"version\":174,\"avatarName\":\"avatar-1639969434865-00582\",\"avatarColor\":\"HeaderColor_Default\",\"signature\":\"讨厌控制\",\"originSignature\":\"讨厌控制\",\"stateTip\":\"提个问题\",\"mood\":\"https://china-img.soulapp.cn/bubbling/icon/tgwt.png\",\"moodTip\":\"平静\",\"createStr\":\"刚刚\",\"followType\":0,\"desc\":\"东北人都会唱爱情故事？\",\"bubblingType\":1,\"consecutiveTimes\":1,\"hasPicked\":false,\"hasExp\":false},{\"userId\":-1,\"userIdEcpt\":\"ZmIwRzdsRWYzUmlodUl5MEpNOUVkQT09\",\"version\":1376,\"avatarName\":\"avatar-1638230865507-06070\",\"avatarColor\":\"HeaderColor_Default\",\"signature\":\"❤️\",\"originSignature\":\"❤️\",\"stateTip\":\"找连麦搭子\",\"mood\":\"https://china-img.soulapp.cn/bubbling/icon/xlm.png\",\"moodTip\":\"平静\",\"createStr\":\"刚刚\",\"followType\":0,\"desc\":\"无声\",\"bubblingType\":1,\"consecutiveTimes\":1,\"hasPicked\":false,\"hasExp\":false},{\"userId\":-1,\"userIdEcpt\":\"bW9YZ2hJdXY1L0ZjYmFXR3FVZDBLUT09\",\"version\":1052,\"avatarName\":\"avatar-1669878365089-02816\",\"avatarColor\":\"HeaderColor_Default\",\"signature\":\"唯一\",\"originSignature\":\"唯一\",\"stateTip\":\"找聊天搭子\",\"mood\":\"https://china-img.soulapp.cn/bubbling/icon/xlt.png\",\"moodTip\":\"平静\",\"createStr\":\"刚刚\",\"followType\":0,\"desc\":\"\uD83D\uDC36\uD83D\uDC36过来\",\"bubblingType\":1,\"consecutiveTimes\":1,\"hasPicked\":false,\"hasExp\":false},{\"userId\":-1,\"userIdEcpt\":\"ZVFVZnZHc0lhcWpWVlgvZUh1NEExdz09\",\"version\":118,\"avatarName\":\"avatar-1709064649749-00100\",\"avatarColor\":\"HeaderColor_Default\",\"signature\":\"舟\",\"originSignature\":\"舟\",\"stateTip\":\"找连麦搭子\",\"mood\":\"https://china-img.soulapp.cn/bubbling/icon/xlm.png\",\"moodTip\":\"平静\",\"createStr\":\"刚刚\",\"followType\":0,\"desc\":\"有没有80后女的聊天连麦\",\"bubblingType\":1,\"consecutiveTimes\":1,\"hasPicked\":false,\"hasExp\":false},{\"userId\":-1,\"userIdEcpt\":\"bC93WjlpTW5xYWx1WUNwTVhMR0V3QT09\",\"version\":162,\"avatarName\":\"avatar-1719326344950-04038\",\"avatarColor\":\"HeaderColor_Default\",\"signature\":\"NULL\",\"originSignature\":\"NULL\",\"stateTip\":\"找聊天搭子\",\"mood\":\"https://china-img.soulapp.cn/bubbling/icon/xlt.png\",\"moodTip\":\"平静\",\"createStr\":\"刚刚\",\"followType\":0,\"desc\":\"长期陪伴\",\"bubblingType\":1,\"consecutiveTimes\":1,\"hasPicked\":false,\"hasExp\":false},{\"userId\":-1,\"userIdEcpt\":\"aFEwSjNueWVKckdiM0YxdnRVc25uQT09\",\"version\":2,\"avatarName\":\"avatar-1640158474736-07575\",\"avatarColor\":\"HeaderColor_Default\",\"signature\":\"良\",\"originSignature\":\"良\",\"stateTip\":\"想面基\",\"mood\":\"https://china-img.soulapp.cn/admin/2021-04-02/28f5d8b8-74c4-40c6-b5dc-a45406c4b630.png\",\"moodTip\":\"平静\",\"createStr\":\"刚刚\",\"followType\":0,\"desc\":\"无聊\",\"bubblingType\":1,\"consecutiveTimes\":1,\"hasPicked\":false,\"hasExp\":false},{\"userId\":-1,\"userIdEcpt\":\"cnV5YUNJeU02aEtiM0YxdnRVc25uQT09\",\"version\":332,\"avatarName\":\"avatar-1640780085998-00102\",\"avatarColor\":\"HeaderColor_Default\",\"signature\":\"Lonly\",\"originSignature\":\"Lonly\",\"stateTip\":\"找连麦搭子\",\"mood\":\"https://china-img.soulapp.cn/bubbling/icon/xlm.png\",\"moodTip\":\"平静\",\"createStr\":\"刚刚\",\"followType\":0,\"desc\":\"无声\",\"bubblingType\":1,\"consecutiveTimes\":1,\"hasPicked\":false,\"hasExp\":false},{\"userId\":-1,\"userIdEcpt\":\"aGdOR2JRUUxKYTZIY0JMSUs3VlMxZz09\",\"version\":897,\"avatarName\":\"avatar-1636377803297-03234\",\"avatarColor\":\"HeaderColor_Default\",\"signature\":\"我命由我\",\"originSignature\":\"我命由我\",\"stateTip\":\"找连麦搭子\",\"mood\":\"https://china-img.soulapp.cn/bubbling/icon/xlm.png\",\"moodTip\":\"平静\",\"createStr\":\"刚刚\",\"followType\":0,\"desc\":\"不介意的话我有声，你随意\",\"bubblingType\":1,\"consecutiveTimes\":1,\"hasPicked\":false,\"hasExp\":false},{\"userId\":-1,\"userIdEcpt\":\"MTlqZG1XTFozRW5WVlgvZUh1NEExdz09\",\"version\":23,\"avatarName\":\"portrait_7_new\",\"avatarColor\":\"HeaderColor_1\",\"signature\":\"社会上的边角料\",\"originSignature\":\"社会上的边角料\",\"stateTip\":\"睡觉\",\"mood\":\"https://china-img.soulapp.cn/bubbling/icon/sj.png\",\"moodTip\":\"平静\",\"createStr\":\"刚刚\",\"followType\":0,\"desc\":\"白天睡不醒，晚上睡不着\",\"bubblingType\":3,\"consecutiveTimes\":1,\"hasPicked\":false,\"hasExp\":false},{\"userId\":-1,\"userIdEcpt\":\"K252akJWSlJCaStiM0YxdnRVc25uQT09\",\"version\":2,\"avatarName\":\"avatar-1636954851249-00337\",\"avatarColor\":\"HeaderColor_Default\",\"signature\":\"Xx-\",\"originSignature\":\"Xx-\",\"stateTip\":\"找聊天搭子\",\"mood\":\"https://china-img.soulapp.cn/bubbling/icon/xlt.png\",\"moodTip\":\"平静\",\"createStr\":\"刚刚\",\"followType\":0,\"desc\":\"哈喽\",\"bubblingType\":1,\"consecutiveTimes\":1,\"hasPicked\":false,\"hasExp\":false},{\"userId\":-1,\"userIdEcpt\":\"c2FkTHVEYWxwU2N1RTU3dnhxODE4QT09\",\"version\":186,\"avatarName\":\"avatar-1646021488577-01853\",\"avatarColor\":\"HeaderColor_Default\",\"signature\":\"Alisa\",\"originSignature\":\"Alisa\",\"stateTip\":\"我正在...\",\"mood\":\"https://china-img.soulapp.cn/bubbling/icon/wzz....png\",\"moodTip\":\"平静\",\"createStr\":\"刚刚\",\"followType\":0,\"desc\":\"好想您\",\"bubblingType\":1,\"consecutiveTimes\":1,\"hasPicked\":false,\"hasExp\":false},{\"userId\":-1,\"userIdEcpt\":\"RE1JeFdFMk9BOEMvZ29OU1VteVFpUT09\",\"version\":44,\"avatarName\":\"avatar-1643117411286-01036\",\"avatarColor\":\"HeaderColor_Default\",\"signature\":\"越努力越幸运\",\"originSignature\":\"越努力越幸运\",\"stateTip\":\"找聊天搭子\",\"mood\":\"https://china-img.soulapp.cn/bubbling/icon/xlt.png\",\"moodTip\":\"平静\",\"createStr\":\"刚刚\",\"followType\":0,\"desc\":\"有人唠嗑没\",\"bubblingType\":1,\"consecutiveTimes\":1,\"hasPicked\":false,\"hasExp\":false},{\"userId\":-1,\"userIdEcpt\":\"NmtZaWhLVExWOUNIY0JMSUs3VlMxZz09\",\"version\":1799,\"avatarName\":\"avatar-1639929226234-02333\",\"avatarColor\":\"HeaderColor_Default\",\"signature\":\"tony\",\"originSignature\":\"tony\",\"stateTip\":\"找连麦搭子\",\"mood\":\"https://china-img.soulapp.cn/bubbling/icon/xlm.png\",\"moodTip\":\"平静\",\"createStr\":\"刚刚\",\"followType\":0,\"desc\":\"80后园林绿化工程类聊聊\",\"bubblingType\":1,\"consecutiveTimes\":1,\"hasPicked\":false,\"hasExp\":false},{\"userId\":-1,\"userIdEcpt\":\"UVYyOWF0OHpUZGx1WUNwTVhMR0V3QT09\",\"version\":1,\"avatarName\":\"avatar-1642542279614-02891\",\"avatarColor\":\"HeaderColor_Default\",\"signature\":\"嘻嘻\",\"originSignature\":\"嘻嘻\",\"stateTip\":\"我正在...\",\"mood\":\"https://china-img.soulapp.cn/bubbling/icon/wzz....png\",\"moodTip\":\"平静\",\"createStr\":\"刚刚\",\"followType\":0,\"desc\":\"摸鱼小分队下班了吗\",\"bubblingType\":1,\"consecutiveTimes\":1,\"hasPicked\":false,\"hasExp\":false},{\"userId\":-1,\"userIdEcpt\":\"OUNLOEZyVFRpazl1WUNwTVhMR0V3QT09\",\"version\":19,\"avatarName\":\"avatar-1636094662771-02004\",\"avatarColor\":\"HeaderColor_Default\",\"signature\":\"咖啡味奶茶\",\"originSignature\":\"咖啡味奶茶\",\"stateTip\":\"我正在...\",\"mood\":\"https://china-img.soulapp.cn/bubbling/icon/wzz....png\",\"moodTip\":\"平静\",\"createStr\":\"刚刚\",\"followType\":0,\"desc\":\"中秋快乐！\",\"bubblingType\":1,\"consecutiveTimes\":1,\"hasPicked\":false,\"hasExp\":false},{\"userId\":-1,\"userIdEcpt\":\"eDZDZXJsQjBUTW5WVlgvZUh1NEExdz09\",\"version\":299,\"avatarName\":\"avatar-1722423415208-03164\",\"avatarColor\":\"HeaderColor_Default\",\"signature\":\"欣欣吖\uD83E\uDEE7\",\"originSignature\":\"欣欣吖\uD83E\uDEE7\",\"stateTip\":\"我正在...\",\"mood\":\"https://china-img.soulapp.cn/bubbling/icon/wzz....png\",\"moodTip\":\"平静\",\"createStr\":\"刚刚\",\"followType\":0,\"desc\":\"跟我，除了恋爱没什么好谈\",\"bubblingType\":1,\"consecutiveTimes\":1,\"hasPicked\":false,\"hasExp\":false},{\"userId\":-1,\"userIdEcpt\":\"c01WN0lHN1V5RkJ1WUNwTVhMR0V3QT09\",\"version\":21,\"avatarName\":\"avatar-1726477196446-02334\",\"avatarColor\":\"HeaderColor_Default\",\"signature\":\"拉吉莫拉拉\",\"originSignature\":\"拉吉莫拉拉\",\"stateTip\":\"找聊天搭子\",\"mood\":\"https://china-img.soulapp.cn/bubbling/icon/xlt.png\",\"moodTip\":\"平静\",\"createStr\":\"刚刚\",\"followType\":0,\"desc\":\"甜蜜的聊天\",\"bubblingType\":1,\"consecutiveTimes\":1,\"hasPicked\":false,\"hasExp\":false},{\"userId\":-1,\"userIdEcpt\":\"d2dYZnVwVmxseHFiM0YxdnRVc25uQT09\",\"version\":395,\"avatarName\":\"avatar-1627221203408-02983\",\"avatarColor\":\"HeaderColor_Default\",\"signature\":\"风说可以了\",\"originSignature\":\"风说可以了\",\"stateTip\":\"找聊天搭子\",\"mood\":\"https://china-img.soulapp.cn/bubbling/icon/xlt.png\",\"moodTip\":\"平静\",\"createStr\":\"刚刚\",\"followType\":0,\"desc\":\"正常讨论一个东西太难了\",\"bubblingType\":1,\"consecutiveTimes\":1,\"hasPicked\":false,\"hasExp\":false},{\"userId\":-1,\"userIdEcpt\":\"Q0lGdzlWaGVnaDVYRnBhV05La1orUT09\",\"version\":1,\"avatarName\":\"avatar-1634552774691-01835\",\"avatarColor\":\"HeaderColor_Default\",\"signature\":\"妩媚兒\",\"originSignature\":\"妩媚兒\",\"stateTip\":\"我正在...\",\"mood\":\"https://china-img.soulapp.cn/bubbling/icon/wzz....png\",\"moodTip\":\"平静\",\"createStr\":\"5分钟前\",\"followType\":0,\"desc\":\"想去海边吹吹风\",\"bubblingType\":1,\"consecutiveTimes\":1,\"hasPicked\":false,\"hasExp\":false},{\"userId\":-1,\"userIdEcpt\":\"L3J2bHlmdTJnUFRnQkp2UDhiV2VUdz09\",\"version\":203,\"avatarName\":\"avatar-1635747365781-05338\",\"avatarColor\":\"HeaderColor_Default\",\"signature\":\"喵呜\",\"originSignature\":\"喵呜\",\"stateTip\":\"我正在...\",\"mood\":\"https://china-img.soulapp.cn/bubbling/icon/wzz....png\",\"moodTip\":\"平静\",\"createStr\":\"5分钟前\",\"followType\":0,\"desc\":\"居然没有玩无期迷途的吗\",\"bubblingType\":1,\"consecutiveTimes\":1,\"hasPicked\":false,\"hasExp\":false},{\"userId\":-1,\"userIdEcpt\":\"Q0dnaG9iMGhmc3FIY0JMSUs3VlMxZz09\",\"version\":9,\"avatarName\":\"avatar-1646021488577-01853\",\"avatarColor\":\"HeaderColor_Default\",\"signature\":\"千岁\",\"originSignature\":\"千岁\",\"stateTip\":\"找聊天搭子\",\"mood\":\"https://china-img.soulapp.cn/bubbling/icon/xlt.png\",\"moodTip\":\"平静\",\"createStr\":\"5分钟前\",\"followType\":0,\"desc\":\"dd\",\"bubblingType\":1,\"consecutiveTimes\":1,\"hasPicked\":false,\"hasExp\":false},{\"userId\":-1,\"userIdEcpt\":\"UU5aK0t0SE1OTzRxVVNCSWZSZjNlUT09\",\"version\":148,\"avatarName\":\"avatar-1636644200797-01167\",\"avatarColor\":\"HeaderColor_Default\",\"signature\":\"姐姐\uD83D\uDCAB\",\"originSignature\":\"姐姐\uD83D\uDCAB\",\"stateTip\":\"我正在...\",\"mood\":\"https://china-img.soulapp.cn/bubbling/icon/wzz....png\",\"moodTip\":\"平静\",\"createStr\":\"5分钟前\",\"followType\":0,\"desc\":\"晚上 不知道吃什么\",\"bubblingType\":1,\"consecutiveTimes\":1,\"hasPicked\":false,\"hasExp\":false},{\"userId\":-1,\"userIdEcpt\":\"a0VhcEVuN2NHV2RjYmFXR3FVZDBLUT09\",\"version\":1155,\"avatarName\":\"avatar-1716872049830-02471\",\"avatarColor\":\"HeaderColor_Default\",\"signature\":\"♈️跑偏了的白羊座♈️\",\"originSignature\":\"♈️跑偏了的白羊座♈️\",\"stateTip\":\"平静\",\"mood\":\"https://china-img.soulapp.cn/admin/2021-05-20/47ce0475-c9da-4bb3-9dae-10268773a493.png\",\"moodTip\":\"平静\",\"createStr\":\"5分钟前\",\"followType\":0,\"desc\":\"那些温暖又幸福的瞬间～\",\"bubblingType\":2,\"consecutiveTimes\":1,\"hasPicked\":false,\"hasExp\":false},{\"userId\":-1,\"userIdEcpt\":\"STd4VFlNbERCclJYRnBhV05La1orUT09\",\"version\":43,\"avatarName\":\"avatar-1634904982914-02353\",\"avatarColor\":\"HeaderColor_Default\",\"signature\":\"朋友圈\",\"originSignature\":\"朋友圈\",\"stateTip\":\"找聊天搭子\",\"mood\":\"https://china-img.soulapp.cn/bubbling/icon/xlt.png\",\"moodTip\":\"平静\",\"createStr\":\"6分钟前\",\"followType\":0,\"desc\":\"聊聊天\",\"bubblingType\":1,\"consecutiveTimes\":1,\"hasPicked\":false,\"hasExp\":false},{\"userId\":-1,\"userIdEcpt\":\"ellpcjU0T1d2UVcvZ29OU1VteVFpUT09\",\"version\":9,\"avatarName\":\"avatar-1725592910755-02132\",\"avatarColor\":\"HeaderColor_Default\",\"signature\":\"Souler\",\"originSignature\":\"Souler\",\"stateTip\":\"平静\",\"mood\":\"https://china-img.soulapp.cn/admin/2021-05-20/47ce0475-c9da-4bb3-9dae-10268773a493.png\",\"moodTip\":\"平静\",\"createStr\":\"6分钟前\",\"followType\":0,\"desc\":\"无聊哦\",\"bubblingType\":2,\"consecutiveTimes\":1,\"hasPicked\":false,\"hasExp\":false},{\"userId\":-1,\"userIdEcpt\":\"VEwzZzhRQnVYbllxVVNCSWZSZjNlUT09\",\"version\":39,\"avatarName\":\"avatar-1638157844789-02227\",\"avatarColor\":\"HeaderColor_Default\",\"signature\":\"人生过客\",\"originSignature\":\"人生过客\",\"stateTip\":\"我正在...\",\"mood\":\"https://china-img.soulapp.cn/bubbling/icon/wzz....png\",\"moodTip\":\"平静\",\"createStr\":\"7分钟前\",\"followType\":0,\"desc\":\"不想说话！\",\"bubblingType\":1,\"consecutiveTimes\":1,\"hasPicked\":false,\"hasExp\":false},{\"userId\":-1,\"userIdEcpt\":\"SWg0cGJjUGlRY1ZYRnBhV05La1orUT09\",\"version\":256,\"avatarName\":\"avatar-1635574367159-04177\",\"avatarColor\":\"HeaderColor_Default\",\"signature\":\"㓀归\",\"originSignature\":\"㓀归\",\"stateTip\":\"想脱单\",\"mood\":\"https://china-img.soulapp.cn/admin/2020-09-27/e07981e6-1842-4ece-ba1f-a003935f91d5.png\",\"moodTip\":\"平静\",\"createStr\":\"7分钟前\",\"followType\":0,\"desc\":\"有点冷呢\",\"bubblingType\":1,\"consecutiveTimes\":1,\"hasPicked\":false,\"hasExp\":false},{\"userId\":-1,\"userIdEcpt\":\"azM4ZGEwdk81RzdnQkp2UDhiV2VUdz09\",\"version\":35,\"avatarName\":\"avatar-1634739646375-00727\",\"avatarColor\":\"HeaderColor_Default\",\"signature\":\"玫瑰花坊的守护者\",\"originSignature\":\"玫瑰花坊的守护者\",\"stateTip\":\"我正在...\",\"mood\":\"https://china-img.soulapp.cn/bubbling/icon/wzz....png\",\"moodTip\":\"平静\",\"createStr\":\"7分钟前\",\"followType\":0,\"desc\":\"明天去吉林玩玩希望是晴天\",\"bubblingType\":1,\"consecutiveTimes\":1,\"hasPicked\":false,\"hasExp\":false},{\"userId\":-1,\"userIdEcpt\":\"NzNVaU9yc2NNblJYRnBhV05La1orUT09\",\"version\":2,\"avatarName\":\"avatar-1711698188243-01169\",\"avatarColor\":\"HeaderColor_Default\",\"signature\":\"拿铁不加冰\uD83E\uDDCA\",\"originSignature\":\"拿铁不加冰\uD83E\uDDCA\",\"stateTip\":\"认真上课\",\"mood\":\"https://china-img.soulapp.cn/bubbling/icon/rzsk.png\",\"moodTip\":\"平静\",\"createStr\":\"9分钟前\",\"followType\":0,\"desc\":\"好好的 心态最重要\",\"bubblingType\":3,\"consecutiveTimes\":1,\"hasPicked\":false,\"hasExp\":false},{\"userId\":-1,\"userIdEcpt\":\"TmswWWVSUGdRa09iM0YxdnRVc25uQT09\",\"version\":7,\"avatarName\":\"avatar-1646100013378-02766\",\"avatarColor\":\"HeaderColor_Default\",\"signature\":\"恬嘉恋酱.\",\"originSignature\":\"恬嘉恋酱.\",\"stateTip\":\"此刻心愿\",\"mood\":\"https://china-img.soulapp.cn/bubbling/icon/ckxy.png\",\"moodTip\":\"平静\",\"createStr\":\"9分钟前\",\"followType\":0,\"desc\":\"想看电影\",\"bubblingType\":1,\"consecutiveTimes\":1,\"hasPicked\":false,\"hasExp\":false},{\"userId\":-1,\"userIdEcpt\":\"ZXhCTlliQUpYMUlxVVNCSWZSZjNlUT09\",\"version\":232,\"avatarName\":\"avatar-1641257796559-03316\",\"avatarColor\":\"HeaderColor_Default\",\"signature\":\"Qiu.嗝屁\",\"originSignature\":\"Qiu.嗝屁\",\"stateTip\":\"找王者搭子\",\"mood\":\"https://china-img.soulapp.cn/bubbling/icon/wzdd.png\",\"moodTip\":\"平静\",\"createStr\":\"9分钟前\",\"followType\":0,\"desc\":\"Q区有人带我上个传奇吗？\",\"bubblingType\":1,\"consecutiveTimes\":1,\"hasPicked\":false,\"hasExp\":false},{\"userId\":-1,\"userIdEcpt\":\"SXc0RzhmSWNxSGhYRnBhV05La1orUT09\",\"version\":295,\"avatarName\":\"avatar-1646219613066-00261\",\"avatarColor\":\"HeaderColor_Default\",\"signature\":\"不想开学第n天\",\"originSignature\":\"不想开学第n天\",\"stateTip\":\"我正在...\",\"mood\":\"https://china-img.soulapp.cn/bubbling/icon/wzz....png\",\"moodTip\":\"平静\",\"createStr\":\"10分钟前\",\"followType\":0,\"desc\":\"脑壳痛\uD83D\uDE37\",\"bubblingType\":1,\"consecutiveTimes\":1,\"hasPicked\":false,\"hasExp\":false},{\"userId\":-1,\"userIdEcpt\":\"d1NhNmwwVk5qRHJnQkp2UDhiV2VUdz09\",\"version\":291,\"avatarName\":\"avatar-1639934626100-00331\",\"avatarColor\":\"HeaderColor_Default\",\"signature\":\"……\",\"originSignature\":\"……\",\"stateTip\":\"此刻心愿\",\"mood\":\"https://china-img.soulapp.cn/bubbling/icon/ckxy.png\",\"moodTip\":\"平静\",\"createStr\":\"10分钟前\",\"followType\":0,\"desc\":\"在立元世纪年华的有么\",\"bubblingType\":1,\"consecutiveTimes\":1,\"hasPicked\":false,\"hasExp\":false},{\"userId\":-1,\"userIdEcpt\":\"S0pVejRmbHJUeHc9\",\"version\":3,\"avatarName\":\"avatar-1725023081019-00171\",\"avatarColor\":\"HeaderColor_Default\",\"signature\":\"阿夏夏夏\",\"originSignature\":\"阿夏夏夏\",\"stateTip\":\"提个问题\",\"mood\":\"https://china-img.soulapp.cn/bubbling/icon/tgwt.png\",\"moodTip\":\"平静\",\"createStr\":\"10分钟前\",\"followType\":0,\"desc\":\"纯爱战神没有嘛\",\"bubblingType\":1,\"consecutiveTimes\":1,\"hasPicked\":false,\"hasExp\":false},{\"userId\":-1,\"userIdEcpt\":\"VFppZ2c5VTVOQWVIY0JMSUs3VlMxZz09\",\"version\":584,\"avatarName\":\"avatar-1714365369200-00909\",\"avatarColor\":\"HeaderColor_Default\",\"signature\":\"七安姐姐灬\",\"originSignature\":\"七安姐姐灬\",\"stateTip\":\"想脱单\",\"mood\":\"https://china-img.soulapp.cn/admin/2020-09-27/e07981e6-1842-4ece-ba1f-a003935f91d5.png\",\"moodTip\":\"平静\",\"createStr\":\"10分钟前\",\"followType\":0,\"desc\":\"谈恋爱吗？认真的\",\"bubblingType\":1,\"consecutiveTimes\":1,\"hasPicked\":false,\"hasExp\":false},{\"userId\":-1,\"userIdEcpt\":\"WXltTm9UN0dGK1hWVlgvZUh1NEExdz09\",\"version\":373,\"avatarName\":\"avatar-1723730021072-05063\",\"avatarColor\":\"HeaderColor_Default\",\"signature\":\".\",\"originSignature\":\".\",\"stateTip\":\"干饭贼香\",\"mood\":\"https://china-img.soulapp.cn/bubbling/icon/gfzx.png\",\"moodTip\":\"平静\",\"createStr\":\"2小时前\",\"followType\":0,\"skinUrl\":\"https://china-img.soulapp.cn/bubbling/skin/xingguangchibang.png\",\"bubblingType\":1,\"consecutiveTimes\":1,\"hasPicked\":false,\"hasExp\":false},{\"userId\":-1,\"userIdEcpt\":\"TDU4NTlBOTJ2VEdiM0YxdnRVc25uQT09\",\"version\":72,\"avatarName\":\"avatar-1722314232557-01827\",\"avatarColor\":\"HeaderColor_Default\",\"signature\":\"伊呀小婷\uD83C\uDF67\",\"originSignature\":\"伊呀小婷\uD83C\uDF67\",\"stateTip\":\"郁闷\",\"mood\":\"https://china-img.soulapp.cn/admin/2021-05-20/b2110677-4aae-484a-b841-67771caf3158.png\",\"moodTip\":\"郁闷\",\"createStr\":\"3小时前\",\"followType\":0,\"skinUrl\":\"https://china-img.soulapp.cn/bubbling/skin/haidishijie.png\",\"bubblingType\":2,\"consecutiveTimes\":1,\"hasPicked\":false,\"hasExp\":false},{\"userId\":-1,\"userIdEcpt\":\"dEJaYjl6WEtYV3FodUl5MEpNOUVkQT09\",\"version\":69,\"avatarName\":\"avatar-1721763451823-00681\",\"avatarColor\":\"HeaderColor_Default\",\"signature\":\"早晨阳光下的香气\",\"originSignature\":\"早晨阳光下的香气\",\"stateTip\":\"我正在...\",\"mood\":\"https://china-img.soulapp.cn/bubbling/icon/wzz....png\",\"moodTip\":\"平静\",\"createStr\":\"4小时前\",\"followType\":0,\"desc\":\"休息好无聊\",\"skinUrl\":\"https://china-img.soulapp.cn/bubbling/skin/haidishijie.png\",\"bubblingType\":1,\"consecutiveTimes\":1,\"hasPicked\":false,\"hasExp\":false},{\"userId\":-1,\"userIdEcpt\":\"NjNLYUhURmNRUk5YRnBhV05La1orUT09\",\"version\":1,\"avatarName\":\"avatar-1724960974651-00202\",\"avatarColor\":\"HeaderColor_Default\",\"signature\":\"图姐颜直说（社恐版）\",\"originSignature\":\"图姐颜直说（社恐版）\",\"stateTip\":\"难过\",\"mood\":\"https://china-img.soulapp.cn/admin/2021-05-20/12f08afc-36cf-4e14-bf09-94202e8ee866.png\",\"moodTip\":\"难过\",\"createStr\":\"刚刚\",\"followType\":0,\"bubblingType\":2,\"consecutiveTimes\":1,\"hasPicked\":false,\"hasExp\":false},{\"userId\":-1,\"userIdEcpt\":\"amJwdng1ZGxWWlJYRnBhV05La1orUT09\",\"version\":606,\"avatarName\":\"avatar-1644376468596-01879\",\"avatarColor\":\"HeaderColor_Default\",\"signature\":\"连不上WiFi\",\"originSignature\":\"连不上WiFi\",\"stateTip\":\"找聊天搭子\",\"mood\":\"https://china-img.soulapp.cn/bubbling/icon/xlt.png\",\"moodTip\":\"平静\",\"createStr\":\"刚刚\",\"followType\":0,\"bubblingType\":1,\"consecutiveTimes\":1,\"hasPicked\":false,\"hasExp\":false},{\"userId\":-1,\"userIdEcpt\":\"anNIUXBVS2dGMWEvZ29OU1VteVFpUT09\",\"version\":109,\"avatarName\":\"avatar-1642830636021-02376\",\"avatarColor\":\"HeaderColor_Default\",\"signature\":\"棕熊多不多肉\",\"originSignature\":\"棕熊多不多肉\",\"stateTip\":\"找聊天搭子\",\"mood\":\"https://china-img.soulapp.cn/bubbling/icon/xlt.png\",\"moodTip\":\"平静\",\"createStr\":\"刚刚\",\"followType\":0,\"bubblingType\":1,\"consecutiveTimes\":1,\"hasPicked\":false,\"hasExp\":false},{\"userId\":-1,\"userIdEcpt\":\"eFIraWxodlY1TVhWVlgvZUh1NEExdz09\",\"version\":2,\"avatarName\":\"avatar-1690437653564-00266_gif\",\"avatarColor\":\"HeaderColor_Default\",\"signature\":\"哈哈哈30\",\"originSignature\":\"哈哈哈30\",\"stateTip\":\"想脱单\",\"mood\":\"https://china-img.soulapp.cn/admin/2020-09-27/e07981e6-1842-4ece-ba1f-a003935f91d5.png\",\"moodTip\":\"平静\",\"createStr\":\"刚刚\",\"followType\":0,\"bubblingType\":1,\"consecutiveTimes\":1,\"hasPicked\":false,\"hasExp\":false},{\"userId\":-1,\"userIdEcpt\":\"SHBCL0xwREtNV3liM0YxdnRVc25uQT09\",\"version\":102,\"avatarName\":\"avatar-1641804927283-03102\",\"avatarColor\":\"HeaderColor_Default\",\"signature\":\"给我一瓶矿泉水\",\"originSignature\":\"给我一瓶矿泉水\",\"stateTip\":\"找聊天搭子\",\"mood\":\"https://china-img.soulapp.cn/bubbling/icon/xlt.png\",\"moodTip\":\"平静\",\"createStr\":\"刚刚\",\"followType\":0,\"bubblingType\":1,\"consecutiveTimes\":1,\"hasPicked\":false,\"hasExp\":false},{\"userId\":-1,\"userIdEcpt\":\"WEFPUElGSGdSQ3VodUl5MEpNOUVkQT09\",\"version\":3,\"avatarName\":\"avatar-1637860476921-04293\",\"avatarColor\":\"HeaderColor_Default\",\"signature\":\"默言\",\"originSignature\":\"默言\",\"stateTip\":\"想脱单\",\"mood\":\"https://china-img.soulapp.cn/admin/2020-09-27/e07981e6-1842-4ece-ba1f-a003935f91d5.png\",\"moodTip\":\"平静\",\"createStr\":\"刚刚\",\"followType\":0,\"bubblingType\":1,\"consecutiveTimes\":1,\"hasPicked\":false,\"hasExp\":false},{\"userId\":-1,\"userIdEcpt\":\"aVdvdzBITmlHMnpnQkp2UDhiV2VUdz09\",\"version\":16,\"avatarName\":\"avatar-1641448266371-01811\",\"avatarColor\":\"HeaderColor_Default\",\"signature\":\"小杨哭包\",\"originSignature\":\"小杨哭包\",\"stateTip\":\"想脱单\",\"mood\":\"https://china-img.soulapp.cn/admin/2020-09-27/e07981e6-1842-4ece-ba1f-a003935f91d5.png\",\"moodTip\":\"平静\",\"createStr\":\"刚刚\",\"followType\":0,\"bubblingType\":1,\"consecutiveTimes\":1,\"hasPicked\":false,\"hasExp\":false},{\"userId\":-1,\"userIdEcpt\":\"ZktKWVR3eFBxMTVYRnBhV05La1orUT09\",\"version\":243,\"avatarName\":\"avatar-1694167702447-00052\",\"avatarColor\":\"HeaderColor_Default\",\"signature\":\"༺以星河为聘༺\",\"originSignature\":\"༺以星河为聘༺\",\"stateTip\":\"找连麦搭子\",\"mood\":\"https://china-img.soulapp.cn/bubbling/icon/xlm.png\",\"moodTip\":\"平静\",\"createStr\":\"刚刚\",\"followType\":0,\"bubblingType\":1,\"consecutiveTimes\":1,\"hasPicked\":false,\"hasExp\":false},{\"userId\":-1,\"userIdEcpt\":\"TS9NTGZNNHdkNVl1RTU3dnhxODE4QT09\",\"version\":11,\"avatarName\":\"avatar-1696238130682-00618\",\"avatarColor\":\"HeaderColor_Default\",\"signature\":\"西红柿炒鸡蛋\",\"originSignature\":\"西红柿炒鸡蛋\",\"stateTip\":\"快乐摸鱼\",\"mood\":\"https://china-img.soulapp.cn/bubbling/icon/klmy.png\",\"moodTip\":\"平静\",\"createStr\":\"刚刚\",\"followType\":0,\"bubblingType\":1,\"consecutiveTimes\":1,\"hasPicked\":false,\"hasExp\":false},{\"userId\":-1,\"userIdEcpt\":\"SjV5aWRZNmRnWVp1WUNwTVhMR0V3QT09\",\"version\":1,\"avatarName\":\"avatar-1641433124630-00989\",\"avatarColor\":\"HeaderColor_Default\",\"signature\":\"小气芭拉\",\"originSignature\":\"小气芭拉\",\"stateTip\":\"大学生DD\",\"mood\":\"https://china-img.soulapp.cn/admin/2023-07-05/83afd2c2-8cd7-4525-ba13-d0407b82bf6f.png\",\"moodTip\":\"平静\",\"createStr\":\"刚刚\",\"followType\":0,\"bubblingType\":1,\"consecutiveTimes\":1,\"hasPicked\":false,\"hasExp\":false},{\"userId\":-1,\"userIdEcpt\":\"c2IydWc1TktZSnkvZ29OU1VteVFpUT09\",\"version\":2096,\"avatarName\":\"avatar-1632915847795-02085\",\"avatarColor\":\"HeaderColor_Default\",\"signature\":\"欲穿花寻路.\",\"originSignature\":\"欲穿花寻路.\",\"stateTip\":\"找聊天搭子\",\"mood\":\"https://china-img.soulapp.cn/bubbling/icon/xlt.png\",\"moodTip\":\"平静\",\"createStr\":\"刚刚\",\"followType\":0,\"bubblingType\":1,\"consecutiveTimes\":1,\"hasPicked\":false,\"hasExp\":false},{\"userId\":-1,\"userIdEcpt\":\"UDFuNkJUa3Q5ZzB1RTU3dnhxODE4QT09\",\"version\":5,\"avatarName\":\"avatar-1640687016478-04921\",\"avatarColor\":\"HeaderColor_Default\",\"signature\":\"七秒钟的记忆\uD83E\uDD70\",\"originSignature\":\"七秒钟的记忆\uD83E\uDD70\",\"stateTip\":\"树洞吐槽DD\",\"mood\":\"https://china-img.soulapp.cn/bubbling/icon/bglk.png\",\"moodTip\":\"平静\",\"createStr\":\"5分钟前\",\"followType\":0,\"bubblingType\":1,\"consecutiveTimes\":1,\"hasPicked\":false,\"hasExp\":false},{\"userId\":-1,\"userIdEcpt\":\"WGU0UTBhV1lsRVdIY0JMSUs3VlMxZz09\",\"version\":1,\"avatarName\":\"avatar-1643340190129-01191\",\"avatarColor\":\"HeaderColor_Default\",\"signature\":\"俄罗斯娜娜\",\"originSignature\":\"俄罗斯娜娜\",\"stateTip\":\"找王者搭子\",\"mood\":\"https://china-img.soulapp.cn/bubbling/icon/wzdd.png\",\"moodTip\":\"平静\",\"createStr\":\"5分钟前\",\"followType\":0,\"bubblingType\":1,\"consecutiveTimes\":1,\"hasPicked\":false,\"hasExp\":false},{\"userId\":-1,\"userIdEcpt\":\"dG5paGs0NWpKZnRjYmFXR3FVZDBLUT09\",\"version\":30,\"avatarName\":\"avatar-1720849137397-03545\",\"avatarColor\":\"HeaderColor_Default\",\"signature\":\"渃業\",\"originSignature\":\"渃業\",\"stateTip\":\"找聊天搭子\",\"mood\":\"https://china-img.soulapp.cn/bubbling/icon/xlt.png\",\"moodTip\":\"平静\",\"createStr\":\"8分钟前\",\"followType\":0,\"bubblingType\":1,\"consecutiveTimes\":1,\"hasPicked\":false,\"hasExp\":false},{\"userId\":-1,\"userIdEcpt\":\"eVI2aWg4MjRnYVNodUl5MEpNOUVkQT09\",\"version\":24,\"avatarName\":\"avatar-1643982892344-01273\",\"avatarColor\":\"HeaderColor_Default\",\"signature\":\"空谷幽兰\",\"originSignature\":\"空谷幽兰\",\"stateTip\":\"热爱旅行DD\",\"mood\":\"https://china-img.soulapp.cn/admin/2023-07-05/10b5981f-b078-4e89-a47e-a87c7ee403b0.png\",\"moodTip\":\"平静\",\"createStr\":\"9分钟前\",\"followType\":0,\"bubblingType\":1,\"consecutiveTimes\":1,\"hasPicked\":false,\"hasExp\":false},{\"userId\":-1,\"userIdEcpt\":\"Z0ZzUHVKZVQzbUMvZ29OU1VteVFpUT09\",\"version\":7,\"avatarName\":\"avatar-1644724975374-07152\",\"avatarColor\":\"HeaderColor_Default\",\"signature\":\"feelings\",\"originSignature\":\"feelings\",\"stateTip\":\"找连麦搭子\",\"mood\":\"https://china-img.soulapp.cn/bubbling/icon/xlm.png\",\"moodTip\":\"平静\",\"createStr\":\"10分钟前\",\"followType\":0,\"bubblingType\":1,\"consecutiveTimes\":1,\"hasPicked\":false,\"hasExp\":false},{\"userId\":-1,\"userIdEcpt\":\"TE5yV0JIWVRVMGJnQkp2UDhiV2VUdz09\",\"version\":5,\"avatarName\":\"avatar-1635124502642-00333\",\"avatarColor\":\"HeaderColor_Default\",\"signature\":\"米热\",\"originSignature\":\"米热\",\"stateTip\":\"心累\",\"mood\":\"https://china-img.soulapp.cn/admin/2021-05-21/e82a68d5-bc24-4f2a-9441-4aa928a667b8.png\",\"moodTip\":\"心累\",\"createStr\":\"10分钟前\",\"followType\":0,\"bubblingType\":2,\"consecutiveTimes\":1,\"hasPicked\":false,\"hasExp\":false},{\"userId\":-1,\"userIdEcpt\":\"RDROWFpwQjFIanBYRnBhV05La1orUT09\",\"version\":125,\"avatarName\":\"avatar-1641304425865-00167\",\"avatarColor\":\"HeaderColor_Default\",\"signature\":\"Souler\",\"originSignature\":\"Souler\",\"stateTip\":\"我正在...\",\"mood\":\"https://china-img.soulapp.cn/bubbling/icon/wzz....png\",\"moodTip\":\"平静\",\"createStr\":\"10分钟前\",\"followType\":0,\"desc\":\"ggjt\",\"bubblingType\":1,\"consecutiveTimes\":1,\"hasPicked\":false,\"hasExp\":false},{\"userId\":-1,\"userIdEcpt\":\"V1JUay9FaExwTVdiM0YxdnRVc25uQT09\",\"version\":369,\"avatarName\":\"avatar-1640143622695-02394\",\"avatarColor\":\"HeaderColor_Default\",\"signature\":\"可有可无的人\",\"originSignature\":\"可有可无的人\",\"stateTip\":\"我正在...\",\"mood\":\"https://china-img.soulapp.cn/bubbling/icon/wzz....png\",\"moodTip\":\"平静\",\"createStr\":\"11分钟前\",\"followType\":0,\"desc\":\"从不高估自己\",\"bubblingType\":1,\"consecutiveTimes\":1,\"hasPicked\":false,\"hasExp\":false},{\"userId\":-1,\"userIdEcpt\":\"dGxTL2hMWllRUnlIY0JMSUs3VlMxZz09\",\"version\":25,\"avatarName\":\"avatar-1638407103582-01107\",\"avatarColor\":\"HeaderColor_Default\",\"signature\":\"对伱微笑莼屬澧貌126\",\"originSignature\":\"对伱微笑莼屬澧貌126\",\"stateTip\":\"开心\",\"mood\":\"https://china-img.soulapp.cn/admin/2021-05-20/719a77ec-8195-4504-b538-55437e29f54d.png\",\"moodTip\":\"开心\",\"createStr\":\"12分钟前\",\"followType\":0,\"bubblingType\":2,\"consecutiveTimes\":1,\"hasPicked\":false,\"hasExp\":false}],\"skinCnt\":0},\"success\":true}" // JSON 响应体内容
                    );
                    Response response = new Response.Builder()
                            .request(request)
                            .protocol(Protocol.HTTP_1_1)
                            .code(200) // HTTP 状态码
                            .message("OK") // HTTP 状态消息
                            .body(responseBody)
                            .build();
                    XOkHttpUtil.soulInterceptor(request,response);
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
