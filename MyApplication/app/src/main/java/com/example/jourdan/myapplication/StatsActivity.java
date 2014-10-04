package com.example.jourdan.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;

public class StatsActivity extends BaseActivity {
    final String TAG = "StatsActivity.java";
    private AppData appData = null;
    private Utility util = new Utility();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        appData = ((MyApplication) this.getApplication()).getAppData();
        Log.d(TAG, "AppData: "+appData.getSalaryString() +" "+ appData.getCities());
        super.onCreate(savedInstanceState);
        Log.d(TAG, TAG + " created successfully" );
    }
}