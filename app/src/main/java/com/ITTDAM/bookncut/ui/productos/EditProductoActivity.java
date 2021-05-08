package com.ITTDAM.bookncut.ui.productos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.ITTDAM.bookncut.R;
import com.ITTDAM.bookncut.database.Database;
import com.ITTDAM.bookncut.models.CitasPeluqueria;
import com.ITTDAM.bookncut.models.Productos;
import com.ITTDAM.bookncut.ui.citas.EditCitaPeluqueriaActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

public class EditProductoActivity extends AppCompatActivity {

    private static final String TAG = "EDIT PRODUCTO";
    private EditText nombre,precio,tipo;
    private String Peluqueria,usuarioEmail,usuarioNombres,Id;
    Database db;
    private FirebaseFirestore dbF = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_producto);

        db = new Database(this);
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            usuarioEmail = extras.getString("email");
            usuarioNombres = extras.getString("nombre");
            Peluqueria = extras.getString("peluqueria");
            Id = extras.getString("id");
        }

        nombre = findViewById(R.id.txtNombreEditProducto);
        precio = findViewById(R.id.txtPrecioEditProducto);
        tipo = findViewById(R.id.txtTipoEditProducto);

        dbF.document("peluqueria/"+Peluqueria+"/producto/"+Id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Productos productos = documentSnapshot.toObject(Productos.class);
                nombre.setText(productos.getNombre());
                precio.setText(productos.getPrecio()+"");
                tipo.setText(productos.getTipo());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(EditProductoActivity.this,"Error al obtener datos de firestore", Toast.LENGTH_SHORT).show();
                Log.e(TAG,"Error",e);
            }
        });
    }

    public void modificarProducto(View view){
        if(!nombre.getText().toString().isEmpty()&&!precio.getText().toString().isEmpty()&&!tipo.getText().toString().isEmpty()){
            Productos producto = new Productos(nombre.getText().toString(),Double.parseDouble(precio.getText().toString()),tipo.getText().toString());
            db.modificarProducto(Peluqueria,producto,Id);
            Toast.makeText(this,"Se modifico el producto", Toast.LENGTH_SHORT).show();
            finish();
        }
        else{
            Toast.makeText(this,"Datos incompletos", Toast.LENGTH_SHORT).show();
            Log.d(TAG,"Error Datos incompletos");
        }
    }

    public  void borrarProducto(View v){
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