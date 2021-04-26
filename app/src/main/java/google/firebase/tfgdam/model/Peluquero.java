package google.firebase.tfgdam.model;

import java.io.Serializable;

public class Peluquero implements Serializable {

    private int id;
    private int idPeluqueria;
    private String nombrePeluquero;

    public Peluquero() {
        this(-1,  -1, null, null);
    }

    public Peluquero(int id, int idPeluqueria, String nombrePeluquero, String ubicacion) {
        this.id = id;
        this.idPeluqueria = idPeluqueria;
        this.nombrePeluquero = nombrePeluquero;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPeluqueria() {
        return idPeluqueria;
    }

    public void setIdPeluqueria(int idPeluqueria) {
        this.idPeluqueria = idPeluqueria;
    }

    public String getNombrePeluquero() {
        return nombrePeluquero;
    }

    public void setNombrePeluquero(String nombrePeluquero) {
        this.nombrePeluquero = nombrePeluquero;
    }

    @Override
    public String toString() {
        return "Peluquero{" +
                "id=" + id +
                ", nombrePeluquero='" + nombrePeluquero + '\'' +
                '}';
    }
}
