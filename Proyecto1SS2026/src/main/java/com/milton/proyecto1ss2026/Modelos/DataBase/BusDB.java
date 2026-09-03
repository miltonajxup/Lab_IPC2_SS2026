/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto1ss2026.Modelos.DataBase;

/**
 *
 * @author milton
 */
public class BusDB {
    
    private final String numeroPlaca;
    private final byte[] foto;
    private final String marca;
    private final String modelo;
    private final String fechaFabricacion;
    private final int capacidadPasajeros;
    private final int kilometraje;
    private final boolean estadoOperativo;
    private final String sucursalBase;
    private final String sucursalActual;

    public BusDB(String numeroPlaca, byte[] foto, String marca, String modelo, String fechaFabricacion, int capacidadPasajeros, int kilometraje, boolean estadoOperativo, String sucursalBase, String sucursalActual) {
        this.numeroPlaca = numeroPlaca;
        this.foto = foto;
        this.marca = marca;
        this.modelo = modelo;
        this.fechaFabricacion = fechaFabricacion;
        this.capacidadPasajeros = capacidadPasajeros;
        this.kilometraje = kilometraje;
        this.estadoOperativo = estadoOperativo;
        this.sucursalBase = sucursalBase;
        this.sucursalActual = sucursalActual;
    }

    public String getNumeroPlaca() {
        return numeroPlaca;
    }

    public byte[] getFoto() {
        return foto;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public String getFechaFabricacion() {
        return fechaFabricacion;
    }

    public int getCapacidadPasajeros() {
        return capacidadPasajeros;
    }

    public int getKilometraje() {
        return kilometraje;
    }

    public boolean isEstadoOperativo() {
        return estadoOperativo;
    }

    public String getSucursalBase() {
        return sucursalBase;
    }

    public String getSucursalActual() {
        return sucursalActual;
    }

}
