package com.example.apptools.utils.soul.util;

import android.content.Context;

import com.example.apptools.utils.XToast;

import cn.soulapp.android.component.bubble.api.BubblePublishViewModel;
import cn.soulapp.android.component.bubble.api.BubbleViewModel;
import cn.soulapp.android.component.bubble.api.bean.BubbleCreateRequest;

public class BubbleUtil {
    /**
     * 获取Bubble列表
     * @param context
     */
    public static void requestBubbleList(Context context) {
        BubbleViewModel model = new BubbleViewModel();
        model.g();
        XToast.showToast(context,"获取Bubble");
    }

    /**
     * 发送Bubble
     * @param context
     * @param content
     */
    public static void sendBubble(Context context, String content) {
        BubbleCreateRequest request = new BubbleCreateRequest();
        request.setContent(content);
        request.setEmoji("https://china-img.soulapp.cn/bubbling/icon/xlt.png");
        request.setStateTip("找聊天搭子");
        request.setReqVersion("1");
        request.setBubblingType("1");
        BubblePublishViewModel model = new BubblePublishViewModel();
        model.a(request);
        XToast.showToast(context,"发送了Bubble");
    }

    public static void prick(String userIdEcpt) {
        BubbleViewModel model = new BubbleViewModel();
        model.h(userIdEcpt,null);
    }
}
