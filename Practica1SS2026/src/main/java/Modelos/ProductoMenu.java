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
    private final String urlImagen;
    private final String pathImagen;
    private List<Insumo> insumos;

    public ProductoMenu(int codigo, String nombre, double precio, String categoria, String urlImagen, String pathImagen) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
        this.urlImagen = urlImagen;
        this.pathImagen = pathImagen;
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

    public String getUrlImagen() {
        return urlImagen;
    }

    public String getPathImagen() {
        return pathImagen;
    }
    
    public void setInsumos(List<Insumo> insumos) {
        this.insumos = insumos;
    }
    
}
