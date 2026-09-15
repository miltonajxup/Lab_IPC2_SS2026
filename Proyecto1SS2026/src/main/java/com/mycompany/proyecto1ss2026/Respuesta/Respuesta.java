/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto1ss2026.Respuesta;

/**
 *
 * @author milton
 */
public class Respuesta {
    
    private final boolean correcto;
    private final String mensaje;

    public Respuesta(boolean correcto, String mensaje) {
        this.correcto = correcto;
        this.mensaje = mensaje;
    }

    public boolean isCorrecto() {
        return correcto;
    }

    public String getMensaje() {
        return mensaje;
    }
    
}
