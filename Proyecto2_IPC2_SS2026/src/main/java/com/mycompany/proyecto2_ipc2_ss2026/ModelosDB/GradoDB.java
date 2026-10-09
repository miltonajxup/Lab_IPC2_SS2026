/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.ModelosDB;

/**
 *
 * @author milton
 */
public class GradoDB {
    
    private final int id;
    private final String nombre;
    private final double costoInscripcion;
    private final double costoColegiatura;
    private final int nivelAcademicoId;
    private final String nombreNivel;

    public GradoDB(int id, String nombre, double costoInscripcion, double costoColegiatura, int nivelAcademicoId, String nombreNivel) {
        this.id = id;
        this.nombre = nombre;
        this.costoInscripcion = costoInscripcion;
        this.costoColegiatura = costoColegiatura;
        this.nivelAcademicoId = nivelAcademicoId;
        this.nombreNivel = nombreNivel;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public double getCostoInscripcion() {
        return costoInscripcion;
    }

    public double getCostoColegiatura() {
        return costoColegiatura;
    }

    public int getNivelAcademicoId() {
        return nivelAcademicoId;
    }

    public String getNombreNivel() {
        return nombreNivel;
    }
    
}
