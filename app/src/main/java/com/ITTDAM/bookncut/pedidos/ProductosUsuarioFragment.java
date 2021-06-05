package com.ITTDAM.bookncut.pedidos;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ITTDAM.bookncut.Adapters.AdapterProductosUsuario;
import com.ITTDAM.bookncut.MainAdminActivity;
import com.ITTDAM.bookncut.R;
import com.ITTDAM.bookncut.models.Productos;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ProductosUsuarioFragment extends Fragment implements AdapterProductosUsuario.MyListener{
    public static final String TAG = "MAIN PRODUCTOS ADMIN";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String usuarioEmail;
    private String usuarioNombres;
    private RecyclerView rvProductos;
    private AdapterProductosUsuario adapterProductos;
    private List<Productos> productos;
    private String peluqueria;
    private ArrayList<Productos> carrito;
    private ArrayList<Integer> cantidadesCarrito;
    private TextView cerrar;
    private TextView carritoFragment;

    public ProductosUsuarioFragment() {
        // Required empty public constructor
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null) {
            usuarioEmail = extras.getString("email");
            usuarioNombres = extras.getString("nombre");
            peluqueria=extras.getString("peluqueria");
            // and get whatever type user account id is
        }
        carrito = new ArrayList<>();
        cantidadesCarrito=new ArrayList<>();
        cerrar = (TextView) getActivity().findViewById(R.id.btnVolverEditarCita);
        carritoFragment = (TextView) getActivity().findViewById(R.id.carrito);
        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainAdminActivity.CerrarSesion(getActivity());
            }
        });
        carritoFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(carrito!=null){
                    Intent intent3 = new Intent(getActivity(), CarritoUsuariosActivity.class);
                    intent3.putExtra("email",usuarioEmail);
                    intent3.putExtra("peluqueria",peluqueria);
                    intent3.putExtra("carrito",carrito);
                    intent3.putExtra("cantidades",cantidadesCarrito);
                    startActivityForResult(intent3,1);
                }
                else 
                    carrito=new ArrayList<>();
            }
        });
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_productos_usuario, container, false);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1){
            if(resultCode==getActivity().RESULT_OK){
                carrito=new ArrayList<>();
                cantidadesCarrito=new ArrayList<>();
            }
            else if (resultCode==getActivity().RESULT_CANCELED){
                if(data!=null){
                    carrito=(ArrayList<Productos>) data.getSerializableExtra("carrito");
                    cantidadesCarrito=(ArrayList<Integer>)data.getSerializableExtra("cantidades");
                }

            }
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        rvProductos=getActivity().findViewById(R.id.rvProductosUsuario);
        rvProductos.setLayoutManager(new GridLayoutManager(getActivity(),2));
        rvProductos.setHasFixedSize(true);
        rvProductos.setItemAnimator(new DefaultItemAnimator());

        db.collection("peluqueria/"+peluqueria+"/producto/").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                productos=new ArrayList<>();
                for(QueryDocumentSnapshot documentSnapshot1 : queryDocumentSnapshots){
                    Productos producto = documentSnapshot1.toObject(Productos.class);

                    producto.Id=documentSnapshot1.getId();
                    productos.add(producto);

                }
                Log.d(TAG, "onSuccess: "+productos.size());
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

    @Override
    public void onClick(Productos ca) {
        if(carrito!=null){
            int contains=carrito.indexOf(ca);

            if(!(contains>0)){
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setTitle("Cantidad");
                alertDialog.setMessage("Cantidad de productos a añadir: ");

                final EditText input = new EditText(getActivity());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                alertDialog.setView(input);
                alertDialog.setIcon(R.drawable.ic_productos);

                alertDialog.setPositiveButton("Añadir",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                int cantidad = Integer.parseInt(input.getText().toString());
                                if (cantidad>0) {

                                    carrito.add(ca);
                                    cantidadesCarrito.add(cantidad);
                                    Toast.makeText(getActivity(), "Se añadio correctamente", Toast.LENGTH_SHORT).show();
                                    Toast.makeText(getActivity(), "Ve al carrito", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getActivity().getApplicationContext(),
                                            "Pon una cantidad valida!", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });

                alertDialog.setNegativeButton("Cancelar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                alertDialog.show();
            }else
            {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setTitle("Cantidad");
                alertDialog.setMessage("¿Cuantos productos quires añadir?");

                final EditText input = new EditText(getActivity());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                input.setText(contains+"");
                alertDialog.setView(input);
                alertDialog.setIcon(R.drawable.ic_productos);

                int finalContains = contains;
                alertDialog.setPositiveButton("Modificar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                int cantidad = Integer.parseInt(input.getText().toString());
                                if (cantidad>0) {
                                    cantidadesCarrito.set(contains,cantidad);

                                } else {
                                    Toast.makeText(getActivity().getApplicationContext(),
                                            "Pon una cantidad valida!", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });

                alertDialog.setNegativeButton("Cancelar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                alertDialog.show();
            }

        }
        else{
            Toast.makeText(getActivity(), "Añade productos al carrito", Toast.LENGTH_SHORT).show();
            carrito = new ArrayList<>();
        }




    }
}