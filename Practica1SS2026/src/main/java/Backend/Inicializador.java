/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;

import Backend.Administrador.ControlParaEmpleado;
import Backend.Inventario.ControladorInventario;
import Backend.Administrador.ControladorPagoEmpleado;
import Backend.Menu.ControladorMenu;
import Backend.Mesero.ControladorMesero;
import Backend.Mesero.ControladorOrden;
import Backend.Mesero.ControladorPago;
import Backend.Reporte.ControladorReportes;
import DAOs.InsumoDAO;
import DAOs.MesaDAO;
import DAOs.ProductoDAO;
import Exceptions.AccesoALaDataException;
import Frontent.Administrador.ServDeshabilitarEmpleado;
import Frontent.JavaBeansCafe;
import Frontent.MenuProducto;
import Frontent.Mesero.ServicioDeOrden;
import Frontent.Mesero.ServicioMesero;
import Frontent.Mesero.ServicioPagoCuenta;
import Frontent.Mesero.SubMenuMeseros;
import Frontent.Administrador.ServicioAdminstrarInsumos;
import Frontent.Administrador.ServicioPagoEmpleado;
import Frontent.Reporte.VentanaReporte;
import Frontent.ServicioInventario;
import Modelos.Insumo;
import Modelos.InsumoPedido;
import Modelos.Mesa;
import Modelos.ProductoMenu;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author milton
 */
public class Inicializador {
    
    private JavaBeansCafe jbcafe;
    private MenuProducto menuProductos;
    private ServicioMesero servicioMesero;
    private SubMenuMeseros subMenuMeseros;
    private ServicioDeOrden servicioOrden;
    private ServicioPagoCuenta servicioCuenta;
    private ServicioInventario servicioInventario;
    private ServicioAdminstrarInsumos servicioAdminstrador;
    private ServicioPagoEmpleado servicioPagoEmpleado;
    private ServDeshabilitarEmpleado servHabilitarEmpleado;
    private VentanaReporte ventanaReporte;
    private List<Insumo> insumos;
    
    public void iniciar() {
        
        menuProductos = new MenuProducto();
        
        jbcafe = new JavaBeansCafe(menuProductos);
        jbcafe.setLocationRelativeTo(null);
        
        servicioMesero = new ServicioMesero();
        
        subMenuMeseros = new SubMenuMeseros();
        jbcafe.setSubMenu(subMenuMeseros);
        servicioOrden = new ServicioDeOrden(jbcafe);
        jbcafe.setServicioOrden(servicioOrden);
        servicioCuenta = new ServicioPagoCuenta(jbcafe);
        jbcafe.setServicioCuenta(servicioCuenta);
        servicioInventario = new ServicioInventario();
        servicioAdminstrador = new ServicioAdminstrarInsumos();
        jbcafe.setServAdministarInsumos(servicioAdminstrador);
        servicioPagoEmpleado = new ServicioPagoEmpleado();
        jbcafe.setServicioPagoEmpleado(servicioPagoEmpleado);
        servHabilitarEmpleado = new ServDeshabilitarEmpleado();
        ventanaReporte = new VentanaReporte();
        conectarControladores();
        
        jbcafe.setVisible(true);
    }
    
    private void conectarControladores() {
        InsumoDAO insumodao = new InsumoDAO();
        MesaDAO mesasdao = new MesaDAO();
        List<Mesa> mesas = null;
        try {
            mesas = mesasdao.getMesas();
        } catch (AccesoALaDataException e) {
            jbcafe.mostrarError(e.getMessage());
        }
        List<InsumoPedido> insumosPedido = inicializarInsumos(insumodao);
        
        ControladorMesero controladorMesero = new ControladorMesero(servicioMesero, subMenuMeseros, jbcafe, servicioCuenta, mesas);
        jbcafe.setServicioMesero(servicioMesero, controladorMesero);

        try {
            controladorMesero.colocarMesas();
        } catch (AccesoALaDataException e) {
            jbcafe.mostrarError(e.getMessage());
        }
        
        ProductoDAO productodao = new ProductoDAO();
        List<ProductoMenu> productos = null;
        try {
            productos = productodao.getProductos();
        } catch (AccesoALaDataException e) {
            jbcafe.mostrarError(e.getMessage());
        }
        
        ControladorMenu controladorMenu = new ControladorMenu(menuProductos, productodao, productos);
        controladorMenu.colocarProductos();
        
        ControladorOrden controladorOrden = new ControladorOrden(servicioOrden, insumosPedido, insumodao, productodao, productos);
        controladorOrden.colocarProductos();
        servicioOrden.setControladorOrden(controladorOrden);
        
        ControladorPago controladorPago = new ControladorPago(servicioCuenta, controladorOrden, mesasdao, insumodao);
        controladorMesero.setControladroOrden(controladorPago);
        servicioCuenta.setControladorPago(controladorPago);
        servicioOrden.setControladorPago(controladorPago);
        
        ControladorInventario controladorInventario = new ControladorInventario(servicioInventario, servicioAdminstrador, insumodao, productodao);
        servicioAdminstrador.setControlador(controladorInventario);
        jbcafe.setServicioInventario(servicioInventario, controladorInventario);
        
        ControladorPagoEmpleado controladorPagoEmpleado = new ControladorPagoEmpleado(servicioPagoEmpleado);
        servicioPagoEmpleado.setControladorPago(controladorPagoEmpleado);
        
        ControlParaEmpleado controlParaEmpleado = new ControlParaEmpleado(servHabilitarEmpleado);
        servHabilitarEmpleado.setControlEmpleado(controlParaEmpleado);
        jbcafe.setServDeshabilitarEmpleado(servHabilitarEmpleado, controlParaEmpleado);
        
        ControladorReportes controladorReporte = new ControladorReportes(ventanaReporte, insumodao);
        ventanaReporte.setControladorReportes(controladorReporte);
        jbcafe.setControladorReporte(controladorReporte, ventanaReporte);
    }
    
    private List<InsumoPedido> inicializarInsumos(InsumoDAO insumodao) {
        try {
            insumos = insumodao.getTodosInsumos();
        } catch (AccesoALaDataException e) {
            jbcafe.mostrarError(e.getMessage());
        }
        List<InsumoPedido> insumosPedido = new ArrayList<>();
        for (int i = 0; i < insumos.size(); i++) {
            Insumo insumo = insumos.get(i);
            insumosPedido.add(new InsumoPedido(insumo.getNombre(), insumo.getCodigo()));
        }
        return insumosPedido;
    }
    
}
