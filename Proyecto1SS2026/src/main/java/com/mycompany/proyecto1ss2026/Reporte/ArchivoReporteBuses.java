/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto1ss2026.Reporte;

import com.mycompany.proyecto1ss2026.DAOs.ReporteDeSucursalDAO;
import com.mycompany.proyecto1ss2026.Exeptions.AccesoALaDataException;
import com.mycompany.proyecto1ss2026.Modelos.DataBase.BusDB;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 *
 * @author milton
 */
public class ArchivoReporteBuses {
    
    private String carpeta = "Reporte";
    private final String NOMBRE = "reporteBuses.html";
    private int contador;
    
    private final ReporteDeSucursalDAO reportedao;

    public ArchivoReporteBuses() {
        reportedao = new ReporteDeSucursalDAO();
        contador = 1;
    }
    
    public String getReporte(String ruta, String sucursal) throws AccesoALaDataException {
        if (ruta == null || ruta.isEmpty()) {
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
            writer.println(armarReporte(sucursal));
        } catch (IOException e) {
            throw new AccesoALaDataException("Error al construir el reporte de rutas " + e.getMessage());
        } 
        contador ++;
        return file.getAbsolutePath();
    }
    
    private String armarReporte(String sucursal) throws AccesoALaDataException {
        String reporte = EstilosReporte.ESTILOS;
        
        reporte += 
                """
                <body>
                    <h1>Reporte de Gastos</h1>
                    <table class="reporte">
                        <tr>
                            <th class="verde">Numero de Placa</th>
                            <th class="azul">Marca</th>
                            <th class="verde">Modelo</th>
                            <th class="azul">Capacidad</th>
                            <th class="verde">Estado Operativo</th>
                            <th class="azul">Kilometraje</th>
                            <th class="verde">Viajes Realizados</th>
                        </tr>
                """;
        List<BusDB> buses = reportedao.getReporteBuses(sucursal);
        for (BusDB bus : buses) {
            reporte += "<tr class=\"celeste\">";
            reporte += ("   <td>" + bus.getNumeroPlaca() + "</td>");
            reporte += ("   <td>" + bus.getMarca() + "</td>");
            reporte += ("   <td>" + bus.getModelo() + "</td>");
            reporte += ("   <td>" + bus.getCapacidadPasajeros() + "</td>");
            reporte += ("   <td>" + bus.isEstadoOperativo() + "</td>");
            reporte += ("   <td>" + bus.getKilometraje() + "</td>");
            reporte += ("   <td>" + bus.getViajesCompletados() + "</td>");
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
