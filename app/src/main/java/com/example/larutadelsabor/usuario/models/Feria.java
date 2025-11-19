package com.example.larutadelsabor.usuario.models;

import java.util.Date;
import java.util.List;

public class Feria {
    private String id;
    private String titulo;
    private String descripcion;
    private String imagen;
    private double latitud;
    private double longitud;
    private Date fechaInicio;
    private Date fechaFin;
    private List<FeriaActividad> cronograma;

    public Feria() {}

    public Feria(String titulo, String descripcion, double latitud, double longitud, Date fechaInicio, Date fechaFin, List<FeriaActividad> cronograma) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.latitud = latitud;
        this.longitud = longitud;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.cronograma = cronograma;
    }

    // --- Getters y Setters ---
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getImagen() { return imagen; }
    public void setImagen(String imagen) { this.imagen = imagen; }

    public double getLatitud() { return latitud; }
    public void setLatitud(double latitud) { this.latitud = latitud; }

    public double getLongitud() { return longitud; }
    public void setLongitud(double longitud) { this.longitud = longitud; }

    public Date getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(Date fechaInicio) { this.fechaInicio = fechaInicio; }

    public Date getFechaFin() { return fechaFin; }
    public void setFechaFin(Date fechaFin) { this.fechaFin = fechaFin; }

    public List<FeriaActividad> getCronograma() { return cronograma; }
    public void setCronograma(List<FeriaActividad> cronograma) { this.cronograma = cronograma; }
}