package com.example.comonlib.interceptor;

import android.os.Debug;
import android.util.Log;

import com.example.comonlib.Interceptor;
import com.example.comonlib.Utils;
import com.example.comonlib.WatchDogBuilder;

import java.io.File;

public class OomIntercepter implements Interceptor {
    public static final String TAG = "OomIntercepter";

    public static final String FILE_FIX = ".hprof";

    @Override
    public boolean intercept(Throwable throwable) {
        if (throwable instanceof OutOfMemoryError) {
            Log.d(TAG, " oom error ");

            try {
                Debug.dumpHprofData(WatchDogBuilder.OOM_PATH + File.separator + System.currentTimeMillis() + FILE_FIX);
            } catch (Exception e) {
                Log.d(TAG, " dump error " + e.getMessage());
            }
            Utils.deleteFile(WatchDogBuilder.OOM_PATH, WatchDogBuilder.sOomSize);
            return true;
        }
        return false;
    }
}
