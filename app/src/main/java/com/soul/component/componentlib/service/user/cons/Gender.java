package com.soul.component.componentlib.service.user.cons;

public class Gender {
    private static final /* synthetic */ Gender[] list;
    public static final Gender FEMALE;
    public static final Gender MALE;
    public static final Gender UNKNOWN;

    static {

        Gender gender = new Gender("MALE", 0);
        MALE = new Gender("MALE", 0);
        Gender gender2 = new Gender("FEMALE", 1);
        FEMALE = gender2;
        Gender gender3 = new Gender("UNKNOWN", 2);
        UNKNOWN = gender3;
        list = new Gender[]{gender, gender2, gender3};
    }

    public String name;
    public Integer type;


    Gender(String str, int i11) {
        name = str;
        type = i11;
    }

    public static Gender valueOfd(String str) {
        for (Gender gender : list) {
            if(gender.name.equals(str)){
                return gender;
            }
        }
        return UNKNOWN;
    }

    public static Gender[] valuesd() {
        return list;
    }

    public String toGenderString() {
        if (MALE == this) {
            return "男";
        }
        return "女";
    }

    public int toInt() {
        if (MALE == this) {
            return 0;
        }
        if (FEMALE == this) {
            return 1;
        }
        return -1;
    }

}
