package com.github.shyamking.deathapp.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import com.github.shyamking.deathapp.R;

public class ForcesFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_forces, container, false);
        TextView t = v.findViewById(R.id.textView);
        t.setText("Forces");
        setHasOptionsMenu(true);



        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_forces, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.forces_search).getActionView();
        searchView.setQueryHint("Search Forces");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("SHYAMDEBUG", query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("SHYAMDEBUG", "ch: " + newText);
                return true;
            }
        });
    }
}
