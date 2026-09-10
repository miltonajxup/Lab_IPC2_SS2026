/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto1ss2026.Modelos.DataBase;

/**
 *
 * @author milton
 */
public class ViajePublicoDB {
    
    private final int id;
    private final String chofer;
    private final String bus;
    private final String fechaSalida;
    private final int horario;
    private final String origen;
    private final String destino;
    private final String horaSalida;
    private final String horaLlegada;

    public ViajePublicoDB(int id, String chofer, String bus, String fechaSalida, int horario, String origen, String destino, String horaSalida, String horaLlegada) {
        this.id = id;
        this.chofer = chofer;
        this.bus = bus;
        this.fechaSalida = fechaSalida;
        this.horario = horario;
        this.origen = origen;
        this.destino = destino;
        this.horaSalida = horaSalida;
        this.horaLlegada = horaLlegada;
    }

    public int getId() {
        return id;
    }

    public String getChofer() {
        return chofer;
    }

    public String getBus() {
        return bus;
    }

    public String getFechaSalida() {
        return fechaSalida;
    }

    public int getHorario() {
        return horario;
    }

    public String getOrigen() {
        return origen;
    }

    public String getDestino() {
        return destino;
    }

    public String getHoraSalida() {
        return horaSalida;
    }

    public String getHoraLlegada() {
        return horaLlegada;
    }

}
