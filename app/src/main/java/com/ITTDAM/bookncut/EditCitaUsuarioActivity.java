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
import com.ITTDAM.bookncut.ui.citas.NewCitaPeluqueriaActivity;
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

public class EditCitaUsuarioActivity extends AppCompatActivity {

    private static final String TAG = "EDIT CITA USUARIO";
    private EditText dia;
    private Spinner hora;
    private Spinner servicio;
    private Database db;
    private FirebaseFirestore dbF = FirebaseFirestore.getInstance();
    private String usuarioEmail;
    private String usuarioNombres;
    private String Id;
    Peluqueria Peluqueria=new Peluqueria();
    private String diainicial="",horainicial="";

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
            Peluqueria.Id = extras.getString("peluqueria");
            Id = extras.getString("id");
        }
        dbF.document("peluqueria/"+Peluqueria.Id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Peluqueria = documentSnapshot.toObject(com.ITTDAM.bookncut.models.Peluqueria.class);
                Peluqueria.Id=documentSnapshot.getId();
            }
        });
        dia = findViewById(R.id.txtVDiaEditCitaUsuario);
        dia.setOnClickListener(this::chooseDate);
        hora = findViewById(R.id.spnHoraEditCitaUsuario);
        servicio = findViewById(R.id.spnServicioEditCitasUsuario);

        //Pintamos cada item desde una lista de strings
        hora.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,new ArrayList<>(List.of("Selecciona el dia"))));

        //Sacamos y recorremos los servicios de la peluqueria desde Firestore y los pintamos en cada item
        List<String> servicios = new ArrayList<>();
        dbF.collection("peluqueria/"+Peluqueria.Id+"/servicio").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
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
        //busca la cita del usuario y obntiene los datos
        dbF.document("usuario/"+usuarioEmail+"/citasusuario/"+Id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    CitasUsuario cita = documentSnapshot.toObject(CitasUsuario.class);
                    setHora(cita.getDia());
                    diainicial=cita.getDia();
                    horainicial=cita.getHora();
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

    void setHora(String selectedDate){
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
                    horas= String.valueOf(Peluqueria.getHorario().get("lunes")).split(",");

                    break;
                case "Tuesday":
                    horas = String.valueOf(Peluqueria.getHorario().get("martes")).split(",");
                    break;
                case "Wednesday":
                    horas = String.valueOf(Peluqueria.getHorario().get("miercoles")).split(",");
                    break;
                case "Thursday":
                    horas = String.valueOf(Peluqueria.getHorario().get("jueves")).split(",");
                    break;
                case "Friday":
                    horas = String.valueOf(Peluqueria.getHorario().get("viernes")).split(",");
                    break;
                case "Saturday":
                    horas = String.valueOf(Peluqueria.getHorario().get("sabado")).split(",");
                    break;
                case "lunes":
                    horas = String.valueOf(Peluqueria.getHorario().get("lunes")).split(",");
                    break;
                case "martes":
                    horas = String.valueOf(Peluqueria.getHorario().get("martes")).split(",");
                    break;
                case "miercoles":
                    horas = String.valueOf(Peluqueria.getHorario().get("miercoles")).split(",");
                    break;
                case "jueves":
                    horas = String.valueOf(Peluqueria.getHorario().get("jueves")).split(",");
                    break;
                case "viernes":
                    horas = String.valueOf(Peluqueria.getHorario().get("viernes")).split(",");
                    break;
                case "Sabado":
                    horas= String.valueOf(Peluqueria.getHorario().get("sabado")).split(",");

                    break;
                default:
                    horas= new String[]{"Selecciona un dia"};
                    Toast.makeText(EditCitaUsuarioActivity.this,"Selecciona un dia que este abierto", Toast.LENGTH_SHORT).show();


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
            hora.setAdapter(new ArrayAdapter<String>(EditCitaUsuarioActivity.this, android.R.layout.simple_list_item_1,finalHorasDeArray));

            Log.d(TAG, "onDateSet: "+finalDay);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void chooseDate(View view){
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because January is zero
                // +1 because January is zero
                final String selectedDate = day + "/" + (month+1) + "/" + year;
                setHora(selectedDate);
                dia.setText(selectedDate);
            }
        });

        newFragment.show(this.getSupportFragmentManager(), "datePicker");
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    public void modificarCita(View v){

        if(!dia.getText().toString().isEmpty()){
           dbF.collection("peluqueria/"+Peluqueria.Id+"/cita/").whereEqualTo("dia",diainicial).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @RequiresApi(api = Build.VERSION_CODES.R)
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        CitasPeluqueria citasPeluqueria = documentSnapshot.toObject(CitasPeluqueria.class);
                        if(citasPeluqueria.getHora().equals(horainicial)){
                            CitasPeluqueria citasPeluqueria1 = new CitasPeluqueria(dia.getText().toString(),servicio.getSelectedItem()+"",hora.getSelectedItem()+"",false,(Map) Map.ofEntries( new AbstractMap.SimpleEntry<String,String>("usuario",usuarioEmail),new AbstractMap.SimpleEntry<String,String>("nombre",usuarioNombres)));
                            db.modificarCita(Peluqueria.Id,citasPeluqueria1,documentSnapshot.getId());
                        }

                    }
                }
            });
            CitasUsuario cita = new CitasUsuario(Peluqueria.Id,dia.getText().toString(),hora.getSelectedItem()+"",servicio.getSelectedItem()+"",false);
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

        dbF.collection("peluqueria/"+Peluqueria.Id+"/cita/").whereEqualTo("dia",diainicial).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.R)
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    CitasPeluqueria citasPeluqueria = documentSnapshot.toObject(CitasPeluqueria.class);
                    if(citasPeluqueria.getHora().equals(horainicial)){
                        dbF.document("peluqueria/"+Peluqueria.Id+"/cita/"+documentSnapshot.getId()).update("finalizado",true);
                    }

                }
            }
        });

        dbF.document("usuario/"+usuarioEmail+"/citasusuario/"+Id).update("finalizado",true);
        Toast.makeText(EditCitaUsuarioActivity.this,"Se finalizo la cita", Toast.LENGTH_SHORT).show();
        finish();

    }

    public void borrarCita(View v){
        dbF.collection("peluqueria/"+Peluqueria.Id+"/cita/").whereEqualTo("dia",diainicial).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.R)
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    CitasPeluqueria citasPeluqueria = documentSnapshot.toObject(CitasPeluqueria.class);
                    if(citasPeluqueria.getHora().equals(horainicial)){
                        dbF.document("peluqueria/"+Peluqueria.Id+"/cita/"+documentSnapshot.getId()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d(TAG, "onSuccess: Se borro la cita de la peluqueria");
                            }
                        });
                    }

                }
            }
        });
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