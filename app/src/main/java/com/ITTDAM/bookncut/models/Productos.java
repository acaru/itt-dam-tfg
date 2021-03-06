package com.ITTDAM.bookncut.models;

import java.io.Serializable;
//modelo utilizado para obtener el valor de los productos
public class Productos implements Serializable {
    private String nombre;
    private Double precio;
    private String tipo;
    public String Id;

    public Productos(String nombre, Double precio, String tipo) {
        this.nombre = nombre;
        this.precio = precio;
        this.tipo = tipo;
    }

    public Productos() {
    }

    public String getNombre() {
        return nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public String getTipo() {
        return tipo;
    }
}
