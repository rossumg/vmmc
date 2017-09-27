package org.itech.vmmc;

import android.app.Application;
import android.content.Context;

/**
 * Created by rossumg on 9/25/2017.
 */

public class MyApplication extends Application {

    private static Context context;

    public void onCreate() {
        super.onCreate();
        MyApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return MyApplication.context;
    }
}
