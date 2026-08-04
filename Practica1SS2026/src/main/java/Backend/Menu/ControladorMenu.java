/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend.Menu;

import Frontent.MenuProducto;
import Modelos.ProductoMenu;
import java.util.List;

/**
 *
 * @author milton
 */
public class ControladorMenu {
    
    private final List<ProductoMenu> productos;
    private final MenuProducto menuProductos;

    public ControladorMenu(List<ProductoMenu> productos, MenuProducto menuProductos) {
        this.productos = productos;
        this.menuProductos = menuProductos;
    }

    public void colocarProductos() {
        
    }
    
}
