/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend.Reporte;

import Exceptions.CreacionArchivoException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author milton
 */
public class ExportarReporte {
    
    private final FormatoReporteCaja reporteCaja;
    private final FormatoReporteRanking reporteRanking;
    private final FormatoReporteInsumos reporteInsumos;
    private final String ARCHIVO_REPORTE = "reportes/";
    private final String EXTENSION = ".html";

    public ExportarReporte(FormatoReporteRanking reporteRanking, FormatoReporteInsumos reporteInsumos) {
        this.reporteCaja = new FormatoReporteCaja();
        this.reporteRanking = reporteRanking;
        this.reporteInsumos = reporteInsumos;
    }
    
    public void exportarReporteCaja(String fecha, String gastosInsumo, double gastosPagos, double ventas, String ganancias) throws CreacionArchivoException {
        String nombreReporte = "reporte_caja";
        existeCarpeta();
        String ruta = ARCHIVO_REPORTE + nombreReporte + EXTENSION;
        try (PrintWriter writer = new PrintWriter(new FileWriter(ruta))) {
            String reporte = reporteCaja.reporteCaja(fecha, gastosInsumo, gastosPagos, ventas, ganancias);
            writer.print(reporte);
        } catch (IOException e) {
            throw new CreacionArchivoException("Erro al generar el html del Reporte de Caja " + e.getMessage());
        }
    }
    
    public void exportarReporteRanking() throws CreacionArchivoException {
        String nombreReporte = "reporte_ranking";
        existeCarpeta();
        String ruta = ARCHIVO_REPORTE + nombreReporte + EXTENSION;
        try (PrintWriter writer = new PrintWriter(new FileWriter(ruta))) {
            reporteRanking.reporteRanking(writer);
        } catch (IOException e) {
            throw new CreacionArchivoException("Error al generar el html del Reporte de Ranking " + e.getMessage());
        }
    }
    
    public void exportarReporteInsumos() throws CreacionArchivoException {
        String nombreReporte = "reporte_insumos";
        existeCarpeta();
        String ruta = ARCHIVO_REPORTE + nombreReporte + EXTENSION;
        try (PrintWriter writer = new PrintWriter(new FileWriter(ruta))) {
            reporteInsumos.reporteInsumos(writer);
        } catch (IOException e) {
            throw new CreacionArchivoException("Error al generar el html del Reporte de Insumos " + e.getMessage());
        }
    }
    
    private void existeCarpeta() {
        File file = new File(ARCHIVO_REPORTE);
        if (!file.exists()) {
            file.mkdirs();
        }
    }
    
}
