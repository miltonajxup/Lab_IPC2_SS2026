/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.ModelosDB;

import com.mycompany.proyecto2_ipc2_ss2026.Constantes.RolUsuario;

/**
 *
 * @author milton
 */
public class EmpleadoDB extends UsuarioDB {
    
    private final double salario;
    private final String fechaContratacion;
    
    public EmpleadoDB(String dpi, String nombre, RolUsuario rol, double salario, String fechaContratacion) {
        super(dpi, nombre, rol);
        this.salario = salario;
        this.fechaContratacion = fechaContratacion;
    }

    public double getSalario() {
        return salario;
    }

    public String getFechaContratacion() {
        return fechaContratacion;
    }
    
}
