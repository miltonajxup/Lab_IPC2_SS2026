/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend.Reporte;

import DAOs.InsumoDAO;
import DAOs.ReporteDAO;
import Exceptions.AccesoALaDataException;
import Exceptions.CreacionArchivoException;
import Exceptions.ErrorIngresarDatosException;
import Frontent.Reporte.VentanaReporte;
import Modelos.GastoInsumo;
import Modelos.Insumo;
import Modelos.ProductoVendido;
import java.util.List;

/**
 *
 * @author milton
 */
public class ControladorReportes {
    
    private final VentanaReporte ventanaReporte;
    private final InsumoDAO insumodao;
    private final ReporteDAO reportedao;
    private final ExportarReporte exportarReporte;
    private List<GastoInsumo> gastoInsumos;
    private List<ProductoVendido> productosVendidos;
    private List<Insumo> insumos;
    private double gastosDeInsumos;
    private double gastosPagos;
    private double totalVentas;
    private double gananciasTotales;

    public ControladorReportes(VentanaReporte ventanaReporte, InsumoDAO insumodao) {
        this.ventanaReporte = ventanaReporte;
        this.insumodao = insumodao;
        this.reportedao = new ReporteDAO();
        FormatoReporteRanking formatoRanking = new FormatoReporteRanking(this);
        FormatoReporteInsumos formatoInsumos = new FormatoReporteInsumos(this);
        this.exportarReporte = new ExportarReporte(formatoRanking, formatoInsumos);
    }
    
    public List<ProductoVendido> getProductosVendidos() {
        return productosVendidos;
    }
    
    public List<Insumo> getInsumos() {
        return insumos;
    }
    
    public void agregarReportes() throws AccesoALaDataException {
        gastoInsumos = reportedao.getGastosInsumos();
        gastosPagos = reportedao.getTotalPagos();
        totalVentas = reportedao.getTotalVentas();
        agregarReporteCaja();
        agregarReporteRanking();
        agregarReporteProductosStock();
    }
    
    public void pedirReportesFechas() throws AccesoALaDataException, ErrorIngresarDatosException {
        String diaInicialTexto = ventanaReporte.getDiaInicial();
        String diaFinalTexto = ventanaReporte.getDiaFinal();
        String mesInicialTexto = ventanaReporte.getMesInicial();
        String mesFinalTexto = ventanaReporte.getMesFinal();
        String añoInicialTexto = ventanaReporte.getAñoInicial();
        String añoFinalTexto = ventanaReporte.getAñoFinal();
        
        if (diaInicialTexto.isEmpty() && diaFinalTexto.isEmpty() && mesInicialTexto.isEmpty() && mesFinalTexto.isEmpty() && añoFinalTexto.isEmpty() && añoInicialTexto.isEmpty()) {
            agregarReportes();
            return;
        }
        
        String fechaInicial = procesarFecha(diaInicialTexto, mesInicialTexto, añoInicialTexto);
        String fechaFinal = procesarFecha(diaFinalTexto, mesFinalTexto, añoFinalTexto);
        
        gastoInsumos = reportedao.getGastosInsumosEntreFechas(fechaInicial, fechaFinal);
        gastosPagos = reportedao.getTotalPagosFecha(fechaInicial, fechaFinal);
        totalVentas = reportedao.getTotalVentasFecha(fechaInicial, fechaFinal);
        agregarReporteCaja();
    }
    
    public void exportarReporteCaja() throws AccesoALaDataException, ErrorIngresarDatosException, CreacionArchivoException {
        String diaInicialTexto = ventanaReporte.getDiaInicial();
        String diaFinalTexto = ventanaReporte.getDiaFinal();
        String mesInicialTexto = ventanaReporte.getMesInicial();
        String mesFinalTexto = ventanaReporte.getMesFinal();
        String añoInicialTexto = ventanaReporte.getAñoInicial();
        String añoFinalTexto = ventanaReporte.getAñoFinal();
        
        String gastosInsumo = String.format("%.2f", gastosDeInsumos);
        String ganancias = String.format("%.2f", gananciasTotales);
        if (diaInicialTexto.isEmpty() && diaFinalTexto.isEmpty() && mesInicialTexto.isEmpty() && mesFinalTexto.isEmpty() && añoFinalTexto.isEmpty() && añoInicialTexto.isEmpty()) {
            exportarReporte.exportarReporteCaja(null, gastosInsumo, gastosPagos, totalVentas, ganancias);
        } else {
            String fechaInicial = procesarFecha(diaInicialTexto, mesInicialTexto, añoInicialTexto);
            String fechaFinal = procesarFecha(diaFinalTexto, mesFinalTexto, añoFinalTexto);
            String fecha = fechaInicial + " -- " + fechaFinal;
            exportarReporte.exportarReporteCaja(fecha, gastosInsumo, gastosPagos, totalVentas, ganancias);
        }
        ventanaReporte.mostrarMensaje("El Reporte se ha exportado con exito");
    }
    
