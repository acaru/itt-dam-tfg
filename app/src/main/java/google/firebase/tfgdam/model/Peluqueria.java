package google.firebase.tfgdam.model;

import java.io.Serializable;

public class Peluqueria implements Serializable {

    private int id;
    private String nombreTienda;
    private String ubicacion;

    public Peluqueria() {
        this(-1, null, null);
    }

    public Peluqueria(int id, String nombreTienda, String ubicacion) {
        this.id = id;
        this.nombreTienda = nombreTienda;
        this.ubicacion = ubicacion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreTienda() {
        return nombreTienda;
    }

    public void setNombreTienda(String nombreTienda) {
        this.nombreTienda = nombreTienda;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    @Override
    public String toString() {
        return "Peluqueria{" +
                "id=" + id +
                ", nombreTienda='" + nombreTienda + '\'' +
                ", ubicacion='" + ubicacion + '\'' +
                '}';
    }
}
