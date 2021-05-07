package com.ITTDAM.bookncut.models;

public class Productos {
    private String Nombre;
    private Double Precio;
    private String TipoProducto;

    public Productos(String nombre, Double precio, String tipoProducto) {
        Nombre = nombre;
        Precio = precio;
        TipoProducto = tipoProducto;
    }

    public Productos() {
    }

    public String getNombre() {
        return Nombre;
    }

    public Double getPrecio() {
        return Precio;
    }

    public String getTipoProducto() {
        return TipoProducto;
    }
}
