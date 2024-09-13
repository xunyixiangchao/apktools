package com.example.apptools;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apptools.utils.LogToFile;
import com.example.apptools.utils.XDataUtil;
import com.example.apptools.utils.XFloatingUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static class ListItem {
        public Class<?> aClass;
        public CharSequence label;

        public ListItem(String log, Class<?> activityClass) {
            label = log;
            aClass = activityClass;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<ListItem> result = new ArrayList<>();
//        result.add(new ListItem("Log",LogActivity.class));
//        result.add(new ListItem("Toast",ToastActivity.class));
        result.add(new ListItem("Toast", MainActivity.class));
        MyAdapter adapter = new MyAdapter(this, result);
        recyclerView.setAdapter(adapter);
        recyclerView.setVisibility(View.GONE);
//        XDataUtil.setXDataValue(getApplication(),1,"5");

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (!Settings.canDrawOverlays(this)) {
//                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
//                        Uri.parse("package:" + getPackageName()));
//                startActivityForResult(intent, 111);
//            }
//        }

        XFloatingUtil.init(this);

    }

    public void tag(String tag, String content) {
        /**
         * 1.不带tag
         * invoke-static {v2}, Lcom/example/apptools/utils/LogToFile;->write(Ljava/lang/String;)V
         * 2.带tag
         * invoke-static {v1,v2}, Lcom/example/apptools/utils/LogToFile;->write(Ljava/lang/String;Ljava/lang/String;)V
         */
        LogToFile.write(content);
        LogToFile.write(tag, content);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 111) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Settings.canDrawOverlays(this)) {
                    XFloatingUtil.init(this);
                } else {
                    // 权限被拒绝，处理权限拒绝的情况
                    Toast.makeText(this, "需要悬浮窗权限才能继续", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public static class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private List<ListItem> mItemList;
        private Activity mContext;

        public MyAdapter(Activity context, List<ListItem> listItemList) {
            mItemList = listItemList;
            mContext = context;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            ListItem listItem = mItemList.get(position);

            ViewGroup.LayoutParams layoutParams = holder.textView.getLayoutParams();
            layoutParams.height = mContext.getResources().getDisplayMetrics().widthPixels / 4;
            holder.textView.setLayoutParams(layoutParams);
            holder.textView.setText(listItem.label);
            holder.textView.setOnClickListener(v -> {
                int xposedGameValue = XDataUtil.getXposedGameValue(mContext.getApplication());
                Toast.makeText(mContext, String.valueOf(xposedGameValue), Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(mContext,listItem.aClass);
//                if (intent != null) {
//                    mContext.startActivity(intent);
//                }
            });
        }

        @Override
        public int getItemCount() {
            return (mItemList == null || mItemList.size() == 0) ? 0 : mItemList.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            private TextView textView;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                textView = itemView.findViewById(R.id.text);
            }
        }
    }
}