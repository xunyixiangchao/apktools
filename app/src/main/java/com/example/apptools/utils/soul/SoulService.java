package com.example.apptools.utils.soul;

import android.content.Context;

import java.util.Map;

import okhttp3.Request;
import okhttp3.Response;

public interface SoulService {


    void interceptor(Context context, Request request, Response response, Map<String, String> queryParams);
}
