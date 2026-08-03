/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;

import Frontent.JavaBeansCafe;
import Modelos.Mesa.Mesa;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author milton
 */
public class Inicializador {
    
    public void iniciar() {
        
        List<String> empleados = new ArrayList<>();
        empleados.add("numero1");
        empleados.add("numero2");
        empleados.add("numero3");
        empleados.add("numero4");
        empleados.add("numero5");
        empleados.add("numero6");
        
        List<Mesa> mesas = new ArrayList<>();
        Mesa mesa = new Mesa(1, 2, 0);
        Mesa mesa2 = new Mesa(2, 3, 0);
        mesas.add(mesa);
        mesas.add(mesa2);
        
        JavaBeansCafe jbcafe = new JavaBeansCafe(empleados, mesas);
        jbcafe.setLocationRelativeTo(null);
        jbcafe.setVisible(true);
        
    }
    
}
