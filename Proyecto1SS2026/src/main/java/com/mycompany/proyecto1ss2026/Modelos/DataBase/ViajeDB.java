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

    public ViajeDB(int id, String chofer, String bus) {
        this.id = id;
        this.chofer = chofer;
        this.bus = bus;
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
    
}
