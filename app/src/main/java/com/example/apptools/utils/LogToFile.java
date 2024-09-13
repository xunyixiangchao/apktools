package com.example.apptools.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogToFile {
    private static final String LOG_FILE = "Slog-%s.txt";
    private static final String LOG_FILE_TAG = "Slog-%s-%s.txt";
    /**
     * 1.不带tag
     * invoke-static {v2}, Lcom/example/apptools/utils/LogToFile;->write(Ljava/lang/String;)V
     * 2.带tag
     * invoke-static {v1,v2}, Lcom/example/apptools/utils/LogToFile;->write(Ljava/lang/String;Ljava/lang/String;)V
     */
    public static void write(String content) {
        write(null, content);
    }
    /**
     * 1.不带tag
     * invoke-static {v2}, Lcom/example/apptools/utils/LogToFile;->write(Ljava/lang/String;)V
     * 2.带tag
     * invoke-static {v1,v2}, Lcom/example/apptools/utils/LogToFile;->write(Ljava/lang/String;Ljava/lang/String;)V
     */
    public static void write(String tag, String content) {
        try {
            Date now = new Date(System.currentTimeMillis());
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String date = format.format(now);
            Format format1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
            String textData = format1.format(now);
            String fileName = String.format(LOG_FILE, date);
//            if (tag != null) {
//                fileName = String.format(LOG_FILE_TAG, tag, date);
//            }
            File logFile = new File("/storage/emulated/0/", fileName);
            FileWriter fw = new FileWriter(logFile, true);
            fw.write(textData + "-->" + (tag != null ? tag + ": " : ": ") + content + "\n");
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
