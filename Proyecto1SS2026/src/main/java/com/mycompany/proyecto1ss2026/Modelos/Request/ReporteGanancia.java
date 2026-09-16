/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto1ss2026.Modelos.Request;

/**
 *
 * @author milton
 */
public class ReporteGanancia {
    
    private String sucursal;
    private double ventas;
    private double gastoCombustible;
    private double gastoTallerMano;
    private double gastoTallerRepuestos;
    private double depreciacion;
    private double totalGanancias;
    private double totalGastos;
    
    public ReporteGanancia() {
    }

    public ReporteGanancia(String sucursal) {
        this.sucursal = sucursal;
    }

    public ReporteGanancia(double ventas, double gastoCombustible, double gastoTallerMano, double gastoTallerRepuestos, double depreciacion) {
        this.ventas = ventas;
        this.gastoCombustible = gastoCombustible;
        this.gastoTallerMano = gastoTallerMano;
        this.gastoTallerRepuestos = gastoTallerRepuestos;
        this.depreciacion = depreciacion;
    }

    public double getVentas() {
        return ventas;
    }

    public void setVentas(double ventas) {
        this.ventas = ventas;
    }

    public String getSucursal() {
        return sucursal;
    }

    public double getGastoCombustible() {
        return gastoCombustible;
    }

    public void setGastoCombustible(double gastoCombustible) {
        this.gastoCombustible = gastoCombustible;
    }

    public double getGastoTallerMano() {
        return gastoTallerMano;
    }

    public void setGastoTallerMano(double gastoTallerMano) {
        this.gastoTallerMano = gastoTallerMano;
    }

    public double getGastoTallerRepuestos() {
        return gastoTallerRepuestos;
    }

    public void setGastoTallerRepuestos(double gastoTallerRepuestos) {
        this.gastoTallerRepuestos = gastoTallerRepuestos;
    }

    public double getDepreciacion() {
        return depreciacion;
    }

    public void setDepreciacion(double depreciacion) {
        this.depreciacion = depreciacion;
    }
    
    public double getTotalGanancias() {
        return ventas - (getTotalGastos());
    }
    
    public double getTotalGastos() {
        return gastoCombustible + gastoTallerMano + gastoTallerRepuestos + depreciacion;
    }
    
}
