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
public class UsuarioCompleto extends UsuarioDB {
    
    private final String contraseña;

    public UsuarioCompleto(String dpi, String nombre, RolUsuario rol, String contraseña) {
        super(dpi, nombre, rol);
        this.contraseña = contraseña;
    }

    public String getContraseña() {
        return contraseña;
    }
    
}
