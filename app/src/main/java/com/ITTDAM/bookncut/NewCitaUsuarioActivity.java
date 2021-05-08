package com.ITTDAM.bookncut;

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
import android.widget.TextView;
import android.widget.Toast;

import com.ITTDAM.bookncut.database.Database;
import com.ITTDAM.bookncut.models.CitasPeluqueria;
import com.ITTDAM.bookncut.models.CitasUsuario;
import com.ITTDAM.bookncut.models.Peluqueria;
import com.ITTDAM.bookncut.models.Servicios;
import com.ITTDAM.bookncut.ui.citas.NewCitaPeluqueriaActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewCitaUsuarioActivity extends AppCompatActivity {

    private static final String TAG = "NEW CITA USUARIO";
    EditText dia;
    Spinner hora;
    Spinner servicio;
    Spinner peluqueriaSpn;
    Database db;
    FirebaseFirestore dbF = FirebaseFirestore.getInstance();
    String Peluqueria;
    String usuarioEmail;
    String usuarioNombres;

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_cita_usuario);
        db = new Database(this);
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            usuarioEmail = extras.getString("email");
            usuarioNombres = extras.getString("nombre");
            // and get whatever type user account id is
        }
        dia = findViewById(R.id.txtVDiaCitaUsuario);
        hora = findViewById(R.id.spnHoraCitaUsuario);
        servicio = findViewById(R.id.spnServicioCitasUsuario);
        hora.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,new ArrayList<>(List.of("Selecciona la hora","9:00","10:00","11:00","12:00","13:00","15:00","16:00","17:00","18:00","19:00"))));

        List<String> servicios = new ArrayList<>();
        servicios.add("Selecciona un servicio");
        dbF.collection("peluqueria/").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    Peluqueria = documentSnapshot.getId();
                    dbF.collection("peluqueria/"+Peluqueria+"/servicio/").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            Log.d(TAG, "onSuccess: Fuerda del for"+Peluqueria);
                            for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                                Log.d(TAG, "onSuccess: Dentro");
                                Servicios servicio = documentSnapshot.toObject(Servicios.class);
                                Log.d(TAG, "onSuccess: "+ documentSnapshot.toObject(Servicios.class).getNombre());
                                servicios.add(servicio.getNombre());
                            }
                            servicio.setAdapter(new ArrayAdapter<String>(NewCitaUsuarioActivity.this, android.R.layout.simple_list_item_1,servicios));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull @NotNull Exception e) {
                            Toast.makeText(NewCitaUsuarioActivity.this,"Error al obtener datos de firestore", Toast.LENGTH_SHORT).show();
                            Log.e(TAG,"Error",e);
                        }
                    });
                }
            }
                });







    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    public void createCita(View view){
        if(!dia.getText().toString().isEmpty()&&servicio.getSelectedItem()!="Selecciona el servicio"&&hora.getSelectedItem()!="Selecciona la hora"){
            CitasUsuario cita = new CitasUsuario(Peluqueria,dia.getText().toString(),hora.getSelectedItem()+"",servicio.getSelectedItem()+"",false);
                CitasPeluqueria citaPeluqueria = new CitasPeluqueria(dia.getText().toString(),servicio.getSelectedItem()+"",hora.getSelectedItem()+"",false,(Map) Map.ofEntries( new AbstractMap.SimpleEntry<String,String>("usuario",usuarioEmail),new AbstractMap.SimpleEntry<String,String>("nombre",usuarioNombres)));
                db.crearCitaUsuario(this.usuarioEmail,cita);
                db.crearCita(Peluqueria,citaPeluqueria);
                finish();
        }
        else{
            Toast.makeText(this,"Datos incompletos", Toast.LENGTH_SHORT).show();
            Log.d(TAG,"Error Datos incompletos");
        }
    }
}