package com.ITTDAM.bookncut.pedidos;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ITTDAM.bookncut.Adapters.AdapterProductos;
import com.ITTDAM.bookncut.R;
import com.ITTDAM.bookncut.database.Database;
import com.ITTDAM.bookncut.models.Productos;

import java.util.ArrayList;

public class CarritoUsuariosActivity extends AppCompatActivity implements AdapterProductos.MyListener {
    //declaracion de variables
    private static final String TAG = "PEDIDOS";
    private ArrayList<Productos> productosCarrito;
    private ArrayList<Integer> cantidadesCarrito;
    private String Id,usuarioEmail,peluqueria;
    private TextView Volver;
    private TextView total;
    private RecyclerView rvCarrito ;
    private AdapterProductos adapterProductos;
    private Database db = new Database(this);
    private Button btn;

    //oncreateview se ejecuta cuando se crea el elemento
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos_usuarios);


        //obtiene los extras enviados por el intent del login
        Bundle extras = getIntent().getExtras();
        if(extras != null){

            Id=extras.getString("id");
            usuarioEmail=extras.getString("email");
            peluqueria=extras.getString("peluqueria");


        }
        else
            finish();
        if(getIntent().getSerializableExtra("carrito")!=null){
            productosCarrito= (ArrayList<Productos>) getIntent().getSerializableExtra("carrito");
            cantidadesCarrito=(ArrayList<Integer>) getIntent().getSerializableExtra("cantidades");


        }
        else{

            productosCarrito=new ArrayList<>();
            cantidadesCarrito= new ArrayList<>();
        }
//declara el recyclerview
        rvCarrito=findViewById(R.id.rvCarritoPedido);
        //le asigna un layout al recycler view
        rvCarrito.setLayoutManager(new LinearLayoutManager(this));

        //Pinta en el RecyclerView todas las productos
        adapterProductos =  new AdapterProductos(productosCarrito,this);
        //Le pone al adapter la lista que va a mostrar
        adapterProductos.submitList(productosCarrito);
        //Por último le pone el adapter al Recycler View
        rvCarrito.setAdapter(adapterProductos);
        //se registra para el evento de mantener el mucho tiempo el clck
        registerForContextMenu(rvCarrito);
        //se declaran las variables a utilizar de la activity
        total =findViewById(R.id.txtTotalCarrito);
        Volver = findViewById(R.id.volverCarrito);
        //asgina el total del pedido
        double a=total();
        total.setText("Total: "+a+"€");
        //asigna el boton para volver atras
        Volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.putExtra("productos",productosCarrito);
                i.putExtra("cantidades",cantidadesCarrito);
                setResult(RESULT_CANCELED,i);
                finish();
            }
        });
        //asigna el boton de pagar
        Button btnPagar = findViewById(R.id.btnPagarPedido);
        btnPagar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                pagar(view);
            }
        });
    }

    //funcion que calcula el total
    public double total(){
        double a=0;
        for(int k=0;k<productosCarrito.size();k++){
            a+=productosCarrito.get(k).getPrecio()*cantidadesCarrito.get(k);
        }
        return a;
    }

    //si selecciona un item del menu pueden ocurrir 2 cosas
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int position = -1;
        try {
            position = ((AdapterProductos)rvCarrito.getAdapter()).getPosition();
        } catch (Exception e) {
            Log.d(TAG, e.getLocalizedMessage(), e);//errore
            return super.onContextItemSelected(item);
        }
        //swtch para verificar cual item se selecciono
        switch (item.getItemId()) {
            case R.id.modificarCantidad:// sii selecciona modificar
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                alertDialog.setTitle("Cantidad");
                alertDialog.setMessage("Cantidad de productos a modificar");

                //crea un edit text
                final EditText input = new EditText(this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                //le asigna el texto
                input.setText(cantidadesCarrito.get(adapterProductos.getPosition())+"");
                alertDialog.setView(input);
                alertDialog.setIcon(R.drawable.ic_productos);

                int finalContains = cantidadesCarrito.get(adapterProductos.getPosition());
                int finalPosition = adapterProductos.getPosition();
                alertDialog.setPositiveButton("Añadir",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                int cantidad = Integer.parseInt(input.getText().toString());
                                if (cantidad>0) {
                                    cantidadesCarrito.set(adapterProductos.getPosition(),cantidad);
                                    total.setText("Total: "+total()+"€");
                                } else {
                                    Toast.makeText(getApplicationContext(),
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
                break;
            case R.id.borraCantidad://si lo borra se elimina de la lista
                productosCarrito.remove(adapterProductos.getPosition());
                cantidadesCarrito.remove(adapterProductos.getPosition());
                total.setText("Total: "+total()+"€");
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onClick(Productos ca) {

    }
    //boton pagar crea la factura
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void pagar(View v){
        Log.d(TAG, "pagar: "+usuarioEmail+peluqueria+productosCarrito.size()+cantidadesCarrito.size());
        db.crearFactura(usuarioEmail,peluqueria,productosCarrito,cantidadesCarrito);
        finish();
    }
}