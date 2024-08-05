package com.ron.FoodDelivery.utils;

public class ImageUrlDriveUtil {
    public static String toUrlCanRead(String url) {
        String[] urlSplit = url.split("/");
        return "https://drive.google.com/thumbnail?id=" + urlSplit[urlSplit.length - 2] + "&sz=w1000;";
    }
}
