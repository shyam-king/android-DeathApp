package com.github.shyamking.deathapp.ui;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

import com.github.shyamking.deathapp.R;
import com.github.shyamking.deathapp.SQLContract;
import com.github.shyamking.deathapp.SQLiteHelper;

import java.util.ArrayList;

public class FavouritesFragment extends Fragment {
    TextView errorMessage;
    RecyclerView favouritesList;
    ArrayList<Crime> data = new ArrayList<>();
    ArrayList<Crime> dataSubset = new ArrayList<>();
    CrimeListAdapter crimeListAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_favourites, container, false);
        setHasOptionsMenu(true);
        errorMessage = v.findViewById(R.id.favourites_error);
        favouritesList = v.findViewById(R.id.favourites_list);

        crimeListAdapter = new CrimeListAdapter(getContext(), dataSubset);
        favouritesList.setLayoutManager(new LinearLayoutManager(getContext()));
        favouritesList.setAdapter(crimeListAdapter);

        fetchData();

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchData();
    }

    private void fetchData() {
        SQLiteHelper sql = new SQLiteHelper(getContext());
        SQLiteDatabase db = sql.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT COUNT (*) FROM " + SQLContract.CrimeFavourites.TableName, null);
        c.moveToFirst();
        long count = c.getLong(0);

        data.clear();
        dataSubset.clear();

        if (count == 0) {
            errorMessage.setText("No favourites yet!");
            errorMessage.setVisibility(View.VISIBLE);
        }
        else {
            errorMessage.setVisibility(View.GONE);
            String[] projection = {
                    SQLContract.CrimeFavourites.Category,
                    SQLContract.CrimeFavourites.Outcome_status,
                    SQLContract.CrimeFavourites.Month,
                    SQLContract.CrimeFavourites.Location_type,
                    SQLContract.CrimeFavourites.Location_subtype,
                    SQLContract.CrimeFavourites.Persistent_key,
                    SQLContract.CrimeFavourites.Street,
                    SQLContract.CrimeFavourites.Context,
                    SQLContract.CrimeFavourites.Id,
                    SQLContract.CrimeFavourites.Latitude,
                    SQLContract.CrimeFavourites.Longitude
            };

            c = db.query(SQLContract.CrimeFavourites.TableName, projection, null, null, null, null, null);
            c.moveToFirst();

            for (int i = 0; i < c.getCount(); i++) {
                Crime crime = new Crime();

                crime.setCategory(c.getString(0));
                crime.setOutcomeStatus(c.getString(1));
                crime.setMonth(c.getString(2));
                crime.setLocationType(c.getString(3));
                crime.setLocationSubtype(c.getString(4));
                crime.setPersistentId(c.getString(5));
                crime.setStreet(c.getString(6));
                crime.setContext(c.getString(7));
                crime.setId(c.getString(8));
                crime.setLatitude(c.getDouble(9));
                crime.setLongitude(c.getDouble(10));

                data.add(crime);
                c.moveToNext();
            }
            dataSubset.addAll(data);
        }

        crimeListAdapter.notifyDataSetChanged();
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_forces, menu);
        final SearchView searchView = (SearchView) menu.findItem(R.id.forces_search).getActionView();
        searchView.setQueryHint("Search Favourites");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                dataSubset.clear();
                for (Crime f : data) {
                    if (f.getStreet().toLowerCase().contains(newText.toLowerCase())) {
                        dataSubset.add(f);
                    }
                }
                crimeListAdapter.notifyDataSetChanged();
                return true;
            }
        });
    }
}