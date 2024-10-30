package com.example.apptools.utils.soul;

import android.util.Log;

import com.example.apptools.utils.GsonUtil;
import com.example.apptools.utils.XToast;
import com.example.apptools.utils.soul.bean.sign.SignResponse;

import java.io.IOException;
import java.util.Map;

import cn.soulapp.lib.basic.app.MartianApp;
import okhttp3.Request;
import okhttp3.Response;

public class SoulSignService implements SoulService {
    @Override
    public void interceptor(Request request, Response response, Map<String, String> queryParams) {
        try {
            SignResponse signResponse = GsonUtil.build().fromJson(response.body().string(), SignResponse.class);
            Log.i(TAG + "SignService", GsonUtil.build().toJson(signResponse));
            if (signResponse != null && signResponse.getData() != null) {
                XToast.showToast(MartianApp.b(), signResponse.getData().getMsg());
            }
        } catch (IOException e) {
            Log.e(TAG + "SignService", e.toString());
        }
    }
}
