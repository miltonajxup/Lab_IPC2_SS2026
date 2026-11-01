/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.resources;

import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.HistorialPagosEstudiante;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.IngresoDB;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.PromedioCursoDB;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.SolvenciaEstudiante;
import com.mycompany.proyecto2_ipc2_ss2026.Servicios.ServicioReporte;
import jakarta.servlet.ServletContext;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.StreamingOutput;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

/**
 *
 * @author milton
 */
@Path("exportar-reporte")
public class ExportarReporteController {

    @Context
    private ServletContext servletContext;

    @GET
    @Path("promedios/{idGrupo}")
    public Response reportePromedios(@PathParam("idGrupo") int idGrupo) {

        String reportePath = "/WEB-INF/reportes/reporte_de_promedios.jrxml";

        try (InputStream jrxmlInput = servletContext.getResourceAsStream(reportePath)) {

            if (jrxmlInput == null) {
                throw new RuntimeException(
                    "No se encontró el reporte: " + reportePath
                );
            }

            ServicioReporte servicio = new ServicioReporte();

            List<PromedioCursoDB> promedios = servicio.buscarPromedioCursos(idGrupo);

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(promedios);

            JasperDesign design = JRXmlLoader.load(jrxmlInput);

            JasperReport report = JasperCompileManager.compileReport(design);

            Map<String, Object> parametros = new HashMap<>();
            parametros.put("ds", dataSource);

            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parametros, new JREmptyDataSource());
            
            byte[] pdf = JasperExportManager.exportReportToPdf(jasperPrint);

            StreamingOutput out = (OutputStream output) -> {
                output.write(pdf);
                output.flush();
            };

            return Response.ok(out)
                    .type("application/pdf")
                    .header(
                        "Content-Disposition",
                        "inline; filename=\"reporte-promedios.pdf\""
                    )
                    .build();

        } catch (Exception e) {
            return Response.serverError().entity("No se pudo generar el reporte: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("solvencias/{idGrupo}/{pagina}")
    public Response reporteSolvencias(@PathParam("idGrupo") int idGrupo, @PathParam("pagina") int pagina) {

        String reportePath = "/WEB-INF/reportes/reporte-solvencias.jrxml";

        try (InputStream jrxmlInput = servletContext.getResourceAsStream(reportePath)) {

            if (jrxmlInput == null) {
                throw new RuntimeException(
                    "No se encontró el reporte: " + reportePath
                );
            }

            ServicioReporte servicio = new ServicioReporte();

            List<SolvenciaEstudiante> solvencias = servicio.buscarSolvencias(idGrupo, pagina);
            
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(solvencias);

            JasperDesign design = JRXmlLoader.load(jrxmlInput);

            JasperReport report = JasperCompileManager.compileReport(design);

            Map<String, Object> parametros = new HashMap<>();
            parametros.put("ds", dataSource);
            
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parametros, new JREmptyDataSource());
            
            byte[] pdf = JasperExportManager.exportReportToPdf(jasperPrint);

            StreamingOutput out = (OutputStream output) -> {
                output.write(pdf);
                output.flush();
            };

            return Response.ok(out)
                    .type("application/pdf")
                    .header(
                        "Content-Disposition",
                        "inline; filename=\"reporte-promedios.pdf\""
                    )
                    .build();

        } catch (Exception e) {
            return Response.serverError().entity("No se pudo generar el reporte: " + e.getMessage()).build();
        }
    }
    
    @GET
    @Path("pagos-estudiante/{idEstudiante}/{pagina}")
    public Response reportePagosEstudiante(@PathParam("idEstudiante") String idEstudiante, @PathParam("pagina") int pagina) {

        String reportePath = "/WEB-INF/reportes/reporte-pagos-estudiante.jrxml";

        try (InputStream jrxmlInput = servletContext.getResourceAsStream(reportePath)) {

            if (jrxmlInput == null) {
                throw new RuntimeException(
                    "No se encontró el reporte: " + reportePath
                );
            }

            ServicioReporte servicio = new ServicioReporte();

            HistorialPagosEstudiante historial = servicio.buscarPagosPorEstudianteSinFecha(idEstudiante, pagina);
            
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(historial.getHistorial());
            JRBeanCollectionDataSource dsPendientes = new JRBeanCollectionDataSource(historial.getPagosPendientes());

            JasperDesign design = JRXmlLoader.load(jrxmlInput);

            JasperReport report = JasperCompileManager.compileReport(design);

            Map<String, Object> parametros = new HashMap<>();
            parametros.put("ds", dataSource);
            parametros.put("dsPendientes", dsPendientes);
            
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parametros, new JREmptyDataSource());
            
            byte[] pdf = JasperExportManager.exportReportToPdf(jasperPrint);

            StreamingOutput out = (OutputStream output) -> {
                output.write(pdf);
                output.flush();
            };

            return Response.ok(out)
                    .type("application/pdf")
                    .header(
                        "Content-Disposition",
                        "inline; filename=\"reporte-promedios.pdf\""
                    )
                    .build();

        } catch (Exception e) {
            return Response.serverError().entity("No se pudo generar el reporte: " + e.getMessage()).build();
        }
    }
    
