/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto1ss2026.Modelos.Request;

import com.mycompany.proyecto1ss2026.Constantes.EstadoViajePrivado;

/**
 *
 * @author milton
 */
public class ViajePrivado {
    
    private final String chofer;
    private final String bus;
    private final String sucursal;
    private EstadoViajePrivado estadoViaje;
    private String fechaLlegada;
    
    private final int cantidadPasajeros;
    private final String origen;
    private final String destino;
    private final int distanciaAProximada;
    private final String horaSalida;
    private final String horaLlegada;
    private final String fechaSalida;
    private final double costo;
    private final String usuarioSolicitante;

    public ViajePrivado(PropuestaViaje propuesta, String chofer, String bus, String sucursal) {
        this.chofer = chofer;
        this.bus = bus;
        this.cantidadPasajeros = propuesta.getCantidadPasajeros();
        this.origen = propuesta.getOrigen();
        this.destino = propuesta.getDestino();
        this.distanciaAProximada = propuesta.getDistanciaAProximada();
        this.horaSalida = propuesta.getHoraSalida();
        this.horaLlegada = propuesta.getHoraLlegada();
        this.fechaSalida = propuesta.getFechaSalida();
        this.costo = propuesta.getCosto();
        this.usuarioSolicitante = propuesta.getUsuarioSolicitante();
        this.sucursal = sucursal;
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

    public void setFechaLlegada(String fechaLlegada) {
        this.fechaLlegada = fechaLlegada;
    }

    public double getCosto() {
        return costo;
    }

    public String getUsuarioSolicitante() {
        return usuarioSolicitante;
    }

    public EstadoViajePrivado getEstadoViaje() {
        return estadoViaje;
    }

    public String getSucursal() {
        return sucursal;
    }

}
