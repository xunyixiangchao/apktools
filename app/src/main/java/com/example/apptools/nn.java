package com.example.apptools;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class nn {

//    public C0928c(ClassLoader classLoader, int i) {
//        Class cls = Boolean.TYPE;
//        this.f4982a = classLoader;
//        if (i >= 20121503 && i < 20122104) {
//            C0874f.m2672m(classLoader, "cn.soulapp.android.square.utils.f0", "a", String.class, Context.class, Long.TYPE, cls, new C0949g());
//            C0874f.m2672m(classLoader, "cn.soulapp.android.square.utils.ImageDownloader", "a", String.class, cls, cls, new C0948f(classLoader));
//            C0874f.m2672m(classLoader, "cn.soulapp.imlib.msg.ImMessage", "a", XposedHelpers.findClass("cn.soulapp.imlib.msg.b.c", classLoader), new C0954l(classLoader, 1));
//            C0874f.m2672m(classLoader, "cn.soulapp.imlib.msg.ImMessage", "a", XposedHelpers.findClass("cn.soulapp.imlib.msg.b.c", classLoader), String.class, String.class, new C0954l(classLoader, 3));
//            C0874f.m2672m(classLoader, "cn.soulapp.imlib.msg.ImMessage", "a", XposedHelpers.findClass("cn.soulapp.imlib.msg.b.c", classLoader), XposedHelpers.findClass("com.soul.im.protos.i", classLoader), new C0954l(classLoader, 2));
//            C0874f.m2672m(classLoader, "cn.soulapp.imlib.msg.ImMessage", "e", new C0954l(classLoader, 4));
//            C0874f.m2672m(classLoader, "cn.soulapp.android.ui.splash.SplashActivity", "a", cls, Bundle.class, new C0943a(classLoader));
//            C0874f.m2672m(classLoader, "cn.soulapp.android.ui.splash.SplashActivity", "a", cls, Bundle.class, Throwable.class, new C0943a(classLoader));
//            C0874f.m2672m(classLoader, "cn.soulapp.android.lib.common.bean.ChatLimitModel", "isLimit", new C0947e(classLoader));
//            Class cls2 = Integer.TYPE;
//            C0874f.m2672m(classLoader, "cn.soulapp.android.component.chat.utils.k0", "c", cls2, new C0950h(classLoader));
//            C0874f.m2672m(classLoader, "cn.soulapp.android.component.chat.utils.k0", "b", cls2, new C0951i(classLoader));
//            C0874f.m2672m(classLoader, "cn.soulapp.android.component.setting.v2.AccountSettingActivity$a", "a", XposedHelpers.findClass("cn.soulapp.android.client.component.middle.platform.d.k1", classLoader), new C0955m(classLoader));
//            return;
//        }
//        if (i >= 20122104 && i < 20122902) {
//            C0874f.m2672m(classLoader, "cn.soulapp.android.square.utils.g0", "a", String.class, Context.class, Long.TYPE, cls, new C0949g());
//            C0874f.m2672m(classLoader, "cn.soulapp.android.square.utils.ImageDownloader", "a", String.class, cls, cls, new C0948f(classLoader));
//            C0874f.m2672m(classLoader, "cn.soulapp.imlib.msg.ImMessage", "a", XposedHelpers.findClass("cn.soulapp.imlib.msg.b.c", classLoader), new C0954l(classLoader, 1));
//            C0874f.m2672m(classLoader, "cn.soulapp.imlib.msg.ImMessage", "a", XposedHelpers.findClass("cn.soulapp.imlib.msg.b.c", classLoader), String.class, String.class, new C0954l(classLoader, 3));
//            C0874f.m2672m(classLoader, "cn.soulapp.imlib.msg.ImMessage", "a", XposedHelpers.findClass("cn.soulapp.imlib.msg.b.c", classLoader), XposedHelpers.findClass("com.soul.im.protos.i", classLoader), new C0954l(classLoader, 2));
//            C0874f.m2672m(classLoader, "cn.soulapp.imlib.msg.ImMessage", "e", new C0954l(classLoader, 4));
//            C0874f.m2672m(classLoader, "cn.soulapp.android.ui.splash.SplashActivity", "a", cls, Bundle.class, new C0943a(classLoader));
//            C0874f.m2672m(classLoader, "cn.soulapp.android.ui.splash.SplashActivity", "a", cls, Bundle.class, Throwable.class, new C0943a(classLoader));
//            C0874f.m2672m(classLoader, "cn.soulapp.android.lib.common.bean.ChatLimitModel", "isLimit", new C0947e(classLoader));
//            Class cls3 = Integer.TYPE;
//            C0874f.m2672m(classLoader, "cn.soulapp.android.component.chat.utils.k0", "c", cls3, new C0950h(classLoader));
//            C0874f.m2672m(classLoader, "cn.soulapp.android.component.chat.utils.k0", "b", cls3, new C0951i(classLoader));
//            C0874f.m2672m(classLoader, "cn.soulapp.android.component.setting.v2.AccountSettingActivity$a", "a", XposedHelpers.findClass("cn.soulapp.android.client.component.middle.platform.d.k1", classLoader), new C0955m(classLoader));
//            return;
//        }
//        if (i >= 20122902 && i < 21010415) {
//            C0874f.m2672m(classLoader, "cn.soulapp.android.square.utils.e0", "a", String.class, Context.class, Long.TYPE, cls, new C0949g());
//            C0874f.m2672m(classLoader, "cn.soulapp.android.square.utils.ImageDownloader", "a", String.class, cls, cls, new C0948f(classLoader));
//            C0874f.m2672m(classLoader, "cn.soulapp.imlib.msg.ImMessage", "a", XposedHelpers.findClass("cn.soulapp.imlib.msg.b.c", classLoader), new C0954l(classLoader, 1));
//            C0874f.m2672m(classLoader, "cn.soulapp.imlib.msg.ImMessage", "a", XposedHelpers.findClass("cn.soulapp.imlib.msg.b.c", classLoader), String.class, String.class, new C0954l(classLoader, 3));
//            C0874f.m2672m(classLoader, "cn.soulapp.imlib.msg.ImMessage", "a", XposedHelpers.findClass("cn.soulapp.imlib.msg.b.c", classLoader), XposedHelpers.findClass("com.soul.im.protos.i", classLoader), new C0954l(classLoader, 2));
//            C0874f.m2672m(classLoader, "cn.soulapp.imlib.msg.ImMessage", "e", new C0954l(classLoader, 4));
//            C0874f.m2672m(classLoader, "cn.soulapp.android.ui.splash.SplashActivity", "a", cls, Bundle.class, new C0943a(classLoader));
//            C0874f.m2672m(classLoader, "cn.soulapp.android.ui.splash.SplashActivity", "a", cls, Bundle.class, Throwable.class, new C0943a(classLoader));
//            Class cls4 = Integer.TYPE;
//            C0874f.m2672m(classLoader, "cn.soulapp.android.ui.splash.HotAdActivity", "a", cls4, new C0945c(classLoader));
//            C0874f.m2672m(classLoader, "cn.soulapp.android.component.chat.ConversationMenuActivity", "a", XposedHelpers.findClass("cn.soulapp.android.client.component.middle.platform.model.api.user.a", classLoader), String.class, cls, cls, Intent.class, new C0956n(classLoader));
//            C0874f.m2672m(classLoader, "cn.soulapp.android.lib.common.bean.ChatLimitModel", "isLimit", new C0947e(classLoader));
//            C0874f.m2672m(classLoader, "cn.soulapp.android.component.chat.utils.k0", "c", cls4, new C0950h(classLoader));
//            C0874f.m2672m(classLoader, "cn.soulapp.android.component.chat.utils.k0", "b", cls4, new C0951i(classLoader));
//            C0874f.m2672m(classLoader, "cn.soulapp.android.component.setting.v2.AccountSettingActivity$a", "a", XposedHelpers.findClass("cn.soulapp.android.client.component.middle.platform.d.k1", classLoader), new C0955m(classLoader));
//            C0874f.m2672m(classLoader, "cn.soulapp.android.component.square.main.SquarePostProvider", "a", Context.class, XposedHelpers.findClass("cn.soulapp.android.square.post.o.e", classLoader), XposedHelpers.findClass("cn.soulapp.android.component.square.main.d1", classLoader), cls4, new C0952j(classLoader));
//            return;
//        }
//        if (i >= 21010415 && i < 21011203) {
//            C0874f.m2672m(classLoader, "cn.soulapp.android.square.utils.e0", "a", String.class, Context.class, Long.TYPE, cls, new C0949g());
//            C0874f.m2672m(classLoader, "cn.soulapp.android.square.utils.ImageDownloader", "a", String.class, cls, cls, new C0948f(classLoader));
//            C0874f.m2672m(classLoader, "cn.soulapp.imlib.msg.ImMessage", "a", XposedHelpers.findClass("cn.soulapp.imlib.msg.b.c", classLoader), new C0954l(classLoader, 1));
//            C0874f.m2672m(classLoader, "cn.soulapp.imlib.msg.ImMessage", "a", XposedHelpers.findClass("cn.soulapp.imlib.msg.b.c", classLoader), String.class, String.class, new C0954l(classLoader, 3));
//            C0874f.m2672m(classLoader, "cn.soulapp.imlib.msg.ImMessage", "a", XposedHelpers.findClass("cn.soulapp.imlib.msg.b.c", classLoader), XposedHelpers.findClass("com.soul.im.protos.i", classLoader), new C0954l(classLoader, 2));
//            C0874f.m2672m(classLoader, "cn.soulapp.imlib.msg.ImMessage", "e", new C0954l(classLoader, 4));
//            C0874f.m2672m(classLoader, "cn.soulapp.android.ui.splash.SplashActivity", "a", cls, Bundle.class, new C0943a(classLoader));
//            C0874f.m2672m(classLoader, "cn.soulapp.android.ui.splash.SplashActivity", "a", cls, Bundle.class, Throwable.class, new C0943a(classLoader));
//            Class cls5 = Integer.TYPE;
//            C0874f.m2672m(classLoader, "cn.soulapp.android.ui.splash.HotAdActivity", "a", cls5, new C0945c(classLoader));
//            C0874f.m2672m(classLoader, "cn.soulapp.android.component.chat.ConversationMenuActivity", "a", XposedHelpers.findClass("cn.soulapp.android.client.component.middle.platform.model.api.user.a", classLoader), String.class, cls, cls, Intent.class, new C0956n(classLoader));
//            C0874f.m2672m(classLoader, "cn.soulapp.android.lib.common.bean.ChatLimitModel", "isLimit", new C0947e(classLoader));
//            C0874f.m2672m(classLoader, "cn.soulapp.android.component.chat.utils.k0", "c", cls5, new C0950h(classLoader));
//            C0874f.m2672m(classLoader, "cn.soulapp.android.component.chat.utils.k0", "b", cls5, new C0951i(classLoader));
//            C0874f.m2672m(classLoader, "cn.soulapp.android.component.setting.v2.AccountSettingActivity$a", "a", XposedHelpers.findClass("cn.soulapp.android.client.component.middle.platform.d.k1", classLoader), new C0955m(classLoader));
//            C0874f.m2672m(classLoader, "cn.soulapp.android.component.square.main.SquarePostProvider", "a", Context.class, XposedHelpers.findClass("cn.soulapp.android.square.post.p.e", classLoader), XposedHelpers.findClass("cn.soulapp.android.component.square.main.e1", classLoader), cls5, new C0952j(classLoader));
//            return;
//        }
//        if (i >= 21011203) {
//            C0874f.m2672m(classLoader, "cn.soulapp.android.square.utils.e0", "a", String.class, Context.class, Long.TYPE, cls, new C0949g());
//            C0874f.m2672m(classLoader, "cn.soulapp.android.square.utils.ImageDownloader", "a", String.class, cls, cls, new C0948f(classLoader));
//            C0874f.m2672m(classLoader, "cn.soulapp.imlib.msg.ImMessage", "a", XposedHelpers.findClass("cn.soulapp.imlib.msg.b.c", classLoader), new C0954l(classLoader, 1));
//            C0874f.m2672m(classLoader, "cn.soulapp.imlib.msg.ImMessage", "a", XposedHelpers.findClass("cn.soulapp.imlib.msg.b.c", classLoader), String.class, String.class, new C0954l(classLoader, 3));
//            C0874f.m2672m(classLoader, "cn.soulapp.imlib.msg.ImMessage", "a", XposedHelpers.findClass("cn.soulapp.imlib.msg.b.c", classLoader), XposedHelpers.findClass("com.soul.im.protos.i", classLoader), new C0954l(classLoader, 2));
//            C0874f.m2672m(classLoader, "cn.soulapp.imlib.msg.ImMessage", "e", new C0954l(classLoader, 4));
//            Class cls6 = Integer.TYPE;
//            C0874f.m2672m(classLoader, "cn.soulapp.android.ui.splash.SplashActivity", "a", cls6, new C0944b(classLoader));
//            C0874f.m2672m(classLoader, "cn.soulapp.android.ui.splash.SplashActivity", "a", cls, Bundle.class, new C0943a(classLoader));
//            C0874f.m2672m(classLoader, "cn.soulapp.android.ui.splash.SplashActivity", "a", cls, Bundle.class, Throwable.class, new C0943a(classLoader));
//            C0874f.m2672m(classLoader, "cn.soulapp.android.ui.splash.HotAdActivity", "a", cls6, new C0945c(classLoader));
//            C0874f.m2672m(classLoader, "cn.soulapp.android.component.chat.ConversationMenuActivity", "a", XposedHelpers.findClass("cn.soulapp.android.client.component.middle.platform.model.api.user.a", classLoader), String.class, cls, cls, Intent.class, new C0956n(classLoader));
//            C0874f.m2672m(classLoader, "cn.soulapp.android.lib.common.bean.ChatLimitModel", "isLimit", new C0947e(classLoader));
//            C0874f.m2672m(classLoader, "cn.soulapp.android.component.chat.utils.k0", "c", cls6, new C0950h(classLoader));
//            C0874f.m2672m(classLoader, "cn.soulapp.android.component.chat.utils.k0", "b", cls6, new C0951i(classLoader));
//            C0874f.m2672m(classLoader, "cn.soulapp.android.component.setting.v2.AccountSettingActivity$a", "a", XposedHelpers.findClass("cn.soulapp.android.client.component.middle.platform.d.k1", classLoader), new C0955m(classLoader));
//            C0874f.m2672m(classLoader, "cn.soulapp.android.component.square.main.SquarePostProvider", "a", Context.class, XposedHelpers.findClass("cn.soulapp.android.square.post.p.e", classLoader), XposedHelpers.findClass("cn.soulapp.android.component.square.main.c1", classLoader), cls6, new C0952j(classLoader));
//            C0874f.m2672m(classLoader, "cn.soulapp.android.component.publish.ui.position.l", "c", XposedHelpers.findClass("com.baidu.mapapi.search.geocode.ReverseGeoCodeResult", classLoader), new C0953k(classLoader));
//            C0874f.m2672m(classLoader, "cn.soulapp.android.component.publish.ui.card.PublishRichTextView", "a", cls6, XposedHelpers.findClass("cn.soulapp.android.square.post.p.j", classLoader), new C0946d(classLoader));
//        }
//    }
}