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
public class AsistenciaDB {
    
    private final int id;
    private final int idCursoEstudiante;
    private final boolean estado;
    private final LocalDate fecha;

    public AsistenciaDB(int id, int idCursoEstudiante, boolean estado, LocalDate fecha) {
        this.id = id;
        this.idCursoEstudiante = idCursoEstudiante;
        this.estado = estado;
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public int getIdCursoEstudiante() {
        return idCursoEstudiante;
    }

    public boolean isEstado() {
        return estado;
    }

    public LocalDate getFecha() {
        return fecha;
    }
    
}
