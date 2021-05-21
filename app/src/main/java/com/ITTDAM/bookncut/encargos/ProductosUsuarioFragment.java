package com.ITTDAM.bookncut.encargos;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ITTDAM.bookncut.Adapters.AdapterProductosUsuario;
import com.ITTDAM.bookncut.R;
import com.ITTDAM.bookncut.models.EncargosAdmin;
import com.ITTDAM.bookncut.models.EncargosUsuario;
import com.ITTDAM.bookncut.models.Productos;
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

public class ProductosUsuarioFragment extends Fragment implements AdapterProductosUsuario.MyListener{
    public static final String TAG = "MAIN PRODUCTOS ADMIN";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String usuarioEmail;
    private String usuarioNombres;
    private RecyclerView rvProductos;
    private AdapterProductosUsuario adapterProductos;
    private List<Productos> productos;
    private List<EncargosAdmin> encargosAdmins;
    private String Peluqueria;

    public ProductosUsuarioFragment() {
        // Required empty public constructor
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null) {
            usuarioEmail = extras.getString("email");
            usuarioNombres = extras.getString("nombre");
            // and get whatever type user account id is
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_productos_usuario, container, false);
    }
    @Override
    public void onStart() {
        super.onStart();
        rvProductos=getActivity().findViewById(R.id.rvProductos);
        rvProductos.setLayoutManager(new LinearLayoutManager(getActivity()));
        db.collection("peluqueria").whereEqualTo("propietario",usuarioEmail).addSnapshotListener(new EventListener<QuerySnapshot>() {            @Override
        public void onEvent(@Nullable @org.jetbrains.annotations.Nullable QuerySnapshot queryDocumentSnapshots, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException e) {
            for(QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots){
                Peluqueria=documentSnapshot.getId();

                db.collection("peluqueria/"+documentSnapshot.getId()+"/producto/").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        productos = new ArrayList<>();
                        for(QueryDocumentSnapshot documentSnapshot1 : queryDocumentSnapshots){
                            Productos producto = documentSnapshot1.toObject(Productos.class);

                            producto.Id=documentSnapshot1.getId();
                            productos.add(producto);

                        }

                        adapterProductos = new AdapterProductosUsuario(productos, ProductosUsuarioFragment.this);
                        adapterProductos.submitList(productos);
                        rvProductos.setAdapter(adapterProductos);
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
    public void onClick(Productos ca) {
        db.collection("usuario/"+usuarioEmail+"/encargos/").whereEqualTo("producto",ca.getNombre()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                    EncargosUsuario encargo = documentSnapshot.toObject(EncargosUsuario.class);

                    Intent in = new Intent(getActivity(), EncargoAddActivity.class);
                    in.putExtra("email",usuarioEmail);
                    in.putExtra("nombre",usuarioNombres);
                    in.putExtra("producto",encargo.getProducto());

                    in.putExtra("id",documentSnapshot.getId());
                    startActivity(in);
                }

            }
        });

    }
}