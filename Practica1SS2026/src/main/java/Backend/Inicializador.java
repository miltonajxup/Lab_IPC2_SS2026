/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;

import Backend.Menu.ControladorMenu;
import Backend.Mesero.ControladorMesero;
import Backend.Mesero.ControladorOrden;
import DAOs.InsumoDAO;
import Frontent.JavaBeansCafe;
import Frontent.MenuProducto;
import Frontent.ServicioDeOrden;
import Frontent.ServicioMesero;
import Frontent.SubMenuMeseros;
import Modelos.Insumo;
import Modelos.InsumoPedido;
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
    private List<Insumo> insumos;
    
    public void iniciar() {
        
        menuProductos = new MenuProducto();
        //nombres de los productos mas mostrarlos .mostrar
        
        jbcafe = new JavaBeansCafe(menuProductos);
        jbcafe.setLocationRelativeTo(null);
        jbcafe.setVisible(true);
        
        servicioMesero = new ServicioMesero();
        jbcafe.setServicioMesero(servicioMesero);
        subMenuMeseros = new SubMenuMeseros();
        jbcafe.setSubMenu(subMenuMeseros);
        servicioOrden = new ServicioDeOrden(jbcafe);
        jbcafe.setServicioOrden(servicioOrden);
        conectarControladores();
    }
    
    private void conectarControladores() {
        List<InsumoPedido> insumosPedido = inicializarInsumos();
        
        ControladorMesero controladorMesero = new ControladorMesero(servicioMesero, subMenuMeseros, jbcafe);
        jbcafe.setControladorMesero(controladorMesero);
        controladorMesero.colocarMesas();
        
        ControladorMenu controladorMenu = new ControladorMenu(menuProductos);
        controladorMenu.colocarProductos();
        
        ControladorOrden controladorOrden = new ControladorOrden(servicioOrden, insumos, insumosPedido);
        controladorOrden.colocarProductos();
        servicioOrden.setControlador(controladorOrden);
    }
    
    private List<InsumoPedido> inicializarInsumos() {
        InsumoDAO insumodao = new InsumoDAO();
        insumos = insumodao.getTodosInsumos();
        List<InsumoPedido> insumosPedido = new ArrayList<>();
        for (int i = 0; i < insumos.size(); i++) {
            Insumo insumo = insumos.get(i);
            insumosPedido.add(new InsumoPedido(insumo.getNombre(), insumo.getCodigo()));
        }
        return insumosPedido;
    }
    
}
