package com.ITTDAM.bookncut.database;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.ITTDAM.bookncut.models.CitasPeluqueria;
import com.ITTDAM.bookncut.models.CitasUsuario;
import com.ITTDAM.bookncut.models.Peluqueria;
import com.ITTDAM.bookncut.models.Productos;
import com.ITTDAM.bookncut.models.Servicios;
import com.ITTDAM.bookncut.models.Usuarios;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Database {

    //Se crean constantes estáticas para que se pueda utilizar en todo el código
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
    public static final String PELUQUERIA_KEY = "peluqueria";
    public static final String SERVICIO_KEY = "servicio";
    private Context contexto;

    //Constructor de Database al que le pasamos el contexto
    public Database(Context contexto) {
        this.contexto = contexto;
    }

    //Crear instancia para la conexión a Firestore
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    //añadir/modificar datos
    public void crearPeluqueria(String nombrePe, Peluqueria datos){
        Map<String,Object> peluqueria = new HashMap<>();//constructor del documento de firestore (key, value)
        peluqueria.put(NOMBRE_KEY,datos.getNombre()); //obtienes los valores de la peluqueria que le pasamos por parametro y lo añade al constructor Firestore
        peluqueria.put(UBICACION_KEY,datos.getUbicacion());
        peluqueria.put(PROPIETARIO_KEY,datos.getUbicacion());
        peluqueria.put(HORARIO_KEY,datos.getUbicacion());

        //Obtiene la referencia del documento de FB de peluqueria y añade el documento que hemos creado con el método set (que añade y sobreescribe, si no existe lo crea, si existe sobreescribe)
        //en este proceso de añadir se utilizan dos métodos para comprobar si ha tenido éxito o no la inserción
        db.document("peluqueria/"+nombrePe).set(peluqueria).addOnSuccessListener(new OnSuccessListener<Void>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(DATABASE,"Se creo la peluqueria");
                Toast.makeText(contexto,"Se creo la peluqueria "+nombrePe, Toast.LENGTH_SHORT).show();

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

        //Otra forma de añadir documentos es con el método add desde una colección, Firestore añade el documento con un ID automático, asi se asegura el insertado
        db.collection("peluqueria/"+peluqueria+"/servicio/").add(servicios).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
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
        productos.put(TIPO,datos.getTipo());
        db.collection("peluqueria/"+peluqueria+"/producto/").add(productos).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
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


    public void crearCita(String peluqueria, CitasPeluqueria datos){
        Map<String, Object> cita = new HashMap<>();
        cita.put(DIA_KEY, datos.getDia());
        cita.put(HORA_KEY, datos.getHora());
        cita.put(FINALIZADO_KEY, datos.getFinalizado());
        cita.put(SERVICIO_KEY,datos.getServicio());
        cita.put(USUARIO_KEY, datos.getUsuario());
        db.collection("peluqueria/" + peluqueria + "/cita/").add(cita).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d(DATABASE, "Se creo la cita para la peluqueria " + peluqueria);
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
        usuario.put(TIPO,datos.getTipo());
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

    public void crearCitaUsuario(String usuario, CitasUsuario datos) {
        Map<String, Object> cita = new HashMap<>();
        cita.put(PELUQUERIA_KEY,datos.getPeluqueria());
        cita.put(DIA_KEY, datos.getDia());
        cita.put(HORA_KEY, datos.getHora());
        cita.put(FINALIZADO_KEY, datos.getFinalizado());
        cita.put(SERVICIO_KEY, datos.getServicio());
        db.collection("usuario/" + usuario + "/citasusuario/").add(cita).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d(DATABASE, "Se crearon la cita para el usuario " + usuario);
                Toast.makeText(contexto, "Se creo el cita ", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Log.d(DATABASE, "Error " + e.getMessage());
                Toast.makeText(contexto,"Error al insertar la cita", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //modificar datos
    //se utiliza SET para que modifique solo los campos modificados y los no modificados los deja igual, en cambio UPDATE deberiamos de tener en cuenta que es lo que se modificó
    public void modificarServicio(String peluqueria, Servicios datos,String id){
        Map<String,Object> servicios = new HashMap<>();
        servicios.put(NOMBRE_KEY,datos.getNombre());
        servicios.put(DURACION_KEY,datos.getDuracion());
        servicios.put(PRECIO_KEY,datos.getDuracion());
        db.document("peluqueria/"+peluqueria+"/servicio/"+id).set(servicios).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void avoid) {
                Log.d(DATABASE,"Se modifico el servicio para la peluqueria "+peluqueria);
                Toast.makeText(contexto,"Se modifico el servicio para la peluqueria "+peluqueria, Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(DATABASE,"Error "+e.getMessage());
                Toast.makeText(contexto,"Error al modificar el servicio", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void modificarCita(String peluqueria, CitasPeluqueria datos,String id){

        Map<String, Object> cita = new HashMap<>();
        cita.put(DIA_KEY, datos.getDia());
        cita.put(HORA_KEY, datos.getHora());
        cita.put(FINALIZADO_KEY, datos.getFinalizado());
        cita.put(SERVICIO_KEY,datos.getServicio());
        cita.put(USUARIO_KEY, datos.getUsuario());
        db.document("peluqueria/" + peluqueria + "/cita/"+id).set(cita).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void avoid) {
                Log.d(DATABASE, "Se modifico la cita para la peluqueria " + peluqueria);
                Toast.makeText(contexto,"Se modifico el cita para la peluqueria "+peluqueria, Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(DATABASE, "Error " + e.getMessage());
                Toast.makeText(contexto,"Error al modificar la cita", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    public void modificarCitaUsuario(String usuario, CitasUsuario datos, String id){

        Map<String, Object> cita = new HashMap<>();
        cita.put(SERVICIO_KEY,datos.getServicio());
        cita.put(PELUQUERIA_KEY,datos.getPeluqueria());
        cita.put(DIA_KEY, datos.getDia());
        cita.put(HORA_KEY, datos.getHora());
        cita.put(FINALIZADO_KEY, datos.getFinalizado());
        db.document("usuario/" + usuario + "/citasusuario/"+id).set(cita).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void avoid) {
                Log.d(DATABASE, "Se modifico la cita para el usuario " + usuario);
                Toast.makeText(contexto,"Se modifico la cita para el usuario "+usuario, Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(DATABASE, "Error " + e.getMessage());
                Toast.makeText(contexto,"Error al modificar la cita", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void modificarProducto(String peluqueria, Productos datos,String id){
        Map<String,Object> productos = new HashMap<>();
        productos.put(NOMBRE_KEY,datos.getNombre());
        productos.put(PRECIO_KEY,datos.getPrecio());
        productos.put(TIPO,datos.getTipo());
        db.document("peluqueria/"+peluqueria+"/producto/"+id).set(productos).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void avoid) {
                Log.d(DATABASE,"Se modifico el producto para la peluqueria "+peluqueria);
                Toast.makeText(contexto,"Se modifico el producto para la peluqueria "+peluqueria, Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(DATABASE,"Error "+e.getMessage());
                Toast.makeText(contexto,"Error al modificar el producto", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
