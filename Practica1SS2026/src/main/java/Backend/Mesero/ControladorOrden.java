/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend.Mesero;

import DAOs.ProductoDAO;
import Frontent.OpcionMenu;
import Frontent.PlantillaOrden;
import Frontent.ServicioDeOrden;
import Modelos.Insumo;
import Modelos.InsumoPedido;
import Modelos.ProductoMenu;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author milton
 */
public class ControladorOrden {
    
    private final ServicioDeOrden servicioOrden;
    private final ProductoDAO productodao;
    private List<ProductoMenu> productosdb;
    private final List<Insumo> insumos;
    private List<ProductoMenu> productosPedido;
    private final List<InsumoPedido> insumosPedido;
    private List<PlantillaOrden> plantillasOrden;

    public ControladorOrden(ServicioDeOrden servicioOrden, List<Insumo> insumos, List<InsumoPedido> insumosPedido) {
        this.servicioOrden = servicioOrden;
        this.productodao = new ProductoDAO();
        this.productosdb = productodao.getProductos();
        this.insumos = insumos;
        this.productosPedido = new ArrayList<>();
        this.insumosPedido = insumosPedido;
        this.plantillasOrden = new ArrayList<>();
    }
    
    public void actualizarProductos() {
        productosdb = productodao.getProductos();
        colocarProductos();
    }
    
    public void colocarProductos() {
        servicioOrden.setCuadricula(productosdb.size());
        for (int i = 0; i < productosdb.size(); i++) {
            ProductoMenu producto = productosdb.get(i);
            OpcionMenu opcionMenu = new OpcionMenu(this, producto, "/imagenes/cafe1.jpg");
            servicioOrden.agregarProducto(opcionMenu);
            opcionMenu.setCuadricula(producto.getInsumos().size());
        }
    }
    
    public void agregarAPedido(ProductoMenu producto) {
        for (int i = 0; i < producto.getInsumos().size(); i++) {
            Insumo insumoProducto = producto.getInsumos().get(i);
            Insumo insumoLista = buscarInsumo(insumoProducto.getCodigo());
            boolean existenUnidadesInsumo = agregarInsumo(insumoProducto.getCodigo(), insumoProducto.getCantUtilizadaProducto(), insumoLista.getCantidadStock(), insumoLista.getStockMinimo());
            PlantillaOrden plantillaLista = existePlantilla(producto);
            if (existenUnidadesInsumo && plantillaLista == null) {
                PlantillaOrden plantilla = new PlantillaOrden(this, producto, 1);
                servicioOrden.agregarPedido(plantilla);
                productosPedido.add(producto);
                plantillasOrden.add(plantilla);
                return;
            } else if (existenUnidadesInsumo && plantillaLista != null) {
                plantillaLista.agregarUnidad(1);
                return;
            } else {
                servicioOrden.mostrarMensaje("No hay " +insumoLista.getNombre()+" suficientes añadir este elemento a la orden");
                return;
            }
        }
    }
    
    private boolean agregarInsumo(int codigo, double cantidadUtilizada, double cantidadRestante, double limite) {
        double suma;
        for (int i = 0; i < insumosPedido.size(); i++) {
            InsumoPedido actual = insumosPedido.get(i);
            if (actual.getCodigo() == codigo) {
                suma = actual.getCantidad() + cantidadUtilizada;
                cantidadRestante = cantidadRestante - suma;
                if (cantidadRestante >= limite) {
                    actual.setCantidad(cantidadUtilizada);
                    return true;
                }
            }
        }
        return false;
    }
    
    private Insumo buscarInsumo(int codigo) {
        for (int i = 0; i < insumos.size(); i++) {
            Insumo actual = insumos.get(i);
            if (actual.getCodigo() == codigo) {
                return actual;
            }
        }
        return null;
    }
    
    private PlantillaOrden existePlantilla(ProductoMenu producto) {
        for (int i = 0; i < plantillasOrden.size(); i++) {
            PlantillaOrden plantilla = plantillasOrden.get(i);
            if (plantilla.getCodigo() == producto.getCodigo()) {
                return plantilla;
            } 
        }
        return null;
    }
    
    public void reiniciarInsumos() {
        for (int i = 0; i < insumosPedido.size(); i++) {
            insumosPedido.get(i).setCantidad(0);
        }
        servicioOrden.limpiarPedido();
        productosPedido = new ArrayList<>();
        plantillasOrden = new ArrayList<>();
    }
    
    public void revertirPedido(PlantillaOrden plantilla, ProductoMenu producto) {
        plantilla.agregarUnidad(-1);
        productosPedido.remove(producto);
        for (int i = 0; i < producto.getInsumos().size(); i++) {
            Insumo insumoProducto = producto.getInsumos().get(i);
            Insumo insumoLista = buscarInsumo(insumoProducto.getCodigo());
            agregarInsumo(insumoProducto.getCodigo(), -insumoProducto.getCantUtilizadaProducto(), insumoLista.getCantidadStock(), insumoLista.getStockMinimo());
        }
        if (plantilla.getCantidad() <= 0) {
            plantillasOrden.remove(plantilla);
            servicioOrden.eliminarOrden(plantilla);
        }
    }
    
}
