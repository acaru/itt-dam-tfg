package com.ITTDAM.bookncut;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.ViewPropertyAnimator;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class MainClienteActivity2 extends AppCompatActivity {
    TabLayout tabclientes;
    ViewPager viewcliente;
    TabItem tabCita,tabProducto,tabEncargos;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_cliente2);
        tabclientes=findViewById(R.id.tabViewMainCliente);
        viewcliente=findViewById(R.id.vpCliente);
        tabCita=findViewById(R.id.tabCita);
        tabProducto=findViewById(R.id.tabProductos);
        tabEncargos=findViewById(R.id.tabEncargo);
    }
}