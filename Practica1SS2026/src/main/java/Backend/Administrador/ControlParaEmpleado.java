/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend.Administrador;

import DAOs.PersonalDAO;
import Exceptions.AccesoALaDataException;
import Frontent.Administrador.PlantillaEmpleadoHabilitado;
import Frontent.Administrador.ServDeshabilitarEmpleado;
import Modelos.Personal;
import java.util.List;

/**
 *
 * @author milton
 */
public class ControlParaEmpleado {
    
    private final ServDeshabilitarEmpleado servDeshabilitarEmpleado;
    private final PersonalDAO personaldao;
    private int rol;
    private int jornada;
    
    public ControlParaEmpleado(ServDeshabilitarEmpleado servDeshabilitarEmpleado) {
        this.servDeshabilitarEmpleado = servDeshabilitarEmpleado;
        this.personaldao = new PersonalDAO();
    }

    public void setRol(int rol) {
        this.rol = rol;
    }

    public void setJornada(int jornada) {
        this.jornada = jornada;
    }
    
    public void colocarPersonal() throws AccesoALaDataException {
        List<Personal> personal = personaldao.getPersonal();
        servDeshabilitarEmpleado.limpiar();
        servDeshabilitarEmpleado.setFilas(personal.size());
        for (int i = 0; i < personal.size(); i++) {
            Personal actual = personal.get(i);
            String estado = gestionarEstado(actual.isEstado());
            PlantillaEmpleadoHabilitado plantilla = new PlantillaEmpleadoHabilitado(this, actual, estado);
            servDeshabilitarEmpleado.agregarEmpleado(plantilla);
        }
    }
    
    public void habilitar(PlantillaEmpleadoHabilitado plantilla, Personal personal) throws AccesoALaDataException {
        personal.setEstado(!personal.isEstado());
        personaldao.actulizarPersonal(personal.isEstado(), personal.getDpi());
        String estado = gestionarEstado(personal.isEstado());
        plantilla.setEstado(estado);
    }
    
    private String gestionarEstado(boolean estado) {
        String estadoTexto;
        if (estado) {
            estadoTexto = "HABILITADO";
        } else {
            estadoTexto = "DESHABILITADO";
        }
        return estadoTexto;
    }
    
    public void guardarEmpleado(String dpi, String nombre, String salarioTexto) throws AccesoALaDataException {
        if (dpi.isEmpty() || nombre.isEmpty() || salarioTexto.isEmpty()) {
            servDeshabilitarEmpleado.mostrarMensaje("Para poder crear un nuevo empleado no se pueden dejar los campos vacios");
            return;
        }
        if (dpi.length() > 13) {
            servDeshabilitarEmpleado.mostrarMensaje("El DPI no puede sobre pasar los 13 caracteres");
            return;
        }
        if (nombre.length() > 50) {
            servDeshabilitarEmpleado.mostrarMensaje("El nombre no puede sobre pasar los 50 caracteres");
            return;
        }
        double salario;
        try {
            salario = Double.parseDouble(salarioTexto);
        } catch (NumberFormatException e) {
            servDeshabilitarEmpleado.mostrarMensaje("El salario debe ser ingresado en numeros");
            return;
        }
        if (salario <= 0) {
            servDeshabilitarEmpleado.mostrarMensaje("El salario debe ser una cantidad positiva");
            return;
        }
        Personal personal = personaldao.getPersonalPorDpi(dpi);
        if (personal != null) {
            servDeshabilitarEmpleado.mostrarMensaje("El DPI ya existe en los registros");
            return;
        }
        if (rol <= 0) {
            servDeshabilitarEmpleado.mostrarMensaje("Aun no se ha elegigo un rol");
            return;
        }
        if (jornada <= 0) {
            servDeshabilitarEmpleado.mostrarMensaje("Aun no se ha elegido una jornada");
            return;
        }
        personaldao.agregarPersonal(dpi, nombre, salario, rol, jornada);
        servDeshabilitarEmpleado.mostrarMensaje("Empleado registrado con exito");
        limpiarVentanaEmpleados();
        colocarPersonal();
    }
    
    public void limpiarVentanaEmpleados() {
        rol = 0;
        jornada = 0;
        servDeshabilitarEmpleado.limpiar();
    }
    
}
