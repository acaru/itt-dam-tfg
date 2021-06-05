package com.ITTDAM.bookncut.models;

import java.util.List;
import java.util.Map;
//modelo utilizado para obtener la factura de administrador y de usuario
public class Facturas {
    private String usuario;
    private List<String> productos;
    private List<Integer> cantidades;
    private Double total;
    private String peluqueria;
    public String Id;

    public Facturas(String usuario, List<String>productos,List<Integer> cantidades, Double total,String peluqueria) {
        this.usuario = usuario;
        this.productos=productos;
        this.cantidades=cantidades;
        this.total = total;
        this.peluqueria=peluqueria;
    }

    public Facturas() {
    }

    public String getUsuario() {
        return usuario;
    }

    public List<String> getProductos() {
        return productos;
    }

    public Double getTotal() {
        return total;
    }

    public List<Integer> getCantidades() {
        return cantidades;
    }

    public String getPeluqueria() {
        return peluqueria;
    }
}
