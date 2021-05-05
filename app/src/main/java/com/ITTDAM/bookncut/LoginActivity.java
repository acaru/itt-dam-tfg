package com.ITTDAM.bookncut;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {

    //declaracion de los inputs
    EditText Email;
    EditText Password;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Email = findViewById(R.id.txtEmailLogin);
        Password = findViewById(R.id.txtPasswordLogin);

    }

    public void registerRedirect(View view){
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }

    public void Login(View view){
        if(!Email.getText().toString().isEmpty()&&!Password.getText().toString().isEmpty()) {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(Email.getText().toString(),Password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        db.document("usuario/"+Email.getText().toString()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if(documentSnapshot.exists()){
                                    Log.d("LOGIN","Usuario Existe");
                                    Toast.makeText(LoginActivity.this,"Bienvenido",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                    intent.putExtra("email",Email.getText().toString());
                                    intent.putExtra("tipo",documentSnapshot.getString("tipo"));
                                    startActivity(intent);
                                }
                                else{
                                    Log.d("LOGIN","Usuario Inexistente");
                                    Toast.makeText(LoginActivity.this,"Error al iniciar sesion, usuario no existe",Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                Toast.makeText(LoginActivity.this,"Error al iniciar sesion",Toast.LENGTH_SHORT).show();
                                Log.d("LOGIN ERROR",e.getMessage());
                            }
                        });
                    }
                    else{
                        Toast.makeText(LoginActivity.this,"Datos Erroneos",Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(LoginActivity.this,"Datos Erroneos",Toast.LENGTH_SHORT).show();
                    Log.d("LOGIN ERROR",e.getMessage());
                }
            });
        }

    }
}