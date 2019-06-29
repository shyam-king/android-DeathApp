package com.github.shyamking.deathapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
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
    Button addFavouriteButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_crime);
        Intent intent = getIntent();
        crime = (Crime)intent.getSerializableExtra("crime");
        list = findViewById(R.id.crime_item_list);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LayoutInflater lf = LayoutInflater.from(this);
        addFavouriteButton = findViewById(R.id.crime_addFavouriteButton);

        addFavouriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO sql add crime
            }
        });
        
        if (!crime.getStreet().equals("null") && !crime.getStreet().isEmpty()) {
            View v = lf.inflate(R.layout.card_crime_item, list, false);
            TextView title = v.findViewById(R.id.crime_item_title);
            TextView content = v.findViewById(R.id.crime_item_content);

            Toast.makeText(this, crime.getStreet(), Toast.LENGTH_SHORT).show();
            
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
}
