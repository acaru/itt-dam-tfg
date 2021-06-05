package com.ITTDAM.bookncut.models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
//Modelo para las peluquerias
public class Peluqueria {

    private String nombre;
    private String ubicacion;
    private String propietario;
    private Map<String,Object> horario;
    public String Id;

    public Peluqueria(String nombre, String ubicacion, String propietario, Map<String,Object> horario) {
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.propietario = propietario;
        this.horario = horario;
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



    public Map<String,Object> getHorario() {
        return horario;
    }
}