    @GET
    @Path("pagos-estudiante-fechas/{idEstudiante}/{fechaInicio}/{fechaFin}/{pagina}")
    public Response reportePagosEstudianteFechas(
            @PathParam("idEstudiante") String idEstudiante, @PathParam("fechaInicio") String textoFechaInicio, 
            @PathParam("fechaFin") String textoFechaFin, @PathParam("pagina") int pagina) {

        String reportePath = "/WEB-INF/reportes/reporte-pagos-estudiante.jrxml";

        try (InputStream jrxmlInput = servletContext.getResourceAsStream(reportePath)) {

            if (jrxmlInput == null) {
                throw new RuntimeException(
                    "No se encontró el reporte: " + reportePath
                );
            }

            ServicioReporte servicio = new ServicioReporte();

            HistorialPagosEstudiante historial = servicio.buscarPagosPorEstudianteConFecha(idEstudiante, textoFechaInicio, textoFechaFin, pagina);
            
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(historial.getHistorial());
            JRBeanCollectionDataSource dsPendientes = new JRBeanCollectionDataSource(historial.getPagosPendientes());

            JasperDesign design = JRXmlLoader.load(jrxmlInput);

            JasperReport report = JasperCompileManager.compileReport(design);

            Map<String, Object> parametros = new HashMap<>();
            parametros.put("ds", dataSource);
            parametros.put("dsPendientes", dsPendientes);
            
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parametros, new JREmptyDataSource());
            
            byte[] pdf = JasperExportManager.exportReportToPdf(jasperPrint);

            StreamingOutput out = (OutputStream output) -> {
                output.write(pdf);
                output.flush();
            };

            return Response.ok(out)
                    .type("application/pdf")
                    .header(
                        "Content-Disposition",
                        "inline; filename=\"reporte-promedios.pdf\""
                    )
                    .build();

        } catch (Exception e) {
            return Response.serverError().entity("No se pudo generar el reporte: " + e.getMessage()).build();
        }
    }
    
    
    @GET
    @Path("ingresos")
    public Response reporteIngresos() {

        String reportePath = "/WEB-INF/reportes/reporte_ingresos.jrxml";

        try (InputStream jrxmlInput = servletContext.getResourceAsStream(reportePath)) {

            if (jrxmlInput == null) {
                throw new RuntimeException(
                    "No se encontró el reporte: " + reportePath
                );
            }

            ServicioReporte servicio = new ServicioReporte();

            List<IngresoDB> ingresos = servicio.buscarIngresosSinFechas();
            
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(ingresos);

            JasperDesign design = JRXmlLoader.load(jrxmlInput);

            JasperReport report = JasperCompileManager.compileReport(design);

            Map<String, Object> parametros = new HashMap<>();
            parametros.put("ds", dataSource);
            
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parametros, new JREmptyDataSource());
            
            byte[] pdf = JasperExportManager.exportReportToPdf(jasperPrint);

            StreamingOutput out = (OutputStream output) -> {
                output.write(pdf);
                output.flush();
            };

            return Response.ok(out)
                    .type("application/pdf")
                    .header(
                        "Content-Disposition",
                        "inline; filename=\"reporte-promedios.pdf\""
                    )
                    .build();

        } catch (Exception e) {
            return Response.serverError().entity("No se pudo generar el reporte: " + e.getMessage()).build();
        }
    }
    
    @GET
    @Path("ingresos-fechas/{fechaInicio}/{fechaFin}")
    public Response reporteIngresosConFecha(@PathParam("fechaInicio") String textoFechaInicio, @PathParam("fechaFin") String textoFechaFin) {

        String reportePath = "/WEB-INF/reportes/reporte_ingresos.jrxml";

        try (InputStream jrxmlInput = servletContext.getResourceAsStream(reportePath)) {

            if (jrxmlInput == null) {
                throw new RuntimeException(
                    "No se encontró el reporte: " + reportePath
                );
            }

            ServicioReporte servicio = new ServicioReporte();

            List<IngresoDB> ingresos = servicio.buscarIngresosConFechas(textoFechaInicio, textoFechaFin);
            
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(ingresos);

            JasperDesign design = JRXmlLoader.load(jrxmlInput);

            JasperReport report = JasperCompileManager.compileReport(design);

            Map<String, Object> parametros = new HashMap<>();
            parametros.put("ds", dataSource);
            
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parametros, new JREmptyDataSource());
            
            byte[] pdf = JasperExportManager.exportReportToPdf(jasperPrint);

            StreamingOutput out = (OutputStream output) -> {
                output.write(pdf);
                output.flush();
            };

            return Response.ok(out)
                    .type("application/pdf")
                    .header(
                        "Content-Disposition",
                        "inline; filename=\"reporte-promedios.pdf\""
                    )
                    .build();

        } catch (Exception e) {
            return Response.serverError().entity("No se pudo generar el reporte: " + e.getMessage()).build();
        }
    }
    
    
    

