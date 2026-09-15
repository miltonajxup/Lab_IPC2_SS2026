/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto1ss2026.Modelos.DataBase;

/**
 *
 * @author milton
 */
public class RutaDB {
    
    private final int id;
    private final int distanciaAproximada;
    private final double precioBoleto;
    private final String sucursalOrigen;
    private final String sucursalDestino;
    private final boolean rutaHabilitada;
    private String nombreSucOrigen;
    private String nombreCiudadOrigen;
    private String nombreSucDestino;
    private String nombreCiudadDestino;

    public RutaDB(int id, int distanciaAproximada, double precioBoleto, String sucursalOrigen, String sucursalDestino, boolean rutaHabilitada) {
        this.id = id;
        this.distanciaAproximada = distanciaAproximada;
        this.precioBoleto = precioBoleto;
        this.sucursalOrigen = sucursalOrigen;
        this.sucursalDestino = sucursalDestino;
        this.rutaHabilitada = rutaHabilitada;
    }

    public RutaDB(int id, int distanciaAproximada, double precioBoleto, String sucursalOrigen, String sucursalDestino, boolean rutaHabilitada, String nombreSucOrigen, String nombreCiudadOrigen, String nombreSucDestino, String nombreCiudadDestino) {
        this.id = id;
        this.distanciaAproximada = distanciaAproximada;
        this.precioBoleto = precioBoleto;
        this.sucursalOrigen = sucursalOrigen;
        this.sucursalDestino = sucursalDestino;
        this.rutaHabilitada = rutaHabilitada;
        this.nombreSucOrigen = nombreSucOrigen;
        this.nombreCiudadOrigen = nombreCiudadOrigen;
        this.nombreSucDestino = nombreSucDestino;
        this.nombreCiudadDestino = nombreCiudadDestino;
    }

    public int getId() {
        return id;
    }

    public int getDistanciaAproximada() {
        return distanciaAproximada;
    }

    public double getPrecioBoleto() {
        return precioBoleto;
    }

    public String getSucursalOrigen() {
        return sucursalOrigen;
    }

    public String getSucursalDestino() {
        return sucursalDestino;
    }

    public boolean isRutaHabilitada() {
        return rutaHabilitada;
    }

    public String getNombreSucOrigen() {
        return nombreSucOrigen;
    }

    public String getNombreCiudadOrigen() {
        return nombreCiudadOrigen;
    }

    public String getNombreSucDestino() {
        return nombreSucDestino;
    }

    public String getNombreCiudadDestino() {
        return nombreCiudadDestino;
    }

}
