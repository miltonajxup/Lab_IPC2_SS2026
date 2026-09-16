/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto1ss2026.Servicios;

import com.mycompany.proyecto1ss2026.DAOs.BusDAO;
import com.mycompany.proyecto1ss2026.DAOs.GastoTallerDAO;
import com.mycompany.proyecto1ss2026.Exeptions.AccesoALaDataException;
import com.mycompany.proyecto1ss2026.Exeptions.ValorInvalidoException;
import com.mycompany.proyecto1ss2026.Modelos.Request.GastoTaller;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 *
 * @author milton
 */
public class ServicioGastoTaller {
    
    private final GastoTallerDAO gastodao;
    private final BusDAO busdao;
    
    public ServicioGastoTaller() {
        gastodao = new GastoTallerDAO();
        busdao = new BusDAO();
    }
    
    public String agregarRegistroPago(String textoMontoManoDeObra, String textoMontoRepuestos, String textoFecha, String idBus) throws AccesoALaDataException, ValorInvalidoException {
        if (textoMontoManoDeObra == null || textoMontoRepuestos == null || textoFecha == null || idBus == null 
                || textoMontoManoDeObra.isEmpty() || textoMontoRepuestos.isEmpty() || textoFecha.isEmpty() || idBus.isEmpty()) {
            return null;
        }
        double montoManoObra = esValorValido(textoMontoManoDeObra, "de mano de obra");
        double montoRepuestos = esValorValido(textoMontoRepuestos, "de repuestos");
        LocalDate fecha;
        try {
            fecha = LocalDate.parse(textoFecha);
        } catch (DateTimeParseException e) {
            throw new ValorInvalidoException("El valor " + textoFecha + " no es valido para la fecha");
        }
        if (!busdao.existeBus(idBus)) {
            throw new ValorInvalidoException("El bus con numero de placa " + idBus + " no se encuentra registrado");
        }
        GastoTaller gasto = new GastoTaller(montoManoObra, montoRepuestos, fecha, idBus);
        gastodao.agregarRegistroGasto(gasto);
        return "El gasto de taller ha sido registrado";
    }
    
    private double esValorValido(String textoValor, String mensajeError) throws ValorInvalidoException {
        double valor;
        try {
            valor = Double.parseDouble(textoValor);
        } catch (NumberFormatException e) {
            throw new ValorInvalidoException("El valor " + textoValor + " no es valido para el monto " + mensajeError);
        }
        return valor;
    }
    
}
