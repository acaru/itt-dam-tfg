package com.ITTDAM.bookncut.ui.citas;

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
import com.ITTDAM.bookncut.R;
import com.ITTDAM.bookncut.database.Database;
import com.ITTDAM.bookncut.models.CitasPeluqueria;
import com.ITTDAM.bookncut.models.CitasUsuario;
import com.ITTDAM.bookncut.models.Peluqueria;
import com.ITTDAM.bookncut.models.Servicios;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class EditCitaPeluqueriaActivity extends AppCompatActivity {

    private static final String TAG = "EDIT CITA PELUQUERIA";
    private EditText dia,usuario,email;
    private Spinner hora;
    private Spinner servicio;
    private Database db;
    private FirebaseFirestore dbF = FirebaseFirestore.getInstance();
    private String usuarioEmail;
    private String usuarioNombres;
    private String usuarioPeluqeria;
    private String Id;
    Peluqueria Peluqueria;

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_cita_peluqueria);
        db= new Database(this);
        Bundle extras = getIntent().getExtras();

        //Comprobamos que los extras no sean null
        if (extras != null) {
            usuarioEmail = extras.getString("email");
            usuarioNombres = extras.getString("nombre");
            usuarioPeluqeria = extras.getString("peluqueria");
            Id = extras.getString("id");
        }
        dbF.document("peluqueria/"+usuarioPeluqeria).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Peluqueria = documentSnapshot.toObject(com.ITTDAM.bookncut.models.Peluqueria.class);
                Peluqueria.Id=documentSnapshot.getId();
            }
        });

        dia = findViewById(R.id.txtVDiaEditCitaPeluqueria);
        dia.setOnClickListener(this::chooseDate);
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

    public void chooseDate(View view){
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because January is zero
                final String selectedDate = day + "/" + (month+1) + "/" + year;
                try {
                    SimpleDateFormat format1=new SimpleDateFormat("dd/MM/yyyy");
                    Date dt1= null;

                    dt1 = format1.parse(selectedDate);

                    DateFormat format2=new SimpleDateFormat("EEEE");
                    String finalDay=format2.format(dt1);
                    List<String> horasDeArray=null;
                    String[] horas=null;
                    switch (finalDay){
                        case "Monday":
                            horas= Peluqueria.getHorario().get("lunes").split(",");

                            break;
                        case "Tuesday":
                            horas = Peluqueria.getHorario().get("martes").split(",");
                            break;
                        case "Wednesday":
                            horas = Peluqueria.getHorario().get("miercoles").split(",");
                            break;
                        case "Thursday":
                            horas = Peluqueria.getHorario().get("jueves").split(",");
                            break;
                        case "Friday":
                            horas = Peluqueria.getHorario().get("viernes").split(",");
                            break;
                        case "Saturday":
                            horas = Peluqueria.getHorario().get("sabado").split(",");
                            break;
                        default:
                            horas= new String[]{"Selecciona un dia"};
                            Toast.makeText(EditCitaPeluqueriaActivity.this,"Selecciona un dia que este abierto", Toast.LENGTH_SHORT).show();


                    }
                    horasDeArray=  new ArrayList(Arrays.asList(horas));
                    List<String> finalHorasDeArray = horasDeArray;
                    dbF.collection("peluqueria/"+Peluqueria.Id+"/cita").whereEqualTo("dia",selectedDate).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            for(DocumentSnapshot doc : queryDocumentSnapshots){
                                CitasPeluqueria cita = doc.toObject(CitasPeluqueria.class);
                                Log.d(TAG, "onSuccess: "+finalHorasDeArray.indexOf(cita.getHora()));
                                if(finalHorasDeArray.indexOf(cita.getHora())>0)
                                    finalHorasDeArray.remove(finalHorasDeArray.indexOf(cita.getHora()));
                                if(finalHorasDeArray.indexOf(cita.getHora())==0)
                                    finalHorasDeArray.remove(0);
                            }
                        }
                    });
                    hora.setAdapter(new ArrayAdapter<String>(EditCitaPeluqueriaActivity.this, android.R.layout.simple_list_item_1,finalHorasDeArray));

                    Log.d(TAG, "onDateSet: "+finalDay);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                dia.setText(selectedDate);
            }
        });

        newFragment.show(this.getSupportFragmentManager(), "datePicker");
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    public void modificarCita(View v){
        if(!dia.getText().toString().isEmpty()&&!email.getText().toString().isEmpty()&&!usuario.getText().toString().isEmpty()){
            CitasPeluqueria cita = new CitasPeluqueria(dia.getText().toString(),servicio.getSelectedItem()+"",hora.getSelectedItem()+"",false,(Map) Map.ofEntries( new AbstractMap.SimpleEntry<String,String>("usuario",email.getText().toString()),new AbstractMap.SimpleEntry<String,String>("nombre",usuario.getText().toString())));
            db.modificarCita(usuarioPeluqeria,cita,Id);
            /*dbF.collection("usuario/"+email.getText().toString()+"/citausuario/").whereEqualTo("peluqueria",Id).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @RequiresApi(api = Build.VERSION_CODES.R)
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        CitasUsuario citasUsuario = documentSnapshot.toObject(CitasUsuario.class);
                        if(cita.getDia().equals(citasUsuario.getDia())&&cita.getHora().equals(citasUsuario.getDia())){
                            CitasUsuario cita = new CitasUsuario(usuarioPeluqeria,dia.getText().toString(), servicio.getSelectedItem()+"", hora.getSelectedItem()+"", false);
                            db.modificarCitaUsuario(email.getText().toString(), cita, documentSnapshot.getId(),usuario.getText().toString());
                        }

                    }
                }
            });*/

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