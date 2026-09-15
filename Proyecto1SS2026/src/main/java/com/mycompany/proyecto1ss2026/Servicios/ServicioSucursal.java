/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto1ss2026.Servicios;

import com.mycompany.proyecto1ss2026.DAOs.SucursalDAO;
import com.mycompany.proyecto1ss2026.Exeptions.AccesoALaDataException;
import com.mycompany.proyecto1ss2026.Exeptions.ValorExistenteException;
import com.mycompany.proyecto1ss2026.Exeptions.ValorInvalidoException;
import com.mycompany.proyecto1ss2026.Modelos.Request.Sucursal;
import com.mycompany.proyecto1ss2026.Respuesta.Respuesta;

/**
 *
 * @author milton
 */
public class ServicioSucursal {
    
    private final SucursalDAO sucursaldao;

    public ServicioSucursal() {
        sucursaldao = new SucursalDAO();
    }
    
    public Respuesta agregarSucursal(String codigo, String nombre, String ciudad) throws AccesoALaDataException, ValorExistenteException, ValorInvalidoException {
        Sucursal sucursal = new Sucursal(codigo, nombre, ciudad);
        
        if (sucursaldao.existeSucursal(codigo)) {
            throw new ValorExistenteException("Ya existe una sucursal con el codigo " + codigo);
        }
        
        existenciaSucursal(sucursal);
        
        sucursaldao.agregarSucursal(sucursal);
        return new Respuesta(true, "La sucursal " + sucursal.getNombre() + " ha sido creada con exito");
    }
    
    public String modificarSucursal(String codigo, String nombre, String ciudad) throws AccesoALaDataException, ValorExistenteException, ValorInvalidoException {
        Sucursal sucursal = new Sucursal(codigo, nombre, ciudad);
        if (!sucursaldao.existeSucursal(sucursal.getCodigo())) {
            throw new ValorExistenteException("No existe una sucursal con el codigo " + sucursal.getCodigo());
        }
        
        existenciaSucursal(sucursal);
        
        sucursaldao.editarSucursal(sucursal);
        return "La Sucursal " + sucursal.getCodigo() + " ha sido modificada con exito";
    }
    
    private void existenciaSucursal(Sucursal sucursal) throws AccesoALaDataException, ValorExistenteException {
        if (sucursaldao.existeSucursalAtributos(sucursal)) {
            throw new ValorExistenteException("Ya existe una sucursal con codigo " + sucursal.getCodigo() + " llamada " + sucursal.getNombre() + " en " + sucursal.getCiudad());
        }
    }
    
}
