package com.ITTDAM.bookncut;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ITTDAM.bookncut.Adapters.AdapterCitasUsuarios;
import com.ITTDAM.bookncut.database.Database;
import com.ITTDAM.bookncut.models.CitasUsuario;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class CitasUsuarioFragment extends Fragment implements AdapterCitasUsuarios.MyListener{
    public static final String TAG = "MAIN CLIENTE CITAS";
    List<CitasUsuario> citas;
    private Database db;
    private RecyclerView rvCitasUsuario;
    private AdapterCitasUsuarios adapterCitasUsuariocitas;
    private String usuarioEmail;
    private String usuarioNombres;
    FirebaseFirestore dbF = FirebaseFirestore.getInstance();
    public CitasUsuarioFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: "+"usuario/"+usuarioEmail+"/citasusuario");
        this.rvCitasUsuario = getActivity().findViewById(R.id.rcCitasUsuario);
        rvCitasUsuario.setLayoutManager(new LinearLayoutManager(getActivity()));

        dbF.collection("usuario/"+usuarioEmail+"/citasusuario/").addSnapshotListener( new EventListener<QuerySnapshot>() {
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
                adapterCitasUsuariocitas = new AdapterCitasUsuarios(citas,CitasUsuarioFragment.this);
                adapterCitasUsuariocitas.submitList(citas);
                rvCitasUsuario.setAdapter(adapterCitasUsuariocitas);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Bundle extras = getActivity().getIntent().getExtras();

        if (extras != null) {
            usuarioEmail = extras.getString("email");
            usuarioNombres = extras.getString("nombre");
            // and get whatever type user account id is
        }
        db=new Database(getActivity());
        return inflater.inflate(R.layout.fragment_citas_usuario, container, false);
    }

    @Override
    public void onClick(CitasUsuario ca) {
        Log.d("hola", "onClick: "+ca.getPeluqueria());
        Intent in = new Intent(getActivity(), EditCitaUsuarioActivity.class);
        in.putExtra("email",this.usuarioEmail);
        in.putExtra("nombre",this.usuarioNombres);
        in.putExtra("peluqueria",ca.getPeluqueria());
        in.putExtra("id",ca.Id);
        startActivity(in);
    }
}