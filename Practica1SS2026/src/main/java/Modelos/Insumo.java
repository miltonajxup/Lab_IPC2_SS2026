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
    
    private String codigo;
    private final String nombre;
    private String cantidadStock;
    private String stockMinimo;
    private String costo;
    private int cantidadUtilizada;

    public Insumo(String nombre, int cantidadUtilizada) {
        this.nombre = nombre;
        this.cantidadUtilizada = cantidadUtilizada;
    }

    public Insumo(String codigo, String nombre, String cantidadStock, String stockMinimo, String costo) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.cantidadStock = cantidadStock;
        this.stockMinimo = stockMinimo;
        this.costo = costo;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCantidadStock() {
        return cantidadStock;
    }

    public String getStockMinimo() {
        return stockMinimo;
    }

    public String getCosto() {
        return costo;
    }

    public int getCantidadUtilizada() {
        return cantidadUtilizada;
    }
    
    
    
}
