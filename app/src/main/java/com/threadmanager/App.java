package com.threadmanager;

import android.app.Application;
import android.content.Context;

public class App extends Application {
    private static App instance;

    public static Context getContext() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
