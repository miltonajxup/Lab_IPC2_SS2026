/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto1ss2026.Servicios;

import com.mycompany.proyecto1ss2026.DAOs.ReporteAdministradorDAO;
import com.mycompany.proyecto1ss2026.DAOs.ReporteDeSucursalDAO;
import com.mycompany.proyecto1ss2026.Exeptions.AccesoALaDataException;
import com.mycompany.proyecto1ss2026.Exeptions.ValorInvalidoException;
import com.mycompany.proyecto1ss2026.Modelos.Request.ReporteGanancia;
import com.mycompany.proyecto1ss2026.Modelos.Request.ReporteIngresoBoleto;
import com.mycompany.proyecto1ss2026.Modelos.Request.ReporteRutaDemandada;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 *
 * @author milton
 */
public class ServicioReporte {
    
    private final ReporteAdministradorDAO repAdministradordao;
    private final ReporteDeSucursalDAO repSucursaldao;
    
    public ServicioReporte() {
        repAdministradordao = new ReporteAdministradorDAO();
        repSucursaldao = new ReporteDeSucursalDAO();
    }
    
    public ReporteGanancia reporteGananciasTotales(String fechaInicial, String fechaFinal) throws AccesoALaDataException, ValorInvalidoException {
        if (datosInvalidos(fechaInicial, fechaFinal)) {
            return null;
        }
        LocalDate dateInicial = esFechaValida(fechaInicial);
        LocalDate dateFinal = esFechaValida(fechaFinal);
        return repAdministradordao.getTotalGanancia(dateInicial, dateFinal);
    }
    
    public List<ReporteGanancia> reporteGananciasGenerales(String fechaInicial, String fechaFinal) throws AccesoALaDataException, ValorInvalidoException {
        if (datosInvalidos(fechaInicial, fechaFinal)) {
            return null;
        }
        LocalDate dateInicial = esFechaValida(fechaInicial);
        LocalDate dateFinal = esFechaValida(fechaFinal);
        return repAdministradordao.getGanancias(dateInicial, dateFinal);
    }
    
    public List<ReporteRutaDemandada> reporteRutaDemandada(String fechaInicial, String fechaFinal) throws AccesoALaDataException, ValorInvalidoException {
        if (datosInvalidos(fechaInicial, fechaFinal)) {
            return null;
        }
        LocalDate dateInicial = esFechaValida(fechaInicial);
        LocalDate dateFinal = esFechaValida(fechaFinal);
        return repAdministradordao.getRerporteRuta(dateInicial, dateFinal);
    }
    
    public ReporteGanancia reporteGastosTotales(String fechaInicial, String fechaFinal) throws AccesoALaDataException, ValorInvalidoException {
        if (datosInvalidos(fechaInicial, fechaFinal)) {
            return null;
        }
        LocalDate dateInicial = esFechaValida(fechaInicial);
        LocalDate dateFinal = esFechaValida(fechaFinal);
        return repAdministradordao.getGastosOperativos(dateInicial, dateFinal);
    }
    
    public List<ReporteGanancia> reporteGastosGenerales(String fechaInicial, String fechaFinal) throws AccesoALaDataException, ValorInvalidoException {
        if (datosInvalidos(fechaInicial, fechaFinal)) {
            return null;
        }
        LocalDate dateInicial = esFechaValida(fechaInicial);
        LocalDate dateFinal = esFechaValida(fechaFinal);
        return repAdministradordao.getGastosOperativosSucursal(dateInicial, dateFinal);
    }
    
    public List<ReporteIngresoBoleto> reporteVentaBoletos(String sucursal, String fechaInicial, String fechaFinal) throws AccesoALaDataException, ValorInvalidoException {
        if (datosInvalidos(fechaInicial, fechaFinal)) {
            return null;
        }
        LocalDate dateInicial = esFechaValida(fechaInicial);
        LocalDate dateFinal = esFechaValida(fechaFinal);
        return repSucursaldao.getReporteBoletos(sucursal, dateInicial, dateFinal);
    }
    
    private boolean datosInvalidos(String fechaInicial, String fechaFinal) {
        return fechaInicial == null || fechaFinal == null || fechaInicial.isEmpty() || fechaFinal.isEmpty();
    }
    
    private LocalDate esFechaValida(String fecha) throws ValorInvalidoException {
        LocalDate date;
        try {
            date = LocalDate.parse(fecha);
        } catch (DateTimeParseException e) {
            throw new ValorInvalidoException("El valor " + fecha + " no es valido para una fecha");
        }
        return date;
    }
    
}
