package com.github.shyamking.deathapp.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;


public class MainScreenAdapter extends FragmentPagerAdapter {
    @Override
    public Fragment getItem(int i) {
        Fragment f;
        if (i == 0) {
            f = new ForcesFragment();
        }
        else if (i==1) {
            f = new CrimesFragment();
        }
        else {
            f = new FavouritesFragment();
        }
        return f;
    }

    @Override
    public int getCount() {
        return 3;
    }

    public MainScreenAdapter(FragmentManager fm) {
        super(fm);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title[] = {"Forces", "Crimes", "Favourites"};
        Log.d("SHYAMDEBUG", title[position]);
        return title[position];
    }
}
