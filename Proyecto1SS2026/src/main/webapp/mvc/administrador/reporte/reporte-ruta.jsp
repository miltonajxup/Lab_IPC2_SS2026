<%-- 
    Document   : RutaDemandada
    Created on : 15 sept 2026, 17:48:47
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
            <h1>Reporte de la Ruta mas demandada</h1>
            <jsp:include page="/mvc/menu-regreso/regreso-menu-administrador.jsp" />
            <p class="cuadro-texto">Ingresa dos fecha para buscar los Reportes</p>
            <p class="error">${error}</p>
            <form method="GET" action="${pageContext.servletContext.contextPath}/mvc/administrador/reporte/reporte-servlet">
                <div class="contenedor-principal">
                    <input name="reporte-ruta-demandada" value="reporteRutaDemandada" type="hidden"/>
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
                        <button class="boton" >
                            Buscar Registros
                        </button>
                    </div>
                </div>
            </form>
            <table class="reporte" >
                <tr>
                    <th class="verde">Id Ruta</th>
                    <th class="azul">Boletos Vendidos</th>
                    <th class="verde">Precio Boleto</th>
                    <th class="azul">Distancia</th>
                    <th class="verde">Sucursal Origen</th>
                    <th class="azul">Ciudad Origen</th>
                    <th class="verde">Sucursal Destino</th>
                    <th class="azul">Ciudad Destino</th>
                    
                    <c:forEach items="${rutasDemandadas}" var="ruta">
                    <tr class="celeste">
                        <td > ${ruta.rutaId} </td>
                        <td > ${ruta.boletosVendidos} </td>
                        <td > ${ruta.precioBoleto} </td>
                        <td > ${ruta.distancia} </td>
                        <td > ${ruta.sucursalOrigen} </td>
                        <td > ${ruta.ciudadOrigen} </td>
                        <td > ${ruta.sucursalDestino} </td>
                        <td > ${ruta.ciudadDestino} </td>
                    </tr>
                    </c:forEach>
                </tr>
            </table>
        </c:if>
        <c:if test="${usuarioLogeado == null}">
            <jsp:include page="/error-log.jsp" />
        </c:if>
    </body>
</html>
