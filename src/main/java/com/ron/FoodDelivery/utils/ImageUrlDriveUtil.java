package com.ron.FoodDelivery.utils;

import com.ron.FoodDelivery.exceptions.ServiceException;
import org.springframework.http.HttpStatus;

import java.util.regex.Pattern;

public class ImageUrlDriveUtil {
    private static final String URL_PATTERN = "https:\\/\\/drive\\.google\\.com\\/file\\/d\\/[^\\/]+\\/view\\?usp=(drive_link|sharing)";
    private static final String URL_IMG_PATTERN = "https?:\\/\\/.*\\.(png|jpg)";

    public static String toUrlCanRead(String url) {
        Pattern pattern_img = Pattern.compile(URL_IMG_PATTERN);
        if (pattern_img.matcher(url).matches()) {
            return url;
        }
        Pattern pattern = Pattern.compile(URL_PATTERN);
        if (!pattern.matcher(url).matches()) {
            throw new ServiceException("Link driver invalid.", HttpStatus.BAD_REQUEST);
        }
        String[] urlSplit = url.split("/");
        return "https://drive.google.com/thumbnail?id=" + urlSplit[urlSplit.length - 2] + "&sz=w1000;";
    }
}
