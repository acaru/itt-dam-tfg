package com.ITTDAM.bookncut.models;

import java.util.List;
import java.util.Map;

public class EncargosAdmin {
    private String usuarioEmail;
    private List<String> productos;
    private Double preciototal;
    private boolean pagado;
    public String Id;

    public EncargosAdmin(String usuarioEmail, List<String>productos, Double preciototal,boolean pagado) {
        this.usuarioEmail = usuarioEmail;
        this.productos=productos;
        this.preciototal = preciototal;
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

    public Double getPrecioTotal() {
        return preciototal;
    }

    public boolean isPagado() {
        return pagado;
    }
}
