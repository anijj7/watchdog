package com.example.comonlib.interceptor;

import android.util.Log;

import com.example.comonlib.Interceptor;
import com.example.comonlib.Utils;
import com.example.comonlib.WatchDogBuilder;

import java.io.File;
import java.io.PrintWriter;

public class LogInterceptor implements Interceptor {
    public static final String TAG = "WatchD:LogInterceptor";

    public static final String CRASH_FIX = ".txt";

    @Override
    public boolean intercept(Throwable throwable) {
        Log.d(TAG, " throwable e " + throwable.getMessage());
        File file = new File(WatchDogBuilder.CRASH_PATH + File.separator + System.currentTimeMillis() + CRASH_FIX);
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(file);
            throwable.printStackTrace(writer);
            writer.flush();
        } catch (Exception e) {
            Log.e(TAG, " error ee ");
        } finally {
            Utils.close(writer);
        }

        Utils.deleteFile(WatchDogBuilder.CRASH_PATH, WatchDogBuilder.sCrashSize);

        return false;
    }
}
