package com.example.jourdan.myapplication;

import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class HomeActivity extends BaseActivity {
    public final static String SALARY = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Get current GPS location
        LocationManager locationManager = (LocationManager)
        getSystemService(this.LOCATION_SERVICE);
        LocationListener locationListener = new MyLocationListener(this);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
    }

    /** Called when the user clicks the Send button */
    public void sendMessage(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, MapActivity.class);
        EditText editText = (EditText) findViewById(R.id.edit_message);
        String message = editText.getText().toString();
        intent.putExtra(SALARY, message);
        startActivity(intent);
    }
}
