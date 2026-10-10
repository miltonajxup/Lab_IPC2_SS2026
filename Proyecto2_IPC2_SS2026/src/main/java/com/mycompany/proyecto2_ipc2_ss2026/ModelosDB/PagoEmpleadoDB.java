/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.ModelosDB;

import java.time.LocalDate;

/**
 *
 * @author milton
 */
public class PagoEmpleadoDB {
    
    private final int id;
    private final LocalDate fechaPago;
    private final double montoPagado;
    private final String mesPago;
    private final int año;
    private final String dpiEmpleado;
    private final String nombreEmpleado;

    public PagoEmpleadoDB(int id, LocalDate fechaPago, double montoPagado, String mesPago, int año, String dpiEmpleado, String nombreEmpleado) {
        this.id = id;
        this.fechaPago = fechaPago;
        this.montoPagado = montoPagado;
        this.mesPago = mesPago;
        this.año = año;
        this.dpiEmpleado = dpiEmpleado;
        this.nombreEmpleado = nombreEmpleado;
    }

    public int getId() {
        return id;
    }

    public LocalDate getFechaPago() {
        return fechaPago;
    }

    public double getMontoPagado() {
        return montoPagado;
    }
    
    public String getMesPago() {
        return mesPago;
    }

    public int getAño() {
        return año;
    }

    public String getDpiEmpleado() {
        return dpiEmpleado;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }
    
}
