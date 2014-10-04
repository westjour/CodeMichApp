package com.example.jourdan.myapplication;

import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONArray;

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
    protected String doInBackground(String... url) {
        String response = "";
        Log.e(TAG, "base url" + url);
        String queryUrl = url[0];
        Log.e(TAG, "QUERY UURRLLLLLL" + queryUrl);
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
}


