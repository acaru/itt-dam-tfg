package com.ITTDAM.bookncut.adminPeluqueria.productos;

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

import com.ITTDAM.bookncut.Adapters.AdapterFacturasAdmin;
import com.ITTDAM.bookncut.Adapters.AdapterProductosUsuario;
import com.ITTDAM.bookncut.pedidos.FacturaPedidosActivity;
import com.ITTDAM.bookncut.R;
import com.ITTDAM.bookncut.models.Facturas;
import com.ITTDAM.bookncut.models.Productos;
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

public class ProductosFragment extends Fragment implements AdapterProductosUsuario.MyListener{

    //declaracion de variables
    public static final String TAG = "MAIN PRODUCTOS ADMIN";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String usuarioEmail;
    private String usuarioNombres;
    private RecyclerView rvProductos;
    private AdapterProductosUsuario adapterProductos;
    private AdapterFacturasAdmin adapterFacturasAdmin;
    private List<Productos> productos;
    private List<Facturas> facturas;
    private String Peluqueria;

    //oncreateview se ejecuta cuando se crea el elemento
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_productos, container, false);//adapta el layout del fragment
        Button btn = root.findViewById(R.id.button9);//definimos un botón para nueva cita en el layout del fragment
        btn.setOnClickListener(this::redirectProductoNuevo);//asgina el onclick para el boton del redirect

        //obtiene los extras enviados por el intent del login o la view anterior
        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null) {
            usuarioEmail = extras.getString("email");
            usuarioNombres = extras.getString("nombre");
        }
        return root;
    }

    //on start se ejecuta cuando inicia el elemento
    @Override
    public void onStart() {
        super.onStart();
        //boton para redicreccionar a facturas
        Button btn = getActivity().findViewById(R.id.btnRedirectFacturas);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity(), FacturaPedidosActivity.class);
                in.putExtra("email",usuarioEmail);
                in.putExtra("nombre",usuarioNombres);
                in.putExtra("peluqueria",Peluqueria);
                startActivity(in);
            }
        });
        //declara el recyclerview
        rvProductos=getActivity().findViewById(R.id.rvProductos);
        //le asigna un layout al recycler view
        rvProductos.setLayoutManager(new LinearLayoutManager(getActivity()));
        //le pone un tamanño fijo
        rvProductos.setHasFixedSize(true);
        //le asigna unn cache para el item
        rvProductos.setItemViewCacheSize(20);
        //le habilita el cache de dibujo
        rvProductos.setDrawingCacheEnabled(true);
        //modifica la calidad del cache del dibujo
        rvProductos.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        //obtiene los valores de la peluqueria
        db.collection("peluqueria").whereEqualTo("propietario",usuarioEmail).addSnapshotListener(new EventListener<QuerySnapshot>() {            @Override
            public void onEvent(@Nullable @org.jetbrains.annotations.Nullable QuerySnapshot queryDocumentSnapshots, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException e) {
                for(QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots){
                    Peluqueria=documentSnapshot.getId();
                    //obtiene los valoreas de la peluqueria
                    db.collection("peluqueria/"+documentSnapshot.getId()+"/producto/").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            productos = new ArrayList<>();
                            for(QueryDocumentSnapshot documentSnapshot1 : queryDocumentSnapshots){
                                Productos producto = documentSnapshot1.toObject(Productos.class);

                                producto.Id=documentSnapshot1.getId();
                                productos.add(producto);

                            }
                            //Pinta en el RecyclerView todas las peluquerias del propietario
                            adapterProductos = new AdapterProductosUsuario(productos, ProductosFragment.this);
                            //Le pone al adapter la lista que va a mostrar
                            adapterProductos.submitList(productos);
                            //Por último le pone el adapter al Recycler View
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

    //cuando hacen un click en cualquier elemento de las citas lo envia a EditProductoActivity ccon los valores
    //email=usuarioEmail, nombre=usuarioNombres,peluqueria=peluqueria,id=Id
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

    //si se crea una nueva cita se redirige a la actividad NewProductoActivity con los valores
    //email =usuarioEmail,nombre=usuarioNombres,peluqueria=peluqueria
    public void redirectProductoNuevo(View view){
        Intent in = new Intent(getActivity(), NewProductoActivity.class);
        in.putExtra("email",this.usuarioEmail);
        in.putExtra("nombre",this.usuarioNombres);
        in.putExtra("peluqueria",this.Peluqueria);
        startActivity(in);
    }


}