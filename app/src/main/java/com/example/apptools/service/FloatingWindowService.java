package com.example.apptools.service;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.apptools.utils.GsonUtil;
import com.example.apptools.utils.NetAsyncUtil;
import com.example.apptools.utils.XDataUtil;
import com.example.apptools.utils.XDiaLogUtil;
import com.example.apptools.utils.XThread;
import com.example.apptools.utils.soul.bean.bubble.BubblingListItem;
import com.example.apptools.utils.soul.util.BubbleUtil;
import com.example.apptools.utils.soul.util.XSoulUtil;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.soul.android.component.SoulRouter;

public class FloatingWindowService extends Service implements EndCall {

    public WindowManager windowManager;
    private LinearLayout floatingView;
    public LinearLayout recyLayout;
    public RecyclerView recyclerView;
    public MyAdapter adapter;
    public List<String> list;

    public Handler handler;
    public Runnable runnable;
    public int scrollPosition = 0;
    public int delayMillis = 500; // 每次滚动之间的延迟时间（以毫秒为单位）

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    //    String[] items = {"石头剪刀布", "骰子", String.format("防撤%s", XDataUtil.isRecall(this) ? "已开启" : "未开启"),
//            "验证", "关闭", "跳转", "url"};
    private static Map<Integer, String> map = new HashMap<>();

    static {
//        map.put(0,"获取Bubble");
//        map.put(12,"发个Bubble");

//        map.put(1, "剪刀石头布");
//        map.put(2, "骰子");
        map.put(1, "游戏");
        map.put(2, "本地撤回%s");
        map.put(3, "防撤%s");
        map.put(4, "BUBBLE");
        map.put(5, "广告%s");
        map.put(6, "去水印%s");
        map.put(7,"去礼仪限制%s");
        map.put(8, "其他");
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
                            if (!XDataUtil.isChecked(FloatingWindowService.this)) {
                                showCheckDialog();
                            } else {
                                showListDialog();
                            }
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

        initRecyclerView();
        //签到
        XSoulUtil.click(this);

        new NetAsyncUtil(this, XDataUtil.typeMap.get(XDataUtil.NET_CONFIG)).execute(XDataUtil.CONFIG_URL);
        XDataUtil.checkConfig(this);
    }

