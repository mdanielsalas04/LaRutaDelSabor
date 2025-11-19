package com.example.larutadelsabor.usuario.models;

public class FeriaActividad {
    private String hora;
    private String descripcion;
    private String lugar;

    public FeriaActividad() {} // Necesario para Firebase

    public FeriaActividad(String hora, String descripcion, String lugar) {
        this.hora = hora;
        this.descripcion = descripcion;
        this.lugar = lugar;
    }

    // Getters y Setters aquí...
    public String getHora() { return hora; }
    public String getDescripcion() { return descripcion; }
    public String getLugar() { return lugar; }
}