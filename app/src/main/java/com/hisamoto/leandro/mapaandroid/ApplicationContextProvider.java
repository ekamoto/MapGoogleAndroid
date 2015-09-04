package com.hisamoto.leandro.mapaandroid;

import android.app.Application;
import android.content.Context;

/**
 * Created by leandro on 01/09/15.
 */
public class ApplicationContextProvider extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();
    }

    public static Context getContext() {

        return context;
    }
}
