/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend.Administrador;

import DAOs.PagoDAO;
import DAOs.PersonalDAO;
import Exceptions.AccesoALaDataException;
import Exceptions.ErrorIngresarDatosException;
import Frontent.Administrador.PlantillaEmpleado;
import Frontent.Administrador.ServicioPagoEmpleado;
import Modelos.Pago;
import Modelos.Personal;
import java.util.List;

/**
 *
 * @author milton
 */
public class ControladorPagoEmpleado {
    
    private final ServicioPagoEmpleado servicioPagoEmpleado;
    private final PersonalDAO personaldao;
    private final PagoDAO pagodao;
    private List<Personal> empleados;
    private int dia;
    private int mes;
    private int año;

    public ControladorPagoEmpleado(ServicioPagoEmpleado servicioPagoEmpleado) {
        this.servicioPagoEmpleado = servicioPagoEmpleado;
        this.personaldao = new PersonalDAO();
        this.pagodao = new PagoDAO();
    }
    
    public void generarRegistrosPago(String diaTexto, String mesTexto, String añoTexto) throws AccesoALaDataException, ErrorIngresarDatosException {
        procesarFecha(diaTexto, mesTexto, añoTexto);
        empleados = personaldao.getPersonal();
        servicioPagoEmpleado.limpiar();
        servicioPagoEmpleado.setFilas(empleados.size());
        String fecha = procesarFecha(diaTexto, mesTexto, añoTexto);
        if (dia == 10) {
            //Si es este pago es QUINCENA y en base de datos es 1
            generarPago(fecha, 1, 30);
        } else if (dia == 25) {
            //Si es este pago es FIN_DE_MES y en base de datos es 2
            generarPago(fecha, 2, 70);
        } else {
            servicioPagoEmpleado.mostrarError("No se genera nada, los pagos generan los dias 10 y 25");
        }
    }
    
    private void generarPago(String fecha, int tipoPago, double porcentaje) throws AccesoALaDataException {
        for (int i = 0; i < empleados.size(); i++) {
            Personal actual = empleados.get(i);
            Pago pago = pagodao.getPagoEmpleadoFecha(fecha, actual.getDpi());
            if (pago == null && actual.isEstado()) {
                double salario = (porcentaje * actual.getSalario()) / 100;
                pagodao.agregarRegistroPago(fecha, salario, actual.getDpi(), tipoPago);
            }
        }
        servicioPagoEmpleado.agregarInformacion("Pagos generados con exito");
    }
    
    public void buscarPagosPorFecha() throws AccesoALaDataException, ErrorIngresarDatosException {
        String diaTexto = servicioPagoEmpleado.getDia();
        String mesTexto = servicioPagoEmpleado.getMes();
        String añoTexto = servicioPagoEmpleado.getAño();
        String fecha = procesarFecha(diaTexto, mesTexto, añoTexto);
        List<Pago> pagos = pagodao.getPagosEnUnaFecha(fecha);
        servicioPagoEmpleado.limpiar();
        servicioPagoEmpleado.setFilas(pagos.size());
        for (int i = 0; i < pagos.size(); i++) {
            Pago actual = pagos.get(i);
            PlantillaEmpleado plantilla = new PlantillaEmpleado(this, actual.getEmpleado(), actual.getNombreEmpleado(), fecha);
            servicioPagoEmpleado.agregarEmpleado(plantilla);
        }
        if (pagos.size() <= 0) {
            servicioPagoEmpleado.agregarInformacion("No hay pagos generados en la fecha " + fecha);
        }
    }
    
    public void pagarCuenta(String fecha, String dpiEmpleado, PlantillaEmpleado plantillaEmpleado) throws AccesoALaDataException {
        pagodao.actualizarPago(fecha, dpiEmpleado);
        servicioPagoEmpleado.eliminarEmpleado(plantillaEmpleado);
    }
    
    private String procesarFecha(String diaTexto, String mesTexto, String añoTexto) throws ErrorIngresarDatosException {
        if (diaTexto.isEmpty() || mesTexto.isEmpty() || añoTexto.isEmpty()) {
            throw new ErrorIngresarDatosException("No se puede tener los campos de fecha vacios");
        }
        
        try {
            dia = Integer.parseInt(diaTexto);
            mes = Integer.parseInt(mesTexto);
            año = Integer.parseInt(añoTexto);
        } catch (NumberFormatException e) {
            servicioPagoEmpleado.limpiarFecha();
            throw new ErrorIngresarDatosException("Las fechas deben ser ingresadas en numeros");
        }
        if (dia <= 0 || mes <= 0 || año <= 0) {
            throw new ErrorIngresarDatosException("Las fechas deben ingresarse con numeros positivos");
        }
        if (dia > 30 || mes > 12 || año < 2025) {
            throw new ErrorIngresarDatosException("Las fechas deben ser logicas ademas de ser posteiores a 2024");
        }
        String fecha = año + "-" + mes + "-" + dia;
        return fecha;
    }
    
}
