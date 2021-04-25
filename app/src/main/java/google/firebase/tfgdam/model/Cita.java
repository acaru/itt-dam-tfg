package google.firebase.tfgdam.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Cita implements Serializable {

    private int id;
    private int idUsuario;
    private int idPeluquero;
    private LocalDateTime fechayHora;
    private int idTratamiento;

    public Cita() {
        this(-1, -1, -1, null, -1);
    }

    public Cita(int id, int idUsuario, int idPeluquero, LocalDateTime fechayHora, int idTratamiento) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.idPeluquero = idPeluquero;
        this.fechayHora = fechayHora;
        this.idTratamiento = idTratamiento;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdPeluquero() {
        return idPeluquero;
    }

    public void setIdPeluquero(int idPeluquero) {
        this.idPeluquero = idPeluquero;
    }

    public LocalDateTime getFechayHora() {
        return fechayHora;
    }

    public void setFechayHora(LocalDateTime fechayHora) {
        this.fechayHora = fechayHora;
    }

    public int getIdTratamiento() {
        return idTratamiento;
    }

    public void setIdTratamiento(int idTratamiento) {
        this.idTratamiento = idTratamiento;
    }

    @Override
    public String toString() {
        return "Cita{" +
                "id=" + id +
                ", idUsuario=" + idUsuario +
                ", idPeluquero=" + idPeluquero +
                ", fechayHora=" + fechayHora +
                ", idTratamiento=" + idTratamiento +
                '}';
    }
}
