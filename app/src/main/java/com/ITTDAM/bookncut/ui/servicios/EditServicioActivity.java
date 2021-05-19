package com.ITTDAM.bookncut.ui.servicios;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ITTDAM.bookncut.R;
import com.ITTDAM.bookncut.database.Database;
import com.ITTDAM.bookncut.models.Productos;
import com.ITTDAM.bookncut.models.Servicios;
import com.ITTDAM.bookncut.ui.productos.EditProductoActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

public class EditServicioActivity extends AppCompatActivity {

    private static final String TAG = "EDIT PRODUCTO";
    private EditText nombre,precio,duracion;
    private String Peluqueria,usuarioEmail,usuarioNombres,Id;
    Database db;
    private FirebaseFirestore dbF = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_servicio);

        db = new Database(this);
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            usuarioEmail = extras.getString("email");
            usuarioNombres = extras.getString("nombre");
            Peluqueria = extras.getString("peluqueria");
            Id = extras.getString("id");
        }

        nombre = findViewById(R.id.txtNombreEditServicio);
        precio = findViewById(R.id.txtPrecioEditServicio);
        duracion = findViewById(R.id.txtDuracionEditServicio);

        //Obtiene o lee el servicio que recibe de ServiciosFragment (activity anterior) mediante su Id que le hemos pasado por extras
        dbF.document("peluqueria/"+Peluqueria+"/servicio/"+Id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Servicios servicios = documentSnapshot.toObject(Servicios.class);
                nombre.setText(servicios.getNombre());
                precio.setText(servicios.getPrecio()+"");
                duracion.setText(servicios.getDuracion()+"");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(EditServicioActivity.this,"Error al obtener datos de firestore", Toast.LENGTH_SHORT).show();
                Log.e(TAG,"Error",e);
            }
        });
    }
    public void modificarServicio(View v){
        if(!nombre.getText().toString().isEmpty()&&!precio.getText().toString().isEmpty()&&!duracion.getText().toString().isEmpty()){
            Servicios servicio = new Servicios(nombre.getText().toString(),Double.parseDouble(precio.getText().toString()),Integer.parseInt(duracion.getText().toString()));
            db.modificarServicio(Peluqueria,servicio,Id);
            Toast.makeText(this,"Se modifico el servicio", Toast.LENGTH_SHORT).show();
            finish();
        }
        else{
            Toast.makeText(this,"Datos incompletos", Toast.LENGTH_SHORT).show();
            Log.d(TAG,"Error Datos incompletos");
        }
    }

    public  void borrarServicio(View v){
        dbF.document("peluqueria/"+Peluqueria+"/servicio/"+Id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(EditServicioActivity.this,"Se borro el servicio", Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(EditServicioActivity.this,"Error al borrar datos de firestore", Toast.LENGTH_SHORT).show();
                Log.e(TAG,"Error",e);
            }
        });
    }
}