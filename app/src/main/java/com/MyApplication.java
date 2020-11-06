package com;
import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

public class MyApplication extends Application {
    @Override
    protected void attachBaseContext(Context context){
        super.attachBaseContext(context);
        MultiDex.install(this);
    }
}
