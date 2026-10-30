/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.ModelosDB;

import java.util.List;

/**
 *
 * @author milton
 */
public class ExpedienteEstudiante {
    
    private final String dpi;
    private final String nombre;
    private final String rol;
    private final String estado;
    private final String grado;
    private List<NotaDB> notas;

    public ExpedienteEstudiante(String dpi, String nombre, String rol, String estado, String grado) {
        this.dpi = dpi;
        this.nombre = nombre;
        this.rol = rol;
        this.estado = estado;
        this.grado = grado;
    }

    public String getDpi() {
        return dpi;
    }

    public String getNombre() {
        return nombre;
    }

    public String getRol() {
        return rol;
    }

    public String getEstado() {
        return estado;
    }

    public String getGrado() {
        return grado;
    }

    public List<NotaDB> getNotas() {
        return notas;
    }

    public void setNotas(List<NotaDB> notas) {
        this.notas = notas;
    }
    
}
