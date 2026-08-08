/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend.Administrador;

import Exceptions.AccesoALaDataException;
import Frontent.Administrador.ServDeshabilitarEmpleado;
import Modelos.Personal;

/**
 *
 * @author milton
 */
public class ControlParaEmpleado {
    
    private ServDeshabilitarEmpleado servDeshabilitarEmpleado;

    public ControlParaEmpleado(ServDeshabilitarEmpleado servDeshabilitarEmpleado) {
        this.servDeshabilitarEmpleado = servDeshabilitarEmpleado;
    }
    
    public void habilitar(Personal personal) throws AccesoALaDataException {
        
    }
    
}
