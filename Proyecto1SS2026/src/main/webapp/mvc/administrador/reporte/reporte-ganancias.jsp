<%-- 
    Document   : reporte-ganancias
    Created on : 15 sept 2026, 12:05:04
    Author     : milton
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <jsp:include page="/includes/resources.jsp" />
    </head>
    <body>
        <c:if test="${usuarioLogeado != null}">
            <h1>Reporte de Ganancias</h1>
            <jsp:include page="/mvc/menu-regreso/regreso-menu-administrador.jsp" />
            <p class="cuadro-texto">Ingresa dos fecha para buscar los Reportes</p>
            <p class="error">${error}</p>
            <form method="GET" action="${pageContext.servletContext.contextPath}/mvc/administrador/reporte/reporte-servlet">
                <div class="contenedor-principal">
                    <input name="reporte-ganancias" value="reporteGanancias" type="hidden"/>
                    <label>
                        Fecha Inicial: 
                        <input name="fecha-inicial" type="date" />
                    </label>
                    <label>
                        Fecha Final: 
                        <input name="fecha-final" type="date"/>
                    </label>
                </div>
                <div class="contenedor-principal">
                    <div>
                        <input id="input-ganancias" name="totales" type="hidden"/>
                        <button class="boton" onclick="agregarElementoUnico('true', 'input-ganancias')">
                            Buscar Ganancias Totales
                        </button>
                    </div>
                    <div>
                        <button class="boton" onclick="agregarElementoUnico('false', 'input-ganancias')">
                            Buscar Ganancias Por sucursal
                        </button>
                    </div>
                </div>
                <div class="contenedor-principal">
                    <label>
                        <br> <br>
                        Ingresa un carpeta para guardar el reporte
                        <input name="ruta" />
                    </label>
                </div>
                <div >
                    <c:if test="${rutaHtml != null}">
                        La carpeta de Guadado del Reporte es: <strong> ${rutaHtml} </strong>
                    </c:if>
                </div>
            </form>
            
            <p class="cuadro-texto">Ganancias de la Sucursal ${usuarioLogeado.sucursal}    
                
            <table class="reporte" >
                <tr>
                    <c:if test="${ganancias != null}">
                    <th class="verde">Sucursal</th>
                    </c:if>
                    
                    <th class="azul">Ventas</th>
                    <th class="verde">Gasto Combustible</th>
                    <th class="azul">Gasto de Taller (Mano de Obra)</th>
                    <th class="verde">Gasto de Taller (Repuestos)</th>
                    <th class="azul">Depreciacion</th>
                    <th class="verde">Total Gastos</th>
                    <th class="azul">Total Ganancias</th>
                </tr>
                    
                <c:if test="${gananciasTotales != null}" >
                <tr class="celeste">
                    <td > ${gananciasTotales.ventas} </td>
                    <td > ${gananciasTotales.gastoCombustible} </td>
                    <td > ${gananciasTotales.gastoTallerMano} </td>
                    <td > ${gananciasTotales.gastoTallerRepuestos} </td>
                    <td > ${gananciasTotales.depreciacion} </td>
                    <td > ${gananciasTotales.totalGastos} </td>
                    <td > ${gananciasTotales.totalGanancias} </td>
                </tr>
                </c:if>
                <c:forEach items="${ganancias}" var="ganancia" >
                <tr class="celeste">
                    <td > ${ganancia.sucursal} </td>
                    <td > ${ganancia.ventas} </td>
                    <td > ${ganancia.gastoCombustible} </td>
                    <td > ${ganancia.gastoTallerMano} </td>
                    <td > ${ganancia.gastoTallerRepuestos} </td>
                    <td > ${ganancia.depreciacion} </td>
                    <td > ${ganancia.totalGastos} </td>
                    <td > ${ganancia.totalGanancias} </td>
                </tr>
                </c:forEach>
            </table>
        </c:if>
        <c:if test="${usuarioLogeado == null}">
            <jsp:include page="/error-log.jsp" />
        </c:if>
    </body>
</html>
