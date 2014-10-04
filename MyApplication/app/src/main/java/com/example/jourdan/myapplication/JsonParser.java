package com.example.jourdan.myapplication;

<<<<<<< HEAD
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
=======
import android.util.Log;
>>>>>>> master

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
<<<<<<< HEAD
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class JsonParser {

=======
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class JsonParser {
>>>>>>> master
    final String TAG = "JsonParser.java";

    static InputStream is = null;
    static JSONObject jObj = null;
    static JSONArray jAry = null;
    static String json = "";

<<<<<<< HEAD
    public JSONArray getJSONFromUrl(String url) {
=======
    public String getJSONFromUrl(String url) {
>>>>>>> master

        // make HTTP request
        try {

            DefaultHttpClient httpClient = new DefaultHttpClient();
<<<<<<< HEAD
=======

            Log.d(TAG, "HTTP URL:" + url);
>>>>>>> master
            HttpGet httpGet = new HttpGet(url);

            HttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {

            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 12);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            is.close();
            json = sb.toString();
            //json = json.replace("\\", "");
            json = json.replace(" ", "");
<<<<<<< HEAD
            Log.e(TAG, "JSON STRING: "+ json);
=======
            Log.d(TAG, "JSON STRING: " + json);
>>>>>>> master

        } catch (Exception e) {
            Log.e(TAG, "Error converting result " + e.toString());
        }
<<<<<<< HEAD

        // try parse the string to a JSON object
        try {
            //jObj = new JSONObject(json);
            jAry = new JSONArray(json);
        } catch (JSONException e) {
            Log.e(TAG, "Error parsing data " + e.toString());
        }

        // return JSON String
        return jAry;
=======
        return json;
>>>>>>> master
    }
}
