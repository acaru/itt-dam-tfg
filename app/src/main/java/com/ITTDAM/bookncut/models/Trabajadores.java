package com.ITTDAM.bookncut.models;

public class Trabajadores {
    private String Nombre;
    private String Apellidos;
    private String Jordana;
    private String Puesto;

    public Trabajadores(String nombre, String apellidos, String jordana, String puesto) {
        Nombre = nombre;
        Apellidos = apellidos;
        Jordana = jordana;
        Puesto = puesto;
    }

    public String getNombre() {
        return Nombre;
    }

    public String getApellidos() {
        return Apellidos;
    }

    public String getJordana() {
        return Jordana;
    }

    public String getPuesto() {
        return Puesto;
    }
}
