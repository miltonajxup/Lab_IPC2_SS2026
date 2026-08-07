/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

/**
 *
 * @author milton
 */
public class Insumo {
    
    private final int codigo;
    private final String nombre;
    private double cantidadStock;
    private double stockMinimo;
    private double costo;
    private final String undadMedida;
    private int cantUtilizadaProducto;

    public Insumo(int codigo, String nombre, int cantidadUtilizada, String unidadMedida) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.cantUtilizadaProducto = cantidadUtilizada;
        this.undadMedida = unidadMedida;
    }

    public Insumo(int codigo, String nombre, double cantidadStock, double stockMinimo, double costo, String undadMedida) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.cantidadStock = cantidadStock;
        this.stockMinimo = stockMinimo;
        this.costo = costo;
        this.undadMedida = undadMedida;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public double getCantidadStock() {
        return cantidadStock;
    }
    
    public void setCantidadStock(double cantidadStock) {
        this.cantidadStock = cantidadStock;
    }

    public double getStockMinimo() {
        return stockMinimo;
    }

    public double getCosto() {
        return costo;
    }

    public int getCantUtilizadaProducto() {
        return cantUtilizadaProducto;
    }

    public String getUndadMedida() {
        return undadMedida;
    }
    
}
