package com.ITTDAM.bookncut.ui.citas;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.ITTDAM.bookncut.R;
import com.ITTDAM.bookncut.database.Database;
import com.ITTDAM.bookncut.models.CitasPeluqueria;
import com.ITTDAM.bookncut.models.Servicios;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EditCitaPeluqueriaActivity extends AppCompatActivity {

    private static final String TAG = "EDIT CITA PELUQUERIA";
    EditText dia,usuario,email;
    Spinner hora;
    Spinner servicio;
    Database db;
    FirebaseFirestore dbF = FirebaseFirestore.getInstance();
    String usuarioEmail;
    String usuarioNombres;
    String usuarioPeluqeria;
    String Id;

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_cita_peluqueria);
        db= new Database(this);
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            usuarioEmail = extras.getString("email");
            usuarioNombres = extras.getString("nombre");
            usuarioPeluqeria = extras.getString("peluqueria");
            Id = extras.getString("id");
        }

        dia = findViewById(R.id.txtVDiaEditCitaPeluqueria);
        hora = findViewById(R.id.spnHoraEditCitaPeluqueria);
        servicio = findViewById(R.id.spnServicioEditCitasPeluqueria);
        usuario = findViewById(R.id.txtNombreUsuarioEditCitaPeluqueria);
        email=findViewById(R.id.txtEmailEditCitaPeluqueria);
        hora.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,new ArrayList<>(List.of("Selecciona la hora","9:00","10:00","11:00","12:00","13:00","15:00","16:00","17:00","18:00","19:00"))));
        List<String> servicios = new ArrayList<>();
        dbF.collection("peluqueria/"+usuarioPeluqeria+"/servicio").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                    Servicios servicio = documentSnapshot.toObject(Servicios.class);
                    servicios.add(servicio.getNombre());
                }
                servicio.setAdapter(new ArrayAdapter<String>(EditCitaPeluqueriaActivity.this, android.R.layout.simple_list_item_1,servicios));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(EditCitaPeluqueriaActivity.this,"Error al obtener datos de firestore", Toast.LENGTH_SHORT).show();
                Log.e(TAG,"Error",e);
            }
        });
        dbF.document("peluqueria/"+usuarioPeluqeria+"/cita/"+Id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                CitasPeluqueria cita = documentSnapshot.toObject(CitasPeluqueria.class);
                dia.setText(cita.getDia());
                servicio.setSelection(servicios.indexOf(cita.getServicio()));
                hora.setSelection(((ArrayAdapter<String>)hora.getAdapter()).getPosition(cita.getHora()));
                usuario.setText(cita.getUsuario().get("nombre")+"");
                email.setText(cita.getUsuario().get("usuario")+"");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(EditCitaPeluqueriaActivity.this,"Error al obtener datos de firestore", Toast.LENGTH_SHORT).show();
                Log.e(TAG,"Error",e);
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.R)
    public void modificarCita(View v){
        if(!dia.getText().toString().isEmpty()&&!email.getText().toString().isEmpty()&&!usuario.getText().toString().isEmpty()){
            CitasPeluqueria cita = new CitasPeluqueria(dia.getText().toString(),servicio.getSelectedItem()+"",hora.getSelectedItem()+"",false,(Map) Map.ofEntries( new AbstractMap.SimpleEntry<String,String>("usuario",email.getText().toString()),new AbstractMap.SimpleEntry<String,String>("nombre",usuario.getText().toString())));
            db.modificarCita(usuarioPeluqeria,cita,Id);
            Toast.makeText(EditCitaPeluqueriaActivity.this,"Se modifico la cita", Toast.LENGTH_SHORT).show();
            finish();
        }
        else{
            Toast.makeText(EditCitaPeluqueriaActivity.this,"Error rellene todos los campos", Toast.LENGTH_SHORT).show();
            Log.e(TAG,"Error rellene los campos");
        }

    }

    public void finalizarCita(View v){
        dbF.document("peluqueria/"+usuarioPeluqeria+"/cita/"+Id).update("finalizado",true);
        Toast.makeText(EditCitaPeluqueriaActivity.this,"Se finalizo la cita", Toast.LENGTH_SHORT).show();
        finish();

    }

    public void borrarCita(View v){
        dbF.document("peluqueria/"+usuarioPeluqeria+"/cita/"+Id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(EditCitaPeluqueriaActivity.this,"Se borro la cita", Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(EditCitaPeluqueriaActivity.this,"Error al borrar datos de firestore", Toast.LENGTH_SHORT).show();
                Log.e(TAG,"Error",e);
            }
        });
    }
}