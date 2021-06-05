package com.ITTDAM.bookncut;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.ITTDAM.bookncut.Adapters.AdapterPeluquerias;
import com.ITTDAM.bookncut.database.Database;
import com.ITTDAM.bookncut.models.Peluqueria;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainClienteActivity extends AppCompatActivity implements AdapterPeluquerias.MyListener{

    //declara variables
    public static final String TAG = "MAIN CLIENTE";
    private Database db;
    private FirebaseFirestore dbF=FirebaseFirestore.getInstance();
    private RecyclerView rvPeluquerias;
    private AdapterPeluquerias adapterPeluquerias;
    private String usuarioEmail;
    private String usuarioNombres;
    private List<Peluqueria> peluquerias;

    //on start se ejecuta cuando inicia el elemento
    @Override
    protected void onStart() {
        super.onStart();
        //declara el recyclerview
        this.rvPeluquerias = findViewById(R.id.rvPeluqueriasChooser);
        //le asigna un layout al recycler view
        rvPeluquerias.setLayoutManager(new LinearLayoutManager(this));

        //addSnapshotListener escucha los cambios de una colección que se especifique
        dbF.collection("peluqueria/").addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot queryDocumentSnapshots, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException e) {
               if(e!=null){
                   Log.e(TAG, "onEvent: ", e);
                   return;
               }
               //declara la lista de las peluquerias
                peluquerias = new ArrayList<>();
                for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                    //obtiene el valor de la peluqueria
                    Peluqueria peluquerias2 = documentSnapshot.toObject(Peluqueria.class);
                    peluquerias2.Id=documentSnapshot.getId();//obtiene el id de la peluqueria
                        peluquerias.add(peluquerias2);//añade la peluqeuria
                }
                //Pinta en el RecyclerView todas las peluquerias del propietario
                adapterPeluquerias = new AdapterPeluquerias(peluquerias,MainClienteActivity.this);
                //Le pone al adapter la lista que va a mostrar
                adapterPeluquerias.submitList(peluquerias);
                //Por último le pone el adapter al Recycler View
                rvPeluquerias.setAdapter(adapterPeluquerias);
            }
        });
    }

    //oncreateview se ejecuta cuando se crea el elemento
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_cliente);

        //obtiene los extras enviados por el intent del login
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            usuarioEmail = extras.getString("email");
            usuarioNombres = extras.getString("nombre");
            // and get whatever type user account id is
        }
        db=new Database(this);
        

    }


//cuando hacen un click en cualquier elemento de las peluquerias lo envia a MainClienteActivity2 ccon los valores
//email=usuarioEmail, nombre=usuarioNombres,peluqueria=peluqueria,id=Id
    @Override
    public void onClick(Peluqueria ca) {
        Log.d("hola", "onClick: "+ca.getNombre());
        Intent in = new Intent(this, MainClienteActivity2.class);
        in.putExtra("email",this.usuarioEmail);
        in.putExtra("nombre",this.usuarioNombres);
        in.putExtra("peluqueria",ca.Id);
        startActivity(in);
    }
}