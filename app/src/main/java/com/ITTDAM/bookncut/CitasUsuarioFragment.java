package com.ITTDAM.bookncut;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

    //declaracion de variables
    public static final String TAG = "MAIN CLIENTE CITAS";
    private List<CitasUsuario> citas;
    private Database db;
    private RecyclerView rvCitasUsuario;
    private AdapterCitasUsuarios adapterCitasUsuariocitas;
    private String usuarioEmail;
    private String usuarioNombres;
    private String peluqueria;
    private Button newcita;
    private FirebaseFirestore dbF = FirebaseFirestore.getInstance();


    public CitasUsuarioFragment() {
        // Required empty public constructor
    }

    //on start se ejecuta cuando inicia el elemento
    @Override
    public void onStart() {
        super.onStart();
//declara el recyclerview
        this.rvCitasUsuario = getActivity().findViewById(R.id.rvPeluqueriasChooser);
        //le asigna un layout al recycler view
        rvCitasUsuario.setLayoutManager(new LinearLayoutManager(getActivity()));
        //le asigna un tamaño fijo
        rvCitasUsuario.setHasFixedSize(true);
        //le asigna un adnimador al recyclerview
        rvCitasUsuario.setItemAnimator(new DefaultItemAnimator());

        //busca las citas del usuario y las asigna al recyclerview
        dbF.collection("usuario/"+usuarioEmail+"/citasusuario/").whereEqualTo("peluqueria",peluqueria).addSnapshotListener( new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot queryDocumentSnapshots, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException e) {
                if(e!=null){
                    Log.e(TAG, "onEvent: ", e);
                    return;
                }
                //declara las citas
                citas = new ArrayList<>();
                for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                    CitasUsuario citasusuario = documentSnapshot.toObject(CitasUsuario.class);
                    citasusuario.Id=documentSnapshot.getId();
                    if(!citasusuario.getFinalizado())//si la cita no ha finalizado  la añade
                        citas.add(citasusuario);
                }
                //crea el adapter de la cita
                adapterCitasUsuariocitas = new AdapterCitasUsuarios(citas,CitasUsuarioFragment.this);
                //se sube la lista de las citas al adaptador
                adapterCitasUsuariocitas.submitList(citas);
                //se asigna el adptador al recyclerview
                rvCitasUsuario.setAdapter(adapterCitasUsuariocitas);
            }
        });
        //boton para redirigir a la nueva cita
        newcita=(Button) getActivity().findViewById(R.id.btnCitas);

            newcita.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: ");
                    Intent in = new Intent(getActivity(),NewCitaUsuarioActivity.class);
                    in.putExtra("email",usuarioEmail);
                    in.putExtra("nombre",usuarioNombres);
                    in.putExtra("peluqueria",peluqueria);
                    startActivity(in);
                }
            });
    }

    //oncreateview se ejecuta cuando se crea el elemento
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root =inflater.inflate(R.layout.fragment_citas_usuario, container, false);//adapta el layout del fragment
        //obtiene los extras enviados por el intent del login
        Bundle extras = getActivity().getIntent().getExtras();

        if (extras != null) {
            usuarioEmail = extras.getString("email");
            usuarioNombres = extras.getString("nombre");
            peluqueria= extras.getString("peluqueria");
        }


        db=new Database(getActivity());

        return inflater.inflate(R.layout.fragment_citas_usuario, container, false);
    }

    //cuando hacen un click en cualquier elemento de las citas lo envia a EditCitaUsuarioActivity ccon los valores
//email=usuarioEmail, nombre=usuarioNombres,peluqueria=peluqueria,id=Id
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