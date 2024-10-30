package com.example.apptools.utils.soul;

import android.util.Log;

import com.example.apptools.utils.GsonUtil;
import com.example.apptools.utils.LogToFile;
import com.example.apptools.utils.soul.bean.bubble.BubbleResponse;
import com.example.apptools.utils.soul.bean.bubble.BubblingListItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Request;
import okhttp3.Response;

public class SoulBubbleService implements SoulService {

    /**
     * 头像url
     * https://china-img.soulapp.cn/heads/avatar-1634544776581-00623.png?x-oss-process=image/resize,m_fill,h_181,w_181,type_2/format,webp
     * 替换这部分即可 avatar-1634544776581-00623.png
     */
    List<BubblingListItem> listData = new ArrayList<>();

    @Override
    public void interceptor(Request request, Response response, Map<String, String> queryParams) {

        try {
            BubbleResponse bubbleResponse = GsonUtil.build().fromJson(response.body().string(), BubbleResponse.class);
            LogToFile.writeBubble(GsonUtil.build().toJson(bubbleResponse.getData().getBubblingList()));
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG+"SoulBubbleService", e.toString());
        }
    }
}
