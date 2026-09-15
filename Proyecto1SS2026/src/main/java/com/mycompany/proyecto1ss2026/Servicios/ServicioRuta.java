/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto1ss2026.Servicios;

import com.mycompany.proyecto1ss2026.DAOs.RutaDAO;
import com.mycompany.proyecto1ss2026.DAOs.SucursalDAO;
import com.mycompany.proyecto1ss2026.Exeptions.AccesoALaDataException;
import com.mycompany.proyecto1ss2026.Exeptions.ValorExistenteException;
import com.mycompany.proyecto1ss2026.Exeptions.ValorInexistenteException;
import com.mycompany.proyecto1ss2026.Exeptions.ValorInvalidoException;
import com.mycompany.proyecto1ss2026.Modelos.DataBase.RutaDB;
import com.mycompany.proyecto1ss2026.Modelos.Request.Ruta;
import com.mycompany.proyecto1ss2026.Respuesta.Respuesta;
import java.util.List;

/**
 *
 * @author milton
 */
public class ServicioRuta {
    
    private final int MINIMO_DISTANCIA = 1;
    private final int MINIMO_PRECIO = 1;
    private final RutaDAO rutadao;
    private final SucursalDAO sucursaldao;
    private int distanciaAproximada;
    private double precioBoleto;
    
    public ServicioRuta() {
        rutadao = new RutaDAO();
        sucursaldao = new SucursalDAO();
    }
    
    public String agregarRuta(String textoDistanciaAproximada, String textoPrecioBoleto, String sucursalRegistro, String sucursalOrigen, String sucursalDestino) 
            throws AccesoALaDataException, ValorInvalidoException, ValorExistenteException, ValorInexistenteException{
        
        if (textoDistanciaAproximada == null || textoPrecioBoleto == null || sucursalRegistro == null 
                || sucursalOrigen == null || sucursalDestino == null || textoDistanciaAproximada.isEmpty() 
                || textoPrecioBoleto.isEmpty() || sucursalRegistro.isEmpty() || sucursalOrigen.isEmpty() || sucursalDestino.isEmpty()) {
            return null;
        }
        Ruta ruta = filtarRuta(textoDistanciaAproximada, textoPrecioBoleto, sucursalRegistro, sucursalOrigen, sucursalDestino);
        existeRutaSucursales(sucursalOrigen, sucursalDestino);
        if (sucursalOrigen.equals(sucursalDestino)) {
            throw new ValorInvalidoException("No se puede agregar un ruta con el el mismo destino que el origen");
        }
        rutadao.agregarRuta(ruta);
        return "Se agregó la ruta de " + sucursalOrigen + " hacia " + sucursalDestino;
    }
    
    public String modificarRuta(String idRuta, String textoDistanciaAproximada, String textoPrecioBoleto) 
            throws AccesoALaDataException, ValorInvalidoException, ValorInexistenteException{
        if (idRuta == null || textoDistanciaAproximada == null || textoPrecioBoleto == null 
                || idRuta.isEmpty() || textoDistanciaAproximada.isEmpty() || textoPrecioBoleto.isEmpty()) {
            return null;
        }
        Ruta ruta = filtarRuta(idRuta, textoDistanciaAproximada, textoPrecioBoleto);
        existeRuta(idRuta);
        rutadao.modificarRuta(ruta);
        return "Se ha modificado la ruta ";
    }
    
    public RutaDB getRuta(String idRuta) throws AccesoALaDataException, ValorInexistenteException {
        if (idRuta == null || idRuta.isEmpty()) {
            return null;
        }
        existeRuta(idRuta);
        return rutadao.getRutasPorId(idRuta);
    }
    
    public RutaDB getRutaPorSucursales(String sucursalOrigen, String sucursalDestino) throws AccesoALaDataException, ValorInexistenteException {
        if (sucursalOrigen == null || sucursalDestino == null || sucursalOrigen.isEmpty() || sucursalDestino.isEmpty()) {
            return null;
        }
        return rutadao.getRutasPorSucursales(sucursalOrigen, sucursalDestino);
    }
    
    public Respuesta eliminarRuta(String idRuta) throws AccesoALaDataException, ValorInvalidoException, ValorInexistenteException {
        existeRuta(idRuta);
        List<RutaDB> rutasOcupadas = rutadao.getRutasOcupadas();
        for (RutaDB ruta : rutasOcupadas) {
            String textoRuta = String.valueOf(ruta.getId());
            if (textoRuta.equals(idRuta)) {
                return new Respuesta(false, "No se puede eliminar la ruta porque tiene viajes sin terminar o en ejecucion");
            }
        }
        rutadao.deshabilitarRuta(idRuta);
        return new Respuesta(true, "Se ha eliminado la ruta correctamente");
    }
    
    private Ruta filtarRuta(String idRuta, String textoDistanciaAproximada, String textoPrecioBoleto) throws AccesoALaDataException, ValorInvalidoException, ValorInexistenteException {
        distanciaAproximada = 0;
        precioBoleto = 0;
        filtrarValoresNumericos(textoDistanciaAproximada, textoPrecioBoleto);
        return new Ruta(idRuta, distanciaAproximada, precioBoleto);
    }
    
    
    private Ruta filtarRuta(String textoDistanciaAproximada, String textoPrecioBoleto, String sucursalRegistro, String sucursalOrigen, String sucursalDestino) 
            throws AccesoALaDataException, ValorInvalidoException, ValorInexistenteException {
        distanciaAproximada = 0;
        precioBoleto = 0;
        filtrarValoresNumericos(textoDistanciaAproximada, textoPrecioBoleto);
        existeSucursal(sucursalRegistro);
        existeSucursal(sucursalOrigen);
        existeSucursal(sucursalDestino);
        return new Ruta(distanciaAproximada, precioBoleto, sucursalRegistro, sucursalOrigen, sucursalDestino);
    }
    
    private void filtrarValoresNumericos(String textoDistanciaAproximada, String textoPrecioBoleto) throws ValorInvalidoException {
        try {
            distanciaAproximada = Integer.parseInt(textoDistanciaAproximada);
            precioBoleto = Double.parseDouble(textoPrecioBoleto);
        } catch (NumberFormatException e) {
            throw new ValorInvalidoException("Los valores numericos solo aceptan valores numericos");
        }
        if (distanciaAproximada < MINIMO_DISTANCIA) {
            throw new ValorInvalidoException("El minimo de distancia no puede ser menor a " + MINIMO_DISTANCIA);
        }
        if (precioBoleto < MINIMO_PRECIO) {
            throw new ValorInvalidoException("El mimino del precio no puede ser menor a " + MINIMO_PRECIO);
        }
    }
    
    private void existeSucursal(String sucursal) throws AccesoALaDataException, ValorInexistenteException {
        if (!sucursaldao.existeSucursal(sucursal)) {
            throw new ValorInexistenteException("No se pudo encontrar la sucursal " + sucursal);
        }
    }
    
    private void existeRutaSucursales(String sucursalOrigen, String sucursalDestino) throws AccesoALaDataException, ValorExistenteException {
        if (rutadao.existeRuta(sucursalOrigen, sucursalDestino)) {
            throw new ValorExistenteException("Ya existe una ruta registrada de la " + sucursalOrigen + " hacia " + sucursalDestino);
        }
    }
    
    private void existeRuta(String idRuta) throws AccesoALaDataException, ValorInexistenteException {
        if (!rutadao.existeRuta(idRuta)) {
            throw new ValorInexistenteException("No se pudo encontrar la ruta seleccionada");
        }
    }    
}
