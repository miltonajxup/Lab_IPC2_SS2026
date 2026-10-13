/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.ModelosDB;

/**
 *
 * @author milton
 */
public class CursoMaestro {
    
    private final int idCurso;
    private final String nombreCurso;
    private final int idGrupo;
    private final String nombreGrado;
    private final String nombreNivel;
    private final String seccion;
    private final int año;

    public CursoMaestro(int idCurso, String nombreCurso, int idGrupo, String nombreGrado, String nombreNivel, String seccion, int año) {
        this.idCurso = idCurso;
        this.nombreCurso = nombreCurso;
        this.idGrupo = idGrupo;
        this.nombreGrado = nombreGrado;
        this.nombreNivel = nombreNivel;
        this.seccion = seccion;
        this.año = año;
    }

    public int getIdCurso() {
        return idCurso;
    }

    public String getNombreCurso() {
        return nombreCurso;
    }

    public int getIdGrupo() {
        return idGrupo;
    }

    public String getNombreGrado() {
        return nombreGrado;
    }

    public String getNombreNivel() {
        return nombreNivel;
    }

    public String getSeccion() {
        return seccion;
    }

    public int getAño() {
        return año;
    }
    
}
