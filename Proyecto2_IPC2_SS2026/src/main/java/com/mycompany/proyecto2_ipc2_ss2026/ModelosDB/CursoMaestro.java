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
    
    private final int idCursoMaestro;
    private final int idCurso;
    private final String nombreCurso;
    private final int idGrupo;
    private final String nombreGrado;
    private final String nombreNivel;
    private final String seccion;
    private final int anio;

    public CursoMaestro(int idCursoMaestro, int idCurso, String nombreCurso, int idGrupo, String nombreGrado, String nombreNivel, String seccion, int año) {
        this.idCursoMaestro = idCursoMaestro;
        this.idCurso = idCurso;
        this.nombreCurso = nombreCurso;
        this.idGrupo = idGrupo;
        this.nombreGrado = nombreGrado;
        this.nombreNivel = nombreNivel;
        this.seccion = seccion;
        this.anio = año;
    }

    public int getIdCursoMaestro() {
        return idCursoMaestro;
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

    public int getAnio() {
        return anio;
    }
    
}
