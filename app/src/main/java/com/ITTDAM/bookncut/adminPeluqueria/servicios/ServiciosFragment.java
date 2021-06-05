package com.ITTDAM.bookncut.adminPeluqueria.servicios;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ITTDAM.bookncut.Adapters.AdapterServicios;
import com.ITTDAM.bookncut.R;
import com.ITTDAM.bookncut.models.Servicios;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ServiciosFragment extends Fragment implements AdapterServicios.MyListener {

    //declaracion de variables
    public static final String TAG = "MAIN SERVICIOS ADMIN";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String usuarioEmail;
    private String usuarioNombres;
    private RecyclerView rvServicios;
    private AdapterServicios adapterServicios;
    private List<Servicios> servicios;
    private String Peluqueria;


    //oncreateview se ejecuta cuando se crea el elemento
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_servicios, container, false);//adapta el layout del fragment
        Button btn = root.findViewById(R.id.button18);//definimos un botón para nueva cita en el layout del fragment
        //obtiene los extras enviados por el intent del login
        btn.setOnClickListener(this::redirectServicioNuevo);
        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null) {
            usuarioEmail = extras.getString("email");//el email de la persona iniciada sesion en el sistema
            usuarioNombres = extras.getString("nombre");//el nombre de la persona que inicio sesion
        }
        return root;
    }

    //on start se ejecuta cuando inicia el elemento
    @Override
    public void onStart() {
        super.onStart();
        //declara el recyclerview
        rvServicios = getActivity().findViewById(R.id.rvServicios);
        //le asigna un layout al recycler view
        rvServicios.setLayoutManager(new LinearLayoutManager(getActivity()));
        //addSnapshotListener escucha los cambios de una colección que se especifique
        db.collection("peluqueria").whereEqualTo("propietario",usuarioEmail).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable @org.jetbrains.annotations.Nullable QuerySnapshot queryDocumentSnapshots, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException e) {
                //Obtiene todos los sericios que son del propietario
                for(QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots){
                    Peluqueria = documentSnapshot.getId();
                    db.collection("peluqueria/"+documentSnapshot.getId()+"/servicio/").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            servicios = new ArrayList<>();
                            for(QueryDocumentSnapshot documentSnapshot1 : queryDocumentSnapshots){
                                Servicios servicio = documentSnapshot1.toObject(Servicios.class);
                                servicio.Id=documentSnapshot1.getId();
                                servicios.add(servicio);
                            }
                            //Pinta en el RecyclerView todos los servicios de las peluquerias del propietario
                            adapterServicios = new AdapterServicios(servicios, ServiciosFragment.this);
                            //Le pone al adapter la lista que va a mostrar
                            adapterServicios.submitList(servicios);
                            //Por último le pone el adapter al Recycler View
                            rvServicios.setAdapter(adapterServicios);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull @NotNull Exception e) {
                            Log.e(TAG, "onFailure: ", e);//si falla pinta el error
                        }
                    });
                }
            }
        });
    }

    //cuando hacen un click en cualquier elemento de los servicios lo envia a EditServicioActivity ccon los valores
    //email=usuarioEmail, nombre=usuarioNombres,peluqueria=peluqueria,id=Id
    @Override
    public void onClick(Servicios ca) {
        Log.d(TAG, "onClick: "+ca.getNombre());
        Intent in = new Intent(getActivity(), EditServicioActivity.class);
        in.putExtra("email",this.usuarioEmail);
        in.putExtra("nombre",this.usuarioNombres);
        in.putExtra("peluqueria",this.Peluqueria);
        in.putExtra("id",ca.Id);
        startActivity(in);
    }

    //si se crea una nueva cita se redirige a la actividad NewCitaPeluqueriaActivity con los valores
    //email =usuarioEmail,nombre=usuarioNombres,peluqueria=peluqueria
    public void redirectServicioNuevo(View view){
        Intent in = new Intent(getActivity(), NewServicioActivity.class);
        in.putExtra("email",this.usuarioEmail);
        in.putExtra("nombre",this.usuarioNombres);
        in.putExtra("peluqueria",this.Peluqueria);
        startActivity(in);
    }
}