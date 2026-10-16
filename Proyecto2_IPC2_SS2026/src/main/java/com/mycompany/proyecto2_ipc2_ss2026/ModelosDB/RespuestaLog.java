/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.ModelosDB;

/**
 *
 * @author milton
 */
public class RespuestaLog {
    
    private final boolean acceso;
    private String mensaje;
    private UsuarioDB usuario;

    public RespuestaLog(boolean acceso, String mensaje) {
        this.acceso = acceso;
        this.mensaje = mensaje;
    }
    
    public RespuestaLog(boolean acceso, UsuarioDB usuario) {
        this.acceso = acceso;
        this.usuario = usuario;
    }

    public boolean isAcceso() {
        return acceso;
    }

    public UsuarioDB getUsuario() {
        return usuario;
    }

    public String getMensaje() {
        return mensaje;
    }
    
}
