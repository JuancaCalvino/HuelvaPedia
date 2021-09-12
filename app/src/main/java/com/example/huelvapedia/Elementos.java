package com.example.huelvapedia;

public class Elementos { //Clase destinada a almacenar los elementos que componen cada tema
    private String nombre;
    private String foto;
    private String descripcion;
    private String ubicacion1;
    private String ubicacion2;
    private String telefono;
    private Boolean ultimo;
    private String enlace;

    public Elementos() { //Constructor vacio
    }

    public Elementos(String nombre, String foto, String descripcion, String ubicacion1, String ubicacion2, String telefono, Boolean ultimo, String enlace) {
        this.nombre = nombre;
        this.foto = foto;
        this.descripcion = descripcion;
        this.ubicacion1 = ubicacion1;
        this.ubicacion2 = ubicacion2;
        this.telefono = telefono;
        this.ultimo = ultimo;
        this.enlace = enlace;
    }

    //Genero los getters y setter para escribir y leer datos de cada tema

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUbicacion1() {
        return ubicacion1;
    }

    public void setUbicacion1(String ubicacion1) {
        this.ubicacion1 = ubicacion1;
    }

    public String getUbicacion2() {
        return ubicacion2;
    }

    public void setUbicacion2(String ubicacion2) {
        this.ubicacion2 = ubicacion2;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Boolean getUltimo() { return ultimo; }

    public void setUltimo(Boolean ultimo) {
        this.ultimo = ultimo;
    }

    public String getEnlace() {
        return enlace;
    }

    public void setEnlace(String enlace) {
        this.enlace = enlace;
    }
}
