package com.ITTDAM.bookncut.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import org.jetbrains.annotations.NotNull;

public class PagerController extends FragmentPagerAdapter {
    int numTabs;

    public PagerController(@NonNull @NotNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        numTabs=behavior;
    }

    @NonNull
    @NotNull
    @Override
    public Fragment getItem(int position) {

        return null;
    }

    @Override
    public int getCount() {
        return numTabs;
    }
}
