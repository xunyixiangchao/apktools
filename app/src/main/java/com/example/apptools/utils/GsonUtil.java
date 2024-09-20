package com.example.apptools.utils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class GsonUtil {

    private volatile static Gson mGson;

    public static Gson build() {
        synchronized (GsonUtil.class) {
            if (null == mGson) {
                mGson = new com.google.gson.GsonBuilder()
                        .registerTypeAdapter(Double.class, new JsonSerializer<Double>() {
                            @Override
                            public JsonElement serialize(Double src, Type typeOfSrc, JsonSerializationContext context) {
                                if (src == src.longValue())
                                    return new JsonPrimitive(src.longValue());
                                return new JsonPrimitive(src);
                            }
                        })
                        .create();
            }
            return mGson;
        }
    }
}
