package com.thebizio.biziosalonms.service;

public class CalculateUtilService {
    public static Double roundTwoDigits(Double no){
        return Math.round(no * 100.0) / 100.0;
    }

    public static <T> T nullOrZeroValue(T val, T dVal) {
        return val == null ? dVal : val;
    }

    public static boolean isEven(int x) { return x % 2 == 0; }
}
