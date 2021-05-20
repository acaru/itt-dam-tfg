package com.ITTDAM.bookncut;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ITTDAM.bookncut.Adapters.AdapterCitasUsuarios;
import com.ITTDAM.bookncut.database.Database;
import com.ITTDAM.bookncut.models.CitasPeluqueria;
import com.ITTDAM.bookncut.models.CitasUsuario;
import com.ITTDAM.bookncut.ui.citas.EditCitaPeluqueriaActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MainClienteActivity extends AppCompatActivity implements AdapterCitasUsuarios.MyListener{

    public static final String TAG = "MAIN CLIENTE";
    private Database db;
    private RecyclerView rvCitasUsuario;
    private AdapterCitasUsuarios adapterCitasUsuariositas;
    private String usuarioEmail;
    private String usuarioNombres;
    private CollectionReference citasRef;
    private ListenerRegistration citasListener;
    List<CitasUsuario> citas;

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: "+"usuario/"+usuarioEmail+"/citasusuario");
        this.rvCitasUsuario = findViewById(R.id.rcCitasUsuario);
        rvCitasUsuario.setLayoutManager(new LinearLayoutManager(this));

        citasRef.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot queryDocumentSnapshots, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException e) {
               if(e!=null){
                   Log.e(TAG, "onEvent: ", e);
                   return;
               }
                citas = new ArrayList<>();
                for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                    CitasUsuario citasusuario = documentSnapshot.toObject(CitasUsuario.class);
                    citasusuario.Id=documentSnapshot.getId();
                    if(!citasusuario.getFinalizado())
                    citas.add(citasusuario);
                }
                Log.d(TAG, "onEvent: "+citas.size());
                adapterCitasUsuariositas = new AdapterCitasUsuarios(citas,MainClienteActivity.this);
                adapterCitasUsuariositas.submitList(citas);
                rvCitasUsuario.setAdapter(adapterCitasUsuariositas);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_cliente);
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            usuarioEmail = extras.getString("email");
            usuarioNombres = extras.getString("nombre");
            // and get whatever type user account id is
        }
        db=new Database(this);
        citasRef= FirebaseFirestore.getInstance().collection("usuario/"+usuarioEmail+"/citasusuario");

    }

    @Override
    public void onClick(CitasUsuario ca) {
        Log.d("hola", "onClick: "+ca.getPeluqueria());
        Intent in = new Intent(this, EditCitaUsuarioActivity.class);
        in.putExtra("email",this.usuarioEmail);
        in.putExtra("nombre",this.usuarioNombres);
        in.putExtra("peluqueria",ca.getPeluqueria());
        in.putExtra("id",ca.Id);
        startActivity(in);
    }

    public void newCitaRedirect(View view){
        Intent in = new Intent(this,NewCitaUsuarioActivity.class);
        in.putExtra("email",this.usuarioEmail);
        in.putExtra("nombre",this.usuarioNombres);
        startActivity(in);
    }
}