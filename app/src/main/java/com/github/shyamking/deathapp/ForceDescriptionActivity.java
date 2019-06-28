package com.github.shyamking.deathapp;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ForceDescriptionActivity extends AppCompatActivity {

    TextView forceName;
    TextView forceDescription;
    TextView forceURL;
    LinearLayout engagementMethods;
    View forceTelephone;


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
        engagementMethods = findViewById(R.id.force_engagement_list);
        forceTelephone = findViewById(R.id.force_telephone);

        EngagementIconProvider.add("facebook", getResources().getDrawable(R.mipmap.fb));
        EngagementIconProvider.add("Web", getResources().getDrawable(R.mipmap.web));
        EngagementIconProvider.add("twitter", getResources().getDrawable(R.mipmap.twitter));
        EngagementIconProvider.add("youtube", getResources().getDrawable(R.mipmap.youtube));
        EngagementIconProvider.add("telephone", getResources().getDrawable(R.mipmap.telephone));

        final View.OnClickListener linkListener = new View.OnClickListener() {
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
        };

        final View.OnClickListener callListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView t =  v.findViewById(R.id.force_engagement_link);
                Intent dial = new Intent();
                dial.setAction(Intent.ACTION_DIAL);
                dial.setData(Uri.parse("tel:" + t.getText()));

                if (dial.resolveActivity(getPackageManager()) != null) {
                    startActivity(dial);
                }
            }
        };

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
                    forceURL.setOnClickListener(linkListener);
                    ImageView telephoneIcon = forceTelephone.findViewById(R.id.force_engagement_icon);
                    telephoneIcon.setImageDrawable(EngagementIconProvider.getIcon("telephone"));
                    TextView telephoneText = forceTelephone.findViewById(R.id.force_engagement_link);
                    telephoneText.setText(response.getString("telephone"));
                    forceTelephone.setOnClickListener(callListener);

                    JSONArray engagements = response.getJSONArray("engagement_methods");
                    for (int i = 0; i < engagements.length(); i++) {
                        JSONObject l = engagements.getJSONObject(i);
                        LayoutInflater lf = LayoutInflater.from(getApplicationContext());
                        View list_item = lf.inflate(R.layout.card_force_engagement_list, engagementMethods, false);
                        TextView list_item_link = list_item.findViewById(R.id.force_engagement_link);
                        list_item_link.setText(l.getString("url"));
                        list_item_link.setOnClickListener(linkListener);

                        Drawable drawable = EngagementIconProvider.getIcon(l.getString("title").toLowerCase());
                        if (drawable == null) {
                            drawable = EngagementIconProvider.getIcon("Web");
                        }
                        ImageView list_item_icon = list_item.findViewById(R.id.force_engagement_icon);
                        list_item_icon.setImageDrawable(drawable);

                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        engagementMethods.addView(list_item, lp);
                    }

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

class EngagementIconProvider {
    static ArrayList<String> titles = new ArrayList<>();
    static ArrayList<Drawable> icons = new ArrayList<>();

    public static Drawable getIcon(String title) {
        if (titles.contains(title)) {
            return icons.get(titles.indexOf(title));
        }
        return null;
    }

    public static void add(String title, Drawable icon) {
        if (!titles.contains(title)) {
            titles.add(title);
            icons.add(icon);
        }
    }
}
