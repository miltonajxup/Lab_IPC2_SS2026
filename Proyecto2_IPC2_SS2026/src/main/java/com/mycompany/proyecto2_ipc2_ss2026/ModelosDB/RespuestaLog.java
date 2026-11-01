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
    private final String mensaje;

    public RespuestaLog(boolean acceso, String mensaje) {
        this.acceso = acceso;
        this.mensaje = mensaje;
    }

    public boolean isAcceso() {
        return acceso;
    }

    public String getMensaje() {
        return mensaje;
    }
    
}
