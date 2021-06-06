package com.ITTDAM.bookncut.root;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.ITTDAM.bookncut.Adapters.AdapterPeluquerias;
import com.ITTDAM.bookncut.R;
import com.ITTDAM.bookncut.models.Peluqueria;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainRootActivity extends AppCompatActivity implements AdapterPeluquerias.MyListener{

    //declaracion de variables
    private static final String TAG = "ROOT MAIN";
    private RecyclerView rvPeluqueria;
    private AdapterPeluquerias adapterPeluquerias;
    private List<Peluqueria> peluquerias;

    //on start se ejecuta cuando inicia el elemento
    @Override
    protected void onStart() {
        super.onStart();
        //declara el recyclerview
        this.rvPeluqueria = findViewById(R.id.rvPeluquerias);
        //le asigna un layout al recycler view
        rvPeluqueria.setLayoutManager(new LinearLayoutManager(this));

        //addSnapshotListener escucha los cambios de una colección que se especifique
        FirebaseFirestore.getInstance().collection("peluqueria/").addSnapshotListener(this, new EventListener<QuerySnapshot>() {
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
                    Peluqueria peluqueria = documentSnapshot.toObject(Peluqueria.class);
                    peluqueria.Id=documentSnapshot.getId();//obtiene el id de la peluqueria
                        peluquerias.add(peluqueria);//añade la peluqeuria
                }
                //Pinta en el RecyclerView todas las peluquerias del propietario
                adapterPeluquerias = new AdapterPeluquerias(peluquerias,MainRootActivity.this);
                //Le pone al adapter la lista que va a mostrar
                adapterPeluquerias.submitList(peluquerias);
                //Por último le pone el adapter al Recycler View
                rvPeluqueria.setAdapter(adapterPeluquerias);
            }
        });
    }

    //oncreateview se ejecuta cuando se crea el elemento
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_root);
    }
    //redirect a crear peluqueria
    public void redirectNewPeluqueria(View v){
        Intent i = new Intent(this,NewPeluRootActivity.class);
        startActivity(i);
    }

    //onclick peluqueria para editar
    @Override
    public void onClick(Peluqueria ca) {
        Intent i = new Intent(this,EditPeluRootActivity.class);
        i.putExtra("id",ca.Id);
        startActivity(i);
    }
}