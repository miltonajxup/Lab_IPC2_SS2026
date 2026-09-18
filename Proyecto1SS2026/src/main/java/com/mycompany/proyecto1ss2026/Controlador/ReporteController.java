/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.proyecto1ss2026.Controlador;

import com.mycompany.proyecto1ss2026.Constantes.Estado;
import com.mycompany.proyecto1ss2026.DAOs.ReporteDeSucursalDAO;
import com.mycompany.proyecto1ss2026.Exeptions.AccesoALaDataException;
import com.mycompany.proyecto1ss2026.Exeptions.ValorInvalidoException;
import com.mycompany.proyecto1ss2026.Modelos.DataBase.BusDB;
import com.mycompany.proyecto1ss2026.Modelos.DataBase.ChoferDB;
import com.mycompany.proyecto1ss2026.Modelos.Request.ReporteDepreciacionBus;
import com.mycompany.proyecto1ss2026.Modelos.Request.ReporteGanancia;
import com.mycompany.proyecto1ss2026.Modelos.Request.ReporteIngresoBoleto;
import com.mycompany.proyecto1ss2026.Modelos.Request.ReporteRutaDemandada;
import com.mycompany.proyecto1ss2026.Reporte.ArchivoReporteBoletos;
import com.mycompany.proyecto1ss2026.Reporte.ArchivoReporteBuses;
import com.mycompany.proyecto1ss2026.Reporte.ArchivoReporteChoferes;
import com.mycompany.proyecto1ss2026.Reporte.ArchivoReporteDepreciaciones;
import com.mycompany.proyecto1ss2026.Reporte.ArchivoReporteRutasDemandadas;
import com.mycompany.proyecto1ss2026.Reporte.ArchivoReporteGananciasGenerales;
import com.mycompany.proyecto1ss2026.Reporte.ArchivoReporteGastos;
import com.mycompany.proyecto1ss2026.Servicios.ServicioReporte;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @author milton
 */
@WebServlet(name = "ReporteController", urlPatterns = {"/mvc/administrador/reporte/reporte-servlet"})
public class ReporteController extends HttpServlet {
    
    private final ArchivoReporteGananciasGenerales archivoGanancias;
    private final ArchivoReporteRutasDemandadas archivoRutas;
    private final ArchivoReporteGastos archivoGastos;
    private final ArchivoReporteBuses archivoBuses;
    private final ArchivoReporteChoferes archivoChoferes;
    private final ArchivoReporteBoletos archivoBoletos;
    private final ArchivoReporteDepreciaciones archivoDepreciacion;

