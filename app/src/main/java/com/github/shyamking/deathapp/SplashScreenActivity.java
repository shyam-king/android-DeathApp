package com.github.shyamking.deathapp;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class SplashScreenActivity extends AppCompatActivity {
    boolean animated = false;
    Thread redirect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        redirect = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000 * 3);
                }
                catch (InterruptedException e) {
                    Log.e("SHYAMDEBUG", e.toString());
                }
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                animated = true;
                startActivity(intent);
            }
        });
        redirect.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (animated)
            finish();
    }
}
