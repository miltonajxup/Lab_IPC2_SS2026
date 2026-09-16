<%-- 
    Document   : reporte-gastos
    Created on : 15 sept 2026, 18:28:09
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
            <h1>Reporte de Gastos</h1>
            <jsp:include page="/mvc/menu-regreso/regreso-menu-administrador.jsp" />
            <p class="cuadro-texto">Ingresa dos fecha para buscar los Reportes</p>
            <p class="error">${error}</p>
            <form method="GET" action="${pageContext.servletContext.contextPath}/mvc/administrador/reporte/reporte-servlet">
                <div class="contenedor-principal">
                    <input name="reporte-gastos" value="reporteGastos" type="hidden"/>
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
                        <input id="input-gastos" name="totales" type="hidden"/>
                        <button class="boton" onclick="agregarElementoUnico('true', 'input-gastos')">
                            Buscar Gastos Totales
                        </button>
                    </div>
                    <div>
                        <button class="boton" onclick="agregarElementoUnico('false', 'input-gastos')">
                            Buscar Gastos Por sucursal
                        </button>
                    </div>
                </div>
            </form>
            <table class="reporte" >
                <tr>
                    <c:if test="${gastos != null}">
                    <th class="verde">Sucursal</th>
                    </c:if>
                    
                    <th class="azul">Gasto Combustible</th>
                    <th class="verde">Gasto de Taller (Mano de Obra)</th>
                    <th class="azul">Gasto de Taller (Repuestos)</th>
                    <th class="verde">Depreciacion</th>
                    <th class="azul">Total Gastos</th>
                </tr>
                    
                <c:if test="${gastosTotales != null}" >
                <tr class="celeste">
                    <td > ${gastosTotales.gastoCombustible} </td>
                    <td > ${gastosTotales.gastoTallerMano} </td>
                    <td > ${gastosTotales.gastoTallerRepuestos} </td>
                    <td > ${gastosTotales.depreciacion} </td>
                    <td > ${gastosTotales.totalGastos} </td>
                </tr>
                </c:if>
                <c:forEach items="${gastos}" var="gasto" >
                <tr class="celeste">
                    <td > ${gasto.sucursal} </td>
                    <td > ${gasto.gastoCombustible} </td>
                    <td > ${gasto.gastoTallerMano} </td>
                    <td > ${gasto.gastoTallerRepuestos} </td>
                    <td > ${gasto.depreciacion} </td>
                    <td > ${gasto.totalGastos} </td>
                </tr>
                </c:forEach>
            </table>
        </c:if>
        <c:if test="${usuarioLogeado == null}">
            <jsp:include page="/error-log.jsp" />
        </c:if>
    </body>
</html>
