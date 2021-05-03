package com.ITTDAM.bookncut;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.ITTDAM.bookncut.database.Database;
import com.ITTDAM.bookncut.models.prueba;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.core.FirestoreClient;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    //public static final String VALOR_1 = "valor1";

    //private DocumentReference mDocRef = FirebaseFirestore.getInstance().document("prueba/documento");
    Database db;
    prueba prueba1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText campo1 = (EditText) findViewById(R.id.txtcampo1);
        EditText campo2 = (EditText) findViewById(R.id.txtcampo2);

        prueba1 = new prueba(campo1.getText().toString(),campo2.getText().toString());

      db = new Database();

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

    }
    public void click(View v){

        db.createDocumentPrueba1("prueba1",prueba1);
    }
}