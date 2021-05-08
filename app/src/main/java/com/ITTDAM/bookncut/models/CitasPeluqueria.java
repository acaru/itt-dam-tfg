package com.ITTDAM.bookncut.models;

import java.util.Map;

public class CitasPeluqueria {
    private String dia;
    private String servicio;
    private String hora;
    private Boolean finalizado;
    private Map<String,Object> usuario;
    public String Id;

    public CitasPeluqueria(String dia, String servicio, String hora, Boolean finalizado, Map<String, Object> usuario) {
        this.dia = dia;
        this.servicio = servicio;
        this.hora = hora;
        this.finalizado = finalizado;
        this.usuario = usuario;
    }

    public CitasPeluqueria() {
    }

    public String getDia() {
        return this.dia;
    }

    public String getServicio() {
        return servicio;
    }

    public String getHora() {
        return hora;
    }

    public Boolean getFinalizado() {
        return finalizado;
    }

    public Map<String, Object> getUsuario() {
        return usuario;
    }
}
