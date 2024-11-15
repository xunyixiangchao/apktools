package cn.soulapp.android.component.planet.soulmatch.robot.bean;

import com.soul.component.componentlib.service.user.cons.Gender;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

import org.jetbrains.annotations.Nullable;

import cn.soulapp.android.client.component.middle.platform.model.api.match.MatchCard;
import kotlin.jvm.JvmStatic;

public class SoulMatchParams implements Serializable {
    @NotNull
    public static final Companion INSTANCE;

    @Nullable
    private String gameName;

    @Nullable
    private String gameTeamGender;

    @Nullable
    private String gender;
    private boolean isAutoMatch;

    @Nullable
    private MatchCard matchCard;

    @Nullable
    private String matchingActivityImg;

    @Nullable
    private String startMatchType;

    @Nullable
    private Integer subExtType;

    @Nullable
    private String tagName;
    private boolean themeMatch;

    @Nullable
    private String title;

    @Nullable
    private String type;
    private float x;
    private float y;

    /* compiled from: SoulMatchParams.kt */
    /* renamed from: cn.soulapp.android.component.planet.soulmatch.robot.bean.SoulMatchParams$a, reason: from kotlin metadata */
    /* loaded from: classes5.dex */
    public static final class Companion {

        private Companion() {
        }

        public /* synthetic */ Companion(String defaultConstructorMarker) {
            this();
        }

        /* renamed from: f */
        public static /* synthetic */ SoulMatchParams f(Companion companion, MatchCard matchCard, Gender gender, float f11, float f12, boolean z11, String str, String str2, String str3, String str4, int i11, Object obj) {
            if ((i11 & 1) != 0) {
                matchCard = null;
            }
            if ((i11 & 2) != 0) {
                gender = null;
            }
            if ((i11 & 4) != 0) {
                f11 = 0.0f;
            }
            if ((i11 & 8) != 0) {
                f12 = 0.0f;
            }
            if ((i11 & 16) != 0) {
                z11 = false;
            }
            if ((i11 & 32) != 0) {
                str = null;
            }
            if ((i11 & 64) != 0) {
                str2 = null;
            }
            if ((i11 & 128) != 0) {
                str3 = null;
            }
            if ((i11 & 256) != 0) {
                str4 = null;
            }
            return companion.b(matchCard, gender, f11, f12, z11, str, str2, str3, str4);
        }

        @JvmStatic
        @NotNull
        /* renamed from: a */
        public final SoulMatchParams a(@Nullable MatchCard matchCard, @Nullable Gender gender) {
            return f(this, matchCard, gender, 0.0f, 0.0f, false, null, null, null, null, 480, null);
        }

        @JvmStatic
        @NotNull
        /* renamed from: b */
        public final SoulMatchParams b(@Nullable MatchCard matchCard, @Nullable Gender gender, float x11, float y11, boolean autoMatch, @Nullable String gameName, @Nullable String gameTeamGender, @Nullable String title, @Nullable String tagName) {
            String name;

            SoulMatchParams soulMatchParams = new SoulMatchParams();
            soulMatchParams.setMatchCard(matchCard);
            if (gender == null) {
                name = null;
            } else {
                name = gender.name;
            }
            if (name == null) {
                name = Gender.UNKNOWN.name;
            }
            soulMatchParams.setGender(name);
            soulMatchParams.setX(x11);
            soulMatchParams.setY(y11);
            soulMatchParams.setAutoMatch(autoMatch);
            soulMatchParams.setGameName(gameName);
            soulMatchParams.setGameTeamGender(gameTeamGender);
            soulMatchParams.setTitle(title);
            soulMatchParams.setTagName(tagName);
            return soulMatchParams;
        }

        @JvmStatic
        @NotNull
        /* renamed from: c */
        public final SoulMatchParams c(@Nullable MatchCard matchCard, @Nullable Gender gender, @Nullable String gameName, @Nullable String gameTeamGender) {
            return f(this, matchCard, gender, 0.0f, 0.0f, false, gameName, gameTeamGender, null, null, 384, null);
        }

        @JvmStatic
        @NotNull
        /* renamed from: d */
        public final SoulMatchParams d(@Nullable Gender gender, float x11, float y11, boolean autoMatch) {
            return f(this, null, gender, x11, y11, autoMatch, null, null, null, null, 480, null);
        }

