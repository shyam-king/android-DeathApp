package com.github.shyamking.deathapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class ForceDescriptionActivity extends AppCompatActivity {

    TextView forceName;
    TextView forceDescription;
    TextView forceURL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_force_description);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String url = "https://data.police.uk/api/forces/" + id;
        forceName = findViewById(R.id.force_name);
        forceDescription = findViewById(R.id.force_description);
        forceURL = findViewById(R.id.force_url);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("SHYAMDEBUG", response.toString());
                try {
                    forceName.setText(response.getString("name"));
                    String d = response.getString("description");
                    d = d.replace("<p>", "");
                    d = d.replace("</p>", "");
                    d = d.replace("<br />", "\n");
                    d = d.replace("&amp;", "&");
                    if (d == "null") {
                        d  = "No description available.";
                    }
                    forceDescription.setText(d);
                    forceURL.setText(response.getString("url"));
                    forceURL.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            TextView t = (TextView) v;
                            Intent showPage = new Intent();
                            showPage.setAction(Intent.ACTION_VIEW);
                            showPage.setData(Uri.parse(t.getText().toString()));

                            if (showPage.resolveActivity(getPackageManager()) != null) {
                                startActivity(showPage);
                            }
                        }
                    });
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error fetching data", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }
}
