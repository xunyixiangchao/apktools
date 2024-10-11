package com.example.apptools.utils.soul.util;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class XParamsInterceptor implements Interceptor {

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder newBuilder = request.newBuilder();
        return null;
    }

    private Request a(Request.Builder builder, Request request) {
//        C40670a m64829c = BaseInfoUtils.m64829c(SDnsConfig.f175275a.m161448c());
        HttpUrl.Builder newBuilder = request.url().newBuilder();
//        newBuilder.addEncodedQueryParameter("bik", m64829c.f162945a);
//        newBuilder.addQueryParameter("bi", m64829c.f162946b);
        builder.url(newBuilder.build());
        return builder.build();
    }


}
