package com.ron.FoodDelivery.utils;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ConsoleUtil {
    private final Class<?> _class;

    private ConsoleUtil(Class<?> _class) {
        this._class = _class;
    }

    public static ConsoleUtil newConsoleLog(Class<?> _class) {
        return new ConsoleUtil(_class);
    }

    public void info(Object object) {
        System.out.println(this.log(object, Status.INFO, null));
    }
    public void info(Object object,String notes) {
        System.out.println(this.log(object, Status.INFO, notes));
    }

    public void err(Object object) {
        System.err.println(this.log(object, Status.ERROR, null));
    }
    public void err(Object object,String notes) {
        System.err.println(this.log(object, Status.ERROR, notes));
    }

    private enum Status {
        ERROR,
        INFO
    }

    private String getTime() {
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Bangkok"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return now.format(formatter);
    }

    private String log(Object object, Status status, String notes) {
        return "+++++++++++++++++++++++++++++\n[" +
                status.name() +
                "]-[Log position: " +
                this._class +
                "]-[Log time: " +
                this.getTime() +
                "]\n==> " +
                object + (notes != null ? "\nNotes: " + notes : "") + "\n-----------------------------";
    }
}
