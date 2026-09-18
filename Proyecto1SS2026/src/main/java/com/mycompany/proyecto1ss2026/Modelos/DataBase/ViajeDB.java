/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto1ss2026.Modelos.DataBase;

/**
 *
 * @author milton
 */
public class ViajeDB {
    
    private final int id;
    private final String chofer;
    private final String bus;
    private boolean comenzado;
    private String sucursalDestino;

    public ViajeDB(int id, String chofer, String bus) {
        this.id = id;
        this.chofer = chofer;
        this.bus = bus;
    }

    public ViajeDB(int id, String chofer, String bus, boolean comenzado) {
        this.id = id;
        this.chofer = chofer;
        this.bus = bus;
        this.comenzado = comenzado;
    }

    public ViajeDB(int id, String chofer, String bus, boolean comenzado, String sucursalDestino) {
        this.id = id;
        this.chofer = chofer;
        this.bus = bus;
        this.comenzado = comenzado;
        this.sucursalDestino = sucursalDestino;
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

    public boolean isComenzado() {
        return comenzado;
    }

    public String getSucursalDestino() {
        return sucursalDestino;
    }

}
