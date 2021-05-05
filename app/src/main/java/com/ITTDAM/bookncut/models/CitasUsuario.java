package com.ITTDAM.bookncut.models;

public class CitasUsuario {
    private String Peluqueria;
    private String Puesto;
    private String Dia;
    private String Hora;
    private Boolean Finalizado;

    public CitasUsuario(String peluqueria, String puesto, String dia, String hora, Boolean finalizado) {
        Peluqueria = peluqueria;
        Puesto = puesto;
        Dia = dia;
        Hora = hora;
        Finalizado = finalizado;
    }

    public String getPeluqueria() {
        return Peluqueria;
    }

    public String getPuesto() {
        return Puesto;
    }

    public String getDia() {
        return Dia;
    }

    public String getHora() {
        return Hora;
    }

    public Boolean getFinalizado() {
        return Finalizado;
    }
}
