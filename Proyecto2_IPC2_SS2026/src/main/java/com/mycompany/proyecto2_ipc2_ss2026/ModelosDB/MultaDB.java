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
public class MultaDB {
    
    private final int id;
    private final double monto;
    private final boolean estado;
    private final LocalDate fechaPago;
    private final int idPrestamo;

    public MultaDB(int id, double monto, boolean estado, LocalDate fechaPago, int idPrestamo) {
        this.id = id;
        this.monto = monto;
        this.estado = estado;
        this.fechaPago = fechaPago;
        this.idPrestamo = idPrestamo;
    }

    public int getId() {
        return id;
    }

    public double getMonto() {
        return monto;
    }

    public boolean isEstado() {
        return estado;
    }

    public LocalDate getFechaPago() {
        return fechaPago;
    }

    public int getIdPrestamo() {
        return idPrestamo;
    }

}
