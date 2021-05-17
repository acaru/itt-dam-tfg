package com.ITTDAM.bookncut;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.ITTDAM.bookncut.Adapters.DatePickerFragment;
import com.ITTDAM.bookncut.database.Database;
import com.ITTDAM.bookncut.models.CitasPeluqueria;
import com.ITTDAM.bookncut.models.CitasUsuario;
import com.ITTDAM.bookncut.models.Peluqueria;
import com.ITTDAM.bookncut.models.Servicios;
import com.ITTDAM.bookncut.ui.citas.EditCitaPeluqueriaActivity;
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

public class EditCitaUsuarioActivity extends AppCompatActivity {

    private static final String TAG = "EDIT CITA USUARIO";
    private EditText dia;
    private Spinner hora;
    private Spinner servicio;
    private Database db;
    private FirebaseFirestore dbF = FirebaseFirestore.getInstance();
    private String usuarioEmail;
    private String usuarioNombres;
    private String Id,Peluqueria;

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_cita_usuario);

        db= new Database(this);
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            usuarioEmail = extras.getString("email");
            usuarioNombres = extras.getString("nombre");
            Peluqueria = extras.getString("peluqueria");
            Id = extras.getString("id");
        }

        dia = findViewById(R.id.txtVDiaEditCitaUsuario);
        dia.setOnClickListener(this::chooseDate);
        hora = findViewById(R.id.spnHoraEditCitaUsuario);
        servicio = findViewById(R.id.spnServicioEditCitasUsuario);

        //Pintamos cada item desde una lista de strings
        hora.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,new ArrayList<>(List.of("Selecciona la hora","9:00","10:00","11:00","12:00","13:00","15:00","16:00","17:00","18:00","19:00"))));

        //Sacamos y recorremos los servicios de la peluqueria desde Firestore y los pintamos en cada item
        List<String> servicios = new ArrayList<>();
        dbF.collection("peluqueria/"+Peluqueria+"/servicio").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                    Servicios servicio = documentSnapshot.toObject(Servicios.class);
                    servicios.add(servicio.getNombre());
                }
                servicio.setAdapter(new ArrayAdapter<String>(EditCitaUsuarioActivity.this, android.R.layout.simple_list_item_1,servicios));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(EditCitaUsuarioActivity.this,"Error al obtener datos de firestore", Toast.LENGTH_SHORT).show();
                Log.e(TAG,"Error",e);
            }
        });

        dbF.document("usuario/"+usuarioEmail+"/citasusuario/"+Id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    CitasUsuario cita = documentSnapshot.toObject(CitasUsuario.class);
                    dia.setText(cita.getDia());
                    servicio.setSelection(servicios.indexOf(cita.getServicio()));
                    hora.setSelection(((ArrayAdapter<String>)hora.getAdapter()).getPosition(cita.getHora()));
                }
                else{
                    Log.d(TAG, "onSuccess: El documento "+"usuario/"+usuarioEmail+"/citasusuario/"+Id+" no existe");
                    Toast.makeText(EditCitaUsuarioActivity.this,"Error el elemento no existe", Toast.LENGTH_SHORT).show();
                    finish();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(EditCitaUsuarioActivity.this,"Error al obtener datos de firestore", Toast.LENGTH_SHORT).show();
                Log.e(TAG,"Error",e);
            }
        });
    }

    public void chooseDate(View view){
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because January is zero
                final String selectedDate = day + " / " + (month+1) + " / " + year;
                dia.setText(selectedDate);
            }
        });

        newFragment.show(this.getSupportFragmentManager(), "datePicker");
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    public void modificarCita(View v){

        if(!dia.getText().toString().isEmpty()){
/*            dbF.collection("peluqueria/"+Peluqueria+"/cita/").whereEqualTo("usuario",(Map) Map.ofEntries( new AbstractMap.SimpleEntry<String,String>("usuario",usuarioEmail),new AbstractMap.SimpleEntry<String,String>("nombre",usuarioNombres))).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @RequiresApi(api = Build.VERSION_CODES.R)
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        CitasPeluqueria citasPeluqueria = documentSnapshot.toObject(CitasPeluqueria.class);
                        if(dia.getText().toString().equals(citasPeluqueria.getDia())&&hora.getSelectedItem().equals(citasPeluqueria.getHora())){
                            CitasPeluqueria cita = new CitasPeluqueria(dia.getText().toString(), servicio.getSelectedItem()+"", hora.getSelectedItem()+"", false, (Map) Map.ofEntries(new AbstractMap.SimpleEntry<String, String>("usuario", usuarioEmail), new AbstractMap.SimpleEntry<String, String>("nombre", usuarioNombres)));
                            db.modificarCita(Peluqueria, cita, documentSnapshot.getId());
                        }

                    }
                }
            });*/
            CitasUsuario cita = new CitasUsuario(Peluqueria,dia.getText().toString(),servicio.getSelectedItem()+"",hora.getSelectedItem()+"",false);
            db.modificarCitaUsuario(usuarioEmail,cita,Id);

            Toast.makeText(EditCitaUsuarioActivity.this,"Se modifico la cita", Toast.LENGTH_SHORT).show();
            finish();
        }
        else{
            Toast.makeText(EditCitaUsuarioActivity.this,"Error rellene todos los campos", Toast.LENGTH_SHORT).show();
            Log.e(TAG,"Error rellene los campos");
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    public void finalizarCita(View v){

        dbF.document("usuario/"+usuarioEmail+"/citasusuario/"+Id).update("finalizado",true);
        Toast.makeText(EditCitaUsuarioActivity.this,"Se finalizo la cita", Toast.LENGTH_SHORT).show();
        finish();

    }

    public void borrarCita(View v){
        dbF.document("usuario/"+usuarioEmail+"/citasusuario/"+Id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(EditCitaUsuarioActivity.this,"Se borro la cita", Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(EditCitaUsuarioActivity.this,"Error al borrar datos de firestore", Toast.LENGTH_SHORT).show();
                Log.e(TAG,"Error",e);
            }
        });
    }
}