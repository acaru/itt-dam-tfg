package com.ITTDAM.bookncut.ui.citas;

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

    public static final String TAG = "MAIN CITAS ADMIN";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String usuarioEmail;
    private String usuarioNombres;
    private String peluqueria;
    private RecyclerView rvCitasPeluqueria;
    private AdapterCitasPeluqueria adapterCitasPeluqueria;
    private List<CitasPeluqueria> citas;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_citas, container, false);
        Button botn = root.findViewById(R.id.button8); //definimos un botón para nueva cita en el layout del fragment
        botn.setOnClickListener(this::redirectNuevaCita);
        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null) {
            usuarioEmail = extras.getString("email");
            usuarioNombres = extras.getString("nombre");
        }
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        rvCitasPeluqueria = getView().findViewById(R.id.rvCitasPeluqueria);
        rvCitasPeluqueria.setLayoutManager(new LinearLayoutManager(getActivity()));

        //addSnapshotListener escucha los cambios de una colección que se especifique
        db.collection("peluqueria").whereEqualTo("propietario",usuarioEmail).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable @org.jetbrains.annotations.Nullable QuerySnapshot queryDocumentSnapshots, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException e) {

                //Obtiene todas las citas de la peluqueria que son del propietario
                for(QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots){//admin@gmail.com
                    peluqueria =documentSnapshot.getId();
                    Peluqueria as = documentSnapshot.toObject(Peluqueria.class);
                    Log.d(TAG, "onEvent: "+as.getHorario().get(0));
                    db.collection("peluqueria/"+ peluqueria +"/cita/").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            citas = new ArrayList<>();
                            for(QueryDocumentSnapshot documentSnapshot1 : queryDocumentSnapshots){
                                    CitasPeluqueria cita = documentSnapshot1.toObject(CitasPeluqueria.class);
                                    cita.Id=documentSnapshot1.getId();
                                    if(!cita.getFinalizado())
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
                            Log.e(TAG, "onFailure: ", e);
                        }
                    });
                }
            }
        });
    }

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

    public void redirectNuevaCita(View view){
        Intent in = new Intent(getActivity(), NewCitaPeluqueriaActivity.class);
        in.putExtra("email",this.usuarioEmail);
        in.putExtra("nombre",this.usuarioNombres);
        in.putExtra("peluqueria",this.peluqueria);
        startActivity(in);
    }
}