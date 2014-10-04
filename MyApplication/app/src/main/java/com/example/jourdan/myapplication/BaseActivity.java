package com.example.jourdan.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public abstract class BaseActivity extends Activity{
    AppData APP_DATA = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.base, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.
        int id = item.getItemId();

        Intent intent;
        switch (id) {
            // Go to Home Activity
            case R.id.action_Home:
                intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
                break;
            // Go to Map Activity
            case R.id.action_Map:
                intent = new Intent(this, MapActivity.class);
                startActivity(intent);
                break;
            // Go to Stats Activity
            case R.id.action_Stats:
                intent = new Intent(this, StatsActivity.class);
                startActivity(intent);
                break;
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
