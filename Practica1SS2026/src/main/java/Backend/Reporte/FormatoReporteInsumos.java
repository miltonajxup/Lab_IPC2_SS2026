/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend.Reporte;

import Modelos.Insumo;
import java.io.PrintWriter;
import java.util.List;

/**
 *
 * @author milton
 */
public class FormatoReporteInsumos {
    
    private final ControladorReportes controlador;
    
    public FormatoReporteInsumos(ControladorReportes controlador) {
        this.controlador = controlador;
    }
    
    public void reporteInsumos(PrintWriter writer) {
        List<Insumo> insumos = controlador.getInsumos();
        String inicio = """
                        <head>
                            <style>
                                body {
                                    font-family: system-ui, -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, 
                                    'Open Sans', 'Helvetica Neue', sans-serif;
                                    font-size: 25px;
                                }
                                h1 { text-align: center; color: rgb(10, 114, 200); }
                                .principal { margin: 50px; padding: 20px; border: 2px solid black; border-radius: 15px; }
                                .titulo { display: flex; justify-content: center; }
                                .texto { font-size: 30px; padding-bottom: 20px; display: flex; justify-content: center; }
                                .texto2 { font-size: 30px; padding: 20px; display: flex; justify-content: center; 
                                    border: 2px solid black; border-radius: 5px; margin: 30px; }
                                .verde { margin: 0px; background-color: rgb(234, 157, 94); padding: 8px; }
                                .azul { margin: 0px; background-color: rgb(98, 171, 234); padding: 8px; }
                                .celda { margin: 3px; border: 2px solid black; min-width: 300px; padding: 5px; text-align: center; }
                                table { font-size: 25px; }
                            </style>
                        </head>
                        <body>
                            <h1>Reportes de Practica 1</h1>
                            <div class="principal">
                                <div class="titulo">
                                    <h2>Reporte de los Insumos Escasos</h2>
                                </div>
                                <div class="texto">
                                    Productos Vendidos ordenados del mas vendido al menos
                                </div>
                                <table>
                                    <tr>
                                        <th class="verde">Codigo del Insumo</th>
                                        <th class="azul">Nombre</th>
                                        <th class="verde">Cantidad en Stock</th>
                                        <th class="azul">Cantidad minima</th>
                                        <th class="verde">Unidad de medida</th>
                                    </tr>
                        """;
        writer.print(inicio);
        int registros = 0;
        for (int i = 0; i < insumos.size(); i++) {
            Insumo insumo = insumos.get(i);
            if (insumo.getCantidadStock() <= insumo.getStockMinimo()) {
                registros++;
                String reporte = "<tr> <td class=\"celda\"> " + insumo.getCodigo() 
                              + "</td> <td class=\"celda\"> " + insumo.getNombre() 
                             + " </td> <td class=\"celda\"> " + insumo.getCantidadStock() 
                             + " </td> <td class=\"celda\"> " + insumo.getStockMinimo() 
                              + "</td> <td class=\"celda\"> " + insumo.getUndadMedida() + "</td> </tr>";
                writer.println(reporte);
            }
        }
        if (registros <= 0) {
            String informacion = "</table> <div class=\"texto2\">Por el momento no hay insumos con la cantidad o debajo de la cantidad minima</div>";
            writer.println(informacion);
        } else {
            writer.println("</table>");
        }
        String fin = """
                            </div>
                        </body>
                     """;
        writer.println(fin);
    }
    
}