//@WebServlet("/api/v1/exportar-reporte")
        
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        
//        String reportePath = "/WEB-INF/reportes/reporte_de_promedios.jrxml";
//        
//        try (InputStream jrxmlInput = getServletContext().getResourceAsStream(reportePath)) {
//            if (jrxmlInput == null) {
//                throw new RuntimeException("El valor " + reportePath + " no es un path valido");
//            }
//            
//            String parametro = req.getParameter("tipo");
//            ReporteDAO reportedao = new ReporteDAO();
//            
//            if (parametro.equals("promedios")) {
//                int idGrupo = Integer.parseInt(req.getParameter("idGrupo"));
//                List<PromedioCursoDB> promedios = reportedao.getPromedioCursos(idGrupo);
//                JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(promedios);
//                
//                JasperDesign desing = JRXmlLoader.load(jrxmlInput);
//                JasperReport report = JasperCompileManager.compileReport(desing);
//                
//                Map<String, Object> parametros = new HashMap<>();
//                parametros.put("ds", dataSource);
//                
//                JasperPrint jasperPrint = JasperFillManager.fillReport(report, parametros, dataSource);
//                
//                resp.setContentType("application/pdf");
//                resp.setHeader("Content-Disposition", "inline; filename=rendimiento.pdf");
//                
//                try (OutputStream out = resp.getOutputStream()) {
//                    JasperExportManager.exportReportToPdfStream(jasperPrint, out);
//                }
//            }
//        } catch (Exception e) {
//            resp.setContentType("text/plain");
//            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//            e.printStackTrace();
//        }
        
}
