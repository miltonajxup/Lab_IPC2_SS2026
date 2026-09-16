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
public class GastoTaller {
    
    private double montoManoObra;
    private double montoRepuestos;
    private LocalDate fechaMantenimiento;
    private String bus;

    public GastoTaller(double montoManoObra, double montoRepuestos, LocalDate fechaMantenimiento, String bus) {
        this.montoManoObra = montoManoObra;
        this.montoRepuestos = montoRepuestos;
        this.fechaMantenimiento = fechaMantenimiento;
        this.bus = bus;
    }

    public double getMontoManoObra() {
        return montoManoObra;
    }

    public void setMontoManoObra(double montoManoObra) {
        this.montoManoObra = montoManoObra;
    }

    public double getMontoRepuestos() {
        return montoRepuestos;
    }

    public void setMontoRepuestos(double montoRepuestos) {
        this.montoRepuestos = montoRepuestos;
    }

    public LocalDate getFechaMantenimiento() {
        return fechaMantenimiento;
    }

    public void setFechaMantenimiento(LocalDate fechaMantenimiento) {
        this.fechaMantenimiento = fechaMantenimiento;
    }

    public String getBus() {
        return bus;
    }

    public void setBus(String bus) {
        this.bus = bus;
    }
    
}
