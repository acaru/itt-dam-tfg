package com.ITTDAM.bookncut.models;

public class Servicios {
    private String Nombre;
    private Double Precio;
    private Double Duracion;

    public Servicios(String nombre, Double precio, Double duracion) {
        Nombre = nombre;
        Precio = precio;
        Duracion = duracion;
    }

    public Servicios() {
    }

    public String getNombre() {
        return Nombre;
    }

    public Double getPrecio() {
        return Precio;
    }

    public Double getDuracion() {
        return Duracion;
    }
}
