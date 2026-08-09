/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend.Reporte;

import Modelos.ProductoVendido;
import java.io.PrintWriter;
import java.util.List;

/**
 *
 * @author milton
 */
public class FormatoReporteRanking {
    
    private final ControladorReportes controlador;
    private List<ProductoVendido> productosVendidos;

    public FormatoReporteRanking(ControladorReportes controlador) {
        this.controlador = controlador;
    }
    
    public void reporteRanking(PrintWriter printer) {
        productosVendidos = controlador.getProductosVendidos();
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
                                .verde { margin: 0px; background-color: rgb(234, 157, 94); padding: 8px; }
                                .azul { margin: 0px; background-color: rgb(98, 171, 234); padding: 8px; }
                                .celda { margin: 3px; border: 2px solid black; min-width: 350px; padding: 5px; text-align: center; }
                                table { font-size: 25px; }
                            </style>
                        </head>
                        <body>
                            <h1>Reportes de Practica 1</h1>
                            <div class="principal">
                                <div class="titulo">
                                    <h2>Reporte del Ranking de Productos</h2>
                                </div>
                                <div class="texto">
                                    Productos Vendidos ordenados del mas vendido al menos
                                </div>
                                <table>
                                    <tr>
                                        <th class="verde">Codigo del Producto</th>
                                        <th class="azul">Nombre</th>
                                        <th class="verde">Numero de veces Vendida</th>
                                    </tr>
                        """;
        printer.print(inicio);
        for (int i = 0; i < productosVendidos.size(); i++) {
            ProductoVendido actual = productosVendidos.get(i);
            String reporte = "<tr> <td class=\"celda\"> " + actual.getCodigoProducto() 
                         + " </td> <td class=\"celda\"> " + actual.getNombreProducto() 
                         + " </td> <td class=\"celda\"> " + actual.getVecesVendida() + " </td> </tr>";
            printer.println(reporte);
        }
        String fin = """
                                </table>
                            </div>
                        </body>
                     """;
        printer.println(fin);
    }
    
}
