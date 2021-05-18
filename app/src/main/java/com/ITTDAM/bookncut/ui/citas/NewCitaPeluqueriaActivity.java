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
import com.ITTDAM.bookncut.EditCitaUsuarioActivity;
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

public class NewCitaPeluqueriaActivity extends AppCompatActivity {

    private static final String TAG = "NEW CITA PELUQUERIA";
    EditText dia,nombre,usuario;
    Spinner hora;
    Spinner servicio;
    Database db;
    FirebaseFirestore dbF = FirebaseFirestore.getInstance();
    String usuarioEmail;
    String usuarioNombres;
    String usuarioPeluqeria;
    Peluqueria Peluqueria;

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
        dbF.document("peluqueria/"+usuarioPeluqeria).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Peluqueria = documentSnapshot.toObject(com.ITTDAM.bookncut.models.Peluqueria.class);
                Peluqueria.Id=documentSnapshot.getId();
            }
        });
        dia = findViewById(R.id.txtVDiaCitaPeluqueria);
        dia.setOnClickListener(this::chooseDate);
        hora = findViewById(R.id.spnHoraCitaPeluqueria);
        servicio = findViewById(R.id.spnServicioCitasPeluqueria);
        nombre = findViewById(R.id.txtNombreUsuarioCitaPeluqueria);
        usuario= findViewById(R.id.txtEmailCitaPeluqueria);

        //Pintamos cada item desde una lista de strings
        hora.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,new ArrayList<>(List.of("Selecciona la hora","9:00","10:00","11:00","12:00","13:00","15:00","16:00","17:00","18:00","19:00"))));

        //Sacamos y recorremos los servicios de la peluqueria desde Firestore y los pintamos en cada item
        List<String> servicios = new ArrayList<>();
        servicios.add("Selecciona un servicio");
        dbF.collection("peluqueria/"+usuarioPeluqeria+"/servicio").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                    Servicios servicio = documentSnapshot.toObject(Servicios.class);
                    servicios.add(servicio.getNombre());
                }
                servicio.setAdapter(new ArrayAdapter<String>(NewCitaPeluqueriaActivity.this, android.R.layout.simple_list_item_1, servicios));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(NewCitaPeluqueriaActivity.this,"Error al obtener datos de firestore", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(NewCitaPeluqueriaActivity.this,"Selecciona un dia que este abierto", Toast.LENGTH_SHORT).show();


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
                    hora.setAdapter(new ArrayAdapter<String>(NewCitaPeluqueriaActivity.this, android.R.layout.simple_list_item_1,finalHorasDeArray));

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
    public void createCita(View view){
        if(!dia.getText().toString().isEmpty()&&hora.getSelectedItem()!="Selecciona la hora"&&servicio.getSelectedItem()!="Selecciona un servicio"){

            CitasPeluqueria cita;

            //Validaciones para crear la cita de la peluqueria
            if(!nombre.toString().isEmpty()&&usuario.getText().toString().isEmpty()){
                cita = new CitasPeluqueria(dia.getText().toString(),servicio.getSelectedItem()+"",hora.getSelectedItem()+"",false,(Map) Map.ofEntries( new AbstractMap.SimpleEntry<String,String>("usuario",""),new AbstractMap.SimpleEntry<String,String>("nombre",nombre.getText().toString())));
                db.crearCita(usuarioPeluqeria,cita);
                Toast.makeText(this,"Se creo la cita", Toast.LENGTH_SHORT).show();
                finish();
            }
            else if(nombre.toString().isEmpty()&&!usuario.getText().toString().isEmpty()){
                Toast.makeText(this,"Escriba un nombre", Toast.LENGTH_SHORT).show();
            }
            else if(!nombre.toString().isEmpty()&&!usuario.getText().toString().isEmpty()){
                cita = new CitasPeluqueria(dia.getText().toString(),servicio.getSelectedItem()+"",hora.getSelectedItem()+"",false,(Map) Map.ofEntries( new AbstractMap.SimpleEntry<String,String>("usuario",usuario.getText().toString()),new AbstractMap.SimpleEntry<String,String>("nombre",nombre.getText().toString())));
                db.crearCita(usuarioPeluqeria,cita);
                CitasUsuario citU = new CitasUsuario(usuarioPeluqeria,dia.getText().toString(),hora.getSelectedItem()+"",servicio.getSelectedItem()+"",false);
                db.crearCitaUsuario(nombre.getText().toString(),citU);
                Toast.makeText(this,"Se creo la cita", Toast.LENGTH_SHORT).show();
                finish();
            }
            else
                Toast.makeText(this,"Escriba por lo menos nombre", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this,"Datos incompletos", Toast.LENGTH_SHORT).show();
            Log.d(TAG,"Error Datos incompletos");
        }
    }
}