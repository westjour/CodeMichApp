package com.example.jourdan.myapplication;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class JsonParser {
    final String TAG = "JsonParser.java";

    static InputStream is = null;
    static String json = "";

    public String getJSONFromUrl(String url) {

        // make HTTP request
        try {

            DefaultHttpClient httpClient = new DefaultHttpClient();
            Log.d(TAG, "HTTP URL:" + url);
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
            json = json.replace(" ", "");
            Log.d(TAG, "JSON STRING: " + json);

        } catch (Exception e) {
            Log.e(TAG, "Error converting result " + e.toString());
        }
        return json;
    }
}
