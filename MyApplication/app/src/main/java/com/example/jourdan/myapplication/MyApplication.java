package com.example.jourdan.myapplication;

import android.app.Application;

/**
 * Created by timsloncz on 10/4/14.
 */
public class MyApplication extends Application {
    AppData APP_DATA = new AppData();

    public AppData getAppData()
    {
        return APP_DATA;
    }
    public void setAppData(AppData appData)
    {
        APP_DATA = appData;
    }

}
