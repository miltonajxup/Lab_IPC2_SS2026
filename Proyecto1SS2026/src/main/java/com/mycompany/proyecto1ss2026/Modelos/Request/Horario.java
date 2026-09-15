/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto1ss2026.Modelos.Request;

import java.time.LocalTime;

/**
 *
 * @author milton
 */
public class Horario {
    
    private LocalTime horaSalida;
    private LocalTime horaLlegada;
    private String ruta;

    public Horario(LocalTime horaSalida, LocalTime horaLlegada, String ruta) {
        this.horaSalida = horaSalida;
        this.horaLlegada = horaLlegada;
        this.ruta = ruta;
    }

    public LocalTime getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(LocalTime horaSalida) {
        this.horaSalida = horaSalida;
    }

    public LocalTime getHoraLlegada() {
        return horaLlegada;
    }

    public void setHoraLlegada(LocalTime horaLlegada) {
        this.horaLlegada = horaLlegada;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }
    
}
