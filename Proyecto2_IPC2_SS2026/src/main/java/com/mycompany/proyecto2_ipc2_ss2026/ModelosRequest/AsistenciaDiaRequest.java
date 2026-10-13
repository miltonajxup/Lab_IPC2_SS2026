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
public class AsistenciaDiaRequest {
    
    private int idGrupoEstudiantes;
    private int idCurso;
    private LocalDate fecha;

    public int getIdGrupoEstudiantes() {
        return idGrupoEstudiantes;
    }

    public void setIdGrupoEstudiantes(int idGrupoEstudiantes) {
        this.idGrupoEstudiantes = idGrupoEstudiantes;
    }

    public int getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
    
}
