package com.example.larutadelsabor.usuario.models; // Ojo con tu package

import java.util.List;

public class Ruta {
    private String id;
    private String titulo;
    private String descripcion;
    private String imagen;
    private String duracion;
    // Usamos la nueva clase RutaPunto que creamos
    private List<RutaPunto> puntos;

    public Ruta() {} // Constructor vacío para Firebase

    public Ruta(String titulo, String descripcion, String duracion, List<RutaPunto> puntos) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.duracion = duracion;
        this.puntos = puntos;
    }

    // --- Getters y Setters (NECESARIOS) ---
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getImagen() { return imagen; }
    public void setImagen(String imagen) { this.imagen = imagen; }

    public String getDuracion() { return duracion; }
    public void setDuracion(String duracion) { this.duracion = duracion; }

    public List<RutaPunto> getPuntos() { return puntos; }
    public void setPuntos(List<RutaPunto> puntos) { this.puntos = puntos; }
}