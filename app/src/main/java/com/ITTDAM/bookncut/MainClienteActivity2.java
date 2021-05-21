package com.ITTDAM.bookncut;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewPropertyAnimator;

import com.ITTDAM.bookncut.Adapters.PagerController;
import com.ITTDAM.bookncut.database.Database;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class MainClienteActivity2 extends AppCompatActivity {
    TabLayout tabclientes;
    ViewPager viewcliente;
    TabItem tabCita,tabProducto,tabEncargos;
    PagerController pagerController;
    String usuarioEmail,usuarioNombres;
    Database db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_cliente2);
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            usuarioEmail = extras.getString("email");
            usuarioNombres = extras.getString("nombre");
            // and get whatever type user account id is
        }
        db=new Database(this);
        tabclientes=findViewById(R.id.tabViewMainCliente);
        viewcliente=findViewById(R.id.vpCliente);
        tabCita=findViewById(R.id.tabCita);
        tabProducto=findViewById(R.id.tabProductos);
        tabEncargos=findViewById(R.id.tabEncargo);

        pagerController = new PagerController(getSupportFragmentManager(),tabclientes.getTabCount());
        viewcliente.setAdapter(pagerController);
        tabclientes.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
             viewcliente.setCurrentItem(tab.getPosition());
             if(tab.getPosition()==0||tab.getPosition()==1||tab.getPosition()==2){
                 pagerController.notifyDataSetChanged();
             }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewcliente.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabclientes));
    }
    public void newCitaRedirect(View view){
        Intent in = new Intent(this,NewCitaUsuarioActivity.class);
        in.putExtra("email",this.usuarioEmail);
        in.putExtra("nombre",this.usuarioNombres);
        startActivity(in);
    }
}