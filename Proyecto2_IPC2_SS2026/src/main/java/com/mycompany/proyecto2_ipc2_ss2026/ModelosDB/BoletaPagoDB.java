/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.ModelosDB;

/**
 *
 * @author milton
 */
public class BoletaPagoDB {
    
    private final int id;
    private final double monto;
    private final boolean estado;
    private final String tipoPago;
    private final String mesPago;
    private final int cicloEscolar;
    private final String idEstudiante;
    private final String nombreEstudiante;

    public BoletaPagoDB(int id, double monto, boolean estado, String tipoPago, String mesPago, int cicloEscolar, String idEstudiante, String nombreEstudiante) {
        this.id = id;
        this.monto = monto;
        this.estado = estado;
        this.tipoPago = tipoPago;
        this.mesPago = mesPago;
        this.cicloEscolar = cicloEscolar;
        this.idEstudiante = idEstudiante;
        this.nombreEstudiante = nombreEstudiante;
    }

    public int getId() {
        return id;
    }

    public double getMonto() {
        return monto;
    }

    public boolean isEstado() {
        return estado;
    }

    public String getTipoPago() {
        return tipoPago;
    }

    public String getMesPago() {
        return mesPago;
    }

    public int getCicloEscolar() {
        return cicloEscolar;
    }

    public String getIdEstudiante() {
        return idEstudiante;
    }

    public String getNombreEstudiante() {
        return nombreEstudiante;
    }
    
}
