package cn.soulapp.android.component.bubble.api.bean;


import org.jetbrains.annotations.NotNull;

public class BubbleCreateRequest {

    private String content;

    private String emoji="https://china-img.soulapp.cn/bubbling/icon/xlt.png";

    private String skinId;

    private String stateTip="找聊天搭子";

    @NotNull
    private String bubblingType = "1";

    @NotNull
    private String reqVersion = "1";

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEmoji() {
        return emoji;
    }

    public void setEmoji(String emoji) {
        this.emoji = emoji;
    }

    public String getSkinId() {
        return skinId;
    }

    public void setSkinId(String skinId) {
        this.skinId = skinId;
    }

    public String getStateTip() {
        return stateTip;
    }

    public void setStateTip(String stateTip) {
        this.stateTip = stateTip;
    }

    @NotNull
    public String getBubblingType() {
        return bubblingType;
    }

    public void setBubblingType(@NotNull String bubblingType) {
        this.bubblingType = bubblingType;
    }

    @NotNull
    public String getReqVersion() {
        return reqVersion;
    }

    public void setReqVersion(@NotNull String reqVersion) {
        this.reqVersion = reqVersion;
    }
}
