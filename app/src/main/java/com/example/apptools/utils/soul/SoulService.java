package com.example.apptools.utils.soul;

import java.util.Map;

import okhttp3.Request;
import okhttp3.Response;

public interface SoulService {


    void interceptor(Request request, Response response, Map<String, String> queryParams);
}
