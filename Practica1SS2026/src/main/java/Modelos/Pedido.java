/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

/**
 *
 * @author milton
 */
public class Pedido {
    
    private final int numeroPedido;
    private final String horaOcupacion;
    private final String horaLiberacion;
    private final boolean estado;
    private final double pagoTotal;
    private final double propina;
    private final String mesero;
    private final String nombreMesero;
    private final int numeroMesa;

    public Pedido(int numeroPedido, String horaOcupacion, String horaLiberacion, boolean estado, double pagoTotal, double propina, String mesero, String nombreMesero, int numeroMesa) {
        this.numeroPedido = numeroPedido;
        this.horaOcupacion = horaOcupacion;
        this.horaLiberacion = horaLiberacion;
        this.estado = estado;
        this.pagoTotal = pagoTotal;
        this.propina = propina;
        this.mesero = mesero;
        this.nombreMesero = nombreMesero;
        this.numeroMesa = numeroMesa;
    }

    public int getNumeroPedido() {
        return numeroPedido;
    }

    public String getHoraOcupacion() {
        return horaOcupacion;
    }

    public String getHoraLiberacion() {
        return horaLiberacion;
    }

    public boolean isEstado() {
        return estado;
    }

    public double getPagoTotal() {
        return pagoTotal;
    }

    public double getPropina() {
        return propina;
    }

    public String getMesero() {
        return mesero;
    }
    
    public String getNombreMesero() {
        return nombreMesero;
    }

    public int getNumeroMesa() {
        return numeroMesa;
    }
    
}
