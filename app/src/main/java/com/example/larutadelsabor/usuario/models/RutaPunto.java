package com.example.larutadelsabor.usuario.models;

public class RutaPunto {
    private double latitud;
    private double longitud;
    private int orden;

    public RutaPunto() {}

    public RutaPunto(double latitud, double longitud, int orden) {
        this.latitud = latitud;
        this.longitud = longitud;
        this.orden = orden;
    }

    public double getLatitud() { return latitud; }
    public double getLongitud() { return longitud; }
}