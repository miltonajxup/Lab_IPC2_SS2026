/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.Servicios;

import com.mycompany.proyecto2_ipc2_ss2026.Constantes.LimitePagina;
import com.mycompany.proyecto2_ipc2_ss2026.Constantes.Mes;
import com.mycompany.proyecto2_ipc2_ss2026.Constantes.RolUsuario;
import com.mycompany.proyecto2_ipc2_ss2026.Constantes.TipoPago;
import com.mycompany.proyecto2_ipc2_ss2026.DAOs.Administrador.AdministradorDAO;
import com.mycompany.proyecto2_ipc2_ss2026.DAOs.Administrador.CursoDAO;
import com.mycompany.proyecto2_ipc2_ss2026.DAOs.Administrador.EmpleadoDAO;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.AccesoALaDataException;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.DataInexistenteException;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.ValorInvalidoException;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.BoletaPagoDB;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.CursoDB;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.EmpleadoDB;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.PagoEmpleadoDB;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosRequest.CursoMaestroRequest;
import java.util.List;

/**
 *
 * @author milton
 */
public class ServicioAdministrador {
    
    private final AdministradorDAO administradordao;
    
    public ServicioAdministrador() {
        administradordao = new AdministradorDAO();
    }
    
    public String generarBoletasDePago(Mes mes) throws AccesoALaDataException {
        int boletasGeneradas = administradordao.generarBoletasDePago(mes.name());
        return "Se han generado " + boletasGeneradas + " boletas de pago de " + TipoPago.COLEGIATURA;
    }
    
    public List<BoletaPagoDB> getBoletasGeneradas(Mes mes, int pagina) throws AccesoALaDataException, ValorInvalidoException {
        if (pagina < LimitePagina.PAGINA_MINIMA) {
            throw new ValorInvalidoException("La pagina minima que se puede buscar es: " + LimitePagina.PAGINA_MINIMA);
        }
        return administradordao.getBoletasDePagoGeneradas(mes.name(), pagina);
    }
    
    public String pagarEmpleados(Mes mes) throws AccesoALaDataException {
        int pagosRealizados = administradordao.pagarAEmpleados(mes.name());
        return "Se han generado " + pagosRealizados + " pagos a los empleados del colegio";
    }
    
    public List<PagoEmpleadoDB> getPagosRealizados(Mes mes, int pagina) throws AccesoALaDataException, ValorInvalidoException {
        if (pagina < LimitePagina.PAGINA_MINIMA) {
            throw new ValorInvalidoException("La pagina minima que se puede buscar es: " + LimitePagina.PAGINA_MINIMA);
        }
        return administradordao.getRegistrosPagos(mes.name(), pagina);
    }
    
    public String asignarCursoMaestro(CursoMaestroRequest cursoMaestro) throws AccesoALaDataException, DataInexistenteException, ValorInvalidoException {
        CursoDAO cursodao = new CursoDAO();
        CursoDB curso = cursodao.getCursoId(cursoMaestro.getCurso());
        if (curso == null) {
            throw new DataInexistenteException("El curso que se quiere asignar no existe");
        }
        EmpleadoDAO empleadodao = new EmpleadoDAO();
        EmpleadoDB empleado = empleadodao.getEmpleadoPorID(cursoMaestro.getMaestro());
        if (empleado == null) {
            throw new DataInexistenteException("El maestro elegido para el curso no se encuentra contratado para el año vigente");
        }
        if (empleado.getRol() != RolUsuario.MAESTRO) {
            throw new ValorInvalidoException("El empleado que se quiere agregar no es un " + RolUsuario.MAESTRO);
        }
        if (administradordao.cursoTieneMaestro(cursoMaestro.getCurso())) {
            //throw new ValorInvalidoException("El curso ya tiene asignado un maestro");
            administradordao.modificarCursoMaestro(cursoMaestro);
        } else {
            administradordao.asignarMaestroCurso(cursoMaestro);
        }
        return "Se ha asignado el maestro: " + empleado.getNombre() + " para el curso " + curso.getNombre();
    }
    
}