    public void exportarReporteRanking() throws CreacionArchivoException {
        exportarReporte.exportarReporteRanking();
        ventanaReporte.mostrarMensaje("El Reporte se ha exportado con exito");

    }
    
    public void exportarReporteInsumos() throws CreacionArchivoException {
        exportarReporte.exportarReporteInsumos();
        ventanaReporte.mostrarMensaje("El Reporte se ha exportado con exito");
    }
    
    private void agregarReporteCaja() throws AccesoALaDataException {
        calcularGastoInsumos();
        double totalGastos = gastosPagos + gastosDeInsumos;
        String gastos = String.format("%.2f", totalGastos);
        gananciasTotales = totalVentas - totalGastos;
        String ganancias = String.format("%.2f", gananciasTotales);
        ventanaReporte.setInformacionCaja(gastos, totalVentas, ganancias);
    }
    
    private void agregarReporteRanking() throws AccesoALaDataException {
        productosVendidos = reportedao.getRankingProductos();
        ventanaReporte.setFilasRanking(productosVendidos.size());
        for (int i = 0; i < productosVendidos.size(); i++) {
            ProductoVendido actual = productosVendidos.get(i);
            ventanaReporte.agregarProductoRanking(actual.getVecesVendida(), actual.getNombreProducto(), actual.getCodigoProducto());
        }
    }
    
    private void agregarReporteProductosStock() throws AccesoALaDataException {
        ventanaReporte.limpiarProducto();
        int filas = 0;
        for (int i = 0; i < insumos.size(); i++) {
            Insumo insumo = insumos.get(i);
            if (insumo.getCantidadStock() <= insumo.getStockMinimo()) {
                filas++;
                ventanaReporte.setFilasProducto(filas);
                ventanaReporte.agregarProducto(insumo.getNombre(), insumo.getCantidadStock(), insumo.getUndadMedida(), insumo.getStockMinimo());
            }
        }
        if (filas <= 0) {
            ventanaReporte.agregarInfoProducto("Actualmente no hay productos con el stock minimo");
        }
    }
    
    private void calcularGastoInsumos() throws AccesoALaDataException {
        insumos = insumodao.getTodosInsumos();
        gastosDeInsumos = 0;
        for (int i = 0; i < gastoInsumos.size(); i++) {
            GastoInsumo gastoActual = gastoInsumos.get(i);
            for (int j = 0; j < insumos.size(); j++) {
                Insumo insumoActual = insumos.get(j);
                if (gastoActual.getCodigo() == insumoActual.getCodigo()) {
                    gastosDeInsumos = gastosDeInsumos + (gastoActual.getUnidadesInsumo() * insumoActual.getCosto());
                    break;
                }
            }
        }
    }
    
    private String procesarFecha(String diaTexto, String mesTexto, String añoTexto) throws AccesoALaDataException, ErrorIngresarDatosException {
        int dia;
        int mes;
        int año;
        if (diaTexto.isEmpty() || mesTexto.isEmpty() || añoTexto.isEmpty()) {
            throw new ErrorIngresarDatosException("No se puede tener los campos de fecha vacios");
        }
        try {
            dia = Integer.parseInt(diaTexto);
            mes = Integer.parseInt(mesTexto);
            año = Integer.parseInt(añoTexto);
        } catch (NumberFormatException e) {
            throw new ErrorIngresarDatosException("Las fechas deben ser ingresadas en numeros");
        }
        if (dia <= 0 || mes <= 0 || año <= 0) {
            throw new ErrorIngresarDatosException("Las fechas deben ingresarse con numeros positivos");
        }
        if (dia > 30 || mes > 12 || año < 2025) {
            throw new ErrorIngresarDatosException("Las fechas deben ser logicas ademas de ser posteiores a 2024");
        }
        String fecha = año + "-" + mes + "-" + dia;
        return fecha;
    }
    
}
