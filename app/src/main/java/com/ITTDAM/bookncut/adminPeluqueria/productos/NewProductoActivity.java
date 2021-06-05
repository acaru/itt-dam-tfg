package com.ITTDAM.bookncut.adminPeluqueria.productos;

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

import java.util.ArrayList;
import java.util.List;

public class NewProductoActivity extends AppCompatActivity {

    //declara las variables
    private static final String TAG = "NEW PRODUCTO";
    private EditText nombre,precio;
    private Spinner tipo;
    private String Peluqueria,usuarioEmail,usuarioNombres;
    private Database db;
    private ImageButton btnVolver;

    //oncreateview se ejecuta cuando se crea el elemento
    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_producto);
        db = new Database(this);//declara la base de datos
        //obtiene los extras enviados por el intent del login
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            usuarioEmail = extras.getString("email");
            usuarioNombres = extras.getString("nombre");
            Peluqueria = extras.getString("peluqueria");
        }
        //declara los valores del formulario a utilizar
        nombre = findViewById(R.id.txtNombreNewProducto);
        precio = findViewById(R.id.txtPrecioNewProducto);
        tipo = findViewById(R.id.spnTipoNewProducto);
        btnVolver=findViewById(R.id.btnVolver);
        //on click para volver atras
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tipo.setAdapter(new ArrayAdapter<String>(this, R.layout.spinner_item,new ArrayList<>(List.of("Seleccione el tipo de producto","Maquina de afeitar","Champu","Fijador","Tijera","Tinte","Peine o Cepillo"))));
    }

    //Metodo para que la peluqueria cree nuevos productos
    public void guardarProducto(View view){
        if(!nombre.getText().toString().isEmpty()&&!precio.getText().toString().isEmpty()&&!tipo.getSelectedItem().equals("Seleccione el tipo de producto")){
            Productos producto = new Productos(nombre.getText().toString(),Double.parseDouble(precio.getText().toString()),tipo.getSelectedItem()+"");
            db.crearProducto(Peluqueria,producto);
            Toast.makeText(this,"Se creo el producto", Toast.LENGTH_SHORT).show();
            finish();
        }
        else{
            Toast.makeText(this,"Datos incompletos", Toast.LENGTH_SHORT).show();
            Log.d(TAG,"Error Datos incompletos");
        }
    }
}