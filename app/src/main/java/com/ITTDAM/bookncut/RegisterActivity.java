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
    private EditText Nombre;
    private EditText Apellidos;
    private EditText Email;
    private EditText Password;
    private EditText Telefono;
    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
//declara los valores del formulario a utilizar
        Nombre = findViewById(R.id.txtNombreUsuario);
        Apellidos = findViewById(R.id.txtApellidosUsuario);
        Email = findViewById(R.id.txtEmailUsuario);
        Password = findViewById(R.id.txtPasswordUsuario);
        Telefono = findViewById(R.id.txtTelefonoUsuario);
        db = new Database(this);

    }

    public void registerApp(View view){
//verifica que no hay ningun dato sin elegir del cliente
        if(!Nombre.getText().toString().isEmpty()&&!Apellidos.getText().toString().isEmpty()&&!Email.getText().toString().isEmpty()&&!Password.getText().toString().isEmpty()&&!Telefono.getText().toString().isEmpty()){
            //crea el usuario en firebase auth
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(Email.getText().toString(),Password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){//si lo puede crear
                        //crea el usuario
                        Usuarios usuario = new Usuarios(Nombre.getText().toString(),Apellidos.getText().toString(),Email.getText().toString(),Integer.parseInt(Telefono.getText().toString()),"Cliente");

                        db.crearUsuario(usuario);
                        Toast.makeText(RegisterActivity.this,"Se ha registrado el usuario",Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent();
                        intent.putExtra("nombre",Nombre.getText().toString()+" "+Apellidos.getText().toString());
                        setResult(1,intent);//se pone el resultado porque asi se envia directamente al menu de cliente
                        finish();//finishing activity
                    }
                    else{
                        Log.e("REGISTER ERROR", "onComplete: ", task.getException());
                        Toast.makeText(RegisterActivity.this,"Revisa los campos email y contrase??a (min. 6 caracteres)",Toast.LENGTH_SHORT).show();
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

    public void cancelar(View v){
        finish();
    }
}