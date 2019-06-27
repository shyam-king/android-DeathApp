package com.github.shyamking.deathapp.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.github.shyamking.deathapp.R;

public class CrimesFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_crimes, container, false);

        return root;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        Log.d("SHYAMDEBUG", "CRIMES OPTIONS");
        super.onPrepareOptionsMenu(menu);
    }
}
