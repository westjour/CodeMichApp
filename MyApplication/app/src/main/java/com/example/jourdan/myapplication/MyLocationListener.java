package com.example.jourdan.myapplication;

import android.app.Activity;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;


/*---------- Listener class to get coordinates ------------- */
public class MyLocationListener implements LocationListener {
    final String TAG = "MyLocationListener.java";

        // current latitude
        public double mLat;

        // current longitude
        public double mLng;

        // The activity in which this listener was created
        Activity mActivity;

        /* Explicit Constructor */
        MyLocationListener(Activity activity){
            mActivity = activity;
        }

        @Override
        public void onLocationChanged(Location loc) {
            mLat = loc.getLatitude();
            mLng = loc.getLongitude();
            Log.d(TAG, "New Coords: "+mLat+"-"+mLng);

            TextView myLatText = (TextView)mActivity.findViewById(R.id.lat);
            TextView myLngText = (TextView)mActivity.findViewById(R.id.lng);
            myLatText.setText(String.valueOf(mLat));
            myLngText.setText(String.valueOf(mLng));
        }

        @Override
        public void onProviderDisabled(String provider) {}

        @Override
        public void onProviderEnabled(String provider) {}

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}

        public String getLat()
        {
            return Double.toString(mLat);
        }
}
