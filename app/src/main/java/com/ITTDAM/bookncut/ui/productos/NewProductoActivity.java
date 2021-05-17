package com.ITTDAM.bookncut.ui.productos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.ITTDAM.bookncut.R;
import com.ITTDAM.bookncut.database.Database;
import com.ITTDAM.bookncut.models.Productos;

public class NewProductoActivity extends AppCompatActivity {

    private static final String TAG = "NEW PRODUCTO";
    private EditText nombre,precio,tipo;
    private String Peluqueria,usuarioEmail,usuarioNombres;
    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_producto);
        db = new Database(this);
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            usuarioEmail = extras.getString("email");
            usuarioNombres = extras.getString("nombre");
            Peluqueria = extras.getString("peluqueria");
        }
        nombre = findViewById(R.id.txtNombreNewProducto);
        precio = findViewById(R.id.txtPrecioNewProducto);
        tipo = findViewById(R.id.txtTipoNewProducto);
    }

    //Metodo para que la peluqueria cree nuevos productos
    public void guardarProducto(View view){
        if(!nombre.getText().toString().isEmpty()&&!precio.getText().toString().isEmpty()&&!tipo.getText().toString().isEmpty()){
            Productos producto = new Productos(nombre.getText().toString(),Double.parseDouble(precio.getText().toString()),tipo.getText().toString());
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