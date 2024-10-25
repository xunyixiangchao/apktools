package com.example.apptools.utils.soul;

import android.util.Log;

import com.example.apptools.utils.GsonUtil;
import com.example.apptools.utils.LogToFile;
import com.example.apptools.utils.soul.bean.avatar.AvatarResponse;
import com.example.apptools.utils.soul.bean.avatar.AvatarsItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Request;
import okhttp3.Response;

public class SoulAvatarService implements SoulService {
    public static String TAG = SoulAvatarService.class.getSimpleName();

    @Override
    public void interceptor(Request request, Response response, Map<String, String> queryParams) {
        try {
            AvatarResponse avatarResponse = GsonUtil.build().fromJson(response.body().string(), AvatarResponse.class);
            List<AvatarsItem> list = new ArrayList<>();
            list.addAll(avatarResponse.getData().getFemaleAvatars());
            list.addAll(avatarResponse.getData().getMaleAvatars());
            LogToFile.writeTag(TAG, GsonUtil.build().toJson(list));
//            LogToFile.writeUrl(TAG, request.url().url(), response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("XOkHttpUtil", e.toString());
        }
    }
}
