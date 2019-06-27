package com.github.shyamking.deathapp.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


public class MainScreenAdapter extends FragmentPagerAdapter {
    @Override
    public Fragment getItem(int i) {
        Fragment f;
        if (i == 0) {
            f = new ForcesFragment();
        }
        else {
            f = new CrimesFragment();
        }
        return f;
    }

    @Override
    public int getCount() {
        return 2;
    }

    public MainScreenAdapter(FragmentManager fm) {
        super(fm);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title[] = {"Forces", "Crimes"};
        return title[position];
    }
}
