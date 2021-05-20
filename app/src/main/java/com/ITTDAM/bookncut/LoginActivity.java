package com.ITTDAM.bookncut;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ITTDAM.bookncut.models.Usuarios;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

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

    //Metodo para redirigir activity login a registro
    public void registerRedirect(View view){
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==1)
        {
            Intent intent = new Intent(this,MainClienteActivity.class);
            intent.putExtra("nombre",data.getStringExtra("nombre"));
            intent.putExtra("email",data.getStringExtra("email"));
            startActivity(intent);
        }
    }


    public void Login(View view){
        if(!Email.getText().toString().isEmpty()&&!Password.getText().toString().isEmpty()) {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(Email.getText().toString(),Password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        if(Email.getText().toString().equals("root@root.com")){
                            Intent intent=new Intent(LoginActivity.this,MainRootActivity.class);
                            startActivity(intent);
                        }
                        db.document("usuario/"+Email.getText().toString()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if(documentSnapshot.exists()){

                                    Usuarios usuario =documentSnapshot.toObject(Usuarios.class);
                                    Log.d("LOGIN","Usuario Existe");
                                    Toast.makeText(LoginActivity.this,"Bienvenido",Toast.LENGTH_SHORT).show();

                                    //ACL (Access Control List) se usa para verificar el tipo de usuario en Firestore, peluqueria (administrador) o usuario (cliente)
                                    if(usuario.getTipo().equals("Cliente")){
                                        Intent intent = new Intent(LoginActivity.this,MainClienteActivity.class);
                                        intent.putExtra("email",Email.getText().toString());
                                        intent.putExtra("nombre",usuario.getNombre()+" "+usuario.getApellidos());
                                        startActivity(intent);
                                    }
                                    else if(usuario.getTipo().equals("Administrador")){
                                        Intent intent = new Intent(LoginActivity.this,MainAdminActivity.class);
                                        intent.putExtra("email",Email.getText().toString());
                                        intent.putExtra("nombre",usuario.getNombre()+" "+usuario.getApellidos());
                                        startActivity(intent);
                                    }
                                    Email.setText("");
                                    Password.setText("");
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