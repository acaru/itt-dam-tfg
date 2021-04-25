package google.firebase.tfgdam.model;

import java.io.Serializable;

public class Peluquero implements Serializable {

    private int id;
    private String nombrePeluquero;
    private String ubicacion;

    public Peluquero() {
        this(-1, null, null);
    }

    public Peluquero(int id, String nombrePeluquero, String ubicacion) {
        this.id = id;
        this.nombrePeluquero = nombrePeluquero;
        this.ubicacion = ubicacion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombrePeluquero() {
        return nombrePeluquero;
    }

    public void setNombrePeluquero(String nombrePeluquero) {
        this.nombrePeluquero = nombrePeluquero;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    @Override
    public String toString() {
        return "Peluquero{" +
                "id=" + id +
                ", nombrePeluquero='" + nombrePeluquero + '\'' +
                ", ubicacion='" + ubicacion + '\'' +
                '}';
    }
}
