package com.example.jourdan.myapplication;

import android.os.Bundle;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.widget.TextView;
import android.app.Activity;
import android.location.LocationListener;


/*---------- Listener class to get coordinates ------------- */
public class MyLocationListener implements LocationListener {

        // current latitude
        double mLat;

        // current longitude
        double mLng;

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
}
