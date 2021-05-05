package com.ITTDAM.bookncut.models;

import java.util.List;
import java.util.Map;

public class Peluqueria {
    private String Nombre;
    private String Ubicacion;
    private String Propietario;
    private Map<String, String > Puestos;
    private String[] Dias;

    public Peluqueria(String nombre, String ubicacion, String propietario, Map<String, String> puestos, String[] dias) {
        Nombre = nombre;
        Ubicacion = ubicacion;
        Propietario = propietario;
        Puestos = puestos;
        Dias = dias;
    }

    public String getNombre() {
        return Nombre;
    }

    public String getUbicacion() {
        return Ubicacion;
    }

    public String getPropietario() {
        return Propietario;
    }

    public Map<String, String> getPuestos() {
        return Puestos;
    }

    public String[] getDias() {
        return Dias;
    }
}
