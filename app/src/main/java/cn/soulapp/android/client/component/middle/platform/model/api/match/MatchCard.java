package cn.soulapp.android.client.component.middle.platform.model.api.match;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MatchCard implements Serializable, Parcelable {
    public static final Parcelable.Creator<MatchCard> CREATOR;
    public ArrayList<String> _carouselTexts;
    public String _contentUrl;
    public String _jumpUrl;
    public String areaCode;

    @Nullable
    public List<MatchCard> batchMatchCardList;
    public String buttonDesc;
    public boolean canGameTeam;
    public boolean canOpenFateCard;
    public int cardType;
    public String cardUserContent;
    public String childTitle;
    public String cityCode;
    public String cityName;
    public int commodityId;

    @Nullable
    public String commodityPicUrl;

    @Nullable
    public String confirmPopupContent;
    public String cornerMarkUrl;
    public int countdownTimes;
    public int discountSoulCoin;
    public String displayDiscount;

    @Nullable
    public Integer expireDays;
    public boolean filterMatching;
    public int filterTime;
    public int freeTimes;
    public boolean hasFateCardConfig;
    public String instruction;
    public boolean isTop;
    public String itemIdentity;
    public String matchPageChildTitle;
    public String matchPageTitle;
    public MatchCard packageCardInfo;
    public String pageId;
    public String priceIconContent;
    public String priceIconName;
    public String priceIconUrl;
    public int priceType;
    public String reason;
    public int soulCoin;
    public String sourceCode;
    public boolean speedup;
    public int speedupCount;
    public int speedupTime;
    public int status;
    public String title;
    public int type;
    public String useRemindMsg;
    public String vipDiscountContent;
    public int vipSoulCoin;

    /* loaded from: classes25.dex */
    public @interface CardType {
    }

    /* renamed from: cn.soulapp.android.client.component.middle.platform.model.api.match.MatchCard$a */
    /* loaded from: classes25.dex */
    static class a implements Parcelable.Creator<MatchCard> {

        a() {
        }

        @Override // android.os.Parcelable.Creator
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public MatchCard createFromParcel(Parcel parcel) {
            return new MatchCard(parcel);
        }

        @Override // android.os.Parcelable.Creator
        /* renamed from: b, reason: merged with bridge method [inline-methods] */
        public MatchCard[] newArray(int i11) {
            return new MatchCard[i11];
        }
    }

    static {
        CREATOR = new a();
    }

    public MatchCard(int i11) {
        this.cardType = i11;
    }

    public void decreaseFreeTimes() {
        int i11 = this.freeTimes - 1;
        this.freeTimes = i11;
        this.freeTimes = Math.max(i11, 0);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String displayCount() {
        if (this.cardUserContent.length() > 7) {
            return "999999+";
        }
        return this.cardUserContent;
    }

    public boolean isSetVoice() {
        if (TextUtils.isEmpty(this.buttonDesc) || "音色偏好".equals(this.buttonDesc)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MatchCard{title='" + this.title + "', childTitle='" + this.childTitle + "', reason='" + this.reason + "', cityName='" + this.cityName + "', areaCode='" + this.areaCode + "', cityCode='" + this.cityCode + "', itemIdentity='" + this.itemIdentity + "', displayDiscount='" + this.displayDiscount + "', instruction='" + this.instruction + "', cardUserContent='" + this.cardUserContent + "', status=" + this.status + ", type=" + this.type + ", commodityId=" + this.commodityId + ", soulCoin=" + this.soulCoin + ", discountSoulCoin=" + this.discountSoulCoin + ", vipSoulCoin=" + this.vipSoulCoin + ", isTop=" + this.isTop + ", cardType=" + this.cardType + ", speedupCount=" + this.speedupCount + ", speedupTime=" + this.speedupTime + ", filterTime=" + this.filterTime + ", priceType=" + this.priceType + ", speedup=" + this.speedup + ", filterMatching=" + this.filterMatching + ", freeTimes=" + this.freeTimes + ", priceIconName='" + this.priceIconName + "', priceIconUrl='" + this.priceIconUrl + "', canGameTeam=" + this.canGameTeam + ", vipDiscountContent='" + this.vipDiscountContent + "', canOpenFateCard=" + this.canOpenFateCard + ", hasFateCardConfig=" + this.hasFateCardConfig + ", _contentUrl='" + this._contentUrl + "', _jumpUrl='" + this._jumpUrl + "', _carouselTexts=" + this._carouselTexts + ", matchPageTitle='" + this.matchPageTitle + "', matchPageChildTitle='" + this.matchPageChildTitle + "', cornerMarkUrl='" + this.cornerMarkUrl + "', expireDays=" + this.expireDays + ", confirmPopupContent='" + this.confirmPopupContent + "', commodityPicUrl='" + this.commodityPicUrl + "', buttonDesc='" + this.buttonDesc + "', useRemindMsg='" + this.useRemindMsg + "', batchMatchCardList='" + this.batchMatchCardList + "'}";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i11) {
        parcel.writeString(this.title);
        parcel.writeString(this.childTitle);
        parcel.writeString(this.reason);
        parcel.writeString(this.cityName);
        parcel.writeString(this.areaCode);
        parcel.writeString(this.cityCode);
        parcel.writeString(this.itemIdentity);
        parcel.writeString(this.displayDiscount);
        parcel.writeString(this.instruction);
        parcel.writeString(this.cardUserContent);
        parcel.writeInt(this.status);
        parcel.writeInt(this.type);
        parcel.writeInt(this.commodityId);
        parcel.writeInt(this.soulCoin);
        parcel.writeInt(this.discountSoulCoin);
        parcel.writeInt(this.vipSoulCoin);
        parcel.writeByte(this.isTop ? (byte) 1 : (byte) 0);
        parcel.writeInt(this.cardType);
        parcel.writeInt(this.speedupCount);
        parcel.writeInt(this.speedupTime);
        parcel.writeInt(this.filterTime);
        parcel.writeInt(this.priceType);
        parcel.writeByte(this.speedup ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.filterMatching ? (byte) 1 : (byte) 0);
        parcel.writeInt(this.freeTimes);
        parcel.writeString(this.priceIconName);
        parcel.writeString(this.priceIconUrl);
        parcel.writeByte(this.canGameTeam ? (byte) 1 : (byte) 0);
        parcel.writeString(this.vipDiscountContent);
        parcel.writeByte(this.canOpenFateCard ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.hasFateCardConfig ? (byte) 1 : (byte) 0);
        parcel.writeString(this._contentUrl);
        parcel.writeString(this._jumpUrl);
        parcel.writeStringList(this._carouselTexts);
        parcel.writeString(this.matchPageTitle);
        parcel.writeString(this.matchPageChildTitle);
        parcel.writeString(this.cornerMarkUrl);
        if (this.expireDays == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(this.expireDays.intValue());
        }
        parcel.writeString(this.confirmPopupContent);
        parcel.writeString(this.commodityPicUrl);
        parcel.writeString(this.priceIconContent);
        parcel.writeString(this.buttonDesc);
        parcel.writeString(this.useRemindMsg);
        parcel.writeTypedList(this.batchMatchCardList);
        parcel.writeString(this.sourceCode);
        parcel.writeString(this.pageId);
    }

    public MatchCard(Parcel parcel) {
        this.title = parcel.readString();
        this.childTitle = parcel.readString();
        this.reason = parcel.readString();
        this.cityName = parcel.readString();
        this.areaCode = parcel.readString();
        this.cityCode = parcel.readString();
        this.itemIdentity = parcel.readString();
        this.displayDiscount = parcel.readString();
        this.instruction = parcel.readString();
        this.cardUserContent = parcel.readString();
        this.status = parcel.readInt();
        this.type = parcel.readInt();
        this.commodityId = parcel.readInt();
        this.soulCoin = parcel.readInt();
        this.discountSoulCoin = parcel.readInt();
        this.vipSoulCoin = parcel.readInt();
        this.isTop = parcel.readByte() != 0;
        this.cardType = parcel.readInt();
        this.speedupCount = parcel.readInt();
        this.speedupTime = parcel.readInt();
        this.filterTime = parcel.readInt();
        this.priceType = parcel.readInt();
        this.speedup = parcel.readByte() != 0;
        this.filterMatching = parcel.readByte() != 0;
        this.freeTimes = parcel.readInt();
        this.priceIconName = parcel.readString();
        this.priceIconUrl = parcel.readString();
        this.canGameTeam = parcel.readByte() != 0;
        this.vipDiscountContent = parcel.readString();
        this.canOpenFateCard = parcel.readByte() != 0;
        this.hasFateCardConfig = parcel.readByte() != 0;
        this._contentUrl = parcel.readString();
        this._jumpUrl = parcel.readString();
        this._carouselTexts = parcel.createStringArrayList();
        this.matchPageTitle = parcel.readString();
        this.matchPageChildTitle = parcel.readString();
        this.cornerMarkUrl = parcel.readString();
        if (parcel.readByte() == 0) {
            this.expireDays = null;
        } else {
            this.expireDays = Integer.valueOf(parcel.readInt());
        }
        this.confirmPopupContent = parcel.readString();
        this.commodityPicUrl = parcel.readString();
        this.priceIconContent = parcel.readString();
        this.buttonDesc = parcel.readString();
        this.useRemindMsg = parcel.readString();
        this.batchMatchCardList = parcel.createTypedArrayList(CREATOR);
        this.sourceCode = parcel.readString();
        this.pageId = parcel.readString();
    }
}