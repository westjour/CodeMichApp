package com.example.jourdan.myapplication;

import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import org.json.JSONException;
import org.json.JSONObject;


public class HomeActivity extends BaseActivity {
    final String TAG = "HomeActivity.java";
    AppData appData = new AppData();
    MyLocationListener locationListener = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Get current GPS location
        LocationManager locationManager = (LocationManager)
        getSystemService(this.LOCATION_SERVICE);

        locationListener = new MyLocationListener(this);
        Log.d(TAG, "Location listener created" );

        MyLocationListener locationListener = new MyLocationListener(this);

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, locationListener);
    }
    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();
        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkbox_parks:
                if (checked)
                    //Add to attributes in appData
                    appData.addAttribute("parks");
                else
                    //Remove from attributes in appData
                    appData.removeAttribute("parks");
                break;
            case R.id.checkbox_schools:
                if (checked)
                    appData.addAttribute("schools");
                else
                    appData.removeAttribute("schools");
                break;
            case R.id.checkbox_Tax:
                if (checked)
                    appData.addAttribute("tax");
                else
                    appData.removeAttribute("tax");
                break;
            case R.id.checkbox_historic:
                if (checked)
                    appData.addAttribute("historic");
                else
                    appData.removeAttribute("historic");
                break;
        }
        Log.d(TAG, "Attributes: " + appData.getAttributes());
    }


    /** Called when the user clicks the Send button */
    public void sendMessage(View view) throws JSONException {
        JSONObject location = new JSONObject();

        // Do something in response to button
        Intent intent = new Intent(this, MapActivity.class);
        String lat = Double.toString( locationListener.mLat );
        location.put("lat",lat);
        String lng = Double.toString(locationListener.mLng);
        location.put("lng",lng);
        EditText editText = (EditText) findViewById(R.id.edit_message);
        String salary = editText.getText().toString();
        Log.d(TAG,"user loc: "+location);
        appData.setSalary(salary);
        appData.addCity(location);

        Log.d(TAG,"Cities on leave Home: "+appData.getSalaryString()+" "+appData.getCities());

        ((MyApplication) this.getApplication()).setAppData(appData);
        startActivity(intent);
    }
}
