package com.example.apptools.utils.soul;

import android.util.Log;

import com.example.apptools.utils.GsonUtil;
import com.example.apptools.utils.soul.bean.user.UserData;
import com.example.apptools.utils.soul.bean.user.UserResponse;

import java.util.Map;

import okhttp3.Request;
import okhttp3.Response;

public class SoulUserInfoService implements SoulService {

    public static UserData userData = null;

    @Override
    public void interceptor(Request request, Response response, Map<String, String> queryParams) {
        try {
            UserResponse userResponse = GsonUtil.build().fromJson(response.body().string(), UserResponse.class);
            userData = userResponse.getData();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("XOkHttpUtil", e.toString());
        }
    }
}
