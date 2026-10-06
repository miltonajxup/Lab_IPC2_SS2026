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
public class UsuarioDB {
    
    private final String dpi;
    private final String nombre;
    private final RolUsuario rol;

    public UsuarioDB(String dpi, String nombre, RolUsuario rol) {
        this.dpi = dpi;
        this.nombre = nombre;
        this.rol = rol;
    }

    public String getDpi() {
        return dpi;
    }

    public String getNombre() {
        return nombre;
    }

    public RolUsuario getRol() {
        return rol;
    }

}
