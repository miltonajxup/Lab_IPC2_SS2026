<%-- 
    Document   : reporte-boletos
    Created on : 15 sept 2026, 19:51:42
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
            <h1>Reporte de Los ingresos Por Boletos Vendidos</h1>
            <jsp:include page="/mvc/menu-regreso/regreso-menu-administrador.jsp" />
            
            <p class="cuadro-texto">Ingresa dos fecha para buscar los Reportes</p>
            <p class="error">${error}</p>
            <form method="GET" action="${pageContext.servletContext.contextPath}/mvc/administrador/reporte/reporte-servlet">
                <div class="contenedor-principal">
                    <input name="reporte-boletos" value="reporteBoletos" type="hidden"/>
                    <input name="sucursal" value="${usuarioLogeado.sucursal}" type="hidden"/>
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
                    <th class="verde">Viaje Id</th>
                    <th class="azul">Numero de Licencia del Chofer</th>
                    <th class="verde">Nombre del Chofer</th>
                    <th class="azul">Numero de Placa del Bus</th>
                    <th class="verde">Id de la Ruta</th>
                    <th class="azul">Distancia (km)</th>
                    <th class="verde">Precio del Boleto</th>
                    <th class="azul">Sucursal de Origen</th>
                    <th class="verde">Sucursal de Destino</th>
                    <th class="azul">Fecha de Salida</th>
                    <th class="verde">Boletos Vendidos</th>
                    <th class="azul">Ingreso Total</th>
                    
                    <c:forEach items="${ingresoBoletos}" var="ingreso">
                    <tr class="celeste">
                        <td > ${ingreso.viajeId} </td>
                        <td > ${ingreso.licencia} </td>
                        <td > ${ingreso.chofer} </td>
                        <td > ${ingreso.bus} </td>
                        <td > ${ingreso.rutaId} </td>
                        <td > ${ingreso.distancia} </td>
                        <td > ${ingreso.precioBoleto} </td>
                        <td > ${ingreso.sucursalOrigen} </td>
                        <td > ${ingreso.sucursalDestino} </td>
                        <td > ${ingreso.fechaSalida} </td>
                        <td > ${ingreso.boletosVendidos} </td>
                        <td > ${ingreso.ingresoTotal} </td>
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
