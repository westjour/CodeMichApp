package com.example.jourdan.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.TableRow;

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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        appData = ((MyApplication) this.getApplication()).getAppData();
        Log.d(TAG, "AppData: "+appData.getSalaryString() +" "+ appData.getCities());
        Log.d(TAG, TAG + " created successfully");

        populateTable();
    }


    /**
     * @Brief
     */
    public void populateTable(){
        TableLayout table = (TableLayout) findViewById(R.id.statsTable);
        table.removeAllViews();

        TableRow row;
        for(int i=0; i<5; i++) {
            row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            for(int j=0; j<3; j++) {
                TextView view = new TextView(this);
                view.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                view.setText("text");
                row.addView(view);
            }

            table.addView(row);
        }
    }
}