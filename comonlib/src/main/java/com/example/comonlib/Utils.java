package com.example.comonlib;

import android.util.Log;

import java.io.Closeable;
import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

public class Utils {
    public static final String TAG = "WatchD:Utils";

    public static void createDir(String dir) {
        Log.d(TAG, " create dir " + dir);
        File file = new File(dir);
        if (!file.exists()) {
            boolean result = file.mkdirs();
            Log.d(TAG, "result " + result);
        }
    }

    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                Log.d(TAG, " close error ");
            }
        }
    }


    public static void deleteFile(String dir, int leftSize) {
        File file = new File(dir);
        File[] files = file.listFiles();
        if (files.length > leftSize) {
            Arrays.sort(files, new Comparator<File>() {
                @Override
                public int compare(File o1, File o2) {
                    return (int) (getCreateTime(o2) - getCreateTime(o1));
                }
            });

            for (int i = files.length - 1; i >= leftSize; i--) {
                files[i].delete();
            }
        }


    }

    private static long getCreateTime(File fi) {
        String name = fi.getName();
        String timeValue = name.substring(0, name.lastIndexOf("."));
        Log.d(TAG, " time vlue " + timeValue);
        long result = 0;
        try {
            result = Long.parseLong(timeValue);
        } catch (Exception e) {
            Log.e(TAG, " parse string error", e);
        }
        return result;
    }
}
