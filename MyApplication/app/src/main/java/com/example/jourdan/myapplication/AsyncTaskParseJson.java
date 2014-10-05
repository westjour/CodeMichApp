package com.example.jourdan.myapplication;

import android.os.AsyncTask;
import android.util.Log;

// you can make this class as another java file so it will be separated from your main activity.
public class AsyncTaskParseJson extends AsyncTask<String, String, String> {
    final String TAG = "AsyncTaskParseJson.java";
    @Override
    protected void onPreExecute() {}

    @Override
    protected String doInBackground(String... url) {
        String response = "";
        String queryUrl = url[0];
        Log.d(TAG, "QUERY URL: " + queryUrl);
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