    private void initRecyclerView() {

        recyLayout = new LinearLayout(this);
        recyLayout.setOrientation(LinearLayout.VERTICAL); // 设置LinearLayout的排列方向为垂直
        // 设置LinearLayout的内边距（padding）
//        int padding = (int) (getResources().getDisplayMetrics().density * 10);
//        recyLayout.setPadding(padding, padding * 5, padding, padding * 5);
        recyLayout.setBackgroundColor(0xFFFFFFFF);
        TextView close = new TextView(this);
        close.setText("X     ");
        close.setTextSize(24);
        close.setGravity(Gravity.END);
        LinearLayout.LayoutParams cl = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 100);
        close.setLayoutParams(cl);
        close.setOnClickListener(v -> jump(0));
        recyLayout.addView(close);
        TextView close2 = new TextView(this);
        close2.setText("停 ");
        close2.setTextSize(24);
        close2.setGravity(Gravity.END);
        close2.setOnClickListener(v -> handler.removeCallbacks(runnable));
        recyLayout.addView(close2);
        recyclerView = new RecyclerView(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setLayoutParams(new LinearLayout.LayoutParams(getResources().getDisplayMetrics().widthPixels, getResources().getDisplayMetrics().widthPixels));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        adapter = new MyAdapter(this, this);
        recyclerView.setAdapter(adapter);
        recyLayout.addView(recyclerView);
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                if (recyclerView.getAdapter() != null) {
                    int itemCount = recyclerView.getAdapter().getItemCount();
                    if (scrollPosition < itemCount - 1) {
                        scrollPosition += 1;
                        recyclerView.smoothScrollToPosition(scrollPosition);
                        // 继续运行此任务
                        handler.postDelayed(runnable, 1000);
                    }
                }
            }
        };
    }

    private void showCheckDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(FloatingWindowService.this);
        String[] items = {"验证"};
        builder.setItems(items, (dialog, which) -> {
            switch (which) {
                case 0:
                    // 验证
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


    private void showListDialog() {
        String[] items = new String[0];
        String checkData = XDataUtil.getXDataValue(this, XDataUtil.CHECK);
        List<String> itemList = new ArrayList<>();
        if ("666999".equals(checkData)) {
            for (String value : map.values()) {
                if (mergeItem(value, itemList)) {
                    continue;
                }
                itemList.add(value);
            }
        } else {
            try {
                String listString = checkData.split("#")[2];
                String[] list = listString.split("_");
                for (String item : list) {
                    String value = map.get(Integer.valueOf(item));
                    if (mergeItem(value, itemList)) {
                        continue;
                    }
                    itemList.add(value);
                }
            } catch (Exception e) {
                XDataUtil.defaultAll(this);
            }
        }
        items = itemList.toArray(new String[0]);
        AlertDialog.Builder builder = new AlertDialog.Builder(FloatingWindowService.this);
        String[] finalItems = items;
        builder.setItems(items, (dialog, which) -> {
            switch (which) {
                default:
                    converge(finalItems, which);
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

    private boolean mergeItem(String value, List<String> itemList) {
        if (value.contains("防撤")) {
            itemList.add(String.format(value, XDataUtil.isRecall(this) ? "已开启" : "未开启"));
            return true;
        }
        if (value.contains("广告")) {
            itemList.add(String.format(value, XDataUtil.isHideAd(this) ? "已开启" : "已关闭"));
            return true;
        }
        if (value.contains("本地撤回")) {
            itemList.add(String.format(value, XDataUtil.isLocalRecall(this) ? "已开启" : "已关闭"));
            return true;
        }
        if (value.contains("去水印")) {
            itemList.add(String.format(value, XDataUtil.isCloseWater(this) ? "已开启" : "已关闭"));
            return true;
        }
        if(value.contains("去礼仪")){
            itemList.add(String.format(value, XDataUtil.isCloseChatLimit(this) ? "已开启" : "已关闭"));
            return true;
        }
        return false;
    }

    private void converge(String[] finalItems, int which) {
        switch (finalItems[which]) {
            case "游戏":
            case "BUBBLE":
            case "其他":
                XDiaLogUtil.showListDialog(this, finalItems[which]);
                break;
            default:
                if (finalItems[which].contains("防撤")) {
                    XDataUtil.recall(this);
                }
                if (finalItems[which].contains("广告")) {
                    XDataUtil.hideAd(this);
                }
                if (finalItems[which].contains("本地撤回")) {
                    XDataUtil.localRecall(this);
                }
                if (finalItems[which].contains("去水印")) {
                    XDataUtil.closeWater(this);
                }
                if (finalItems[which].contains("去礼仪")) {
                    XDataUtil.closeChatLimit(this);
                }
                break;
        }
    }

    @Override
    public void onEnd(int currentId) {
        if (list != null && list.size() - 1 >= currentId) {
            String data = list.get(list.size() - 1 - currentId);
            String[] split = data.split("-->");
            String dataString = split[1];
            Type listType = new TypeToken<List<BubblingListItem>>() {
            }.getType();
//                // 将JSON字符串转换为List集合
            List<BubblingListItem> bubblingList = GsonUtil.build().fromJson(dataString, listType);
            bubblingList.get(0).setTopDate(split[0]);
            if (currentId > 0) {
                adapter.addData(bubblingList, currentId);
            } else {
                adapter.setData(bubblingList, 0);
            }
        }
    }

    @Override
    public void jump(int currentId) {
        if (recyLayout != null) {
            windowManager.removeView(recyLayout);
            handler.removeCallbacks(runnable);
        }
    }


    public static class MyAdapter extends RecyclerView.Adapter<MyAdapter.BubbleViewHolder> {
        private Context mContext;
        private List<BubblingListItem> mList;
        private int currentId;
        private EndCall endCall;


        public MyAdapter(Context context, EndCall endCall) {
            this.mContext = context;
            this.endCall = endCall;
        }

        public void setData(List<BubblingListItem> list, int id) {
            mList = list;
            currentId = id;
            XThread.runOnMain(() -> notifyDataSetChanged());
        }

        public void addData(List<BubblingListItem> list, int id) {
            if (mList != null) {
                mList.addAll(list);
            } else {
                mList = list;
            }
            currentId = id;
            XThread.runOnMain(() -> notifyDataSetChanged());
        }

        @NonNull
        @Override
        public MyAdapter.BubbleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            LinearLayout pLayout = new LinearLayout(parent.getContext());
            pLayout.setOrientation(LinearLayout.VERTICAL);
            pLayout.setLayoutParams(ll);
            TextView topView = new TextView(parent.getContext());
            LinearLayout.LayoutParams topLayout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 100);
            topView.setLayoutParams(topLayout);
            topView.setBackgroundColor(0xFF999999);
            topView.setGravity(Gravity.CENTER);
            topView.setTag("top");
            pLayout.addView(topView);
            LinearLayout layout = new LinearLayout(parent.getContext());
            layout.setBackgroundColor(0xFFFFFFFF);
            layout.setOrientation(LinearLayout.HORIZONTAL);
            layout.setLayoutParams(ll);
            int padding = (int) (parent.getContext().getResources().getDisplayMetrics().density * 5);
            layout.setPadding(padding, padding, padding, padding);
            ImageView icon = new ImageView(parent.getContext());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(181, 181);
            icon.setLayoutParams(layoutParams);
            icon.setTag("icon");
//            icon.setBackgroundColor(0xFFBB86FC);
            layout.addView(icon);
            LinearLayout rightLayout = new LinearLayout(parent.getContext());
            LinearLayout.LayoutParams right = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            rightLayout.setLayoutParams(right);
            rightLayout.setOrientation(LinearLayout.VERTICAL);
//            rightLayout.setGravity(Gravity.CENTER_VERTICAL);
            TextView title = new TextView(parent.getContext());
            title.setPadding(padding, 0, 0, padding);
            title.setTag("title");
            title.setGravity(Gravity.CENTER_VERTICAL);
//            title.setBackgroundColor(0xFFBB8600);
            title.setTextSize(18);
            title.setTextColor(0xFF000000);
            rightLayout.addView(title);
            TextView desc = new TextView(parent.getContext());
            desc.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            desc.setPadding(padding, 0, 0, padding);
            desc.setTag("desc");
            desc.setGravity(Gravity.CENTER_VERTICAL);
            desc.setTextColor(0xFF000000);
            rightLayout.addView(desc);
            layout.addView(rightLayout);
            pLayout.addView(layout);
            return new BubbleViewHolder(pLayout);
        }

        @Override
        public void onBindViewHolder(@NonNull MyAdapter.BubbleViewHolder holder, int position) {
            if (this.endCall != null && position == mList.size() - 1) {
                currentId++;
                endCall.onEnd(currentId);
            }
            BubblingListItem item = mList.get(position);
            if (TextUtils.isEmpty(item.getTopDate())) {
                holder.top.setVisibility(View.GONE);
            } else {
                holder.top.setVisibility(View.VISIBLE);
                holder.top.setText(item.getTopDate());
            }
            String url = String.format("https://china-img.soulapp.cn/heads/%s.png?x-oss-process=image/resize,m_fill,h_181,w_181,type_2/format,webp", item.getAvatarName());
            Glide.with(mContext).load(url).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(holder.icon);
            holder.title.setText(item.getSignature());
            holder.desc.setText(TextUtils.isEmpty(item.getDesc()) ? item.getStateTip() : item.getDesc());
            holder.itemView.setOnClickListener(v -> {
                endCall.jump(currentId);
                XThread.runOnMain(() -> {
                    SoulRouter.i().e("/chat/conversationActivity").w("userIdEcpt", item.getUserIdEcpt()).d();
                    try {
                        BubbleUtil.prick(item.getUserIdEcpt());
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("FloatingWindowService", e.toString());
                    }
                });
            });
        }

        @Override
        public int getItemCount() {
            return (mList == null || mList.size() == 0) ? 0 : mList.size();
        }

        public static class BubbleViewHolder extends RecyclerView.ViewHolder {
            private ImageView icon;
            private TextView title;
            private TextView desc;
            private TextView top;

            public BubbleViewHolder(@NonNull View itemView) {
                super(itemView);
                icon = itemView.findViewWithTag("icon");
                title = itemView.findViewWithTag("title");
                desc = itemView.findViewWithTag("desc");
                top = itemView.findViewWithTag("top");
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            if (floatingView != null) {
                windowManager.removeView(floatingView);
            }
            if (recyLayout != null) {
                windowManager.removeView(recyLayout);
                handler.removeCallbacks(runnable);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
