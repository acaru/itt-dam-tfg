package com.ITTDAM.bookncut.adminPeluqueria.citas;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ITTDAM.bookncut.Adapters.DatePickerFragment;
import com.ITTDAM.bookncut.R;
import com.ITTDAM.bookncut.database.Database;
import com.ITTDAM.bookncut.models.CitasPeluqueria;
import com.ITTDAM.bookncut.models.CitasUsuario;
import com.ITTDAM.bookncut.models.Peluqueria;
import com.ITTDAM.bookncut.models.Servicios;
import com.ITTDAM.bookncut.models.Usuarios;
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

public class NewCitaPeluqueriaActivity extends AppCompatActivity {

    //declara las variables
    private static final String TAG = "NEW CITA PELUQUERIA";
    private EditText dia,nombre;
    private Spinner hora;
    private Spinner servicio;
    private Spinner usuario;
    private Database db;
    private FirebaseFirestore dbF = FirebaseFirestore.getInstance();
    private String usuarioEmail;
    private String usuarioNombres;
    private String usuarioPeluqeria;
    private Peluqueria Peluqueria;
    private CheckBox tieneUsuario;
    private TextView txtUsuario;
    private ImageButton btnVolver;


    //oncreateview se ejecuta cuando se crea el elemento
    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_cita_peluqueria);
        db= new Database(this); //declara la base de datos

        //obtiene los extras enviados por el intent del login
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            usuarioEmail = extras.getString("email");
            usuarioNombres = extras.getString("nombre");
            usuarioPeluqeria = extras.getString("peluqueria");
        }
        //Busca si la peluqueria existe
        dbF.document("peluqueria/"+usuarioPeluqeria).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Peluqueria = documentSnapshot.toObject(com.ITTDAM.bookncut.models.Peluqueria.class);
                Peluqueria.Id=documentSnapshot.getId();
            }
        });
        //declara los valores del formulario a utilizar
        dia = findViewById(R.id.txtVDiaCitaPeluqueria);
        dia.setOnClickListener(this::chooseDate);
        hora = findViewById(R.id.spnHoraCitaPeluqueria);
        servicio = findViewById(R.id.spnServicioCitasPeluqueria);
        nombre = findViewById(R.id.txtNombreUsuarioCitaPeluqueria);
        usuario= findViewById(R.id.spnEmailCitaPeluqueria);
        tieneUsuario=findViewById(R.id.ckbUsuario);
        txtUsuario=findViewById(R.id.txtUsuarioNewCitaPeluqueria);
        btnVolver=findViewById(R.id.btnVolver);
        //on click para volver atras
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //si el checkbox de usuario cambia para mostrarlo o no
        tieneUsuario.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    txtUsuario.setVisibility(View.VISIBLE);
                    usuario.setVisibility(View.VISIBLE);
                }else{
                    txtUsuario.setVisibility(View.GONE);
                    usuario.setVisibility(View.GONE);
                }
            }
        });

        //obntiene datos de la base de datos
        getData();


    }

    //obtiene datos de la base de datos
    private void getData(){
        //obtiene los datos de los usuarios para el spinner usuarios
        List<String> usuarios = new ArrayList<>();
        usuarios.add("Selecciona un usuario");
        dbF.collection("usuario/").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots){
                    Usuarios usuario = documentSnapshot.toObject(Usuarios.class);
                    if(usuario.getTipo().equals("Cliente"))
                    usuarios.add(usuario.getEmail());

                }
                usuario.setAdapter(new ArrayAdapter<String>(NewCitaPeluqueriaActivity.this, android.R.layout.simple_list_item_1,usuarios));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(NewCitaPeluqueriaActivity.this,"Error al obtener datos de firestore", Toast.LENGTH_SHORT).show();
                Log.e(TAG,"Error",e);
            }
        });

        //obtnenmos los datos de los servicios
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

    //cuando eligen la fecha
    public void chooseDate(View view){
        //crea un fragmente DatePickerFragment
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 porque enero es 0
                //obtiene la fecha selccionada
                final String selectedDate = day + "/" + (month+1) + "/" + year;
                //se encarga en cambiar las horas dependiendo del dia seleccionado
                try {
                    //le da formato a la fecha seleccionada
                    SimpleDateFormat format1=new SimpleDateFormat("dd/MM/yyyy");
                    Date dt1= null;

                    dt1 = format1.parse(selectedDate);

                    //obtiene el dia de la semana
                    DateFormat format2=new SimpleDateFormat("EEEE");
                    String finalDay=format2.format(dt1);
                    //reincia el array de las horas
                    List<String> horasDeArray=null;
                    String[] horas=null;
                    //switch encargado de obtener la fecha de la peluqueria apartir del dia seleccionado
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
                        case "miércoles":
                            horas = String.valueOf(Peluqueria.getHorario().get("miercoles")).split(",");
                            break;
                        case "jueves":
                            horas = String.valueOf(Peluqueria.getHorario().get("jueves")).split(",");
                            break;
                        case "viernes":
                            horas = String.valueOf(Peluqueria.getHorario().get("viernes")).split(",");
                            break;
                        case "sábado":
                            horas= String.valueOf(Peluqueria.getHorario().get("sabado")).split(",");

                            break;
                        default:
                            horas= new String[]{"Selecciona un dia"};
                            Toast.makeText(NewCitaPeluqueriaActivity.this,"Selecciona un dia que este abierto", Toast.LENGTH_SHORT).show();


                    }

                    horasDeArray.add("Selecciona una hora");
                    //obtiene la fecha actual para revisar si el dia seleccionado es igual a la fecha actual
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("d/M/yyyy");
                    LocalDateTime now = LocalDateTime.now();
                    //si el dia seleccionado es igual que la fecha actual toma la hora actual y solo muestra las horas dispobibles apartir de esa hora
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
                    //se asignan las horas al spinner
                    hora.setAdapter(new ArrayAdapter<String>(NewCitaPeluqueriaActivity.this, R.layout.spinner_item,finalHorasDeArray));

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
        if(!dia.getText().toString().isEmpty()&&hora.getSelectedItem()!="Selecciona la hora"&&servicio.getSelectedItem()!="Selecciona un servicio"){
            //crea la cita
            CitasPeluqueria cita;

            //Validaciones para crear la cita de la peluqueria
            //si el nombre no esta vacio y el checkbox no esta seleccionado
            if(!nombre.toString().isEmpty()&&!tieneUsuario.isChecked()){//crea una cita solo para la peluqueria
                cita = new CitasPeluqueria(dia.getText().toString(),servicio.getSelectedItem()+"",hora.getSelectedItem()+"",false,(Map) Map.ofEntries( new AbstractMap.SimpleEntry<String,String>("usuario",""),new AbstractMap.SimpleEntry<String,String>("nombre",nombre.getText().toString())));
                db.crearCita(usuarioPeluqeria,cita);
                Toast.makeText(this,"Se creo la cita", Toast.LENGTH_SHORT).show();
                finish();
            }//si el nombre esta vacio y el checkbox no esta seleccionado
            else if(nombre.toString().isEmpty()&&!tieneUsuario.isChecked()){//pide que escirban un nombre
                Toast.makeText(this,"Escriba un nombre", Toast.LENGTH_SHORT).show();
            }//si el nombre no esta vacio y el checkbox esta seleccionado
            else if(!nombre.toString().isEmpty()&&tieneUsuario.isChecked()){//crea la cita para peluqueria y para usuario
                cita = new CitasPeluqueria(dia.getText().toString(),servicio.getSelectedItem()+"",hora.getSelectedItem()+"",false,(Map) Map.ofEntries( new AbstractMap.SimpleEntry<String,String>("usuario",usuario.getSelectedItem()+""),new AbstractMap.SimpleEntry<String,String>("nombre",nombre.getText().toString())));
                db.crearCita(usuarioPeluqeria,cita);
                CitasUsuario citU = new CitasUsuario(usuarioPeluqeria,dia.getText().toString(),hora.getSelectedItem()+"",servicio.getSelectedItem()+"",false);
                db.crearCitaUsuario(usuario.getSelectedItem()+"",citU);
                Toast.makeText(this,"Se creo la cita", Toast.LENGTH_SHORT).show();
                finish();
            }
            else//sino
                Toast.makeText(this,"Escriba por lo menos nombre", Toast.LENGTH_SHORT).show();
        }
        else //sino
        {
            Toast.makeText(this,"Datos incompletos", Toast.LENGTH_SHORT).show();
            Log.d(TAG,"Error Datos incompletos");
        }
    }
}