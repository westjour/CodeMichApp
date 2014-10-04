package com.example.jourdan.myapplication;

import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class HomeActivity extends BaseActivity {
    final String TAG = "HomeActivity.java";
    public final static String APP_DATA = "";
    AppData appData = new AppData();
    MyLocationListener locationListener = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utility util = new Utility();
        util.getAffordableAssistedHousing("GrandRapids");
        setContentView(R.layout.activity_home);

        // Get current GPS location
        LocationManager locationManager = (LocationManager)
        getSystemService(this.LOCATION_SERVICE);

        locationListener = new MyLocationListener(this);
        Log.d(TAG, "Location listener created" );

        MyLocationListener locationListener = new MyLocationListener(this);

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
    }

    /** Called when the user clicks the Send button */
    public void sendMessage(View view) throws JSONException {
        //JSONObject appData = new JSONObject();
        JSONObject location = new JSONObject();
        JSONArray cities = new JSONArray();

        // Do something in response to button
        Intent intent = new Intent(this, MapActivity.class);
        String lat = Double.toString( locationListener.mLat );
        location.put("lat",lat);
        String lng = Double.toString( locationListener.mLng );
        location.put("lng",lng);
        cities.put(location);
        EditText editText = (EditText) findViewById(R.id.edit_message);
        String salary = editText.getText().toString();
        appData.setSalary(salary);
        appData.addCity(location);
        //appData.put("salary", salary);
        //appData.put("cities", cities);
        //intent.putExtra(APP_DATA, appData.toString());
        ((MyApplication) this.getApplication()).setAppData(appData);
        startActivity(intent);
    }
}
