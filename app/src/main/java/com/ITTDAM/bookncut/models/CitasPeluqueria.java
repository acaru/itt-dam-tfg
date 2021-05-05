package com.ITTDAM.bookncut.models;

import java.util.Map;

public class CitasPeluqueria {
    private String Dia;
    private String Servicio;
    private String Hora;
    private Boolean Finalizado;
    private Map<String,Object> Usuario;

    public CitasPeluqueria(String dia, String servicio, String hora, Boolean finalizado, Map<String, Object> usuario) {
        Dia = dia;
        Servicio = servicio;
        Hora = hora;
        Finalizado = finalizado;
        Usuario = usuario;
    }

    public String getDia() {
        return Dia;
    }

    public String getServicio() {
        return Servicio;
    }

    public String getHora() {
        return Hora;
    }

    public Boolean getFinalizado() {
        return Finalizado;
    }

    public Map<String, Object> getUsuario() {
        return Usuario;
    }
}
