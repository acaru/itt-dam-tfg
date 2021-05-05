package com.ITTDAM.bookncut.database;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.ITTDAM.bookncut.models.CitasPeluqueria;
import com.ITTDAM.bookncut.models.Peluqueria;
import com.ITTDAM.bookncut.models.Productos;
import com.ITTDAM.bookncut.models.Servicios;
import com.ITTDAM.bookncut.models.Trabajadores;
import com.ITTDAM.bookncut.models.Usuarios;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ITTDAM.bookncut.models.prueba;

import java.util.HashMap;
import java.util.Map;

public class Database {
    public static final String NOMBRE_KEY = "nombre";
    public static final String APELLIDOS_KEY = "apellidos";
    public static final String UBICACION_KEY = "ubicacion";
    public static final String PROPIETARIO_KEY = "propietario";
    public static final String PUESTOS_KEY = "puestos";
    public static final String HORARIO_KEY = "horario";
    public static final String DURACION_KEY = "duracion";
    public static final String PRECIO_KEY = "precio";
    public static final String DATABASE = "DATABASE";
    public static final String TIPO = "tipo";
    public static final String JORNADA_KEY = "jornada";
    public static final String DIA_KEY = "dia";
    public static final String HORA_KEY = "hora";
    public static final String FINALIZADO_KEY = "finalizado";
    public static final String USUARIO_KEY = "usuario";
    public static final String EMAIL_KEY = "email";
    public static final String TELEFONO_KEY = "telefono";
    private Context contexto;

    public Database(Context contexto) {
        this.contexto = contexto;
    }

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

public void createDocumentPrueba1(String titulo,prueba datos){
    Map<String,Object> prueba = new HashMap<>();
    prueba.put("campo1",datos.getCampo1());
    prueba.put("campo2",datos.getCampo2());
    db.document("peluqueria/"+titulo).set(prueba);
}
public void crearPeluqueria(String titulo, Peluqueria datos){
    Map<String,Object> peluqueria = new HashMap<>();
    peluqueria.put(NOMBRE_KEY,datos.getNombre());
    peluqueria.put(UBICACION_KEY,datos.getUbicacion());
    peluqueria.put(PROPIETARIO_KEY,datos.getUbicacion());
    peluqueria.put(PUESTOS_KEY,datos.getUbicacion());
    peluqueria.put(HORARIO_KEY,datos.getUbicacion());
    db.document("peluqueria/"+titulo).set(peluqueria).addOnSuccessListener(new OnSuccessListener<Void>() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onSuccess(Void aVoid) {
            Log.d(DATABASE,"Se creo la peluqueria");
            Toast.makeText(contexto,"Se creo la peluqueria "+titulo, Toast.LENGTH_SHORT).show();

        }
    }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            Toast.makeText(contexto,"Error al insertar la peluqueria", Toast.LENGTH_SHORT).show();
            Log.d(DATABASE,"Error "+e.getMessage());
        }
    });
}
public void crearServicio(String peluqueria, Servicios datos){
    Map<String,Object> servicios = new HashMap<>();
    servicios.put(NOMBRE_KEY,datos.getNombre());
    servicios.put(DURACION_KEY,datos.getDuracion());
    servicios.put(PRECIO_KEY,datos.getDuracion());
    db.document("peluqueria/"+peluqueria+"/servicios/").set(servicios).addOnSuccessListener(new OnSuccessListener<Void>() {
        @Override
        public void onSuccess(Void aVoid) {
            Log.d(DATABASE,"Se creo el servicio para la peluqueria "+peluqueria);
            Toast.makeText(contexto,"Se creo el servicio para la peluqueria "+peluqueria, Toast.LENGTH_SHORT).show();
        }
    }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            Log.d(DATABASE,"Error "+e.getMessage());
            Toast.makeText(contexto,"Error al insertar el serivicio", Toast.LENGTH_SHORT).show();
        }
    });
}

