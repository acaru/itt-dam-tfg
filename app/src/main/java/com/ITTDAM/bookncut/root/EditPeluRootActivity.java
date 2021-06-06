package com.ITTDAM.bookncut.root;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.ITTDAM.bookncut.R;
import com.ITTDAM.bookncut.database.Database;
import com.ITTDAM.bookncut.models.Peluqueria;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class EditPeluRootActivity extends AppCompatActivity {

    //declara variables
    private String Id;
    private String Propietario;
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

    //oncreateview se ejecuta cuando se crea el elemento
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pelu_root);
        //obtiene los extras enviados por el intent del login
        Bundle extras = getIntent().getExtras();
        if(extras!=null)
            Id=extras.getString("id");
        else
            finish();

        //declara los valores del formulario a utilizar
        txtNombrePeluqueria=findViewById(R.id.txtVNombreEditPeluqueria);
        txtUbicacionPeluqueria=findViewById(R.id.txtVDireccionEditPeluqueria);
        cbLunes =findViewById(R.id.cbLunesEdit);
        cbMartes=findViewById(R.id.cbMartesEdit);
        cbMiercoles=findViewById(R.id.cbMiercolesEdit);
        cbJueves=findViewById(R.id.cbJuevesEdit);
        cbViernes=findViewById(R.id.cbViernesEdit);
        cbSabado=findViewById(R.id.cbSabadoEdit);

        //obtiene los valores de la pelqueria

        dbF.document("peluqueria/"+Id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
               //obtiene el valor de la peluqueria
                Peluqueria peluqueria = documentSnapshot.toObject(Peluqueria.class);
                Propietario=peluqueria.getPropietario();
                //asgina los valores al formulario
                txtNombrePeluqueria.setText(peluqueria.getNombre());
                txtUbicacionPeluqueria.setText(peluqueria.getUbicacion());
                for(String key : peluqueria.getHorario().keySet()){
                    switch (key){
                        case "lunes":
                            cbLunes.setChecked(true);
                            break;
                        case "martes":
                            cbMartes.setChecked(true);
                            break;
                        case "miercoles":
                            cbMiercoles.setChecked(true);
                            break;
                        case "jueves":
                            cbJueves.setChecked(true);
                            break;
                        case "viernes":
                            cbViernes.setChecked(true);
                            break;
                        case "Sabado":
                            cbSabado.setChecked(true);
                            break;
                    }
                }
            }
        });
    }

    //funcion para modificar la peluqueria
    public void modificarPeluqueria(View v){
        //verifica que no hay ningun dato sin elegir
        if(!txtNombrePeluqueria.getText().toString().isEmpty()&&!txtUbicacionPeluqueria.getText().toString().isEmpty()&&(cbLunes.isChecked()||cbMartes.isChecked()||cbMiercoles.isChecked()||cbJueves.isChecked()||cbViernes.isChecked()||cbSabado.isChecked())){
           //string con las horas de los horarios posibles
            String horarioSemana="10:00,10:30,11:00,11:30,12:00,12:30,13:00,13:30,16:00,16:30,17:00,17:30,18:00,18:30,19:00,19:30";
            String horarioSabado="10:00,10:30,11:00,11:30,12:00,12:30,13:00,13:30";
            //mapa con los valoreas de los horarios
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
            Peluqueria peluqueria = new Peluqueria(txtNombrePeluqueria.getText().toString(),txtUbicacionPeluqueria.getText().toString(),Propietario,horario);
            db.modificarPeluqueria(Id,peluqueria);//modifica la peluqueria
            finish();
        }
    }
}