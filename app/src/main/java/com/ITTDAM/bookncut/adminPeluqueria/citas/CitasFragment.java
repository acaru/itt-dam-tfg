package com.ITTDAM.bookncut.adminPeluqueria.citas;

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

import com.ITTDAM.bookncut.Adapters.AdapterCitasPeluqueria;
import com.ITTDAM.bookncut.R;
import com.ITTDAM.bookncut.models.CitasPeluqueria;
import com.ITTDAM.bookncut.models.Peluqueria;
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

public class CitasFragment extends Fragment implements AdapterCitasPeluqueria.MyListener{

    //declaracion de variables
    public static final String TAG = "MAIN CITAS ADMIN";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String usuarioEmail;
    private String usuarioNombres;
    private String peluqueria;
    private RecyclerView rvCitasPeluqueria;
    private AdapterCitasPeluqueria adapterCitasPeluqueria;
    private List<CitasPeluqueria> citas;

    //oncreateview se ejecuta cuando se crea el elemento
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_citas, container, false);//adapta el layout del fragment
        Button botn = root.findViewById(R.id.button8); //definimos un botón para nueva cita en el layout del fragment
        botn.setOnClickListener(this::redirectNuevaCita);//asgina el onclick para el boton del redirect

        //obtiene los extras enviados por el intent del login
        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null) {
            usuarioEmail = extras.getString("email");//el email de la persona iniciada sesion en el sistema
            usuarioNombres = extras.getString("nombre");//el nombre de la persona que inicio sesion
        }
        else
            getActivity().finish();
        return root;
    }

    //on start se ejecuta cuando inicia el elemento
    @Override
    public void onStart() {
        super.onStart();
        //declara el recyclerview
        rvCitasPeluqueria = getView().findViewById(R.id.rvCitasPeluqueria);
        //le asigna un layout al recycler view
        rvCitasPeluqueria.setLayoutManager(new LinearLayoutManager(getActivity()));

        //addSnapshotListener escucha los cambios de una colección que se especifique
        db.collection("peluqueria").whereEqualTo("propietario",usuarioEmail).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable @org.jetbrains.annotations.Nullable QuerySnapshot queryDocumentSnapshots, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException e) {

                //Obtiene todas las citas de la peluqueria que son del propietario
                for(QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots){//admin@gmail.com
                    peluqueria =documentSnapshot.getId();//obtiene el id de la peluqueria
                    Peluqueria as = documentSnapshot.toObject(Peluqueria.class);//obtiene el valor de la peluqueria

                    //obtiene las citas de la peluquerias que existen para ese usuario
                    db.collection("peluqueria/"+ peluqueria +"/cita/").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            citas = new ArrayList<>();//declara la lista para las citas
                            for(QueryDocumentSnapshot documentSnapshot1 : queryDocumentSnapshots){
                                //obtiene el valor de las citas
                                CitasPeluqueria cita = documentSnapshot1.toObject(CitasPeluqueria.class);
                                //asigna el id de la cita al atributo Id de la cita
                                cita.Id=documentSnapshot1.getId();
                                    if(!cita.getFinalizado())//si la cita no ha finalizado la añade a la lista
                                    citas.add(cita);
                            }
                            //Pinta en el RecyclerView todas las peluquerias del propietario
                            adapterCitasPeluqueria = new AdapterCitasPeluqueria(citas,CitasFragment.this);
                            //Le pone al adapter la lista que va a mostrar
                            adapterCitasPeluqueria.submitList(citas);
                            //Por último le pone el adapter al Recycler View
                            rvCitasPeluqueria.setAdapter(adapterCitasPeluqueria);
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

    //cuando hacen un click en cualquier elemento de las citas lo envia a EditCitaPeluqueriaActivity ccon los valores
    //email=usuarioEmail, nombre=usuarioNombres,peluqueria=peluqueria,id=Id
    @Override
    public void onClick(CitasPeluqueria ca) {
        Log.d(TAG, "onClick: "+ca.getUsuario());
        Intent in = new Intent(getActivity(), EditCitaPeluqueriaActivity.class);
        in.putExtra("email",this.usuarioEmail);
        in.putExtra("nombre",this.usuarioNombres);
        in.putExtra("peluqueria",this.peluqueria);
        in.putExtra("id",ca.Id);
        startActivity(in);
    }

    //si se crea una nueva cita se redirige a la actividad NewCitaPeluqueriaActivity con los valores
    //email =usuarioEmail,nombre=usuarioNombres,peluqueria=peluqueria
    public void redirectNuevaCita(View view){
        Intent in = new Intent(getActivity(), NewCitaPeluqueriaActivity.class);
        in.putExtra("email",this.usuarioEmail);
        in.putExtra("nombre",this.usuarioNombres);
        in.putExtra("peluqueria",this.peluqueria);
        startActivity(in);
    }
}