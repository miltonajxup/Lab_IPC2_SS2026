/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.ModelosDB;

import com.mycompany.proyecto2_ipc2_ss2026.Constantes.EstadosEstudiante;
import com.mycompany.proyecto2_ipc2_ss2026.Constantes.RolUsuario;

/**
 *
 * @author milton
 */
public class EstudianteDB extends UsuarioDB {
    
    private final String numeroEncargado;
    private final String numeroEstudiante;
    private final EstadosEstudiante estado;
    
    public EstudianteDB(String dpi, String nombre, RolUsuario rol, String numeroEncargado, String numeroEstudiante, EstadosEstudiante estado) {
        super (dpi, nombre, rol);
        this.numeroEncargado = numeroEncargado;
        this.numeroEstudiante = numeroEstudiante;
        this.estado = estado;
    }

    public String getNumeroEncargado() {
        return numeroEncargado;
    }

    public String getNumeroEstudiante() {
        return numeroEstudiante;
    }

    public EstadosEstudiante getEstado() {
        return estado;
    }

}
