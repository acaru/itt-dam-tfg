package com.ITTDAM.bookncut;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.ITTDAM.bookncut.Adapters.AdapterCitasUsuarios;
import com.ITTDAM.bookncut.Adapters.AdapterPeluquerias;
import com.ITTDAM.bookncut.models.CitasUsuario;
import com.ITTDAM.bookncut.models.Peluqueria;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainRootActivity extends AppCompatActivity implements AdapterPeluquerias.MyListener{

    private static final String TAG = "ROOT MAIN";

    RecyclerView rvPeluqueria;
    AdapterPeluquerias adapterPeluquerias;
    List<Peluqueria> peluquerias;

    @Override
    protected void onStart() {
        super.onStart();
        this.rvPeluqueria = findViewById(R.id.rvPeluquerias);
        rvPeluqueria.setLayoutManager(new LinearLayoutManager(this));

        FirebaseFirestore.getInstance().collection("peluqueria/").addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot queryDocumentSnapshots, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException e) {
                if(e!=null){
                    Log.e(TAG, "onEvent: ", e);
                    return;
                }
                peluquerias = new ArrayList<>();
                for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                    Peluqueria peluqueria = documentSnapshot.toObject(Peluqueria.class);
                    peluqueria.Id=documentSnapshot.getId();
                        peluquerias.add(peluqueria);
                }
                Log.d(TAG, "onEvent: "+peluquerias.size());
                adapterPeluquerias = new AdapterPeluquerias(peluquerias,MainRootActivity.this);
                adapterPeluquerias.submitList(peluquerias);
                rvPeluqueria.setAdapter(adapterPeluquerias);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_root);
    }

    public void redirectNewPeluqueria(View v){
        Intent i = new Intent(this,NewPeluRootActivity.class);
        startActivity(i);
    }

    @Override
    public void onClick(Peluqueria ca) {

    }
}