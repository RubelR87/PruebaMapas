package com.prueba.rubel.pruebamapas;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.rahatarmanahmed.cpv.CircularProgressView;

public class Splash_screen extends Activity {
    CircularProgressView progressView2;

    private static int SPLASH_TIME_OUT = 5000;
    //private Manager_CRUD dbHelper;
    private SimpleCursorAdapter dataAdapter;
    boolean login =false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // progressView = (ProgressBar) findViewById(R.id.native_progress_bar);
        progressView2 = (CircularProgressView) findViewById(R.id.progress_view);
        progressView2.startAnimation();

       // dbHelper = new Manager_CRUD(this);
        //dbHelper.open();

        //login= dbHelper.fetchUsuarios_por_estado();


        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity


                Intent i = new Intent(Splash_screen.this, Home_Navigator.class);
                startActivity(i);
               /*
                if (login == true) {


                    Intent i = new Intent(Splash_screen.this, MainActivity.class);
                    startActivity(i);

                } else {


                    Intent i = new Intent(SplashScreen.this, Login_SQLite.class);
                    startActivity(i);
                }
                */

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);




    }



}
