package google.firebase.tfgdam.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import google.firebase.tfgdam.R;

public class LoginActivity extends AppCompatActivity {

    //Components
    private EditText etUserLogin, etPasswordLogin;
    private Button btLogin, btRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initComponents();
        initEvents();
    }

    private void initComponents() {
        etUserLogin = findViewById(R.id.etUserLogin);
        etPasswordLogin = findViewById(R.id.etPasswordLogin);
        btLogin = findViewById(R.id.btLogin);
        btRegister = findViewById(R.id.btRegisterLogin);
    }

    private void initEvents() {
        //btLogin
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(LoginActivity.this, RecyclerPeluqueriasActivity.class));
            }
        });

        //btRegister
        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}