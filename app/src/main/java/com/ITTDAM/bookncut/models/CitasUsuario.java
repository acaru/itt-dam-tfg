package com.ITTDAM.bookncut.models;

public class CitasUsuario {
    private String peluqueria;
    private String dia;
    private String hora;
    private Boolean finalizado;
    private String servicio;

    public CitasUsuario(String peluqueria, String dia, String hora, String servicio, Boolean finalizado) {
        this.peluqueria = peluqueria;
        this.dia = dia;
        this.hora = hora;
        this.servicio=servicio;
        this.finalizado = finalizado;
    }

    public CitasUsuario() {
    }

    public String getPeluqueria() {
        return peluqueria;
    }

    public String getDia() {
        return dia;
    }

    public String getHora() {
        return hora;
    }

    public String getServicio() {
        return servicio;
    }

    public Boolean getFinalizado() {
        return finalizado;
    }
}
