/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.ModelosRequest;

/**
 *
 * @author milton
 */
public class GradoRequest {
    
    private String nombre;
    private double costoInscripcion;
    private double costoColegiatura;
    private int nivelId;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getCostoInscripcion() {
        return costoInscripcion;
    }

    public void setCostoInscripcion(double costoInscripcion) {
        this.costoInscripcion = costoInscripcion;
    }

    public double getCostoColegiatura() {
        return costoColegiatura;
    }

    public void setCostoColegiatura(double costoColegiatura) {
        this.costoColegiatura = costoColegiatura;
    }

    public int getNivelId() {
        return nivelId;
    }

    public void setNivelId(int nivelId) {
        this.nivelId = nivelId;
    }
    
}
