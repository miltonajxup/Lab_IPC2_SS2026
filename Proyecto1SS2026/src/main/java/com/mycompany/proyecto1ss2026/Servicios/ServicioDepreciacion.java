/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto1ss2026.Servicios;

import com.mycompany.proyecto1ss2026.DAOs.DepreciacionDAO;
import com.mycompany.proyecto1ss2026.Exeptions.AccesoALaDataException;
import com.mycompany.proyecto1ss2026.Exeptions.ValorInvalidoException;

/**
 *
 * @author milton
 */
public class ServicioDepreciacion {
    
    private final double MINIMO_MONTO = 0;
    private final DepreciacionDAO depreciaciondao;
    
    public ServicioDepreciacion() {
        depreciaciondao = new DepreciacionDAO();
    }
    
    public void modificarDepreciacion(String textoMonto) throws AccesoALaDataException, ValorInvalidoException {
        if (textoMonto == null || textoMonto.isEmpty()) {
            return;
        }
        double montoDepreciacion;
        try {
            montoDepreciacion = Double.parseDouble(textoMonto);
        } catch (NumberFormatException e) {
            throw new ValorInvalidoException("El valor " + textoMonto + " no es un valor valido para el monto");
        }
        if (montoDepreciacion < MINIMO_MONTO) {
            throw new ValorInvalidoException("El Monto de Depreciacion no puede ser menor a " + MINIMO_MONTO);
        }
        depreciaciondao.agregarMontoDepreciacion(montoDepreciacion);
    }
    
}
