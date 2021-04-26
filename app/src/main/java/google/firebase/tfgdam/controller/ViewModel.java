package google.firebase.tfgdam.controller;

import java.util.ArrayList;

import google.firebase.tfgdam.model.Peluqueria;
import google.firebase.tfgdam.model.Peluquero;
import google.firebase.tfgdam.model.Servicio;

public class ViewModel {

    public static ArrayList<Peluqueria> getPeluquerias(){
        ArrayList<Peluqueria> lista = new ArrayList<>();
        for (int i = 0; i<30;i++){
            Peluqueria p = new Peluqueria(i, "Peluqueria " + i, "Calle "+i +"Numero" + i+1);
            lista.add(p);
        }
        return lista;
    }

    public static ArrayList<Servicio> getServicios(){
        ArrayList<Servicio> lista = new ArrayList<>();
        for (int i = 0; i<30;i++){
            Servicio p = new Servicio(i, "Servicio " + i, i*10.5f);
            lista.add(p);
        }
        return lista;
    }

    public static ArrayList<Peluquero> getPeluqueros(){
        ArrayList<Peluquero> peluqueros = new ArrayList<>();
        for (int i=0; i<5;i++){
            Peluquero p = new Peluquero(i, i, "Peluquero "+1, "wouewhfgowiejfwoiurghf");
            peluqueros.add(p);
        }
        return peluqueros;
    }



}
