package google.firebase.tfgdam.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import google.firebase.tfgdam.R;

public class RegisterEmpresaActivity extends AppCompatActivity {

    //Componentes
    private EditText etHairdressing, etLocation, etBarber, etPassword;
    private Button btRegister, btRegisterClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_empresa);

        initComponents();
        initEvents();
    }

    private void initComponents() {
        etHairdressing = findViewById(R.id.etPeluqueria);
        etLocation = findViewById(R.id.etUbicacion);
        etBarber = findViewById(R.id.etPeluquero);
        etPassword = findViewById(R.id.etContraseña);

        btRegister = findViewById(R.id.btCliente);
        btRegisterClient = findViewById(R.id.btRegistrarEmpresaEmpresa);
    }

    private void initEvents() {
        //Register
        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        //Register Client
        btRegisterClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(google.firebase.tfgdam.activities.RegisterEmpresaActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}