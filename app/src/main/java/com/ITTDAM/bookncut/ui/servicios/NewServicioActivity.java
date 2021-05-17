package com.ITTDAM.bookncut.ui.servicios;

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

public class NewServicioActivity extends AppCompatActivity {

    private static final String TAG = "NEW SERVICIO";
    private EditText nombre,precio,duracion;
    private String Peluqueria,usuarioEmail,usuarioNombres;
    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_servicio);
        db = new Database(this);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            usuarioEmail = extras.getString("email");
            usuarioNombres = extras.getString("nombre");
            Peluqueria = extras.getString("peluqueria");
        }
        nombre = findViewById(R.id.txtNombreNewServicio);
        precio = findViewById(R.id.txtPrecioNewServicio);
        duracion = findViewById(R.id.txtDuracionNewServicio);
    }

    //Metodo para que la peluqueria cree nuevos servicios
    public void guardarServicio(View view){
        if(!nombre.getText().toString().isEmpty()&&!precio.getText().toString().isEmpty()&&!duracion.getText().toString().isEmpty()){
            Servicios servicios = new Servicios(nombre.getText().toString(),Double.parseDouble(precio.getText().toString()),Double.parseDouble(duracion.getText().toString()));
            db.crearServicio(Peluqueria,servicios);
            Toast.makeText(this,"Se creo el nuevo servicio", Toast.LENGTH_SHORT).show();
            finish();
        }
        else{
            Toast.makeText(this,"Datos incompletos", Toast.LENGTH_SHORT).show();
            Log.d(TAG,"Error Datos incompletos");
        }
    }
}