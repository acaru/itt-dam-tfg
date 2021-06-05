package com.ITTDAM.bookncut;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.ITTDAM.bookncut.database.Database;
import com.ITTDAM.bookncut.models.Peluqueria;
import com.ITTDAM.bookncut.models.Usuarios;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class NewPeluRootActivity extends AppCompatActivity {
    //declara las variables
    public static final String TAG = "MAIN ROOT NEWPELUQUERIA";
    private FirebaseFirestore dbF = FirebaseFirestore.getInstance();
    private Database db = new Database(this);
    private EditText txtNombrePeluqueria;
    private EditText txtUbicacionPeluqueria;
    private CheckBox cbLunes;
    private CheckBox cbMartes;
    private CheckBox cbMiercoles;
    private CheckBox cbJueves;
    private CheckBox cbViernes;
    private CheckBox cbSabado;
    private EditText txtNombrePropietario;
    private EditText txtApellidosPropietario;
    private EditText txtEmailPropietario;
    private EditText txtTelefonoPropietario;
    private EditText txtPasswordPropietario;


    //oncreateview se ejecuta cuando se crea el elemento
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //declara los valores del formulario a utilizar
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_pelu_root);
        txtNombrePeluqueria=findViewById(R.id.txtVNombrePeluqueria);
        txtUbicacionPeluqueria=findViewById(R.id.txtVDireccionPeluqueria);
        cbLunes =findViewById(R.id.cbLunes);
        cbMartes=findViewById(R.id.cbMartes);
        cbMiercoles=findViewById(R.id.cbMiercoles);
        cbJueves=findViewById(R.id.cbJueves);
        cbViernes=findViewById(R.id.cbViernes);
        cbSabado=findViewById(R.id.cbSabado);
        txtNombrePropietario=findViewById(R.id.txtVNombrePropietarioPeluqueria);
        txtApellidosPropietario=findViewById(R.id.txtVApellidosPropietarioPeluqueria);
        txtEmailPropietario=findViewById(R.id.txtVEmailPropietarioPeluqueria);
        txtTelefonoPropietario=findViewById(R.id.txtVTelefonoPropietarioPeluqueria);
        txtPasswordPropietario=findViewById(R.id.txtVContraseñaPeluqueria);


    }
    //funcion para crear la peluqueria
    public void crearPeluqueria(View v){
        //verifica que no hay ningun dato sin elegir de la peluqueria
        if(!txtNombrePeluqueria.getText().toString().isEmpty()&&!txtUbicacionPeluqueria.getText().toString().isEmpty()&&(cbLunes.isChecked()||cbMartes.isChecked()||cbMiercoles.isChecked()||cbJueves.isChecked()||cbViernes.isChecked()||cbSabado.isChecked())){
            //verifica los dias seleccionados para hacer un horario y añadirlo
            String horarioSemana="10:00,10:30,11:00,11:30,12:00,12:30,13:00,13:30,16:00,16:30,17:00,17:30,18:00,18:30,19:00,19:30";
            String horarioSabado="10:00,10:30,11:00,11:30,12:00,12:30,13:00,13:30";
            HashMap<String,Object> horario = new HashMap<>();
            if(cbLunes.isChecked())
                horario.put("lunes",horarioSemana);
            if(cbMartes.isChecked())
                horario.put("martes",horarioSemana);
            if(cbMiercoles.isChecked())
                horario.put("miercoles",horarioSemana);
            if(cbJueves.isChecked())
                horario.put("jueves",horarioSemana);
            if(cbViernes.isChecked())
                horario.put("viernes",horarioSemana);
            if(cbSabado.isChecked())
                horario.put("sabado",horarioSabado);
//verifica que no hay ningun dato sin elegir del propietario
            if(!txtNombrePropietario.getText().toString().isEmpty()&&!txtApellidosPropietario.getText().toString().isEmpty()&&!txtEmailPropietario.getText().toString().isEmpty()&&!txtTelefonoPropietario.getText().toString().isEmpty()&&!txtPasswordPropietario.getText().toString().isEmpty()){
                //crea el usuario en firebase auth
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(txtEmailPropietario.getText().toString(),txtPasswordPropietario.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){//si lo puede crear
                            //crea la peluqueria
                            Peluqueria peluqueria=new Peluqueria(txtNombrePeluqueria.getText().toString(),txtUbicacionPeluqueria.getText().toString(),txtEmailPropietario.getText().toString(),horario);
                            //crea el usuario
                            Usuarios usuario = new Usuarios(txtNombrePropietario.getText().toString(),txtApellidosPropietario.getText().toString(),txtEmailPropietario.getText().toString(),Integer.parseInt(txtTelefonoPropietario.getText().toString()),"Administrador");
                            //con la clase databse crea la pelquueria y usaurio
                            db.crearPeluqueria(txtNombrePeluqueria.getText().toString(),peluqueria);
                            db.crearUsuario(usuario);
                            finish();
                            //finishing activity
                        }
                        else{
                            Log.e("REGISTER ERROR", "onComplete: ", task.getException());
                            Toast.makeText(NewPeluRootActivity.this,"Revisa los campos email y contraseña (min. 6 caracteres)",Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("ERROR",e.getMessage());
                    }
                });

            }
            else{
                Log.d(TAG, "crearPeluqueria: Los datos estan incompletos (propietario)");
                Toast.makeText(this,"Los datos del propietario estan incompletos", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Log.d(TAG, "crearPeluqueria: Los datos estan incompletos");
            Toast.makeText(this,"Los datos de la peluqueria estan incompletos", Toast.LENGTH_SHORT).show();
        }
    }
}