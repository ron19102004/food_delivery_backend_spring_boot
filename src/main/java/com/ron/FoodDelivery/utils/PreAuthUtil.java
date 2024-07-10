package com.ron.FoodDelivery.utils;

public class PreAuthUtil {
    public static final String hasAll = "hasAnyAuthority('USER','SELLER','ADMIN','DELIVER')";
    public static final String hasADMIN = "hasAuthority('ADMIN')";
    public static final String hasUSER = "hasAuthority('USER')";
    public static final String hasSELLER = "hasAuthority('SELLER')";
    public static final String hasDELIVER = "hasAuthority('DELIVER')";
    public static final String hasDELIVER_SELLER = "hasAnyAuthority('DELIVER','SELLER')";
}
