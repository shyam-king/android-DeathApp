package com.github.shyamking.deathapp;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;

import com.github.shyamking.deathapp.ui.MainScreenAdapter;

import java.io.IOException;

import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.GET;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = findViewById(R.id.viewPager);
        MainScreenAdapter mainScreenAdapter = new MainScreenAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mainScreenAdapter);
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://data.police.uk/api/").build();
        UKForces service = retrofit.create(UKForces.class);

        try {
            ResponseBody responseBody = service.getForces().execute().body();
            Log.d("SHYAMDEBUG", responseBody.toString());
        }
        catch (IOException e) {
            Log.d("SHYAMDEBUG",e.toString());
        }
    }
}

interface UKForces {
    @GET("forces")
    Call<ResponseBody> getForces();
}
