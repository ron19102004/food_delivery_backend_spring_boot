package com.ron.FoodDelivery.utils;

import java.util.regex.Pattern;

public abstract class RegexValid {
    protected boolean check(String regex, String value) {
        final Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(value).matches();
    }

    public abstract boolean isEmail(String email);

    public abstract boolean isTel(String tel);
}
