/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto1ss2026.Modelos.Request;

import java.time.LocalDate;

/**
 *
 * @author milton
 */
public class ViajePublicoRequest {
    
    private String chofer;
    private String bus;
    private LocalDate fechaSalida;
    private String horario;
    private int asiento;

    public ViajePublicoRequest(String chofer, String bus, LocalDate fechaSalida, String horario) {
        this.chofer = chofer;
        this.bus = bus;
        this.fechaSalida = fechaSalida;
        this.horario = horario;
    }

    public String getChofer() {
        return chofer;
    }

    public void setChofer(String chofer) {
        this.chofer = chofer;
    }

    public String getBus() {
        return bus;
    }

    public void setBus(String bus) {
        this.bus = bus;
    }

    public LocalDate getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(LocalDate fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public int getAsiento() {
        return asiento;
    }

    public void setAsiento(int asiento) {
        this.asiento = asiento;
    }
    
}
