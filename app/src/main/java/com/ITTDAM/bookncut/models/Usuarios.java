package com.ITTDAM.bookncut.models;

import java.util.List;

public class Usuarios {
    private String Nombre;
    private String Apellidos;
    private String Mail;
    private Integer Telefono;
    private String TipoUsuario;

    public Usuarios(String nombre, String apellidos, String mail, Integer telefono,String tipo) {
        Nombre = nombre;
        Apellidos = apellidos;
        Mail = mail;
        Telefono = telefono;
        TipoUsuario = tipo;
    }

    public String getNombre() {
        return Nombre;
    }

    public String getApellidos() {
        return Apellidos;
    }


    public String getMail() {
        return Mail;
    }

    public Integer getTelefono() {
        return Telefono;
    }

    public String getTipoUsuario() {
        return TipoUsuario;
    }
}
