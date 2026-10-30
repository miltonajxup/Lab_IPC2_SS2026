/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.Servicios;

import com.mycompany.proyecto2_ipc2_ss2026.Constantes.LimitePagina;
import com.mycompany.proyecto2_ipc2_ss2026.DAOs.Reportes.ReporteDAO;
import com.mycompany.proyecto2_ipc2_ss2026.DAOs.UsuarioDAO;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.AccesoALaDataException;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.DataInexistenteException;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.ValorInvalidoException;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.HistorialPagosEstudiante;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.IngresoDB;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.PagoBoletaDB;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.PromedioCursoDB;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.SolvenciaEstudiante;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author milton
 */
public class ServicioReporte {
    
    private final ReporteDAO reportedao;
    
    public ServicioReporte() {
        reportedao = new ReporteDAO();
    }
    
    public List<SolvenciaEstudiante> buscarSolvencias(int idGrupo, int pagina) throws AccesoALaDataException, ValorInvalidoException {
        revisarPagina(pagina);
        return reportedao.getSolvencias(idGrupo, pagina);
    }
    
    public List<PromedioCursoDB> buscarPromedioCursos(int idGrupo) throws AccesoALaDataException { 
        return reportedao.getPromedioCursos(idGrupo);
    }
    
    public HistorialPagosEstudiante buscarPagosPorEstudianteSinFecha(String idUsuario, int pagina) throws AccesoALaDataException, ValorInvalidoException, DataInexistenteException {
        revisarPagina(pagina);
        revisarExistenciaUsuario(idUsuario);
        List<PagoBoletaDB> pagos = reportedao.getPagosPorEstudianteSinFecha(idUsuario, pagina);
        return armarHistorial(pagos);
    }
    
    public HistorialPagosEstudiante buscarPagosPorEstudianteConFecha(String idUsuario, String textoFechaInicio, String textoFechaFin, int pagina) throws AccesoALaDataException, ValorInvalidoException, DataInexistenteException {
        LocalDate fechaInicio = revisarFecha(textoFechaInicio);
        LocalDate fechaFin = revisarFecha(textoFechaFin);
        revisarPagina(pagina);
        revisarExistenciaUsuario(idUsuario);
        
        List<PagoBoletaDB> pagos = reportedao.getPagosPorEstudianteConFecha(idUsuario, fechaInicio, fechaFin, pagina);
        return armarHistorial(pagos);
    }
    
    private void revisarExistenciaUsuario(String idUsuario) throws AccesoALaDataException, DataInexistenteException {
        UsuarioDAO usuariodao = new UsuarioDAO();
        if (!usuariodao.existeUsuarioPorDpi(idUsuario)) {
            throw new DataInexistenteException("No se pudo encontrar al usuario ingresado");
        }
    }
    
    private HistorialPagosEstudiante armarHistorial(List<PagoBoletaDB> pagos) {
        List<PagoBoletaDB> pendientes = new ArrayList<>();
        double dineroAportado = 0;
        double saldoPendiente = 0;
        for (PagoBoletaDB pago : pagos) {
            if (pago.isPagado()) {
                dineroAportado += pago.getMonto();
            } else {
                saldoPendiente += pago.getMonto();
                pendientes.add(pago);
            }
        }
        return new HistorialPagosEstudiante(pagos, pendientes, dineroAportado, saldoPendiente);
    }
    
    public List<IngresoDB> buscarIngresosSinFechas() throws AccesoALaDataException, ValorInvalidoException {
        return reportedao.getIngresosSinFechas();
    }
    
    public List<IngresoDB> buscarIngresosConFechas(String textoFechaInicio, String textoFechaFin) throws AccesoALaDataException, ValorInvalidoException {
        LocalDate fechaInicio = revisarFecha(textoFechaInicio);
        LocalDate fechaFin = revisarFecha(textoFechaFin);
        return reportedao.getIngresosConFechas(fechaInicio, fechaFin);
    }
    
    
    private void revisarPagina(int pagina) throws ValorInvalidoException {
        if (pagina < LimitePagina.PAGINA_MINIMA) {
            throw new ValorInvalidoException("La pagina no puede ser menor a " + LimitePagina.PAGINA_MINIMA);
        }
    }
    
    private LocalDate revisarFecha(String textoFecha) throws ValorInvalidoException {
        try {
            return LocalDate.parse(textoFecha);
        } catch (IllegalArgumentException e) {
            throw new ValorInvalidoException("El valor " + textoFecha + " no es valido para una fecha");
        }
    }
    
}
