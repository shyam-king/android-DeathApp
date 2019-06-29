package com.github.shyamking.deathapp.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.github.shyamking.deathapp.DisplayCrimeActivity;
import com.github.shyamking.deathapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class CrimesFragment extends Fragment {
    TextView monthOfCrime;
    TextView yearOfCrime;
    TextView latitude;
    TextView longitude;
    Button searchButton;
    RecyclerView searchResults;
    ArrayList<Crime> data;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_crimes, container, false);
        monthOfCrime = root.findViewById(R.id.crimeMonth);
        yearOfCrime = root.findViewById(R.id.crimeYear);
        latitude = root.findViewById(R.id.crimeLatitude);
        longitude = root.findViewById(R.id.crimeLongitude);

        searchButton = root.findViewById(R.id.crimeSearchButton);
        searchResults = root.findViewById(R.id.crimeSearchResults);

        data = new ArrayList<>();

        final CrimeListAdapter adapter = new CrimeListAdapter(getContext(), data);
        searchResults.setLayoutManager(new LinearLayoutManager(getContext()));
        searchResults.setAdapter(adapter);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                //TODO verify input

                String url = "https://data.police.uk/api/crimes-at-location?";
                url += "date=" + yearOfCrime.getText().toString() + "-" + monthOfCrime.getText().toString();
                url += "&lat=" + latitude.getText().toString();
                url += "&lng=" + longitude.getText().toString();

                Log.d("SHYAMDEBUG", "url: " + url);
                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("SHYAMDEBUG", response.toString());
                        data.clear();
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                Crime dataElement = new Crime();
                                JSONObject iCrime = response.getJSONObject(i);

                                dataElement.setCategory(iCrime.getString("category"));
                                dataElement.setContext(iCrime.getString("context"));
                                dataElement.setLocationType(iCrime.getString("location_type"));
                                dataElement.setLocationSubtype(iCrime.getString("location_subtype"));
                                dataElement.setMonth(iCrime.getString("month"));
                                JSONObject outcome_status = iCrime.getJSONObject("outcome_status");
                                dataElement.setOutcomeStatus(outcome_status.getString("category") + " (" + outcome_status.getString("date") + ")");
                                dataElement.setStreet(iCrime.getJSONObject("location").getJSONObject("street").getString("name"));
                                dataElement.setPersistentId(iCrime.getString("persistent_id"));

                                data.add(dataElement);
                            }
                        }
                        catch (JSONException e) {
                            Log.d("SHYAMDEBUG", e.toString());
                        }
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("SHYAMDEBUG", "Error receiving stuff");
                        Log.d("SHYAMDEBUG", error.toString());
                    }
                });

                requestQueue.add(jsonArrayRequest);
            }
        });

        return root;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        Log.d("SHYAMDEBUG", "CRIMES OPTIONS");
        super.onPrepareOptionsMenu(menu);
    }
}



class CrimeViewHolder extends RecyclerView.ViewHolder {
    TextView crimeStreet;
    Crime crime;
    public CrimeViewHolder(@NonNull View itemView) {
        super(itemView);


        crimeStreet = itemView.findViewById(R.id.list_item);
        crimeStreet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent displayCrime = new Intent(crimeStreet.getContext(), DisplayCrimeActivity.class);
                displayCrime.putExtra("crime", crime);
                crimeStreet.getContext().startActivity(displayCrime);
            }
        });
    }

    public void update( Crime crime) {
        this.crime = crime;
        crimeStreet.setText(crime.getStreet());
    }
}

class CrimeListAdapter extends RecyclerView.Adapter<CrimeViewHolder> {

    Context context;
    ArrayList<Crime> crimes = new ArrayList();

    public CrimeListAdapter(Context context, ArrayList<Crime> crimes) {
        this.context = context;
        this.crimes = crimes;
    }

    @NonNull
    @Override
    public CrimeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater lf = LayoutInflater.from(context);
        CrimeViewHolder vh = new CrimeViewHolder(lf.inflate(R.layout.card_list, viewGroup, false));
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull CrimeViewHolder crimeViewHolder, int i) {
        crimeViewHolder.update(crimes.get(i));
    }

    @Override
    public int getItemCount() {
        Log.d("SHYAMDEBUG", "item count: " + crimes.size());
        return crimes.size();
    }
}