package google.firebase.tfgdam.model;

import java.io.Serializable;

public class Cliente implements Serializable {

    private int id;
    private int edad;
    private String nombre;
    private String email;
    private String contraseña;
    private int telefono;
    private char sexo;

    public Cliente(){
        this(-1, -1, null, null, null, -1, 'z');
    }

    public Cliente(int id, int edad, String nombre, String email, String contraseña, int telefono, char sexo){
        this.id = id;
        this.edad = edad;
        this.nombre = nombre;
        this.contraseña = contraseña;
        this.telefono = telefono;
        this.sexo = sexo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", edad=" + edad +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", contraseña='" + contraseña + '\'' +
                ", telefono=" + telefono +
                ", sexo=" + sexo +
                '}';
    }

}
