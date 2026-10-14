/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.ModelosDB;

import java.time.LocalDate;

/**
 *
 * @author milton
 */
public class PagoBoletaDB {
    
    private final int idPago;
    private final int idBoleta;
    private final double monto;
    private final boolean pagado;
    private final String tipoPago;
    private final String mesPago;
    private final int año;
    private final String dpiEstudiante;
    private final String nombreEstudiante;
    private final LocalDate fechaPago;
    private final String nombreSecretaria;

    public PagoBoletaDB(int idPago, int idBoleta, double monto, boolean pagado, String tipoPago, String mesPago, int año, String dpiEstudiante, String nombreEstudiante, LocalDate fechaPago, String nombreSecretaria) {
        this.idPago = idPago;
        this.idBoleta = idBoleta;
        this.monto = monto;
        this.pagado = pagado;
        this.tipoPago = tipoPago;
        this.mesPago = mesPago;
        this.año = año;
        this.dpiEstudiante = dpiEstudiante;
        this.nombreEstudiante = nombreEstudiante;
        this.fechaPago = fechaPago;
        this.nombreSecretaria = nombreSecretaria;
    }

    public int getIdPago() {
        return idPago;
    }

    public int getIdBoleta() {
        return idBoleta;
    }

    public double getMonto() {
        return monto;
    }

    public boolean isPagado() {
        return pagado;
    }

    public String getTipoPago() {
        return tipoPago;
    }

    public String getMesPago() {
        return mesPago;
    }

    public int getAño() {
        return año;
    }

    public String getDpiEstudiante() {
        return dpiEstudiante;
    }

    public String getNombreEstudiante() {
        return nombreEstudiante;
    }

    public LocalDate getFechaPago() {
        return fechaPago;
    }

    public String getNombreSecretaria() {
        return nombreSecretaria;
    }
    
}
