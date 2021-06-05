package com.ITTDAM.bookncut.adminPeluqueria.productos;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.ITTDAM.bookncut.R;
import com.ITTDAM.bookncut.database.Database;
import com.ITTDAM.bookncut.models.Productos;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class EditProductoActivity extends AppCompatActivity {

    //declara variables
    private static final String TAG = "EDIT PRODUCTO";
    private EditText nombre,precio;
    private Spinner tipo;
    private String Peluqueria,usuarioEmail,usuarioNombres,Id;
    private Database db;
    private FirebaseFirestore dbF = FirebaseFirestore.getInstance();
    private ImageButton btnVolver;

    //oncreateview se ejecuta cuando se crea el elemento
    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_producto);

        db = new Database(this);
        Bundle extras = getIntent().getExtras();
//obtiene los extras enviados por el intent del login y la view anterior
        if (extras != null) {
            usuarioEmail = extras.getString("email");
            usuarioNombres = extras.getString("nombre");
            Peluqueria = extras.getString("peluqueria");
            Id = extras.getString("id");
        }
        else
            finish();

        //declara los valores del formulario a utilizar
        nombre = findViewById(R.id.txtNombreEditProducto);
        precio = findViewById(R.id.txtPrecioEditProducto);
        tipo = findViewById(R.id.spnTipoEditProducto);
        btnVolver=findViewById(R.id.btnVolver);
        //on click para volver atras
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tipo.setAdapter(new ArrayAdapter<String>(this, R.layout.spinner_item,new ArrayList<>(List.of("Seleccione el tipo de producto","Maquina de afeitar","Champu","Fijador","Tijera","Tinte","Peine o Cepillo"))));
//asigna los valores guardados anteriormente al formulario
        dbF.document("peluqueria/"+Peluqueria+"/producto/"+Id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Productos productos = documentSnapshot.toObject(Productos.class);
                nombre.setText(productos.getNombre());
                precio.setText(productos.getPrecio()+"");
                tipo.setSelection(((ArrayAdapter<String>)tipo.getAdapter()).getPosition(productos.getTipo()));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(EditProductoActivity.this,"Error al obtener datos de firestore", Toast.LENGTH_SHORT).show();
                Log.e(TAG,"Error",e);
            }
        });
    }

    //funcion para modificar producto
    public void modificarProducto(View view){
        //verifica que no hay ningun dato sin elegir
        if(!nombre.getText().toString().isEmpty()&&!precio.getText().toString().isEmpty()&&!tipo.getSelectedItem().equals("Seleccione el tipo de producto")){
            //crea el producto
            Productos producto = new Productos(nombre.getText().toString(),Double.parseDouble(precio.getText().toString()),tipo.getSelectedItem()+"");
            //de la clase database llama al metodo modificar cita y le envia, el usuario,la cita y el id de la cita
            db.modificarProducto(Peluqueria,producto,Id);
            Toast.makeText(this,"Se modifico el producto", Toast.LENGTH_SHORT).show();
            finish();
        }
        else{
            Toast.makeText(this,"Datos incompletos", Toast.LENGTH_SHORT).show();
            Log.d(TAG,"Error Datos incompletos");//si falla imprime datos
        }
    }

    //funcion que borra
    public  void borrarProducto(View v){
        //si existe el producto lo borra
        dbF.document("peluqueria/"+Peluqueria+"/producto/"+Id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(EditProductoActivity.this,"Se borro el producto", Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(EditProductoActivity.this,"Error al borrar datos de firestore", Toast.LENGTH_SHORT).show();
                Log.e(TAG,"Error",e);
            }
        });
    }
}