package google.firebase.tfgdam.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import google.firebase.tfgdam.R;

public class RegisterActivity extends AppCompatActivity {

    //Components
    private EditText etUserRegister, etEmailRegister, etPasswordRegister,
            etConfirmPassword, etPhoneRegister, etAgeRegsiter;

    private Button btRegisterRegister, btRegisterEnterprise;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initComponents();
        initEvents();
    }

    private void initComponents() {
        etUserRegister = findViewById(R.id.etUserRegister);
        etEmailRegister = findViewById(R.id.etEmailRegister);
        etPasswordRegister = findViewById(R.id.etPasswordRegister);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        etPhoneRegister = findViewById(R.id.etPhoneRegister);
        etAgeRegsiter = findViewById(R.id.etAgeRegister);

        btRegisterRegister = findViewById(R.id.btRegisterRegister);
        btRegisterEnterprise = findViewById(R.id.btRegistrarEnterprise);
    }

    private void initEvents() {
        //Registrar
        btRegisterRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, RegisterEmpresaActivity.class);
                startActivity(intent);
            }
        });

        //Registrar empresa
        btRegisterEnterprise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}