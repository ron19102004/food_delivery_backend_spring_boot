package com.ron.FoodDelivery.utils;

import java.util.regex.Pattern;

public abstract class RegexValid {
    public static final String EMAIL = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    public static final String TEL = "^(\\+[0-9]{1,3})?[0-9]{10,}$";
    protected Pattern emailPattern;
    protected Pattern telPattern;

    protected RegexValid() {
        emailPattern = Pattern.compile(EMAIL);
        telPattern = Pattern.compile(TEL);
    }

    public abstract boolean isEmail(String email);

    public abstract boolean isTel(String tel);
}
