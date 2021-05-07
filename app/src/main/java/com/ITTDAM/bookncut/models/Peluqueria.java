package com.ITTDAM.bookncut.models;

import java.util.List;
import java.util.Map;

public class Peluqueria {

    private String nombre;
    private String ubicacion;
    private String propietario;
    private String[] horario;
    public String Id;

    public Peluqueria(String nombre, String ubicacion, String propietario, String[] dias) {
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.propietario = propietario;
        this.horario = dias;
    }

    public Peluqueria() {
    }

    public String getNombre() {
        return nombre;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public String getPropietario() {
        return propietario;
    }



    public String[] getDias() {
        return horario;
    }
}
