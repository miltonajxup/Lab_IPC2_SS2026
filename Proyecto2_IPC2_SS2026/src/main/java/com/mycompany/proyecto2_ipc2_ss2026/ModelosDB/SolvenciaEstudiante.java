/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.ModelosDB;

/**
 *
 * @author milton
 */
public class SolvenciaEstudiante {
    
    private final String dpi;
    private final String nombre;
    private final int totalBoletas;
    private final int pagadas;
    private final int pendientes;
    private final String estado;

    public SolvenciaEstudiante(String dpi, String nombre, int totalBoletas, int pagadas, int pendientes, String estado) {
        this.dpi = dpi;
        this.nombre = nombre;
        this.totalBoletas = totalBoletas;
        this.pagadas = pagadas;
        this.pendientes = pendientes;
        this.estado = estado;
    }

    public String getDpi() {
        return dpi;
    }

    public String getNombre() {
        return nombre;
    }

    public int getTotalBoletas() {
        return totalBoletas;
    }

    public int getPagadas() {
        return pagadas;
    }

    public int getPendientes() {
        return pendientes;
    }

    public String getEstado() {
        return estado;
    }

}