public void crearProducto(String peluqueria, Productos datos){
    Map<String,Object> productos = new HashMap<>();
    productos.put(NOMBRE_KEY,datos.getNombre());
    productos.put(PRECIO_KEY,datos.getPrecio());
    productos.put(TIPO,datos.getTipoProducto());
    db.document("peluqueria/"+peluqueria+"/productos/").set(productos).addOnSuccessListener(new OnSuccessListener<Void>() {
        @Override
        public void onSuccess(Void aVoid) {
            Log.d(DATABASE,"Se creo el producto para la peluqueria "+peluqueria);
            Toast.makeText(contexto,"Se creo el producto para la peluqueria "+peluqueria, Toast.LENGTH_SHORT).show();
        }
    }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            Log.d(DATABASE,"Error "+e.getMessage());
            Toast.makeText(contexto,"Error al insertar el producto", Toast.LENGTH_SHORT).show();
        }
    });
}

public void crearTrabajador(String peluqueria, Trabajadores datos){
    Map<String, Object> trabajadores = new HashMap<>();
    trabajadores.put(NOMBRE_KEY, datos.getNombre());
    trabajadores.put(APELLIDOS_KEY, datos.getApellidos());
    trabajadores.put(JORNADA_KEY, datos.getJordana());
    trabajadores.put(PUESTOS_KEY, datos.getPuesto());
    db.document("peluqueria/" + peluqueria + "/trabajadores/").set(trabajadores).addOnSuccessListener(new OnSuccessListener<Void>() {
        @Override
        public void onSuccess(Void aVoid) {
            Log.d(DATABASE, "Se crearon los trabajadores para la peluqueria " + peluqueria);
            Toast.makeText(contexto,"Se creo el trabajador para la peluqueria "+peluqueria, Toast.LENGTH_SHORT).show();
        }
    }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            Log.d(DATABASE, "Error " + e.getMessage());
            Toast.makeText(contexto,"Error al insertar el producto", Toast.LENGTH_SHORT).show();
        }
    });
}

public void crearCita(String peluqueria, CitasPeluqueria datos){
    Map<String, Object> cita = new HashMap<>();
    cita.put(DIA_KEY, datos.getDia());
    cita.put(HORA_KEY, datos.getHora());
    cita.put(FINALIZADO_KEY, datos.getFinalizado());
    cita.put(USUARIO_KEY, datos.getUsuario());
    db.document("peluqueria/" + peluqueria + "/cita/").set(cita).addOnSuccessListener(new OnSuccessListener<Void>() {
        @Override
        public void onSuccess(Void aVoid) {
            Log.d(DATABASE, "Se crearon la cita para la peluqueria " + peluqueria);
            Toast.makeText(contexto,"Se creo el cita para la peluqueria "+peluqueria, Toast.LENGTH_SHORT).show();
        }
    }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            Log.d(DATABASE, "Error " + e.getMessage());
            Toast.makeText(contexto,"Error al insertar la cita", Toast.LENGTH_SHORT).show();
        }
    });
}

public void crearUsuario(Usuarios datos){
    Map<String, Object> usuario = new HashMap<>();
    usuario.put(NOMBRE_KEY, datos.getNombre());
    usuario.put(APELLIDOS_KEY, datos.getApellidos());
    usuario.put(EMAIL_KEY, datos.getMail());
    usuario.put(TELEFONO_KEY, datos.getTelefono());
    usuario.put(TIPO,datos.getTipoUsuario());
    db.document("usuario/"+datos.getMail()).set(usuario).addOnSuccessListener(new OnSuccessListener<Void>() {
        @Override
        public void onSuccess(Void aVoid) {
            Log.d(DATABASE, "Se creo el usuario ");
            Toast.makeText(contexto,"Se creo el usuario", Toast.LENGTH_SHORT).show();
        }
    }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            Log.d(DATABASE, "Error " + e.getMessage());
            Toast.makeText(contexto,"Error al insertar usuario", Toast.LENGTH_SHORT).show();
        }
    });
}
}
