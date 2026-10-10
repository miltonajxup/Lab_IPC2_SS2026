/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.ModelosDB;

/**
 *
 * @author milton
 */
public class GrupoAcademicoDB {
    
    private final int id;
    private final int cicloEscolarId;
    private final int gradoId;
    private final String nombreGrado;
    private final String nivelAcademico;
    private final String carreraId;
    private final String nombreCarrera;
    private final String seccion;

    public GrupoAcademicoDB(int id, int cicloEscolarId, int gradoId, String nombreGrado, String nivelAcademico, String carreraId, String nombreCarrera, String seccion) {
        this.id = id;
        this.cicloEscolarId = cicloEscolarId;
        this.gradoId = gradoId;
        this.nombreGrado = nombreGrado;
        this.nivelAcademico = nivelAcademico;
        this.carreraId = carreraId;
        this.nombreCarrera = nombreCarrera;
        this.seccion = seccion;
    }

    public int getId() {
        return id;
    }

    public int getCicloEscolarId() {
        return cicloEscolarId;
    }

    public int getGradoId() {
        return gradoId;
    }

    public String getNombreGrado() {
        return nombreGrado;
    }

    public String getNivelAcademico() {
        return nivelAcademico;
    }

    public String getCarreraId() {
        return carreraId;
    }

    public String getNombreCarrera() {
        return nombreCarrera;
    }

    public String getSeccion() {
        return seccion;
    }
    
}
