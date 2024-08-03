package com.ron.FoodDelivery.utils;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public abstract class RegexValid {
    protected boolean check(String regex, String value) {
        final Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(value).matches();
    }

    public abstract boolean isEmail(String email);

    public abstract boolean isTel(String tel);
    public abstract boolean isAwsS3Url(String url);
}
