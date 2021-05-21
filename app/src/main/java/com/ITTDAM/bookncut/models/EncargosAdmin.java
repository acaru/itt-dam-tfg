package com.ITTDAM.bookncut.models;

import java.util.List;
import java.util.Map;

public class EncargosAdmin {
    private String usuarioEmail;
    private List<String> productos;
    private Double precio;
    private boolean pagado;
    public String Id;

    public EncargosAdmin(String usuarioEmail, List<String>productos, Double precio,boolean pagado) {
        this.usuarioEmail = usuarioEmail;
        this.productos=productos;
        this.precio = precio;
        this.pagado= pagado;
    }

    public EncargosAdmin() {
    }

    public String getUsuarioEmail() {
        return usuarioEmail;
    }

    public List<String> getProductos() {
        return productos;
    }

    public Double getPrecio() {
        return precio;
    }

    public boolean isPagado() {
        return pagado;
    }
}
