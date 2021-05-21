package com.ITTDAM.bookncut.models;

public class EncargosUsuario {
    private String peluqueria;
    private String producto;
    private Integer cantidad;
    private Double precio;
    public String Id;

    public EncargosUsuario(String peluqueria,String producto, Integer cantidad, Double precio) {
        this.peluqueria = peluqueria;
        this.producto=producto;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    public EncargosUsuario() {
    }

    public String getPeluqueria() {
        return peluqueria;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public Double getPrecio() {
        return precio;
    }

    public String getProducto() {
        return producto;
    }
}
