package com.ITTDAM.bookncut;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ITTDAM.bookncut.Adapters.PagerController;
import com.ITTDAM.bookncut.database.Database;
import com.ITTDAM.bookncut.pedidos.FacturaPedidosActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.util.HashMap;
import java.util.Map;

public class MainClienteActivity2 extends AppCompatActivity {
    //declaracion de variables
     private TabLayout tabclientes;
     private ViewPager viewcliente;
     private TabItem tabCita,tabProducto;
     private PagerController pagerController;
     private String usuarioEmail,usuarioNombres, peluqueria;
     private Database db;
     private Button cita;
     private TextView facturas;



    //oncreateview se ejecuta cuando se crea el elemento
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_cliente2);
        //obtiene los extras enviados por el intent del login
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            usuarioEmail = extras.getString("email");
            usuarioNombres = extras.getString("nombre");
            peluqueria=extras.getString("peluqueria");
        }
        //declara los valores del TAB a utilizar
        facturas = findViewById(R.id.factura);
        db=new Database(this);
        tabclientes=findViewById(R.id.tabViewMainCliente);
        viewcliente=findViewById(R.id.vpCliente);
        tabCita=findViewById(R.id.tabCita);
        tabProducto=findViewById(R.id.tabProductos);
        cita=findViewById(R.id.btnCitas);
        //redirect facturas
        facturas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(MainClienteActivity2.this, FacturaPedidosActivity.class);
                in.putExtra("email",usuarioEmail);
                in.putExtra("nombre",usuarioNombres);
                startActivity(in);
            }
        });


        //declara el pagercontroler para controlar el tablayout
        pagerController = new PagerController(getSupportFragmentManager(),tabclientes.getTabCount());
        viewcliente.setAdapter(pagerController);//asgina el adapter
        tabclientes.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //el evento de seleccionar items
                //asigna la posicion con tab.getposition
             viewcliente.setCurrentItem(tab.getPosition());
             //si la posicion es alguna de las que estan guardadas envia una datasetChanged
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

}