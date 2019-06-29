package com.github.shyamking.deathapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.shyamking.deathapp.ui.Crime;

public class DisplayCrimeActivity extends AppCompatActivity {
    Crime crime;
    LinearLayout list;
    Toolbar toolbar;
    SQLiteDatabase db;

    boolean recordExists = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_crime);
        Intent intent = getIntent();
        crime = (Crime)intent.getSerializableExtra("crime");
        list = findViewById(R.id.crime_item_list);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LayoutInflater lf = LayoutInflater.from(this);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SQLiteHelper sql = new SQLiteHelper(getApplicationContext());
        db = sql.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT " + SQLContract.CrimeFavourites.Id + " FROM " + SQLContract.CrimeFavourites.TableName +
            " WHERE " + SQLContract.CrimeFavourites.Id + " = \"" +crime.getId()+"\"", null);
        if (c.getCount() != 0) {
            recordExists = true;
        }

        if (!crime.getStreet().equals("null") && !crime.getStreet().isEmpty()) {
            View v = lf.inflate(R.layout.card_crime_item, list, false);
            TextView title = v.findViewById(R.id.crime_item_title);
            TextView content = v.findViewById(R.id.crime_item_content);
            
            title.setText("Street");
            content.setText(crime.getStreet());
            
            list.addView(v, lp);
        }

        if (!crime.getCategory().equals("null") && !crime.getCategory().isEmpty()) {
            View v = lf.inflate(R.layout.card_crime_item, list, false);
            TextView title = v.findViewById(R.id.crime_item_title);
            TextView content = v.findViewById(R.id.crime_item_content);

            title.setText("Category");
            content.setText(crime.getCategory());

            list.addView(v, lp);
        }

        if (!crime.getContext().equals("null") && !crime.getContext().isEmpty()) {
            View v = lf.inflate(R.layout.card_crime_item, list, false);
            TextView title = v.findViewById(R.id.crime_item_title);
            TextView content = v.findViewById(R.id.crime_item_content);

            title.setText("Context");
            content.setText(crime.getContext());

            list.addView(v, lp);
        }

        if (!crime.getMonth().equals("null") && !crime.getMonth().isEmpty()) {
            View v = lf.inflate(R.layout.card_crime_item, list, false);
            TextView title = v.findViewById(R.id.crime_item_title);
            TextView content = v.findViewById(R.id.crime_item_content);

            title.setText("Month");
            content.setText(crime.getMonth());

            list.addView(v, lp);
        }

        if (!crime.getLocation_type().equals("null") && !crime.getLocation_type().isEmpty()) {
            View v = lf.inflate(R.layout.card_crime_item, list, false);
            TextView title = v.findViewById(R.id.crime_item_title);
            TextView content = v.findViewById(R.id.crime_item_content);

            title.setText("Location Type");
            content.setText(crime.getLocation_type());

            list.addView(v, lp);
        }

        if (!crime.getLocation_subtype().equals("null") && !crime.getLocation_subtype().isEmpty()) {
            View v = lf.inflate(R.layout.card_crime_item, list, false);
            TextView title = v.findViewById(R.id.crime_item_title);
            TextView content = v.findViewById(R.id.crime_item_content);

            title.setText("Location Subtype");
            content.setText(crime.getLocation_subtype());

            list.addView(v, lp);
        }

        if (!crime.getOutcome_status().equals("null") && !crime.getOutcome_status().isEmpty()) {
            View v = lf.inflate(R.layout.card_crime_item, list, false);
            TextView title = v.findViewById(R.id.crime_item_title);
            TextView content = v.findViewById(R.id.crime_item_content);

            title.setText("Outcome Status");
            content.setText(crime.getOutcome_status());

            list.addView(v, lp);
        }

        if (!crime.getPersistent_id().equals("null") && !crime.getPersistent_id().isEmpty()) {
            View v = lf.inflate(R.layout.card_crime_item, list, false);
            TextView title = v.findViewById(R.id.crime_item_title);
            TextView content = v.findViewById(R.id.crime_item_content);

            title.setText("Persistent id");
            content.setText(crime.getPersistent_id());

            list.addView(v, lp);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.display_crime_menu, menu);
        invalidateOptionsMenu();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (recordExists)
            menu.getItem(1).setIcon(R.mipmap.fav_del);
        else
            menu.getItem(1).setIcon(R.mipmap.fav_add);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent displayOnMap;
        switch (item.getItemId()) {
            case R.id.crime_addFavouriteButton:
                if (!recordExists) {
                    ContentValues values = new ContentValues();

                    values.put(SQLContract.CrimeFavourites.Persistent_key, crime.getPersistent_id());
                    values.put(SQLContract.CrimeFavourites.Context, crime.getContext());
                    values.put(SQLContract.CrimeFavourites.Category, crime.getCategory());
                    values.put(SQLContract.CrimeFavourites.Street, crime.getStreet());
                    values.put(SQLContract.CrimeFavourites.Outcome_status, crime.getOutcome_status());
                    values.put(SQLContract.CrimeFavourites.Location_subtype, crime.getLocation_subtype());
                    values.put(SQLContract.CrimeFavourites.Location_type, crime.getLocation_type());
                    values.put(SQLContract.CrimeFavourites.Month, crime.getMonth());
                    values.put(SQLContract.CrimeFavourites.Id, crime.getId());
                    values.put(SQLContract.CrimeFavourites.Latitude, crime.getLatitude());
                    values.put(SQLContract.CrimeFavourites.Longitude, crime.getLongitude());

                    long index = db.insert(SQLContract.CrimeFavourites.TableName, null, values);
                    Toast.makeText(getApplicationContext(), "Added to favourites!", Toast.LENGTH_SHORT).show();
                    item.setIcon(R.mipmap.fav_del);
                    recordExists = true;
                }
                else {
                    db.delete(SQLContract.CrimeFavourites.TableName, SQLContract.CrimeFavourites.Id + " = " + crime.getId(), null);
                    Toast.makeText(getApplicationContext(), "Deleted from favourites!", Toast.LENGTH_SHORT).show();
                    recordExists = false;
                    item.setIcon(R.mipmap.fav_add);
                }
                return true;

            case R.id.crime_displayMap:
                displayOnMap = new Intent();
                displayOnMap.setAction(Intent.ACTION_VIEW);
                displayOnMap.setData(Uri.parse("geo:" + crime.getLatitude() + ", " + crime.getLongitude()));
                if (displayOnMap.resolveActivity(getPackageManager()) != null) {
                    startActivity(displayOnMap);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