        @JvmStatic
        @NotNull
        /* renamed from: e */
        public final SoulMatchParams e(@Nullable Gender gender, @Nullable String title, @Nullable String tagName) {
            return f(this, null, gender, 0.0f, 0.0f, false, null, null, title, tagName, 96, null);
        }
    }

    static {
        INSTANCE = new Companion(null);
    }

    @JvmStatic
    @NotNull
    public static final SoulMatchParams build(@Nullable MatchCard matchCard, @Nullable Gender gender) {
        return INSTANCE.a(matchCard, gender);
    }

    @JvmStatic
    @NotNull
    public static final SoulMatchParams build(@Nullable MatchCard matchCard, @Nullable Gender gender, float f11, float f12, boolean z11, @Nullable String str, @Nullable String str2, @Nullable String str3, @Nullable String str4) {
        return INSTANCE.b(matchCard, gender, f11, f12, z11, str, str2, str3, str4);
    }

    @JvmStatic
    @NotNull
    public static final SoulMatchParams build(@Nullable MatchCard matchCard, @Nullable Gender gender, @Nullable String str, @Nullable String str2) {
        return INSTANCE.c(matchCard, gender, str, str2);
    }

    @JvmStatic
    @NotNull
    public static final SoulMatchParams build(@Nullable Gender gender, float f11, float f12, boolean z11) {
       return INSTANCE.d(gender, f11, f12, z11);
    }

    @JvmStatic
    @NotNull
    public static final SoulMatchParams build(@Nullable Gender gender, @Nullable String str, @Nullable String str2) {
        return INSTANCE.e(gender, str, str2);
    }

    public final void copyParams(@NotNull SoulMatchParams params) {
        setMatchCard(params.getMatchCard());
        setGender(params.getGender());
        setAutoMatch(params.getIsAutoMatch());
        setX(params.getX());
        setY(params.getY());
        setTitle(params.getTitle());
        setTagName(params.getTagName());
        setGameName(params.getGameName());
        setGameTeamGender(params.getGameTeamGender());
    }

    @Nullable
    public final String getGameName() {
        return this.gameName;
    }

    @Nullable
    public final String getGameTeamGender() {
        return this.gameTeamGender;
    }

    @Nullable
    public final String getGender() {
        return this.gender;
    }

    @NotNull
    public final Gender getGenderVO() {
        return Gender.UNKNOWN;
    }

    @Nullable
    public final MatchCard getMatchCard() {
        return this.matchCard;
    }

    @Nullable
    public final String getMatchingActivityImg() {
        return this.matchingActivityImg;
    }

    @Nullable
    public final String getStartMatchType() {
        return this.startMatchType;
    }

    @Nullable
    public final Integer getSubExtType() {
        return this.subExtType;
    }

    @Nullable
    public final String getTagName() {
        return this.tagName;
    }

    public final boolean getThemeMatch() {
        return this.themeMatch;
    }

    @Nullable
    public final String getTitle() {
        return this.title;
    }

    @Nullable
    public final String getType() {
        return this.type;
    }

    public final float getX() {
        return this.x;
    }

    public final float getY() {
        return this.y;
    }

    /* renamed from: isAutoMatch, reason: from getter */
    public final boolean getIsAutoMatch() {
        return this.isAutoMatch;
    }

    public final void setAutoMatch(boolean z11) {
        this.isAutoMatch = z11;
    }

    public final void setGameName(@Nullable String str) {
        this.gameName = str;
    }

    public final void setGameTeamGender(@Nullable String str) {
        this.gameTeamGender = str;
    }

    public final void setGender(@Nullable String str) {
        this.gender = str;
    }

    public final void setMatchCard(@Nullable MatchCard matchCard) {
        this.matchCard = matchCard;
    }

    public final void setMatchingActivityImg(@Nullable String str) {
        this.matchingActivityImg = str;
    }

    public final void setStartMatchType(@Nullable String str) {
        this.startMatchType = str;
    }

    public final void setSubExtType(@Nullable Integer num) {
        this.subExtType = num;
    }

    public final void setTagName(@Nullable String str) {
        this.tagName = str;
    }

    public final void setThemeMatch(boolean z11) {
        this.themeMatch = z11;
    }

    public final void setTitle(@Nullable String str) {
        this.title = str;
    }

    public final void setType(@Nullable String str) {
        this.type = str;
    }

    public final void setX(float f11) {
        this.x = f11;
    }

    public final void setY(float f11) {
        this.y = f11;
    }

}
