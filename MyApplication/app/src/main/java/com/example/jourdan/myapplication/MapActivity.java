package com.example.jourdan.myapplication;

import android.app.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class MapActivity extends BaseActivity {
    private static final String TAG = "MapActivity";
    public final static String APP_DATA = "";
    AppData appData = null;
    Intent statsIntent = null;;

    public final static String SALARY = "";
    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private AlertDialog.Builder builder = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        appData = ((MyApplication) this.getApplication()).getAppData();
        builder = new AlertDialog.Builder(this);
        JSONArray cityPoints = null;
        super.onCreate(savedInstanceState);

        statsIntent = new Intent(this, StatsActivity.class);
        Log.d(TAG, "inital appData"+appData.toString());

        setContentView(R.layout.activity_map);
        mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

        // Set listener for user map clicks
        mMap.setOnMapClickListener(new OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
                JSONObject location = new JSONObject();
                String lat = Double.toString(point.latitude);
                String lng = Double.toString(point.longitude);
                try {
                    location.put("lat",lat);
                    location.put("lng",lng);
                    appData.addCity(location);
                    updateAppData( appData );
                    startActivity( statsIntent );

                    displayDialog(builder, appData.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.d(TAG, "User click" + lat + " : " + lng);
                // For GooglePlayServices Version
                // String geoCodeUrl = "https://maps.googleapis.com/maps/api/geocode/json?"+ "latlng=" +lat+","+lng+"&key=AIzaSyDzhKcf70Hb_NfVM1ktF4LtA161JsOJcio";
                String geoCodeUrl = "http://maps.googleapis.com/maps/api/geocode/json?latlng="+lat+","+lng+"&sensor=false";

                Log.d(TAG, "Initial geoCodeUrl: "+geoCodeUrl);
                String city = getCity(geoCodeUrl);
            }
        });
        // we will using AsyncTask during parsing
        // This creates markers for all cities with income tax and places them on the map
        /*
        try {
            String mapStuff = new AsyncTaskParseJson().execute("http://data.michigan.gov/resource/kvss-tqw8.json?").get();
            displayPointsOnMap(new JSONArray(mapStuff));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(42.7336, -84.4467))
                .title("Hello world"));
                */
    }


    /**
     * @Brief Update App data
     * @param data
     */
    private void updateAppData(AppData data)
    {
        ((MyApplication) this.getApplication()).setAppData(data);
    }


    /* Brief:
     * Param: builder
     * Param: message
     */
    public void displayDialog(AlertDialog.Builder builder, String message)
    {
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //do things
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();


     /* Brief:
     *  Param: url
     */
    }

    private JSONObject getGeocodeData(String url)
    {
        JSONObject jObj = null;
        String response = "Failed to connect to Geocoding API";
        try {
            Log.d(TAG,"get geocodeUrl "+url);
            response = new AsyncTaskParseJson().execute(url).get();
            jObj = new JSONObject(response);
            return jObj;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jObj;
    }


    /* Brief:
     * Param: url
     */
    private JSONArray getMichData(String url)
    {
        JSONArray jArry = null;
        String response = "Failed to connect to Michigan Data API";
        try {
            response = new AsyncTaskParseJson().execute(url).get();
            jArry = new JSONArray(response);
            return jArry;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            jArry = new JSONArray("{\"status\":\"fail\"}");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jArry;
    }


    /* Brief:
     * Param: url
     */
    private String getCity(String url)
    {
        String cityName = "default";
        JSONObject test = getGeocodeData(url);
        try
        {
            JSONArray results = test.getJSONArray("results");
            cityName = results.getJSONObject(0).getJSONArray("address_components").getJSONObject(2).getString("long_name");
            Log.d(TAG, "city retrieved from geoCode: "+cityName);
            getCityTax(cityName);

        }
        catch (JSONException e)
        {
            Log.e(TAG, "JSONArray error "+e);
            e.printStackTrace();
        }
        return cityName;
    }

   /* Brief:
    * Param: city
    */
    private void getCityTax(String city) throws JSONException {
        String residentTax = "default";
        String nonResTax = "default";
        String queryUrl = "http://data.michigan.gov/resource/kvss-tqw8.json?";
        JSONArray temp = getMichData(queryUrl);
        for(int i=0;i<temp.length();i++)
        {
            String humanAddress = temp.getJSONObject(i).getJSONObject("location_1").getString("human_address");
            String taxCity = new JSONObject(humanAddress).getString("city");
            Log.d(TAG,"city: "+taxCity);
            if(taxCity.equals(city))
            {
                residentTax = temp.getJSONObject(i).getString("taxrate");
                nonResTax = temp.getJSONObject(i).getString("nonresidenttax");
                displayDialog(this.builder,taxCity +" : "+residentTax+" : "+nonResTax);
            }
        }
    }


  /* Brief:
   * Param: cityCoordinatesArray
   */
    private void displayPointsOnMap(JSONArray cityCoordinatesArray)
    {
        // loop through all cities
        for (int i = 0; i < cityCoordinatesArray.length(); i++) {

            // Each element in the array is a JSONObject
            JSONObject c = null;
            try {
                c = cityCoordinatesArray.getJSONObject(i);

                JSONObject location = c.getJSONObject("location_1");

                String latitude = location.getString("latitude");
                String longitude = location.getString("longitude");

                Double lat = Double.valueOf(latitude);
                Double lng = Double.valueOf(longitude);
                mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(lat, lng))
                        .title("title"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }


   /* Brief:
    * Param: cityCoordinatesArray
    */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                // The Map is verified. It is now safe to manipulate the map.

            }
        }
    }
}
