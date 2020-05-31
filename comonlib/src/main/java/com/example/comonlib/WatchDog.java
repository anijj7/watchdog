package com.example.comonlib;

import android.app.Application;

public class WatchDog {
    public static final String TAG = "WatchDog";

    public static WatchDogBuilder with(Application application) {
        return new WatchDogBuilder(application);
    }

    private WatchDog() {
        throw new AssertionError(" should not new WatchDog() ");
    }
}
