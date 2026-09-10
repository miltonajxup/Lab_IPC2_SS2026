/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto1ss2026.Modelos.DataBase;

/**
 *
 * @author milton
 */
public class HorarioDB {
    
    private final int id;
    private final String horaSalida;
    private final String horaLlegada;
    private final int ruta;

    public HorarioDB(int id, String horaSalida, String horaLlegada, int ruta) {
        this.id = id;
        this.horaSalida = horaSalida;
        this.horaLlegada = horaLlegada;
        this.ruta = ruta;
    }

    public int getId() {
        return id;
    }

    public String getHoraSalida() {
        return horaSalida;
    }

    public String getHoraLlegada() {
        return horaLlegada;
    }

    public int getRuta() {
        return ruta;
    }

}
