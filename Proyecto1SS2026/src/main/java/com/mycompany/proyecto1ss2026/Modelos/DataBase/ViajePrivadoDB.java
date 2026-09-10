/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto1ss2026.Modelos.DataBase;

import com.mycompany.proyecto1ss2026.Constantes.EstadoViajePrivado;

/**
 *
 * @author milton
 */
public class ViajePrivadoDB {
    
    private final int id;
    private final String chofer;
    private final String bus;
    private final int cantidadPasajeros;
    private final String origen;
    private final String destino;
    private final int distanciaAProximada;
    private final String horaSalida;
    private final String horaLlegada;
    private final String fechaSalida;
    private final String fechaLlegada;
    private final double costo;
    private final String usuarioSolicitante;
    private final EstadoViajePrivado estadoViaje;

    public ViajePrivadoDB(int id, String chofer, String bus, int cantidadPasajeros, String origen, String destino, int distanciaAProximada, String horaSalida, String horaLlegada, String fechaSalida, String fechaLlegada, double costo, String usuarioSolicitante, EstadoViajePrivado estadoViaje) {
        this.id = id;
        this.chofer = chofer;
        this.bus = bus;
        this.cantidadPasajeros = cantidadPasajeros;
        this.origen = origen;
        this.destino = destino;
        this.distanciaAProximada = distanciaAProximada;
        this.horaSalida = horaSalida;
        this.horaLlegada = horaLlegada;
        this.fechaSalida = fechaSalida;
        this.fechaLlegada = fechaLlegada;
        this.costo = costo;
        this.usuarioSolicitante = usuarioSolicitante;
        this.estadoViaje = estadoViaje;
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

    public int getCantidadPasajeros() {
        return cantidadPasajeros;
    }

    public String getOrigen() {
        return origen;
    }

    public String getDestino() {
        return destino;
    }

    public int getDistanciaAProximada() {
        return distanciaAProximada;
    }

    public String getHoraSalida() {
        return horaSalida;
    }

    public String getHoraLlegada() {
        return horaLlegada;
    }

    public String getFechaSalida() {
        return fechaSalida;
    }

    public String getFechaLlegada() {
        return fechaLlegada;
    }

    public double getCosto() {
        return costo;
    }

    public EstadoViajePrivado getEstadoViaje() {
        return estadoViaje;
    }

}
