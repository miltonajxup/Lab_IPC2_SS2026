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
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServicioReporte servicio = new ServicioReporte();
        ReporteDeSucursalDAO repSucdao = new ReporteDeSucursalDAO();
        
        String reporteGanancias = request.getParameter("reporte-ganancias");
        String fechaInicial = request.getParameter("fecha-inicial");
        String fechaFinal = request.getParameter("fecha-final");
        
        RequestDispatcher dispatcher;
        
        if (reporteGanancias != null && reporteGanancias.equals("reporteGanancias")) {
            try {
                String totales = request.getParameter("totales");
                if (totales != null && totales.equalsIgnoreCase(Estado.TRUE.name())) {
                    ReporteGanancia gananciasTotales = servicio.reporteGananciasTotales(fechaInicial, fechaFinal);
                    request.setAttribute("gananciasTotales", gananciasTotales);
                } else if (totales != null) {
                    List<ReporteGanancia> ganancias = servicio.reporteGananciasGenerales(fechaInicial, fechaFinal);
                    request.setAttribute("ganancias", ganancias);
                }
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
                request.setAttribute("rutasDemandadas", rutasDemandadas);
            } catch (AccesoALaDataException | ValorInvalidoException e) {
                request.setAttribute("error", e.getMessage());
            }
            dispatcher = getServletContext().getRequestDispatcher("/mvc/administrador/reporte/reporte-ruta.jsp");
            dispatcher.forward(request, response);
        }
        
        String reporteGastos = request.getParameter("reporte-gastos");
        
        if (reporteGastos != null && reporteGastos.equals("reporteGastos")) {
            try {
                String totales = request.getParameter("totales");
                if (totales != null && totales.equalsIgnoreCase(Estado.TRUE.name())) {
                    ReporteGanancia gastosTotales = servicio.reporteGastosTotales(fechaInicial, fechaFinal);
                    request.setAttribute("gastosTotales", gastosTotales);
                } else if (totales != null) {
                    List<ReporteGanancia> gastos = servicio.reporteGastosGenerales(fechaInicial, fechaFinal);
                    request.setAttribute("gastos", gastos);
                }
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
            } catch (AccesoALaDataException e) {
                request.setAttribute("error", e.getMessage());
            }
            dispatcher = getServletContext().getRequestDispatcher("/mvc/administrador/reporte/reporte-depreciacion.jsp");
            dispatcher.forward(request, response);
        }
        
    }

}
