package com.ITTDAM.bookncut.adminPeluqueria.servicios;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.ITTDAM.bookncut.R;
import com.ITTDAM.bookncut.database.Database;
import com.ITTDAM.bookncut.models.Servicios;

public class NewServicioActivity extends AppCompatActivity {

    //declara las variables
    private static final String TAG = "NEW SERVICIO";
    private EditText nombre,precio;
    private String Peluqueria,usuarioEmail,usuarioNombres;
    private Database db;
    private ImageButton btnVolver;

    //oncreateview se ejecuta cuando se crea el elemento
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_servicio);
        db = new Database(this);//declara la base de datos

        //obtiene los extras enviados por el intent del login
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            usuarioEmail = extras.getString("email");
            usuarioNombres = extras.getString("nombre");
            Peluqueria = extras.getString("peluqueria");
        }

        //declara los valores del formulario a utilizar
        nombre = findViewById(R.id.txtNombreNewServicio);
        precio = findViewById(R.id.txtPrecioNewServicio);
        btnVolver=findViewById(R.id.btnVolver);
        //on click para volver atras
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    //Metodo para que la peluqueria cree nuevos servicios
    public void guardarServicio(View view){
        //verifica que no hay ningun dato sin elegir
        if(!nombre.getText().toString().isEmpty()&&!precio.getText().toString().isEmpty()){
            //crea el servicio
            Servicios servicios = new Servicios(nombre.getText().toString(),Double.parseDouble(precio.getText().toString()));
            //cde la base de datos crea el servicio
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