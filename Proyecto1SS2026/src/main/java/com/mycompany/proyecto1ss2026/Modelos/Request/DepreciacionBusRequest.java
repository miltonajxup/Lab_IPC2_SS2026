/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto1ss2026.Modelos.Request;

/**
 *
 * @author milton
 */
public class DepreciacionBusRequest {
    
    private String fechaRegistro;
    private int kilometrosRecorridos;
    private int depreciacionId;
    private double montoDepreciado;
    private String bus;

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public int getKilometrosRecorridos() {
        return kilometrosRecorridos;
    }

    public void setKilometrosRecorridos(int kilometrosRecorridos) {
        this.kilometrosRecorridos = kilometrosRecorridos;
    }

    public String getBus() {
        return bus;
    }

    public void setBus(String bus) {
        this.bus = bus;
    }

    public int getDepreciacionId() {
        return depreciacionId;
    }

    public void setDepreciacionId(int depreciacionId) {
        this.depreciacionId = depreciacionId;
    }

    public double getMontoDepreciado() {
        return montoDepreciado;
    }

    public void setMontoDepreciado(double montoDepreciado) {
        this.montoDepreciado = montoDepreciado;
    }
    
}
