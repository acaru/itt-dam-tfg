package com.ITTDAM.bookncut;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ITTDAM.bookncut.database.Database;
import com.ITTDAM.bookncut.models.Usuarios;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterActivity extends AppCompatActivity {

    //declaracion de inputs
    EditText Nombre;
    EditText Apellidos;
    EditText Email;
    EditText Password;
    EditText Telefono;
    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Nombre = findViewById(R.id.txtNombreUsuario);
        Apellidos = findViewById(R.id.txtApellidosUsuario);
        Email = findViewById(R.id.txtEmailUsuario);
        Password = findViewById(R.id.txtPasswordUsuario);
        Telefono = findViewById(R.id.txtTelefonoUsuario);
        db = new Database(this);

    }

    public void registerApp(View view){

        if(!Nombre.getText().toString().isEmpty()&&!Apellidos.getText().toString().isEmpty()&&!Email.getText().toString().isEmpty()&&!Password.getText().toString().isEmpty()&&!Telefono.getText().toString().isEmpty()){
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(Email.getText().toString(),Password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Usuarios usuario = new Usuarios(Nombre.getText().toString(),Apellidos.getText().toString(),Email.getText().toString(),Integer.parseInt(Telefono.getText().toString()),"Cliente");
                        db.crearUsuario(usuario);
                        Toast.makeText(RegisterActivity.this,"Se ha registrado el usuario",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(RegisterActivity.this,"Se ha producido un error al insertar el usuario",Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("ERROR",e.getMessage());
                }
            });

        }
        else {
            Toast.makeText(RegisterActivity.this,"Rellena todos los campos",Toast.LENGTH_SHORT).show();
        }
    }
}