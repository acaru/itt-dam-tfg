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
import com.ITTDAM.bookncut.models.CitasUsuario;
import com.ITTDAM.bookncut.models.Servicios;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NewCitaPeluqueriaActivity extends AppCompatActivity {

    private static final String TAG = "NEW CITA PELUQUERIA";
    EditText dia,usuario,email;
    Spinner hora;
    Spinner servicio;
    Database db;
    FirebaseFirestore dbF = FirebaseFirestore.getInstance();
    String usuarioEmail;
    String usuarioNombres;
    String usuarioPeluqeria;

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_cita_peluqueria);
        db= new Database(this);
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            usuarioEmail = extras.getString("email");
            usuarioNombres = extras.getString("nombre");
            usuarioPeluqeria = extras.getString("peluqueria");
        }
        dia = findViewById(R.id.txtVDiaCitaPeluqueria);
        hora = findViewById(R.id.spnHoraCitaPeluqueria);
        servicio = findViewById(R.id.spnServicioCitasPeluqueria);
        usuario = findViewById(R.id.txtNombreUsuarioCitaPeluqueria);
        email= findViewById(R.id.txtEmailCitaPeluqueria);
        hora.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,new ArrayList<>(List.of("Selecciona la hora","9:00","10:00","11:00","12:00","13:00","15:00","16:00","17:00","18:00","19:00"))));
        List<String> servicios = new ArrayList<>();
        servicios.add("Selecciona un servicio");
        dbF.collection("peluqueria/"+usuarioPeluqeria+"/servicio").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                    Servicios servicio = documentSnapshot.toObject(Servicios.class);
                    servicios.add(servicio.getNombre());
                }
                servicio.setAdapter(new ArrayAdapter<String>(NewCitaPeluqueriaActivity.this, android.R.layout.simple_list_item_1,servicios));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(NewCitaPeluqueriaActivity.this,"Error al obtener datos de firestore", Toast.LENGTH_SHORT).show();
                Log.e(TAG,"Error",e);
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    public void createCita(View view){
        if(!dia.getText().toString().isEmpty()&&hora.getSelectedItem()!="Selecciona la hora"&&servicio.getSelectedItem()!="Selecciona un servicio"){
            CitasPeluqueria cita;
            if(!usuario.toString().isEmpty()&&email.getText().toString().isEmpty()){
            cita = new CitasPeluqueria(dia.getText().toString(),servicio.getSelectedItem()+"",hora.getSelectedItem()+"",false,(Map) Map.ofEntries( new AbstractMap.SimpleEntry<String,String>("usuario","rellenar"),new AbstractMap.SimpleEntry<String,String>("nombre",usuario.getText().toString())));
            }
            else if(usuario.toString().isEmpty()&&!email.getText().toString().isEmpty()){
                cita = new CitasPeluqueria(dia.getText().toString(),servicio.getSelectedItem()+"",hora.getSelectedItem()+"",false,(Map) Map.ofEntries( new AbstractMap.SimpleEntry<String,String>("usuario",email.getText().toString()),new AbstractMap.SimpleEntry<String,String>("nombre","rellenar")));
            }
            else if(!usuario.toString().isEmpty()&&!email.getText().toString().isEmpty()){
                cita = new CitasPeluqueria(dia.getText().toString(),servicio.getSelectedItem()+"",hora.getSelectedItem()+"",false,(Map) Map.ofEntries( new AbstractMap.SimpleEntry<String,String>("usuario",email.getText().toString()),new AbstractMap.SimpleEntry<String,String>("nombre",usuario.getText().toString())));
                CitasUsuario citU = new CitasUsuario(usuarioPeluqeria,dia.getText().toString(),hora.getSelectedItem()+"",servicio.getSelectedItem()+"",false);
                db.crearCitaUsuario(email.getText().toString(),citU);
            }
            else
             cita = new CitasPeluqueria(dia.getText().toString(),servicio.getSelectedItem()+"",hora.getSelectedItem()+"",false,(Map) Map.ofEntries( new AbstractMap.SimpleEntry<String,String>("usuario","rellenar"),new AbstractMap.SimpleEntry<String,String>("nombre","rellenar")));
            db.crearCita(usuarioPeluqeria,cita);
            finish();
        }
        else
        {
            Toast.makeText(this,"Datos incompletos", Toast.LENGTH_SHORT).show();
            Log.d(TAG,"Error Datos incompletos");
        }
    }
}