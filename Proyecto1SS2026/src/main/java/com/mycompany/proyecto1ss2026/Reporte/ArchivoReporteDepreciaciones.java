/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto1ss2026.Reporte;

import com.mycompany.proyecto1ss2026.DAOs.ReporteDeSucursalDAO;
import com.mycompany.proyecto1ss2026.Exeptions.AccesoALaDataException;
import com.mycompany.proyecto1ss2026.Modelos.DataBase.ChoferDB;
import com.mycompany.proyecto1ss2026.Modelos.Request.ReporteDepreciacionBus;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 *
 * @author milton
 */
public class ArchivoReporteDepreciaciones {
    
    private String carpeta = "Reporte";
    private final String NOMBRE = "reporteDepreciaciones.html";
    private int contador;
    
    private final ReporteDeSucursalDAO reportedao;

    public ArchivoReporteDepreciaciones() {
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
            throw new AccesoALaDataException("Error al construir el reporte de depreciaciones " + e.getMessage());
        } 
        contador ++;
        return file.getAbsolutePath();
    }
    
    private String armarReporte(String sucursal) throws AccesoALaDataException {
        String reporte = EstilosReporte.ESTILOS;
        
        reporte += 
                """
                <body>
                    <h1>Reporte de Depreciaciones</h1>
                    <table class="reporte">
                        <tr>
                            <th class="verde">Numero de Placa del Bus</th>
                            <th class="azul">Monto de Depreciacion</th>
                            <th class="verde">Kilometros Recorridos</th>
                        </tr>
                """;
        List<ReporteDepreciacionBus> dep = reportedao.getReporteDepreciacionBus(sucursal);
        for (ReporteDepreciacionBus chofer : dep) {
            reporte += "<tr class=\"celeste\">";
            reporte += ("   <td>" + chofer.getBus()+ "</td>");
            reporte += ("   <td>" + chofer.getMontoDepreciacion()+ "</td>");
            reporte += ("   <td>" + chofer.getKilometrosRecorridos()+ "</td>");
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
