/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.ModelosRequest;

/**
 *
 * @author milton
 */
public class InscripcionRequest {
    
    private String dpiEstudiante;
    private UsuarioRequest estudiante;
    private int grupoId;

    public String getDpiEstudiante() {
        return dpiEstudiante;
    }

    public void setDpiEstudiante(String dpiEstudiante) {
        this.dpiEstudiante = dpiEstudiante;
    }

    public UsuarioRequest getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(UsuarioRequest estudiante) {
        this.estudiante = estudiante;
    }

    public int getGrupoId() {
        return grupoId;
    }

    public void setGrupoId(int grupoId) {
        this.grupoId = grupoId;
    }
    
}
