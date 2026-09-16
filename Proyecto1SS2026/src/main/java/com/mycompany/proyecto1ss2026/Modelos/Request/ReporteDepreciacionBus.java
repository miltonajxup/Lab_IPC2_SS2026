/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto1ss2026.Modelos.Request;

/**
 *
 * @author milton
 */
public class ReporteDepreciacionBus {
    
    private final String bus;
    private final double montoDepreciacion;
    private final int kilometrosRecorridos;

    public ReporteDepreciacionBus(String bus, double montoDepreciacion, int kilometrosRecorridos) {
        this.bus = bus;
        this.montoDepreciacion = montoDepreciacion;
        this.kilometrosRecorridos = kilometrosRecorridos;
    }

    public String getBus() {
        return bus;
    }

    public double getMontoDepreciacion() {
        return montoDepreciacion;
    }

    public int getKilometrosRecorridos() {
        return kilometrosRecorridos;
    }
    
}
