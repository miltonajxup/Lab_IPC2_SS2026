/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

import java.util.List;

/**
 *
 * @author milton
 */
public class ProductoMenu {
    
    private final int codigo;
    private final String nombre;
    private final double precio;
    private final String categoria;
    private List<Insumo> insumos;

    public ProductoMenu(int codigo, String nombre, double precio, String categoria) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }
    
    public double getPrecio() {
        return precio;
    }

    public List<Insumo> getInsumos() {
        return insumos;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setInsumos(List<Insumo> insumos) {
        this.insumos = insumos;
    }

}
