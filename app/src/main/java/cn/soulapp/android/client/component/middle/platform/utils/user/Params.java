package cn.soulapp.android.client.component.middle.platform.utils.user;

import java.io.Serializable;

public class Params implements Serializable {
    public String avatarName;
    public String avatarParam;
    public String birthday;
    public String oriAvatarName;
    public int sex;

    public Params(String str, String str2, String str3, String str4, int i11) {
        this.avatarName = str;
        this.avatarParam = str2;
        this.oriAvatarName = str3;
        this.birthday = str4;
        this.sex = i11;
    }

}