    public ReporteController() {
        archivoGanancias = new ArchivoReporteGananciasGenerales();
        archivoRutas = new ArchivoReporteRutasDemandadas();
        archivoGastos = new ArchivoReporteGastos();
        archivoBuses = new ArchivoReporteBuses();
        archivoChoferes = new ArchivoReporteChoferes();
        archivoBoletos = new ArchivoReporteBoletos();
        archivoDepreciacion = new ArchivoReporteDepreciaciones();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServicioReporte servicio = new ServicioReporte();
        ReporteDeSucursalDAO repSucdao = new ReporteDeSucursalDAO();
        
        String reporteGanancias = request.getParameter("reporte-ganancias");
        String fechaInicial = request.getParameter("fecha-inicial");
        String fechaFinal = request.getParameter("fecha-final");
        String ruta = request.getParameter("ruta");
        
        RequestDispatcher dispatcher;
        
        if (reporteGanancias != null && reporteGanancias.equals("reporteGanancias")) {
            try {
                String rutaHtml = null;
                String totales = request.getParameter("totales");
                
                if (totales != null && totales.equalsIgnoreCase(Estado.TRUE.name())) {
                    ReporteGanancia gananciasTotales = servicio.reporteGananciasTotales(fechaInicial, fechaFinal);
                    request.setAttribute("gananciasTotales", gananciasTotales);
                    rutaHtml = archivoGanancias.getReporte(ruta, fechaInicial, fechaFinal, true);
                } else if (totales != null) {
                    List<ReporteGanancia> ganancias = servicio.reporteGananciasGenerales(fechaInicial, fechaFinal);
                    rutaHtml = archivoGanancias.getReporte(ruta, fechaInicial, fechaFinal, false);
                    request.setAttribute("ganancias", ganancias);
                }
                request.setAttribute("rutaHtml", rutaHtml);
            } catch (AccesoALaDataException | ValorInvalidoException e) {
                request.setAttribute("error", e.getMessage());
            }
            dispatcher = getServletContext().getRequestDispatcher("/mvc/administrador/reporte/reporte-ganancias.jsp");
            dispatcher.forward(request, response);
        }
        
        String reporteRutaDemandada = request.getParameter("reporte-ruta-demandada");
        
        if (reporteRutaDemandada != null && reporteRutaDemandada.equals("reporteRutaDemandada")) {
            try {
                List<ReporteRutaDemandada> rutasDemandadas = servicio.reporteRutaDemandada(fechaInicial, fechaFinal);
                String rutaArchivo = archivoRutas.getReporte(ruta, fechaInicial, fechaFinal);
                request.setAttribute("rutasDemandadas", rutasDemandadas);
                request.setAttribute("rutaArchivo", rutaArchivo);
            } catch (AccesoALaDataException | ValorInvalidoException e) {
                request.setAttribute("error", e.getMessage());
            }
            dispatcher = getServletContext().getRequestDispatcher("/mvc/administrador/reporte/reporte-ruta.jsp");
            dispatcher.forward(request, response);
        }
        
        String reporteGastos = request.getParameter("reporte-gastos");
        
        if (reporteGastos != null && reporteGastos.equals("reporteGastos")) {
            try {
                String rutaArchivo = null;
                String totales = request.getParameter("totales");
                
                if (totales != null && totales.equalsIgnoreCase(Estado.TRUE.name())) {
                    ReporteGanancia gastosTotales = servicio.reporteGastosTotales(fechaInicial, fechaFinal);
                    rutaArchivo = archivoGastos.getReporte(ruta, fechaInicial, fechaFinal, true);
                    request.setAttribute("gastosTotales", gastosTotales);
                } else if (totales != null) {
                    List<ReporteGanancia> gastos = servicio.reporteGastosGenerales(fechaInicial, fechaFinal);
                    rutaArchivo = archivoGastos.getReporte(ruta, fechaInicial, fechaFinal, false);
                    request.setAttribute("gastos", gastos);
                }
                request.setAttribute("rutaArchivo", rutaArchivo);
            } catch (AccesoALaDataException | ValorInvalidoException e) {
                request.setAttribute("error", e.getMessage());
            }
            dispatcher = getServletContext().getRequestDispatcher("/mvc/administrador/reporte/reporte-gastos.jsp");
            dispatcher.forward(request, response);
        }
        
        String reporteBuses = request.getParameter("reporte-buses");
        String sucursal = request.getParameter("sucursal");
        
        if (reporteBuses != null && reporteBuses.equals("reporteBuses") && sucursal != null) {
            try {
                List<BusDB> buses = repSucdao.getReporteBuses(sucursal);
                request.setAttribute("buses", buses);
                String rutaArchivo = archivoBuses.getReporte(ruta, sucursal);
                request.setAttribute("rutaArchivo", rutaArchivo);
            } catch (AccesoALaDataException e) {
                request.setAttribute("error", e.getMessage());
            }
            dispatcher = getServletContext().getRequestDispatcher("/mvc/administrador/reporte/reporte-buses.jsp");
            dispatcher.forward(request, response);
        }
        
        String reporteChofer = request.getParameter("reporte-choferes");
        
        if (reporteChofer != null && reporteChofer.equals("reporteChoferes") && sucursal != null) {
            try {
                List<ChoferDB> choferes = repSucdao.getReporteChofer(sucursal);
                request.setAttribute("choferes", choferes);
                String rutaArchivo = archivoChoferes.getReporte(ruta, sucursal);
                request.setAttribute("rutaArchivo", rutaArchivo);
            } catch (AccesoALaDataException e) {
                request.setAttribute("error", e.getMessage());
            }
            dispatcher = getServletContext().getRequestDispatcher("/mvc/administrador/reporte/reporte-choferes.jsp");
            dispatcher.forward(request, response);
        }
        
        String reporteBoletos = request.getParameter("reporte-boletos");
        
        if (reporteBoletos != null && reporteBoletos.equals("reporteBoletos") && sucursal != null) {
            try {
                List<ReporteIngresoBoleto> ingresoBoletos = servicio.reporteVentaBoletos(sucursal, fechaInicial, fechaFinal);
                request.setAttribute("ingresoBoletos", ingresoBoletos);
                String rutaArchivo = archivoBoletos.getReporte(ruta, fechaInicial, fechaFinal, sucursal);
                request.setAttribute("rutaArchivo", rutaArchivo);
            } catch (AccesoALaDataException | ValorInvalidoException e) {
                request.setAttribute("error", e.getMessage());
            }
            dispatcher = getServletContext().getRequestDispatcher("/mvc/administrador/reporte/reporte-boletos.jsp");
            dispatcher.forward(request, response);
        }
        
        String reporteDepreciacion = request.getParameter("reporte-depreciacion");
        
        if (reporteDepreciacion != null && reporteDepreciacion.equals("reporteDepreciacion")) {
            try {
                List<ReporteDepreciacionBus> depreciacionBuses = repSucdao.getReporteDepreciacionBus(sucursal);
                request.setAttribute("depreciacionBuses", depreciacionBuses);
                String rutaArchivo = archivoDepreciacion.getReporte(ruta, sucursal);
                request.setAttribute("rutaArchivo", rutaArchivo);
            } catch (AccesoALaDataException e) {
                request.setAttribute("error", e.getMessage());
            }
            dispatcher = getServletContext().getRequestDispatcher("/mvc/administrador/reporte/reporte-depreciacion.jsp");
            dispatcher.forward(request, response);
        }
        
    }

}
