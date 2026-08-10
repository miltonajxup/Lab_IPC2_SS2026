/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend.Menu;

import Exceptions.CreacionArchivoException;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

/**
 *
 * @author milton
 */
public class ExportarMenu {
    
    private final FormatoMenu formatoMenu;
    private final String ARCHIVO_MENU = "menu/";
    private final String EXTENSION = ".html";

    public ExportarMenu() {
        formatoMenu = new FormatoMenu();
    }
    
    public void exportarMenu() throws CreacionArchivoException {
        existeCarpeta();
        String nombreArchivo = "menu";
        String path = ARCHIVO_MENU + nombreArchivo + EXTENSION;
        try (PrintWriter writer = new PrintWriter(new FileWriter(path))) {
            formatoMenu.formatoMenu(writer);
        } catch (Exception e) {
            throw new CreacionArchivoException("Error al crear el html del menu " + e.getMessage());
        }
    }
    
    private void existeCarpeta() {
        File file = new File(ARCHIVO_MENU);
        if (!file.exists()) {
            file.mkdirs();
        }
    }
}
