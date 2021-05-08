package com.ITTDAM.bookncut.ui.servicios;

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
import com.ITTDAM.bookncut.ui.productos.EditProductoActivity;
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

    public static final String TAG = "MAIN SERVICIOS ADMIN";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String usuarioEmail;
    private String usuarioNombres;
    private RecyclerView rvServicios;
    private AdapterServicios adapterServicios;
    private List<Servicios> servicios;
    private String Peluqueria;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_servicios, container, false);
        Button btn = root.findViewById(R.id.button18);
        btn.setOnClickListener(this::redirectServicioNuevo);
        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null) {
            usuarioEmail = extras.getString("email");
            usuarioNombres = extras.getString("nombre");
            // and get whatever type user account id is
        }
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        rvServicios = getActivity().findViewById(R.id.rvServicios);
        rvServicios.setLayoutManager(new LinearLayoutManager(getActivity()));
        db.collection("peluqueria").whereEqualTo("propietario",usuarioEmail).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable @org.jetbrains.annotations.Nullable QuerySnapshot queryDocumentSnapshots, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException e) {
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
                            adapterServicios = new AdapterServicios(servicios, ServiciosFragment.this);
                            adapterServicios.submitList(servicios);
                            rvServicios.setAdapter(adapterServicios);
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
    public void onClick(Servicios ca) {
        Log.d(TAG, "onClick: "+ca.getNombre());
        Intent in = new Intent(getActivity(), EditServicioActivity.class);
        in.putExtra("email",this.usuarioEmail);
        in.putExtra("nombre",this.usuarioNombres);
        in.putExtra("peluqueria",this.Peluqueria);
        in.putExtra("id",ca.Id);
        startActivity(in);
    }

    public void redirectServicioNuevo(View view){
        Intent in = new Intent(getActivity(), NewServicioActivity.class);
        in.putExtra("email",this.usuarioEmail);
        in.putExtra("nombre",this.usuarioNombres);
        in.putExtra("peluqueria",this.Peluqueria);
        startActivity(in);
    }
}