package com.ron.FoodDelivery.utils;


import java.io.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class LogUtil {
    private static String logFilePath;
    private static String folderFilePath;

    private static void checkFolderPath(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public static void initialize(String pathFolder, String nameFile) {
        checkFolderPath(pathFolder);
        LogUtil.folderFilePath = pathFolder;
        LogUtil.logFilePath = pathFolder + "/" + nameFile + "-" + System.currentTimeMillis() + ".txt";
    }

    public enum Status {
        ERROR,
        INFO
    }

    private static String getTime() {
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Bangkok"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return now.format(formatter);
    }

    private static void writeLog(String content) {
        if (logFilePath == null) {
            System.err.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<LogUtil have already initialize yet !!!>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            return;
        }
        checkFolderPath(LogUtil.folderFilePath);
        try (FileWriter fw = new FileWriter(logFilePath, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(content);
        } catch (IOException e) {
            System.err.println("Error write log: " + e.getMessage());
        }
    }

    public static void log(Class<?> log_position, Object object, Status status) {
        StringBuilder content = new StringBuilder();
        content.append("[")
                .append(status.name())
                .append("]-[Log position: ")
                .append(log_position)
                .append("]-[Log time: ")
                .append(getTime())
                .append("]\n==> ")
                .append(object);
        if (status.equals(Status.ERROR)) {
            System.err.println(content);
        } else {
            System.out.println(content);
        }
        writeLog(content.toString());
    }
}
