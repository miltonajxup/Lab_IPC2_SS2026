/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend.Inventario;

import DAOs.InsumoDAO;
import DAOs.ProductoDAO;
import Exceptions.AccesoALaDataException;
import Frontent.Administrador.PlantillaInsumo;
import Frontent.Administrador.ServicioAdminstrarInsumos;
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
    private final ServicioAdminstrarInsumos servicioAdministrador;
    private final InsumoDAO insumodao;
    private List<Insumo> insumos;
    private final ProductoDAO productodao;
    private List<ProductoMenu> productos;
    private Insumo insumoElegido;
    
    public ControladorInventario(ServicioInventario servicioInventario, ServicioAdminstrarInsumos servicioAdministrador, InsumoDAO insumodao, ProductoDAO productodao) {
        this.servicioInventario = servicioInventario;
        this.servicioAdministrador = servicioAdministrador;
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
    
    public void colocarInsumos() throws AccesoALaDataException {
        insumos = insumodao.getTodosInsumos();
        servicioAdministrador.setFilasInsumo(insumos.size());
        for (int i = 0; i < insumos.size(); i++) {
            PlantillaInsumo plantilla = new PlantillaInsumo(this, insumos.get(i));
            servicioAdministrador.agregarInsumo(plantilla);
        }
    }
    
    public void elegirInsumo(Insumo insumo) {
        this.insumoElegido = insumo;
        servicioAdministrador.elegirInsumo(insumo.getNombre());
    }
    
    public void aumentarInsumos(String textoInsumosExtra) throws AccesoALaDataException {
        if (insumoElegido == null) {
            servicioAdministrador.mostrarError("Aun no se ha escogido un Insumo");
            return;
        }
        if (textoInsumosExtra.isBlank()) {
            servicioAdministrador.mostrarError("No se puede dejar el blanco la cantidad");
            return;
        }
        double insumosExtra;
        try {
            insumosExtra = Double.parseDouble(textoInsumosExtra);
        } catch (NumberFormatException e) {
            servicioAdministrador.mostrarError("El valor de insumos debe ser ingresado en numeros");
            return;
        }
        double cantidadActual = insumoElegido.getCantidadStock();
        cantidadActual += insumosExtra;
        insumodao.actualizarInsumo(cantidadActual, insumoElegido.getCodigo(), insumosExtra);
        limpiarAgregarInsumos();
        colocarInsumos();
    }
    
    public void limpiarAgregarInsumos() {
        insumoElegido = null;
        servicioAdministrador.elegirInsumo("");
        servicioAdministrador.limpiarField();
        servicioAdministrador.limpiarPanel();
    }
    
}
