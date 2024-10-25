package com.example.apptools.utils;

public class XRandom {


    public static void main(String[] args) {
        double ra = Math.random() * 1000000.0d;
        System.out.println(ra);
        String out = String.format("%06d", Long.valueOf((long) (ra)));
        System.out.println(out);
    }
}
