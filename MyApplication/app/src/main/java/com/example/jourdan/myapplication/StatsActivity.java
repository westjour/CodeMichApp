package com.example.jourdan.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class StatsActivity extends BaseActivity
{

    final String TAG = "StatsActivity.java";
    private AppData appData = null;

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
     * @return
     */
    public void populateTable(){
        TableLayout table = (TableLayout) findViewById(R.id.statsTable);
        table.removeAllViews();

        JSONArray citiesArray = appData.getCities();
        Utility util = new Utility();

        String lat = null;
        String lng = null;
        String cityName = null;
        JSONObject cityTax = null;

        for(int i=0; i<citiesArray.length(); i++) {
            try
            {
                lat = citiesArray.getJSONObject(0).getString("lat");
                lng = citiesArray.getJSONObject(0).getString("lng");
                cityName = util.getCityFromCoord(lat, lng);
                cityTax = util.getCityTax(cityName);
            }
            catch (JSONException e) { e.printStackTrace(); }

            TableRow row = new TableRow(this);

            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            for(int j=0; j<3; j++) {
                TextView view = new TextView(this);

                // City name
                if(j == 0){
                    view.setText(cityName);
                }

                // Residential income tax
                else if( j == 1){
                    try
                    {
                        view.setText(cityTax.getString("res"));
                    }
                    catch (JSONException e) { e.printStackTrace(); }

                }

                // Non-residential income tax
                else{
                    try
                    {
                        view.setText(cityTax.getString("nonres"));
                    }
                    catch (JSONException e) { e.printStackTrace(); }
                }

                view.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT));

                row.addView(view);
            }

            table.addView(row);
        }
    }
}