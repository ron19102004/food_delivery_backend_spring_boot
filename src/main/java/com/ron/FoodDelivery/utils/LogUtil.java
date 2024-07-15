package com.ron.FoodDelivery.utils;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;


public class LogUtil {
    public enum Status {
        ERROR,
        INFO
    }

    private static String getTime() {
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Bangkok"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss Z");
        return now.format(formatter);
    }

    public static void log(Class<?> log_position, Object object, Status status) {
        if (status.equals(Status.ERROR)) {
            System.err.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<[ERROR]>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        } else {
            System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<[INFO]>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        }
        System.out.println("[Log position: " + log_position + "] *** [Log time: " + getTime() + "]");
        System.out.println(object);
    }
}
