package com.example.jourdan.myapplication;

import android.content.Intent;
import android.nfc.Tag;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.os.AsyncTask;

import java.util.concurrent.ExecutionException;

/**
 * Created by Jourdan on 10/4/2014.
 */
public class Utility {
    final String TAG = "Utility.java";

    /**
     *
     * @param url
     * @return
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


    /**
     *
     * @param url
     * @return
     */
    private JSONObject getGeocodeData(String url)
    {
        JSONObject jObj = null;
        String response = "Failed to connect to Geocoding API";
        try {
            //Log.d(TAG,"get geocodeUrl "+url);
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


    /**
     *
     * @param lat
     * @param lng
     * @return
     */
    public String getCityFromCoord(String lat, String lng)
    {
        String queryString = "http://maps.googleapis.com/maps/api/geocode/json?latlng="+lat+","+lng+"&sensor=false";
        String cityName = getCity(queryString);
        return cityName;
    }


    /**
     *
     * @param url
     * @return
     */
    private String getCity(String url) {
        String cityName = "default";
        JSONObject test = getGeocodeData(url);
        try {
            JSONArray results = test.getJSONArray("results");
            if( !test.getString("status").equals("ZERO_RESULTS"))
            {
                cityName = results.getJSONObject(0).getJSONArray("address_components")
                        .getJSONObject(2).getString("long_name");

            }
            Log.d(TAG,"City from GeoCode: "+cityName);
        } catch (JSONException e) {
            Log.e(TAG, "JSONArray error " + e);
            e.printStackTrace();
            return "not found";
        }
        return cityName;
    }


    /**
     *
     * @param city
     * @return
     * @throws JSONException
     */
    public JSONObject getCityTax(String city) throws JSONException {
        String residentTax = "0.0";
        String nonResTax = "0.0";
        String queryUrl = "http://data.michigan.gov/resource/kvss-tqw8.json?";
        JSONArray temp = getMichData(queryUrl);

        for(int i=0; i<temp.length(); i++){
            String humanAddress = temp.getJSONObject(i).getJSONObject("location_1").getString("human_address");
            String taxCity = new JSONObject(humanAddress).getString("city");

            if(taxCity.equals(city)){
                residentTax = temp.getJSONObject(i).getString("taxrate");
                nonResTax = temp.getJSONObject(i).getString("nonresidenttax");
                break;
            }
        }

        JSONObject taxRates = new JSONObject();
        taxRates.put("res", residentTax);
        taxRates.put("nonres", nonResTax);
        return taxRates;
    }


    /**
     *
     * @param cityName
     * @return
     */
    public String getAffordableAssistedHousing(String cityName)
    {
        String queryUrl = "http://data.michigan.gov/resource/nchs-ngr4.json?";
        Integer num = 0;
        JSONArray temp = getMichData(queryUrl);
        for(int i=0;i<temp.length();i++){
            String city = null;
            try {
                if( temp.getJSONObject(i).has("city")) {
                    city = temp.getJSONObject(i).getString("city");
                    Log.d(TAG, "Housing: " + city);
                }
                else
                {
                    city = "noValue";
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if(city.equals(cityName)){
                num++;
                //displayDialog(this.builder,taxCity +" : "+residentTax+" : "+nonResTax);
            }
        }
        Log.d(TAG,"num: "+num);
        return String.valueOf(num);
    }

    /**
     * Returns number of schools in provided city
     *
     * @param cityName
     *
     * @return String - number of schools
     */
    public String getCitySchools(String cityName){
        String queryUrl = "http://data.michigan.gov/resource/7rph-su5f.json";
        Integer numSchools = 0;
        JSONArray temp = getMichData(queryUrl);
        for(int i=0;i<temp.length();i++){
            String city = null;
            try {
                if( temp.getJSONObject(i).has("city")) {
                    city = temp.getJSONObject(i).getString("city");
                    Log.d(TAG, "school: " + city);
                }
                else
                {
                    city = "noValue";
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if(city.equals(cityName)){
                numSchools++;
            }
        }
        Log.d(TAG,"num: "+numSchools);
        return String.valueOf(numSchools);
    }


    /**
     *
     * @param county
     * @return
     */
    public JSONArray getMiStatePlaces(String county)
    {
        String queryUrl = "http://data.michigan.gov/resource/nchs-ngr4.json";
        JSONArray temp = getMichData(queryUrl);
        return temp;
    }
    public String getParks(String lat, String lng)
    {
        String city = getCityFromCoord(lat, lng);
        return "bla";
    }
    public String getHistoricPlaces(String cityName)
    {
        Integer numParks = 0;
        String queryUrl = "http://data.michigan.gov/resource/ekha-b43f.json";
        JSONArray parks = getMichData(queryUrl);
        for(int i=0;i<parks.length();i++){
            String city = null;
            try {
                if( parks.getJSONObject(i).has("city")) {
                    city = parks.getJSONObject(i).getString("city");
                    Log.d(TAG, "park: " + city);
                }
                else
                {
                    city = "noValue";
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if(city.equals(cityName)){
                numParks++;
            }
        }
        Log.d(TAG,"num: "+numParks);
        return String.valueOf(numParks);
    }
}
