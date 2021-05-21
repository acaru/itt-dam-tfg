package com.ITTDAM.bookncut.ui.productos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ITTDAM.bookncut.R;
import com.ITTDAM.bookncut.models.EncargosAdmin;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class EncargosPagarActivity extends AppCompatActivity {

    FirebaseFirestore dbF = FirebaseFirestore.getInstance();
    TextView aPagar;
    ListView listaProductos;
    String id,usuarioEmail,usuarioNombres,Peluqueria;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encargos_pagar);
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            usuarioEmail = extras.getString("email");
            usuarioNombres = extras.getString("nombre");
            Peluqueria = extras.getString("peluqueria");
            id=extras.getString("id");
        }
        else
            finish();
        aPagar=findViewById(R.id.txtVTotalPagoEncargo);
        listaProductos=findViewById(R.id.lstProductosEncargo);
        dbF.document("peluqueria/"+Peluqueria+"/encargos/"+id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                EncargosAdmin encargo = documentSnapshot.toObject(EncargosAdmin.class);
                aPagar.setText(encargo.getPrecio()+"€");
                listaProductos.setAdapter(new ArrayAdapter<String>(EncargosPagarActivity.this,R.layout.support_simple_spinner_dropdown_item,encargo.getProductos()));

            }
        });
    }
}