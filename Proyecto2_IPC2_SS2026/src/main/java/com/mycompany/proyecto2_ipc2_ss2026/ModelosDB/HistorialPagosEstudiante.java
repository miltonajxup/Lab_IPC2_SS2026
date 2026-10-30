/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.ModelosDB;

import java.util.List;

/**
 *
 * @author milton
 */
public class HistorialPagosEstudiante {
    
    private final List<PagoBoletaDB> historial;
    private final List<PagoBoletaDB> pagosPendientes;
    private final double dineroIngresado;
    private final double saldoPendiente;

    public HistorialPagosEstudiante(List<PagoBoletaDB> historial, List<PagoBoletaDB> pagosPendientes, double dineroIngresado, double saldoPendiente) {
        this.historial = historial;
        this.pagosPendientes = pagosPendientes;
        this.dineroIngresado = dineroIngresado;
        this.saldoPendiente = saldoPendiente;
    }

    public List<PagoBoletaDB> getHistorial() {
        return historial;
    }

    public List<PagoBoletaDB> getPagosPendientes() {
        return pagosPendientes;
    }

    public double getDineroIngresado() {
        return dineroIngresado;
    }

    public double getSaldoPendiente() {
        return saldoPendiente;
    }
    
}
