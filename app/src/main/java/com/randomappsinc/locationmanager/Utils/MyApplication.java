package com.randomappsinc.locationmanager.Utils;

import android.app.Application;
import android.content.Context;

import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.IoniconsModule;

/**
 * Created by alexanderchiou on 4/9/17.
 */

public final class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        Iconify.with(new IoniconsModule());
        context = getApplicationContext();
    }

    public static Context getAppContext() {
        return context;
    }
}
