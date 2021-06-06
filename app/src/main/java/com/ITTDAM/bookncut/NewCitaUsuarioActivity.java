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
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.ITTDAM.bookncut.Adapters.DatePickerFragment;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class NewCitaUsuarioActivity extends AppCompatActivity {

    //declara las variables
    private static final String TAG = "NEW CITA USUARIO";
    private EditText dia;
    private Spinner hora;
    private Spinner servicio;
    private Database db;
    private FirebaseFirestore dbF = FirebaseFirestore.getInstance();
    private String usuarioEmail;
    private String usuarioNombres;
    
    private List<String> horasDeArray=  new ArrayList();
    private List<String> finalHorasDeArray;
    private Boolean disponible=false;
    private List<String> nombresServicios;
    private List<Servicios> servicios;
    private String peluqueria;
    private Peluqueria Peluqueria;
    private ImageButton btnVolver;

    //oncreateview se ejecuta cuando se crea el elemento
    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_cita_usuario);
        db = new Database(this);

        //obtiene los extras enviados por el intent del login
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            usuarioEmail = extras.getString("email");
            usuarioNombres = extras.getString("nombre");
            peluqueria=extras.getString("peluqueria");
            // and get whatever type user account id is
        }

        //declara los valores del formulario a utilizar
        dia = findViewById(R.id.txtVDiaCitaUsuario);
        dia.setOnClickListener(this::chooseDate);
        hora = findViewById(R.id.spnHoraCitaUsuario);
        servicio = findViewById(R.id.spnServicioCitasUsuario);
        btnVolver = findViewById(R.id.btnVolver);
        //on click para volver atras
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //añade valor por defecto al spinner hora
        hora.setAdapter(new ArrayAdapter<String>(this, R.layout.spinner_item,new ArrayList<>(List.of("Selecciona el dia"))));
        //crea las lista para los servicios
        nombresServicios= new ArrayList<>();
        servicios = new ArrayList<>();
        nombresServicios.add("Selecciona un servicio");

        //busca en la peluqueria asignada el valor de los servicios
        dbF.document("peluqueria/"+peluqueria).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Peluqueria=documentSnapshot.toObject(com.ITTDAM.bookncut.models.Peluqueria.class);
                Peluqueria.Id=documentSnapshot.getId();
                dbF.collection("peluqueria/"+peluqueria+"/servicio/").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                            Servicios servicio = documentSnapshot.toObject(Servicios.class);
                            servicios.add(servicio);
                            nombresServicios.add(servicio.getNombre());
                        }
                        servicio.setAdapter(new ArrayAdapter<String>(NewCitaUsuarioActivity.this, R.layout.spinner_item,nombresServicios));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Toast.makeText(NewCitaUsuarioActivity.this,"Error al obtener datos de firestore", Toast.LENGTH_SHORT).show();
                        Log.e(TAG,"Error",e);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(NewCitaUsuarioActivity.this, "Error al obtener datos", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    //Método para poder escoger fecha en la activity de crear nueva cita usuario
    public void chooseDate(View view){
        //crea un fragmente DatePickerFragment
            DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onDateSet(DatePicker datePicker,int year, int month, int day) {
                    // +1 porque enero es 0
                    //obtiene la fecha selccionada
                    final String selectedDate = day + "/" + (month+1) + "/" + year;
                    //se encarga en cambiar las horas dependiendo del dia seleccionado
                    try {
                        SimpleDateFormat format1=new SimpleDateFormat("dd/MM/yyyy");
                        Date dt1= null;

                        dt1 = format1.parse(selectedDate);

                        //obtiene el dia de la semana
                        DateFormat format2=new SimpleDateFormat("EEEE");
                        String finalDay=format2.format(dt1);
                        //reincia el array de las horas
                        String[] horas=null;
                        //switch encargado de obtener la fecha de la peluqueria apartir del dia seleccionado
                        switch (finalDay){
                            case "Monday":
                                horas= (Peluqueria.getHorario().get("lunes")+"").split(",");
                                break;
                            case "Tuesday":
                                horas = (Peluqueria.getHorario().get("martes")+"").split(",");
                                break;
                            case "Wednesday":
                                horas = (Peluqueria.getHorario().get("miercoles")+"").split(",");
                                break;
                            case "Thursday":
                                horas = (Peluqueria.getHorario().get("jueves")+"").split(",");
                                break;
                            case "Friday":
                                horas = (Peluqueria.getHorario().get("viernes")+"").split(",");
                                break;
                            case "Saturday":
                                horas = (Peluqueria.getHorario().get("sabado")+"").split(",");
                                break;
                            case "lunes":
                                horas= (Peluqueria.getHorario().get("lunes")+"").split(",");
                                break;
                            case "martes":
                                horas = (Peluqueria.getHorario().get("martes")+"").split(",");
                                break;
                            case "miércoles":
                                horas = (Peluqueria.getHorario().get("miercoles")+"").split(",");
                                break;
                            case "jueves":
                                horas = (Peluqueria.getHorario().get("jueves")+"").split(",");
                                break;
                            case "viernes":
                                horas = (Peluqueria.getHorario().get("viernes")+"").split(",");
                                break;
                            case "sábado":
                                horas = (Peluqueria.getHorario().get("sabado")+"").split(",");
                                break;
                            default:
                                horas= new String[]{"Selecciona un dia"};
                                Toast.makeText(NewCitaUsuarioActivity.this,"Selecciona un dia que este abierto", Toast.LENGTH_SHORT).show();


                        }
                        horasDeArray.add("Selecciona una hora");
                        Log.d(TAG, "onDateSet: "+finalDay);

                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("d/M/yyyy");
                        LocalDateTime now = LocalDateTime.now();
                        //si el dia seleccionado es igual que la fecha actual toma la hora actual y solo muestra las horas dispobibles apartir de esa hora
                        Log.d(TAG, "setHora: "+dtf.format(now));
                        if(selectedDate.equals(dtf.format(now)+"")) {
                            DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("HH:mm:ss");
                            LocalDateTime now2 = LocalDateTime.now();
                            //for que añade las horas
                            for (String hora : horas) {//10:00 11:30 10 11
                                if (Integer.parseInt(hora.split(":")[0]) > Integer.parseInt(dtf2.format(now2).split(":")[0]))
                                    horasDeArray.add(hora);
                            }
                        }
                        else{//si no es el dia actual toma los valores y los añade
                            for (String hora : horas) {//10:00 11:30 10 11
                                horasDeArray.add(hora);
                            }
                        }
                        //se encarga de revisar todas las citas que esten no finalizadas para que no hayan mas de 2 personas en la misma hora y mismo dia

                        finalHorasDeArray = horasDeArray;
                        dbF.collection("peluqueria/"+peluqueria+"/cita").whereEqualTo("dia",selectedDate).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
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
                        });//se asignan las horas al spinner
                        hora.setAdapter(new ArrayAdapter<String>(NewCitaUsuarioActivity.this, R.layout.spinner_item,finalHorasDeArray));

                        Log.d(TAG, "onDateSet: "+finalDay);
                    } catch (Exception e) {
                        e.printStackTrace();//imprime errores
                    }
                    dia.setText(selectedDate);//asigna la fecha seleccinada

                }
            });
            //muestra el fragmnto para selccionar la fecha
            newFragment.show(this.getSupportFragmentManager(), "datePicker");
    }

    //funcion para crear la cita
    @RequiresApi(api = Build.VERSION_CODES.R)
    public void createCita(View view){
        //verifica que no hay ningun dato sin elegir
        if(!dia.getText().toString().isEmpty()&&!servicio.getSelectedItem().equals("Selecciona el servicio")&&!hora.getSelectedItem().equals("Selecciona la hora")){
            CitasUsuario cita = new CitasUsuario(Peluqueria.Id,dia.getText().toString(),hora.getSelectedItem()+"",servicio.getSelectedItem()+"",false);
            //crea la cita
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