package com.ITTDAM.bookncut.database;

import com.google.firebase.firestore.FirebaseFirestore;
import com.ITTDAM.bookncut.models.prueba;

import java.util.HashMap;
import java.util.Map;

public class Database {
private FirebaseFirestore db = FirebaseFirestore.getInstance();

public void createDocumentPrueba1(String titulo,prueba datos){
    Map<String,Object> prueba = new HashMap<>();
    prueba.put("campo1",datos.getCampo1());
    prueba.put("campo2",datos.getCampo2());
    db.document("peluqueria/"+titulo).set(prueba);
}
public void crearPeluqueria(String titulo,prueba datos){
    Map<String,Object> prueba = new HashMap<>();
    prueba.put("campo1",datos.getCampo1());
    prueba.put("campo2",datos.getCampo2());
    db.document("peluqueria/"+titulo).set(prueba);
}
}
