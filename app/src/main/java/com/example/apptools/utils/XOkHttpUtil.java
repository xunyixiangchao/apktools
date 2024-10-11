package com.example.apptools.utils;


import android.text.TextUtils;
import android.util.Log;


import com.example.apptools.utils.soul.SoulFactory;
import com.example.apptools.utils.soul.SoulService;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

public class XOkHttpUtil {
    private static final Charset UTF8 = Charset.forName("UTF-8");
    public static String TAG = "XOkHttpUtil";

    public static void soulInterceptor(Request request, Response response) {
        //https://api-user.soulapp.cn/bubbling/list?pageId=ChatList_MailList&pageIndex=1
        try {
            URL url = request.url().url();
            Map<String, String> queryParams = splitQuery(url.getQuery());
            SoulService service = SoulFactory.getService(url.getPath());
            Response newResponse = getNewResponse(response);
            if (service != null) {
                service.interceptor(request, newResponse, queryParams);
//                Log.e("XOkHttpUtil-1", GsonUtil.build().toJson(oldResponse.body().string()));
//                Log.e("XOkHttpUtil-3", GsonUtil.build().toJson(newResponse.body().string()));

            } else {
                if ("api-user.soulapp.cn".equals(url.getHost())) {
//                    if (url.getPath().contains("avatar")) {
//                        LogToFile.writeUrl(TAG, url, newResponse.body().string());
//                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            e.printStackTrace();
        }
    }

    private static Response getNewResponse(Response response) {
        ResponseBody responseBody = response.body();
        long contentLength = responseBody.contentLength();
        BufferedSource source = responseBody.source();
        try {
            source.request(Long.MAX_VALUE); // Buffer the entire body.
        } catch (IOException e) {
            e.printStackTrace();
        }
        Buffer buffer = source.buffer();
        Charset charset = UTF8;
        MediaType contentType = responseBody.contentType();
        if (contentType != null) {
            try {
                charset = contentType.charset(UTF8);
            } catch (UnsupportedCharsetException e) {
                Log.e("XOkHttpUtil-Charset", e.toString());
                e.printStackTrace();
            }
        }
        if (contentLength != 0) {
            String result = buffer.clone().readString(charset);
            ResponseBody newBody = ResponseBody.create(contentType, result);
            //得到所需的string，开始判断是否异常
            //***********************do something*****************************
            Response newResponse = new Response.Builder().code(response.code())
                    .request(response.request()).message(response.message()).protocol(response.protocol()).body(newBody).build();
            return newResponse;
        }
        return null;
    }


    // 解析URL查询参数的方法
    private static Map<String, String> splitQuery(String query) throws UnsupportedEncodingException {
        Map<String, String> queryPairs = new HashMap<>();
        if (TextUtils.isEmpty(query)) {
            return queryPairs;
        }
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            queryPairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"),
                    URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
        }
        return queryPairs;
    }
}
