package com.ITTDAM.bookncut.models;
//modelo utilizado para obtener valir de los servicios
public class Servicios {
    private String nombre;
    private Double precio;
    public String Id;

    public Servicios(String nombre, Double precio) {
        this.nombre = nombre;
        this.precio = precio;
    }

    public Servicios() {
    }

    public String getNombre() {
        return nombre;
    }

    public Double getPrecio() {
        return precio;
    }

}
