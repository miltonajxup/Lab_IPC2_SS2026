/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend.Menu;

import DAOs.ProductoDAO;
import Exceptions.AccesoALaDataException;
import Frontent.MenuProducto;
import Frontent.OpcionMenu;
import Modelos.Insumo;
import Modelos.ProductoMenu;
import java.util.List;

/**
 *
 * @author milton
 */
public class ControladorMenu {
    
    private final ProductoDAO productodao; 
    private List<ProductoMenu> productos;
    private final MenuProducto menuProductos;

    public ControladorMenu(MenuProducto menuProductos, ProductoDAO productodao, List<ProductoMenu> productos) {
        this.productodao = productodao;
        this.productos = productos;
        this.menuProductos = menuProductos;
    }
    
    public void actualizarProductos() throws AccesoALaDataException {
        productos = productodao.getProductos();
        colocarProductos();
    }

    public void colocarProductos() {
        menuProductos.setCuadricula(productos.size() / 2 + 1);
        for (int i = 0; i < productos.size(); i++) {
            ProductoMenu producto = productos.get(i);
            OpcionMenu opcionMenu = new OpcionMenu(producto.getNombre(), producto.getPrecio(), "/imagenes/cafe1.jpg");
            menuProductos.agregarProducto(opcionMenu);
            opcionMenu.setCuadricula(producto.getInsumos().size());
            for (int j = 0; j < producto.getInsumos().size(); j++) {
                Insumo insumo = producto.getInsumos().get(j);
                opcionMenu.agregarIngrediente(insumo.getNombre());
            }
        }
    }
    
}
