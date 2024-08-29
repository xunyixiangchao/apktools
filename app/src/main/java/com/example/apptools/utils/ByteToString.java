package com.example.apptools.utils;

import java.io.UnsupportedEncodingException;

public class ByteToString {


    public String getStr(){
        byte[] bbb = new byte[]{};
        String str = null;
        try {
            str = new String(bbb,"UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(str);
        return str;
    }
}
