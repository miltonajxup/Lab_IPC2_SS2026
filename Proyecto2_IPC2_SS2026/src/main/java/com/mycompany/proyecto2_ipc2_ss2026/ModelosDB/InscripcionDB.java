/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.ModelosDB;

/**
 *
 * @author milton
 */
public class InscripcionDB {
    
    private final int id;
    private final String estudiante;
    private final int grupoId;
    private final boolean estado;
    private final boolean pagoRealizado;

    public InscripcionDB(int id, String estudiante, int grupoId, boolean estado, boolean pagoRealizado) {
        this.id = id;
        this.estudiante = estudiante;
        this.grupoId = grupoId;
        this.estado = estado;
        this.pagoRealizado = pagoRealizado;
    }

    public int getId() {
        return id;
    }

    public String getEstudiante() {
        return estudiante;
    }

    public int getGrupoId() {
        return grupoId;
    }

    public boolean isEstado() {
        return estado;
    }

    public boolean isPagoRealizado() {
        return pagoRealizado;
    }

}
