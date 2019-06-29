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
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.github.shyamking.deathapp.ForceDescriptionActivity;
import com.github.shyamking.deathapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ForcesFragment extends Fragment {
    ArrayList<Force> data = new ArrayList<>();
    ArrayList<Force> displaySubset = new ArrayList<>();
    RecyclerView forces_list;
    ForcesListAdapter forcesListAdapter;
    TextView forcesError;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_forces, container, false);
        forces_list = v.findViewById(R.id.forces_list);
        forcesListAdapter = new ForcesListAdapter(displaySubset, getContext());
        forces_list.setLayoutManager(new LinearLayoutManager(getContext()));
        forces_list.setAdapter(forcesListAdapter);
        forcesError = v.findViewById(R.id.forces_error);
        setHasOptionsMenu(true);

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, "https://data.police.uk/api/forces", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                forcesError.setVisibility(View.GONE);
                //parsing and storing the response
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject obj = response.getJSONObject(i);
                        Force f = new Force(obj.getString("id"), obj.getString("name"));
                        data.add(f);
                    }
                    displaySubset.addAll(data);
                    forcesListAdapter.notifyDataSetChanged();
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("SHYAMDEBUG", error.toString());
            }
        });

        requestQueue.add(jsonArrayRequest);

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_forces, menu);
        final SearchView searchView = (SearchView) menu.findItem(R.id.forces_search).getActionView();
        searchView.setQueryHint("Search Forces");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                displaySubset.clear();
                for (Force f : data) {
                    if (f.getName().toLowerCase().contains(newText.toLowerCase())) {
                        displaySubset.add(f);
                    }
                }
                if (displaySubset.size() == 0) {
                    forcesError.setVisibility(View.VISIBLE);
                    forcesError.setText(R.string.force_noMmatch);
                }
                else {
                    forcesError.setVisibility(View.GONE);
                }
                forcesListAdapter.notifyDataSetChanged();
                return true;
            }
        });
    }
}

class Force {
    private String id;
    private String name;

    public Force(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}

class ForcesViewHolder extends RecyclerView.ViewHolder {
    TextView name;
    String id;

    public ForcesViewHolder(@NonNull final View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.list_item);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(itemView.getContext(), ForceDescriptionActivity.class);
                intent.putExtra("id", id);
                itemView.getContext().startActivity(intent);
            }
        });
    }

    public void update(Force data) {
        name.setText(data.getName());
        id = data.getId();
    }
}

class ForcesListAdapter extends RecyclerView.Adapter<ForcesViewHolder> {

    ArrayList<Force> forces = new ArrayList<>();
    Context context = null;

    @NonNull
    @Override
    public ForcesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View v = layoutInflater.inflate(R.layout.card_list, viewGroup, false);
        ForcesViewHolder forcesViewHolder = new ForcesViewHolder(v);
        return forcesViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ForcesViewHolder forcesViewHolder, int i) {
        forcesViewHolder.update(forces.get(i));
    }

    @Override
    public int getItemCount() {
        return forces.size();
    }

    public ForcesListAdapter(ArrayList<Force> force, Context context) {
        this.forces = force;
        this.context = context;
    }

}