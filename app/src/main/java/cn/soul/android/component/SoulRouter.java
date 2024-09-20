package cn.soul.android.component;

public class SoulRouter {
    private static SoulRouter f6790d;
    public static SoulRouter i() {
        if (f6790d == null) {
            synchronized (SoulRouter.class) {
                if (f6790d == null) {
                    f6790d = new SoulRouter();
                }
            }
        }
        return f6790d;

    }

    public a e(String s) {
        return new a();
    }



}
