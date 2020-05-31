package com.example.comonlib;

import android.app.Application;
import android.os.Environment;

import androidx.annotation.NonNull;
import com.example.comonlib.interceptor.LogInterceptor;
import com.example.comonlib.interceptor.OomIntercepter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class WatchDogBuilder implements Thread.UncaughtExceptionHandler {
    public static final String TAG = "WatchD:WatchDogBuilder";

    private Application mApplication;

    private List<Interceptor> mInterceptors;

    // 退出操作监听器
    private ExitListener mExitListener;

    public static final String CRASH_PATH = Environment.getExternalStorageDirectory().getPath() + File.separator + "crash";
    public static final String OOM_PATH = Environment.getExternalStorageDirectory().getPath() + File.separator + "oom";

    private static final int EXIT_CODE = 10;

    public static int sOomSize = 2;
    public static int sCrashSize  = 10;

    public WatchDogBuilder(Application application) {
        this.mApplication = application;
        mInterceptors = new ArrayList<>();
    }

    public WatchDogBuilder addInterceptor(Interceptor interceptor) {
        mInterceptors.add(interceptor);
        return this;
    }

    public WatchDogBuilder addExitListener(ExitListener exitListener) {
        mExitListener = exitListener;
        return this;
    }

    public WatchDogBuilder oomSize(int size) {
        sOomSize = size;
        return this;
    }

    public WatchDogBuilder crashSize(int size) {
        sCrashSize = size;
        return this;
    }


    public void buildAndWatch() {
        Thread.setDefaultUncaughtExceptionHandler(this);
        Utils.createDir(CRASH_PATH);
        Utils.createDir(OOM_PATH);
        addInterceptor(new LogInterceptor());
        addInterceptor(new OomIntercepter());
    }


    @Override
    public void uncaughtException(@NonNull Thread t, @NonNull Throwable e) {
        for (int i = 0; i < mInterceptors.size(); i++) {
            Interceptor interceptor = mInterceptors.get(i);
            if (interceptor.intercept(e)) {
                break;
            }
        }

        if (mExitListener != null) {
            mExitListener.exitApp();
        }
        System.exit(EXIT_CODE);
    }

}
