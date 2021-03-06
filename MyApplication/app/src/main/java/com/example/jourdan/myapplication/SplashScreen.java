package com.example.jourdan.myapplication; /**
 * Created by Jourdan on 10/5/2014.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.content.res.Configuration;
import android.widget.Toast;

public class SplashScreen extends Activity {

    // Splash screen timer (milliseconds)
    private static int SPLASH_TIME_OUT = 7000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {

            //
             // Showing splash screen with a timer. This will be useful when you
             //want to show case your app logo / company
            //

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent intent = new Intent(SplashScreen.this, HomeActivity.class);
                startActivity(intent);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT );
    }
}
