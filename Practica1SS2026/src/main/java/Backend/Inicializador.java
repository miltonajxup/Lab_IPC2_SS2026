/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;

import Backend.Mesero.ControladorMesero;
import Frontent.JavaBeansCafe;
import Frontent.MenuProducto;
import Frontent.ServicioMesero;
import Frontent.SubMenuMeseros;

/**
 *
 * @author milton
 */
public class Inicializador {
    
    private JavaBeansCafe jbcafe;
    private MenuProducto menuProductos;
    private ServicioMesero servicioMesero;
    private SubMenuMeseros subMenuMeseros;
    
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
        conectarControladores();
    }
    
    private void conectarControladores() {
        ControladorMesero controladorMesero = new ControladorMesero(servicioMesero, subMenuMeseros);
        jbcafe.setControladorMesero(controladorMesero);
    }
    
}
