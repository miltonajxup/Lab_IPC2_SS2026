/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend.Menu;

import DAOs.ProductoDAO;
import Exceptions.AccesoALaDataException;
import Modelos.Insumo;
import Modelos.ProductoMenu;
import java.io.PrintWriter;
import java.util.List;

/**
 *
 * @author milton
 */
public class FormatoMenu {
    
    private final ProductoDAO productodao;
    
    public FormatoMenu() {
        productodao = new ProductoDAO();
    }
    
    public void formatoMenu(PrintWriter writer) throws AccesoALaDataException {
        List<ProductoMenu> productos = productodao.getProductos();
        String inicio = """
                        <head>
                            <style>
                                body {
                                    font-family: system-ui, -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, 'Open Sans', 'Helvetica Neue', sans-serif;
                                    margin: 0;
                                }
                                .titulo {
                                    font-size: 20px;
                                    text-align: center;
                                    margin: 50px 50px;
                                    padding-right: 50px;
                                    border-bottom: 2px solid black;
                                }
                                .carta {
                                    background: linear-gradient(to right, rgb(130, 82, 11), rgb(226, 134, 48));
                                    width: 1200px;
                                    margin: auto;
                                    margin-top: 50px;
                                    padding-top: 5px;
                                    margin-bottom: 100px;
                                }
                                .cuadricula {
                                    display: grid;
                                    grid-template-columns: 1fr 1fr;
                                    width: 1200px;
                                }
                                .opcion {
                                    background: linear-gradient(to right, rgb(234, 194, 20), rgb(224, 115, 14));
                                    background-color: rgb(237, 151, 11);
                                    font-size: 25px;
                                    min-height: 100px;
                                    border: 2px solid black;
                                    margin: 15px;
                                    display: flex;
                                    gap: 20px;
                                }
                                .imagen {
                                    max-width: 300px;
                                    max-height: 300px;
                                }
                            </style>
                        </head>
                        <body>
                            <div class="carta">
                                <div class="titulo">
                                    <h1>Menu Practica 1</h1>
                                </div>
                                <div class="cuadricula">
                            """;
        writer.print(inicio);
        for (int i = 0; i < productos.size(); i++) {
            ProductoMenu actual = productos.get(i);
            String abrirOpcionMenu = "   <div class=\"opcion\"> \n <img class=\"imagen\" src=\"" 
                    + actual.getUrlImagen()+ "\" alt=\" imagen " + actual.getNombre() +" \">" 
                    + "<div>" + actual.getCategoria() + 
                      "<div>" + actual.getNombre() + "</div>"
                    + "<div>Ingredientes: </div> \n <ul>";
            writer.println(abrirOpcionMenu);
            for (int j = 0; j < actual.getInsumos().size(); j++) {
                Insumo insumo = actual.getInsumos().get(j);
                String lineaIngrediente = "<li>" + insumo.getNombre() + "</li>";
                writer.println(lineaIngrediente);
            }
            String cerrarOpcionMenu = "</ul> \n </div> \n </div>";
            writer.println(cerrarOpcionMenu);
        }
        String fin = """
                                </div>
                            </div>
                        </body>""";
        writer.println(fin);
    }
    
}

/*
                                <div class="cuadricula">
                                    <div class="opcion">
                                        <img class="imagen" src="">
                                        <div > Nombre 
                                            <div>Ingredientes</div>
                                            <ul>
                                                <li>1</li>
                                                <li>1</li>
                                                <li>1</li>
                                                <li>1</li>
                                            </ul>
                                        </div>
                                    </div>
*/