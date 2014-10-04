package com.example.jourdan.myapplication;

import android.os.Bundle;
import android.util.Log;

public class StatsActivity extends BaseActivity {
    final String TAG = "StatsActivity.java";
    private AppData appData = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        appData = ((MyApplication) this.getApplication()).getAppData();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        Log.d(TAG, TAG + " created successfully" );
    }
}