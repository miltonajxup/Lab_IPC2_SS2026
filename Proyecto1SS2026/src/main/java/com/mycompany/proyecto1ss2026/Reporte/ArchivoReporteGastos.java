/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto1ss2026.Reporte;

import com.mycompany.proyecto1ss2026.DAOs.ReporteAdministradorDAO;
import com.mycompany.proyecto1ss2026.Exeptions.AccesoALaDataException;
import com.mycompany.proyecto1ss2026.Modelos.Request.ReporteGanancia;
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
public class ArchivoReporteGastos {
    
    private String carpeta = "Reporte";
    private final String NOMBRE = "reporteGastos.html";
    private int contador;
    
    private final ReporteAdministradorDAO reportedao;

    public ArchivoReporteGastos() {
        reportedao = new ReporteAdministradorDAO();
        contador = 1;
    }
    
    public String getReporte(String ruta, String fechaInicial, String fechaFinal, boolean totales) throws AccesoALaDataException {
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
            writer.println(armarReporte(fechaInicial, fechaFinal, totales));
        } catch (IOException e) {
            throw new AccesoALaDataException("Error al construir el reporte de rutas " + e.getMessage());
        } 
        contador ++;
        return file.getAbsolutePath();
    }
    
    private String armarReporte(String fechaInicial, String fechaFinal, boolean totales) throws AccesoALaDataException {
        
        LocalDate dateInicial = LocalDate.parse(fechaInicial);
        LocalDate dateFinal = LocalDate.parse(fechaFinal);
        
        String reporte = EstilosReporte.ESTILOS;
        reporte += 
                """
                <body>
                    <h1>Reporte de Gastos</h1>
                    <table class="reporte">
                        <tr>
                """;
        if (!totales) {
            reporte +=
                    """
                            <th class="verde">Sucursal</th>
                    """;
        }
        reporte += 
                """
                            <th class="azul">Gasto Combustible</th>
                            <th class="verde">Gasto de Taller (Mano de Obra)</th>
                            <th class="azul">Gasto de Taller (Repuestos)</th>
                            <th class="verde">Depreciacion</th>
                            <th class="azul">Total Gastos</th>
                        </th>
                    
                """;
        
        if (totales) {
            reporte += "<tr class=\"celeste\">";
            ReporteGanancia rep = reportedao.getGastosOperativos(dateInicial, dateFinal);
            reporte += ("<td >" + rep.getGastoCombustible()+ "</td>"); 
            reporte += ("<td >" + rep.getGastoTallerMano()+ "</td>"); 
            reporte += ("<td >" + rep.getGastoTallerRepuestos()+ "</td>"); 
            reporte += ("<td >" + rep.getDepreciacion()+ "</td>"); 
            reporte += ("<td >" + rep.getTotalGastos()+ "</td>"); 
            reporte += "</tr>";
        } else {
            List<ReporteGanancia> reps = reportedao.getGanancias(dateInicial, dateFinal);
            for (ReporteGanancia rep : reps) {
                reporte += "<tr class=\"celeste\">";
                reporte += ("<td >" + rep.getSucursal()+ "</td>");
                reporte += ("<td >" + rep.getGastoCombustible()+ "</td>"); 
                reporte += ("<td >" + rep.getGastoTallerMano()+ "</td>"); 
                reporte += ("<td >" + rep.getGastoTallerRepuestos()+ "</td>"); 
                reporte += ("<td >" + rep.getDepreciacion()+ "</td>"); 
                reporte += ("<td >" + rep.getTotalGastos()+ "</td>"); 
                reporte += "</tr>";
            }
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
