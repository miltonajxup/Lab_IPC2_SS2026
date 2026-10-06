/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.ModelosRequest;

import java.time.LocalDate;

/**
 *
 * @author milton
 */
public class AñoLectivoRequest {
    
    private int año;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    public int getAño() {
        return año;
    }

    public void setAño(int año) {
        this.año = año;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }
    
}
