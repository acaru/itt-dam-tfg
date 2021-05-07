package com.ITTDAM.bookncut;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ITTDAM.bookncut.database.Database;

public class MainActivity extends AppCompatActivity {

    //public static final String VALOR_1 = "valor1";

    //private DocumentReference mDocRef = FirebaseFirestore.getInstance().document("prueba/documento");
    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //EditText campo1 = (EditText) findViewById(R.id.txtcampo1);
        //EditText campo2 = (EditText) findViewById(R.id.txtcampo2);

        //prueba1 = new prueba(campo1.getText().toString(),campo2.getText().toString());

      //db = new Database();

       // Map<String, Object> data = new HashMap<>();
       // data.put(VALOR_1,"hola");
       // mDocRef.set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
       //     @Override
       //     public void onSuccess(Void aVoid) {
       //         Log.d("hola","que tal?");
       //     }
       // }).addOnFailureListener(new OnFailureListener() {
       //     @Override
       //     public void onFailure(@NonNull Exception e) {
       //         Log.d("jaja","si");
       //     }
       // });

    }public void registerRedirect(View view){
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }

}