package com.ITTDAM.bookncut.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.ITTDAM.bookncut.CitasUsuarioFragment;
import com.ITTDAM.bookncut.encargos.EncargosUsuarioFragment;
import com.ITTDAM.bookncut.encargos.ProductosUsuarioFragment;

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
        switch (position){
            case 0:
                return new CitasUsuarioFragment();

            case 1:
                return new ProductosUsuarioFragment();
            case 2:
                return new EncargosUsuarioFragment();
            default:
                return null;
        }


    }

    @Override
    public int getCount() {
        return numTabs;
    }
}
