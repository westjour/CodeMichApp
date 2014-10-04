package com.example.jourdan.myapplication;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
<<<<<<< HEAD
import org.json.JSONException;
import org.json.JSONObject;
=======
>>>>>>> master

// you can make this class as another java file so it will be separated from your main activity.
public class AsyncTaskParseJson extends AsyncTask<String, String, String> {

    final String TAG = "AsyncTaskParseJson.java";

    // set your json string url here
    //String yourJsonStringUrl = "http://demo.codeofaninja.com/tutorials/json-example-with-php/index.php";
    String yourJsonStringUrl = "http://data.michigan.gov/resource/kvss-tqw8.json?";
    // contacts JSONArray
    JSONArray dataJsonArr = null;

    @Override
    protected void onPreExecute() {}

    @Override
<<<<<<< HEAD
    protected String doInBackground(String... arg0) {

        try {

            // instantiate our json parser
            JsonParser jParser = new JsonParser();
            // get json string from url
            dataJsonArr = jParser.getJSONFromUrl(yourJsonStringUrl);

            // loop through all cities
            for (int i = 0; i < dataJsonArr.length(); i++) {

                // Each element in the array is a JSONObject
                JSONObject c = dataJsonArr.getJSONObject(i);

                JSONObject location = c.getJSONObject("location_1");

                String latitude = location.getString("latitude");
                String longitude = location.getString("longitude");
                // Storing each json item in variable
                String firstname = c.getString("nonresidenttax");
                String lastname = c.getString("taxrate");

                // show the values in our logcat
                Log.e(TAG, "nonresidenttax: " + firstname
                        + ", taxrate: " + lastname
                        + ", lng: " + longitude
                        + ", lat: " + latitude);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String strFromDoInBg) {}
=======
    protected String doInBackground(String... url) {
        String response = "";
        Log.e(TAG, "base url" + url);
        String queryUrl = url[0];
        Log.e(TAG, "QUERY URL" + queryUrl);
        // instantiate our json parser
        JsonParser jParser = new JsonParser();
        // get json string from url
        response = jParser.getJSONFromUrl(queryUrl);

        return response;
    }

    @Override
    protected void onPostExecute(String strFromDoInBg) {
        String returnString = strFromDoInBg;
    }
>>>>>>> master
}


