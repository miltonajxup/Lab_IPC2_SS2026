/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto1ss2026.Reporte;

import com.mycompany.proyecto1ss2026.DAOs.ReporteDeSucursalDAO;
import com.mycompany.proyecto1ss2026.Exeptions.AccesoALaDataException;
import com.mycompany.proyecto1ss2026.Modelos.Request.ReporteIngresoBoleto;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author milton
 */
public class ArchivoReporteBoletos {
    
    private String carpeta = "Reporte";
    private final String NOMBRE = "reporteVentaBoletos.html";
    private int contador;
    
    private final ReporteDeSucursalDAO reportedao;

    public ArchivoReporteBoletos() {
        reportedao = new ReporteDeSucursalDAO();
        contador = 1;
    }
    
    public String getReporte(String ruta, String fechaInicial, String fechaFinal, String sucursal) throws AccesoALaDataException {
        if (fechaInicial == null || fechaFinal == null || fechaInicial.isEmpty() || fechaFinal.isEmpty()
                || ruta == null || ruta.isEmpty()) {
            return null;
        }
        
        File guardado = new File(ruta);
        if (!guardado.isDirectory()) {
            return null;
        }
        carpeta = ruta;
        
        File file = new File(carpeta, contador + "_" + NOMBRE);
        
        existeCarpeta();
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            writer.println(armarRerporte(sucursal, fechaInicial, fechaFinal));
        } catch (IOException e) {
            throw new AccesoALaDataException("Error al generar el reporte de ganancias " + e.getMessage());
        } 
        contador++;
        return file.getAbsolutePath();
    }
    
    public String armarRerporte(String sucursal, String fechaInicial, String fechaFinal) throws AccesoALaDataException {
        
        LocalDate dateInicial = LocalDate.parse(fechaInicial);
        LocalDate dateFinal = LocalDate.parse(fechaFinal);
        
        String reporte = EstilosReporte.ESTILOS;
        reporte += 
                """
                <body>
                    <h1>Reporte de Ingreso de Boletos</h1>
                    <table class="reporte">
                        <tr>
                            <th class="verde">Viaje Id</th>
                            <th class="azul">Numero de Licencia del Chofer</th>
                            <th class="verde">Nombre del Chofer</th>
                            <th class="azul">Numero de Placa del Bus</th>
                            <th class="verde">Id de la Ruta</th>
                            <th class="azul">Distancia (km)</th>
                            <th class="verde">Precio del Boleto</th>
                            <th class="azul">Sucursal de Origen</th>
                            <th class="verde">Sucursal de Destino</th>
                            <th class="azul">Fecha de Salida</th>
                            <th class="verde">Boletos Vendidos</th>
                            <th class="azul">Ingreso Total</th>
                        </tr>
                """;
        
        List<ReporteIngresoBoleto> reps = reportedao.getReporteBoletos(sucursal, dateInicial, dateFinal);
        for (ReporteIngresoBoleto rep : reps) {
            reporte += "<tr class=\"celeste\">";
            reporte += ("<td >" + rep.getViajeId()+ "</td>");
            reporte += ("<td >" + rep.getLicencia()+ "</td>"); 
            reporte += ("<td >" + rep.getChofer()+ "</td>"); 
            reporte += ("<td >" + rep.getBus()+ "</td>"); 
            reporte += ("<td >" + rep.getRutaId()+ "</td>"); 
            reporte += ("<td >" + rep.getDistancia()+ "</td>"); 
            reporte += ("<td >" + rep.getPrecioBoleto()+ "</td>"); 
            reporte += ("<td >" + rep.getSucursalOrigen()+ "</td>"); 
            reporte += ("<td >" + rep.getSucursalDestino()+ "</td>"); 
            reporte += ("<td >" + rep.getFechaSalida()+ "</td>"); 
            reporte += ("<td >" + rep.getBoletosVendidos()+ "</td>"); 
            reporte += ("<td >" + rep.getIngresoTotal()+ "</td>"); 
            reporte += "</tr>";
        }
        
        reporte += 
                """
                    </table>
                </body>
                """;
        
        return reporte;
    }
    
    private void existeCarpeta() {
        File file = new File(carpeta);
        if (!file.exists()) {
            file.mkdirs();
        }
    }
    
}
