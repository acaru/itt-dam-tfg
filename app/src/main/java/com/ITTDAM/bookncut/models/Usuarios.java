package com.ITTDAM.bookncut.models;

import java.util.List;
//modelo para obtener los datos de usuario
public class Usuarios {
    private String nombre;
    private String apellidos;
    private String email;
    private Integer telefono;
    private String tipo;

    public Usuarios(String nombre, String apellidos, String email, Integer telefono,String tipo) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.telefono = telefono;
        this.tipo = tipo;
    }

    public Usuarios() {
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }


    public String getEmail() {
        return email;
    }

    public Integer getTelefono() {
        return telefono;
    }

    public String getTipo() {
        return tipo;
    }
}
