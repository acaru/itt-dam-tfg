package com.ITTDAM.bookncut.ui.productos;

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

import com.ITTDAM.bookncut.Adapters.AdapterProductos;
import com.ITTDAM.bookncut.R;
import com.ITTDAM.bookncut.models.Productos;
import com.ITTDAM.bookncut.ui.citas.EditCitaPeluqueriaActivity;
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

public class ProductosFragment extends Fragment implements AdapterProductos.MyListener{

    public static final String TAG = "MAIN PRODUCTOS ADMIN";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String usuarioEmail;
    private String usuarioNombres;
    private RecyclerView rvProductos;
    private AdapterProductos adapterProductos;
    private List<Productos> productos;
    private String Peluqueria;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_productos, container, false);
        Button btn = root.findViewById(R.id.button9);
        btn.setOnClickListener(this::redirectProductoNuevo);
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
        rvProductos=getActivity().findViewById(R.id.rvProductos);
        rvProductos.setLayoutManager(new LinearLayoutManager(getActivity()));
        db.collection("peluqueria").whereEqualTo("propietario",usuarioEmail).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
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
                            adapterProductos = new AdapterProductos(productos, ProductosFragment.this);
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
        Log.d(TAG, "onClick: "+ca.getNombre());
        Intent in = new Intent(getActivity(), EditProductoActivity.class);
        in.putExtra("email",this.usuarioEmail);
        in.putExtra("nombre",this.usuarioNombres);
        in.putExtra("peluqueria",this.Peluqueria);
        in.putExtra("id",ca.Id);
        startActivity(in);
    }

    public void redirectProductoNuevo(View view){
        Intent in = new Intent(getActivity(), NewProductoActivity.class);
        in.putExtra("email",this.usuarioEmail);
        in.putExtra("nombre",this.usuarioNombres);
        in.putExtra("peluqueria",this.Peluqueria);
        startActivity(in);
    }
}