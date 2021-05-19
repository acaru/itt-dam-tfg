package com.ITTDAM.bookncut.models;

public class Servicios {
    private String nombre;
    private Double precio;
    private int duracion;
    public String Id;

    public Servicios(String nombre, Double precio, int duracion) {
        this.nombre = nombre;
        this.precio = precio;
        this.duracion = duracion;
    }

    public Servicios() {
    }

    public String getNombre() {
        return nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public int getDuracion() {
        return duracion;
    }
}
