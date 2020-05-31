package com.example.myapplication;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import com.example.comonlib.ExitListener;
import com.example.comonlib.Interceptor;
import com.example.comonlib.WatchDog;

public class CrashApplication extends Application {
    public static final String TAG = "CrashApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        WatchDog.with(this)
                .addExitListener(new ExitListener() {
                    @Override
                    public void exitApp() {
                        try {
                            Intent intent = new Intent(CrashApplication.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        } catch (Exception e) {
                            Log.d(TAG, " exception e" + e.getMessage());
                        }
                    }
                })
                .crashSize(100)
                .oomSize(5)
                .addInterceptor(new Interceptor() {
                    @Override
                    public boolean intercept(Throwable throwable) {
                        Log.d(TAG, " do no thing ");
                        return false;
                    }
                })
                .buildAndWatch();
    }
}
