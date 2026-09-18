/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto1ss2026.Reporte;

import com.mycompany.proyecto1ss2026.DAOs.ReporteAdministradorDAO;
import com.mycompany.proyecto1ss2026.Exeptions.AccesoALaDataException;
import com.mycompany.proyecto1ss2026.Modelos.Request.ReporteRutaDemandada;
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
public class ArchivoReporteRutasDemandadas {
    
    private String carpeta = "Reporte";
    private final String NOMBRE = "reporteRutasDemandadas.html";
    private int contador;
    
    private final ReporteAdministradorDAO reportedao;

    public ArchivoReporteRutasDemandadas() {
        reportedao = new ReporteAdministradorDAO();
        contador = 1;
    }
    
    public String getReporte(String ruta, String fechaInicial, String fechaFinal) throws AccesoALaDataException {
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
            writer.println(armarReporte(fechaInicial, fechaFinal));
        } catch (IOException e) {
            throw new AccesoALaDataException("Error al construir el reporte de rutas " + e.getMessage());
        } 
        contador ++;
        return file.getAbsolutePath();
    }
    
    private String armarReporte(String fechaInicial, String fechaFinal) throws AccesoALaDataException {
        
        LocalDate dateInicial = LocalDate.parse(fechaInicial);
        LocalDate dateFinal = LocalDate.parse(fechaFinal);
        
        String reporte = EstilosReporte.ESTILOS;
        
        reporte += 
                """
                <body>
                    <h1>Reporte de Rutas</h1>
                    <table class="reporte">
                        <tr>
                            <th class="verde">Id Ruta</th>
                            <th class="azul">Boletos Vendidos</th>
                            <th class="verde">Precio Boleto</th>
                            <th class="azul">Distancia</th>
                            <th class="verde">Sucursal Origen</th>
                            <th class="azul">Ciudad Origen</th>
                            <th class="verde">Sucursal Destino</th>
                            <th class="azul">Ciudad Destino</th>
                        </tr>
                """;
        List<ReporteRutaDemandada> rutas = reportedao.getRerporteRuta(dateInicial, dateFinal);
        for (ReporteRutaDemandada ruta : rutas) {
            reporte += "<tr class=\"celeste\">";
            reporte += ("   <td> " + ruta.getRutaId() + "</td>");
            reporte += ("   <td> " + ruta.getBoletosVendidos() + "</td>");
            reporte += ("   <td> " + ruta.getPrecioBoleto() + "</td>");
            reporte += ("   <td> " + ruta.getDistancia() + "</td>");
            reporte += ("   <td> " + ruta.getSucursalOrigen() + "</td>");
            reporte += ("   <td> " + ruta.getCiudadOrigen() + "</td>");
            reporte += ("   <td> " + ruta.getSucursalDestino() + "</td>");
            reporte += ("   <td> " + ruta.getCiudadDestino() + "</td>");
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
