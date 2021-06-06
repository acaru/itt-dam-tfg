package com.ITTDAM.bookncut.pedidos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ITTDAM.bookncut.Adapters.AdapterFacturasAdmin;
import com.ITTDAM.bookncut.R;
import com.ITTDAM.bookncut.models.Facturas;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class FacturaPedidosActivity extends AppCompatActivity implements AdapterFacturasAdmin.MyListener{
    //declaracion de variables
    public static final String TAG = "FACTURAS";
    private String usuarioEmail,usuarioNombres,peluqueria;
    private RecyclerView rvFacturas;
    private ImageButton volver;
    private AdapterFacturasAdmin adapterFacturasAdmin;
    private List<Facturas> facturas;
    private FirebaseFirestore dbF=FirebaseFirestore.getInstance();

    //oncreateview se ejecuta cuando se crea el elemento
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factura_pedidos);
        //obtiene los extras enviados por el intent del login
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            usuarioEmail=extras.getString("email");
            usuarioNombres=extras.getString("nombre");
            peluqueria=extras.getString("peluqueria");
        }
//on click para volver atras
        volver=findViewById(R.id.btnVolver);
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //declara el recyclerview
        rvFacturas=findViewById(R.id.rvFacturas);
        //le asigna un layout al recycler view
        rvFacturas.setLayoutManager(new LinearLayoutManager(this));

        //se declara un string vacio para que aunque el query este errornea no tenga erro el sistema
        String query="";
        if(peluqueria==null)
            query="usuario/"+usuarioEmail+"/factura/";
        else
            query="peluqueria/"+peluqueria+"/factura/";

        //oobtiene la informacion de las facturas ya sea de administrador o de usuario
        dbF.collection(query).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                facturas=new ArrayList<>();
                for(QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots){
                    Facturas f=queryDocumentSnapshot.toObject(Facturas.class);
                    facturas.add(f);
                }
                if(facturas.size()>0) {
                    adapterFacturasAdmin = new AdapterFacturasAdmin(facturas, FacturaPedidosActivity.this);
                    adapterFacturasAdmin.submitList(facturas);
                    rvFacturas.setAdapter(adapterFacturasAdmin);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(FacturaPedidosActivity.this, "Error al obtener datos", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    @Override
    public void onClick(Facturas ca) {
        //hacer alertdialog con los detalles de la factura
    }
}