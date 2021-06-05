package com.ITTDAM.bookncut.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.ITTDAM.bookncut.CitasUsuarioFragment;
import com.ITTDAM.bookncut.pedidos.ProductosUsuarioFragment;

import org.jetbrains.annotations.NotNull;

public class PagerController extends FragmentPagerAdapter {
    private int numTabs;//declara la variable que guardara el numero de Tabs en el tablayout
    private Fragment citas,productos;//Fragmentos usados en el tablayout

    //Controlador para las tabs del tablayout
    public PagerController(@NonNull @NotNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        //obtiene el numero de tabs y lo asigna
        numTabs=behavior;
        //declara los fragments que utlizara
        citas =new CitasUsuarioFragment();
        productos=new ProductosUsuarioFragment();
    }

    //funcion que devuelve un item apartir de una seleccion
    @NonNull
    @NotNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0://si la posicion es 0 entonces devuelve el fragmetno citas
                return citas;
            case 1://si la posicion es 1 devuelve el fragmento productos
                return productos;
            default://default devuelve nulo
                return null;
        }


    }

    //devuelve el numero de tabs
    @Override
    public int getCount() {
        return numTabs;
    }
}
