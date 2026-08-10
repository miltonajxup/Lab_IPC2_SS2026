/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend.Mesero;

import DAOs.InsumoDAO;
import DAOs.ProductoDAO;
import Exceptions.AccesoALaDataException;
import Frontent.OpcionMenu;
import Frontent.Mesero.PlantillaOrden;
import Frontent.Mesero.ServicioDeOrden;
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
    private final InsumoDAO insumodao;
    private List<ProductoMenu> productosdb;
    private List<Insumo> insumos;
    private List<ProductoMenu> productosPedido;
    private final List<InsumoPedido> insumosPedido;
    private List<PlantillaOrden> plantillasOrden;

    public ControladorOrden(ServicioDeOrden servicioOrden, List<InsumoPedido> insumosPedido, InsumoDAO insumodao, ProductoDAO productodao, List<ProductoMenu> productosdb) {
        this.servicioOrden = servicioOrden;
        this.productodao = productodao;
        this.insumodao = insumodao;
        this.productosdb = productosdb;
        this.productosPedido = new ArrayList<>();
        this.insumosPedido = insumosPedido;
        this.plantillasOrden = new ArrayList<>();
    }
    
    public List<InsumoPedido> getInsumosPedido() {
        return insumosPedido;
    }
    
    public void setInsumos(List<Insumo> insumos) {
        this.insumos = insumos;
    }
    
    public List<ProductoMenu> getProductoPedido() {
        return productosPedido;
    }
    
    public void actualizarProductos() throws AccesoALaDataException {
        productosdb = productodao.getProductos();
        colocarProductos();
    }
    
    public void colocarProductos() throws AccesoALaDataException {
        servicioOrden.limpiarMenu();
        insumos = insumodao.getTodosInsumos();
        servicioOrden.setCuadricula(productosdb.size());
        for (int i = 0; i < productosdb.size(); i++) {
            ProductoMenu producto = productosdb.get(i);
            boolean insumoBajo = insumoBajo(producto);
            OpcionMenu opcionMenu = new OpcionMenu(this, producto, insumoBajo);
            servicioOrden.agregarProducto(opcionMenu);
            opcionMenu.setCuadricula(producto.getInsumos().size());
        }
    }
    
    private boolean insumoBajo(ProductoMenu producto) {
        for (int i = 0; i < producto.getInsumos().size(); i++) {
            for (int j = 0; j < insumos.size(); j++) {
                Insumo insumo = insumos.get(j);
                if (producto.getInsumos().get(i).getCodigo() == insumo.getCodigo()) {
                    if (insumo.getCantidadStock() <= insumo.getStockMinimo()) {
                        return true;
                    }
                    break;
                }
            }
        }
        return false;
    }
    
    public void agregarAPedido(ProductoMenu producto) throws AccesoALaDataException {
        insumos = insumodao.getTodosInsumos();
        for (int i = 0; i < producto.getInsumos().size(); i++) {
            Insumo insumoProducto = producto.getInsumos().get(i);
            Insumo insumoLista = buscarInsumo(insumoProducto.getCodigo());
            boolean existenUnidadesInsumo = existenUnidades(insumoProducto.getCodigo(), insumoProducto.getCantUtilizadaProducto(), insumoLista.getCantidadStock());
            if (!existenUnidadesInsumo) {
                servicioOrden.mostrarMensaje("No hay " +insumoLista.getNombre()+" suficiente para añadir este elemento a la orden");
                return;
            }
        }
        for (int i = 0; i < producto.getInsumos().size(); i++) {
            Insumo insumoProducto = producto.getInsumos().get(i);
            modificarCantidadesInsumo(insumoProducto.getCodigo(), insumoProducto.getCantUtilizadaProducto());
        }
        
        productosPedido.add(producto);
        PlantillaOrden plantillaLista = existePlantilla(producto);
        if(plantillaLista == null) {
            PlantillaOrden plantilla = new PlantillaOrden(this, producto, 1);
            servicioOrden.agregarPedido(plantilla);
            plantillasOrden.add(plantilla);
        } else {
            plantillaLista.agregarUnidad(1);
        }
    }
    
    private boolean existenUnidades(int codigo, double cantidadUtilizada, double cantidadRestante) {
        double suma;
        for (int i = 0; i < insumosPedido.size(); i++) {
            InsumoPedido actual = insumosPedido.get(i);
            if (actual.getCodigo() == codigo) {
                suma = actual.getCantidad() + cantidadUtilizada;
                cantidadRestante = cantidadRestante - suma;
                return cantidadRestante >= 0;
            }
        }
        return false;
    }
    
    private void modificarCantidadesInsumo(int codigo, double cantidadUtilizada) {
        double suma;
        for (int i = 0; i < insumosPedido.size(); i++) {
            InsumoPedido actual = insumosPedido.get(i);
            if (actual.getCodigo() == codigo) {
                suma = actual.getCantidad() + cantidadUtilizada;
                actual.setCantidad(suma);
            }
        }
    }
    
    public Insumo buscarInsumo(int codigo) {
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
        productosPedido = new ArrayList<>();
        plantillasOrden = new ArrayList<>();
    }
    
    public void revertirPedido(PlantillaOrden plantilla, ProductoMenu producto) {
        plantilla.agregarUnidad(-1);
        productosPedido.remove(producto);
        for (int i = 0; i < producto.getInsumos().size(); i++) {
            Insumo insumoProducto = producto.getInsumos().get(i);
            modificarCantidadesInsumo(insumoProducto.getCodigo(), -insumoProducto.getCantUtilizadaProducto());
        }
        if (plantilla.getCantidad() <= 0) {
            plantillasOrden.remove(plantilla);
            servicioOrden.eliminarOrden(plantilla);
        }
    }
    
}
