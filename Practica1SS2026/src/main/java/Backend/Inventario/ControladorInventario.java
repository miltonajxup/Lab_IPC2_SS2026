/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend.Inventario;

import DAOs.InsumoDAO;
import DAOs.ProductoDAO;
import Exceptions.AccesoALaDataException;
import Frontent.OpcionMenu;
import Frontent.ServicioInventario;
import Modelos.Insumo;
import Modelos.ProductoMenu;
import java.util.List;

/**
 *
 * @author milton
 */
public class ControladorInventario {
    
    private final ServicioInventario servicioInventario;
    private final InsumoDAO insumodao;
    private List<Insumo> insumos;
    private final ProductoDAO productodao;
    private List<ProductoMenu> productos;

    public ControladorInventario(ServicioInventario servicioInventario, InsumoDAO insumodao, ProductoDAO productodao) {
        this.servicioInventario = servicioInventario;
        this.insumodao = insumodao;
        this.productodao = productodao;
    }
    
    public void colocarInventario() throws AccesoALaDataException {
        productos = productodao.getProductos();
        servicioInventario.setNumeroFilasProductos(productos.size());
        insumos = insumodao.getTodosInsumos();
        servicioInventario.setNumeroFilasInsumo(insumos.size());
        for (int i = 0; i < productos.size(); i++) {
            ProductoMenu actual = productos.get(i);
            OpcionMenu opcionMenu = new OpcionMenu(actual.getNombre(), actual.getPrecio(), "/imagenes/cafe1.jpg");
            opcionMenu.setCuadricula(actual.getInsumos().size());
            for (int j = 0; j < actual.getInsumos().size(); j++) {
                Insumo insumoProducto = actual.getInsumos().get(j);
                opcionMenu.agregarIngrediente(insumoProducto.getNombre() + " x " + insumoProducto.getCantUtilizadaProducto() + insumoProducto.getUndadMedida());
            }
            servicioInventario.agregarProducto(opcionMenu);
        }
        for (int i = 0; i < insumos.size(); i++) {
            Insumo insumo = insumos.get(i);
            String informacionInsumo = insumo.getNombre() + ": " + insumo.getCantidadStock() 
                    + " |  Cant Disp: " + insumo.getStockMinimo() + insumo.getUndadMedida();
            servicioInventario.agregarInsumo(informacionInsumo);
        }
    }
    
}
