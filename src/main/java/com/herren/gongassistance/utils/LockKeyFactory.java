package com.herren.gongassistance.utils;

public class LockKeyFactory {
    private static final String CREATE_SHOP_KEY_PREFIX = "CREATE_SHOP_";
    private static final String CREATE_EMPLOYEE_KEY_PREFIX = "CREATE_SHOP_";

    public static String createShopKey(String bizNo) {
        return String.join(CREATE_SHOP_KEY_PREFIX, bizNo);
    }

    public static String createEmployeeKey(String tel) {
        return String.join(CREATE_EMPLOYEE_KEY_PREFIX, tel);
    }
}
