package com.example.jourdan.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.concurrent.ExecutionException;

public class MapActivity extends BaseActivity implements GoogleMap.OnMarkerClickListener {
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
        Log.d(TAG,"appData on map init: "+appData.getSalaryString()+"-"+appData.getCities());
        builder = new AlertDialog.Builder(this);

        super.onCreate(savedInstanceState);

        statsIntent = new Intent(this, StatsActivity.class);

        setContentView(R.layout.activity_map);
        mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        mMap.setOnMarkerClickListener(this);
        JSONArray points = appData.getCities();
        for(int i=0; i<points.length(); i++ )
        {
            try {
                Double lat = Double.valueOf(points.getJSONObject(i).getString("lat"));
                Double lng = Double.valueOf(points.getJSONObject(i).getString("lng"));
                if(lat == 0.0 && lng == 0.0)
                {
                    continue;
                }
                else{
                    addMarker(mMap,lat,lng);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        // Set listener for user map clicks
        mMap.setOnMapClickListener(new OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
                JSONObject location = new JSONObject();
                String lat = Double.toString(point.latitude);
                String lng = Double.toString(point.longitude);
                Log.d(TAG, "User click" + lat + " : " + lng);
                if(appData.getCities().length() < 4) {
                    addMarker(mMap, point.latitude, point.longitude);
                    try {
                        location.put("lat", lat);
                        location.put("lng", lng);
                        appData.addCity(location);
                        updateAppData(appData);

                        Log.d(TAG, "appData on MapLeave: " + appData.getSalaryString() + " " + appData.getCities());
                        startActivity(statsIntent);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else
                {
                    displayDialog(builder, "Location limit reached. Please remove a location by tapping the marker before adding another location");
                }
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
    public void addMarker(GoogleMap mMap, Double lat, Double lng)
    {
        Marker marker;
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(lat, lng)));
    }
    @Override
    public boolean onMarkerClick(Marker marker) {
        LatLng point = marker.getPosition();
        Double lat = point.latitude;
        Double lng = point.longitude;
        JSONArray json = appData.getCities();
        JSONArray tempArray = new JSONArray();
        marker.remove();
        for(int i=0; i<json.length(); i++)
        {
            try {
                JSONObject tempObj = json.getJSONObject(i);
                Double tempLat = Double.valueOf(tempObj.getString("lat"));
                Double tempLng = Double.valueOf(tempObj.getString("lng"));
                Log.d(TAG, tempLat+":"+lat+" "+tempLng+":"+lng);
                int equalLat = Double.compare(lat,tempLat);
                int equalLng = Double.compare(lng,tempLng);
                if( equalLat == 0 && equalLng == 0) {
                    appData.removeLocation(i);
                    Log.d(TAG, "EQUAL");
                    continue;
                }
            } catch (JSONException e) {

            }
        }
        return true;
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
