/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto1ss2026.Modelos.Request;

import java.time.LocalDate;

/**
 *
 * @author milton
 */
public class BusRequest {
    
    private String numeroPlaca;
    private byte[] foto;
    private String marca;
    private String modelo;
    private LocalDate fechaFabricacion;
    private int capacidadPasajeros;
    private int kilometraje;
    private boolean estadoOperativo;
    private String sucursalBase;
    private String sucursalActual;

    public BusRequest(String numeroPlaca, String marca, String modelo, LocalDate fechaFabricacion, int capacidadPasajeros, int kilometraje, String sucursalBase) {
        this.numeroPlaca = numeroPlaca;
        this.marca = marca;
        this.modelo = modelo;
        this.fechaFabricacion = fechaFabricacion;
        this.capacidadPasajeros = capacidadPasajeros;
        this.kilometraje = kilometraje;
        this.sucursalBase = sucursalBase;
    }

    public String getNumeroPlaca() {
        return numeroPlaca;
    }

    public void setNumeroPlaca(String numeroPlaca) {
        this.numeroPlaca = numeroPlaca;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public LocalDate getFechaFabricacion() {
        return fechaFabricacion;
    }

    public void setFechaFabricacion(LocalDate fechaFabricacion) {
        this.fechaFabricacion = fechaFabricacion;
    }

    public int getCapacidadPasajeros() {
        return capacidadPasajeros;
    }

    public void setCapacidadPasajeros(int capacidadPasajeros) {
        this.capacidadPasajeros = capacidadPasajeros;
    }

    public int getKilometraje() {
        return kilometraje;
    }

    public void setKilometraje(int kilometraje) {
        this.kilometraje = kilometraje;
    }

    public boolean isEstadoOperativo() {
        return estadoOperativo;
    }

    public void setEstadoOperativo(boolean estadoOperativo) {
        this.estadoOperativo = estadoOperativo;
    }

    public String getSucursalBase() {
        return sucursalBase;
    }

    public void setSucursalBase(String sucursalBase) {
        this.sucursalBase = sucursalBase;
    }

    public String getSucursalActual() {
        return sucursalActual;
    }

    public void setSucursalActual(String sucursalActual) {
        this.sucursalActual = sucursalActual;
    }
    
}
