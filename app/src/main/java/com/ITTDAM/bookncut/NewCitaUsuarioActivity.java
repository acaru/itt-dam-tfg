package com.ITTDAM.bookncut;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ITTDAM.bookncut.Adapters.DatePickerFragment;
import com.ITTDAM.bookncut.database.Database;
import com.ITTDAM.bookncut.models.CitasPeluqueria;
import com.ITTDAM.bookncut.models.CitasUsuario;
import com.ITTDAM.bookncut.models.Peluqueria;
import com.ITTDAM.bookncut.models.Servicios;
import com.ITTDAM.bookncut.ui.citas.NewCitaPeluqueriaActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
    Peluqueria Peluqueria;
    String usuarioEmail;
    String usuarioNombres;
    List<String> horasDeArray=null;
    List<String> finalHorasDeArray;
    Boolean disponible=false;
    List<String> nombresServicios;
    List<Servicios> servicios;

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

        dia.setOnClickListener(this::chooseDate);
        hora = findViewById(R.id.spnHoraCitaUsuario);
        servicio = findViewById(R.id.spnServicioCitasUsuario);
        hora.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,new ArrayList<>(List.of("Selecciona el dia"))));

         nombresServicios= new ArrayList<>();
        servicios = new ArrayList<>();
        nombresServicios.add("Selecciona un servicio");
        dbF.collection("peluqueria/").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    Peluqueria = documentSnapshot.toObject(com.ITTDAM.bookncut.models.Peluqueria.class);
                    Peluqueria.Id=documentSnapshot.getId();
                    dbF.collection("peluqueria/"+Peluqueria.Id+"/servicio/").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            Log.d(TAG, "onSuccess: Fuerda del for"+Peluqueria.Id);
                            for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                                Log.d(TAG, "onSuccess: Dentro");
                                Servicios servicio = documentSnapshot.toObject(Servicios.class);
                                Log.d(TAG, "onSuccess: "+ documentSnapshot.toObject(Servicios.class).getNombre());
                                servicios.add(servicio);
                                nombresServicios.add(servicio.getNombre());
                            }
                            servicio.setAdapter(new ArrayAdapter<String>(NewCitaUsuarioActivity.this, android.R.layout.simple_list_item_1,nombresServicios));
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

    //Método para poder escoger fecha en la activity de crear nueva cita usuario
    public void chooseDate(View view){
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker,int year, int month, int day) {
                // +1 because January is zero
                final String selectedDate = day + "/" + (month+1) + "/" + year;
                try {
                SimpleDateFormat format1=new SimpleDateFormat("dd/MM/yyyy");
                Date dt1= null;

                    dt1 = format1.parse(selectedDate);

                DateFormat format2=new SimpleDateFormat("EEEE");
                String finalDay=format2.format(dt1);

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

                        case "lunes":
                            horas = Peluqueria.getHorario().get("lunes").split(",");
                            break;
                        case "martes":
                            horas = Peluqueria.getHorario().get("martes").split(",");
                            break;
                        case "miercoles":
                            horas = Peluqueria.getHorario().get("miercoles").split(",");
                            break;
                        case "jueves":
                            horas = Peluqueria.getHorario().get("jueves").split(",");
                            break;
                        case "viernes":
                            horas = Peluqueria.getHorario().get("viernes").split(",");
                            break;
                        case "Sabado":
                            horas= Peluqueria.getHorario().get("sabado").split(",");

                            break;
                        default:
                            horas= new String[]{"Selecciona un dia"};
                            Toast.makeText(NewCitaUsuarioActivity.this,"Selecciona un dia que este abierto", Toast.LENGTH_SHORT).show();


                }
                    Log.d(TAG, "onDateSet: "+finalDay);
                    horasDeArray=  new ArrayList(Arrays.asList(horas));
                    finalHorasDeArray = horasDeArray;
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
                hora.setAdapter(new ArrayAdapter<String>(NewCitaUsuarioActivity.this, android.R.layout.simple_list_item_1,finalHorasDeArray));

                    Log.d(TAG, "onDateSet: "+finalDay);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                dia.setText(selectedDate);
                /*hora.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        int index =nombresServicios.indexOf(servicio.getSelectedItem());
                        if(servicio.getSelectedItem().equals(nombresServicios.get(index))){
                            int duracion =servicios.get(index-1).getDuracion();
                            if(finalHorasDeArray!=null){
                                if(hora.getSelectedItem().equals(finalHorasDeArray.get(position))){
                                    int response= 1;
                                    for(int k= position;k<finalHorasDeArray.size()-1;k++){
                                        Log.d(TAG, "onItemSelected: "+finalHorasDeArray.get(k+1));
                                        Log.d(TAG, "onItemSelected: "+horasDeArray.get(horasDeArray.indexOf(hora.getSelectedItem())+response));
                                        if(finalHorasDeArray.get(k+1).equals(horasDeArray.get(horasDeArray.indexOf(hora.getSelectedItem())+1))){
                                            response++;
                                        }
                                    }
                                    if(duracion<=response){
                                        disponible=true;

                                    }
                                    Log.d(TAG, "onItemSelected: "+disponible+duracion+" "+response);
                                }
                            }
                            else{
                                Toast.makeText(NewCitaUsuarioActivity.this,"Selecciona un dia", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });*/
            }
        });

        newFragment.show(this.getSupportFragmentManager(), "datePicker");
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    public void createCita(View view){
        if(!dia.getText().toString().isEmpty()&&!servicio.getSelectedItem().equals("Selecciona el servicio")&&!hora.getSelectedItem().equals("Selecciona la hora")){
            CitasUsuario cita = new CitasUsuario(Peluqueria.Id,dia.getText().toString(),hora.getSelectedItem()+"",servicio.getSelectedItem()+"",false);
                CitasPeluqueria citaPeluqueria = new CitasPeluqueria(dia.getText().toString(),servicio.getSelectedItem()+"",hora.getSelectedItem()+"",false,(Map) Map.ofEntries( new AbstractMap.SimpleEntry<String,String>("usuario",usuarioEmail),new AbstractMap.SimpleEntry<String,String>("nombre",usuarioNombres)));
                db.crearCitaUsuario(this.usuarioEmail,cita);
                db.crearCita(Peluqueria.Id,citaPeluqueria);
                Toast.makeText(this,"Se creo la cita", Toast.LENGTH_SHORT).show();
                finish();
        }
        else{
            Toast.makeText(this,"Datos incompletos", Toast.LENGTH_SHORT).show();
            Log.d(TAG,"Error Datos incompletos");
        }
    }


}