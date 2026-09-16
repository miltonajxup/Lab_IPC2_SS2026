<%-- 
    Document   : reporte-buses
    Created on : 15 sept 2026, 18:59:42
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
            <h1>Reporte de Buses</h1>
            <jsp:include page="/mvc/menu-regreso/regreso-menu-administrador.jsp" />
            
<!--            <form class="contenedor-principal" method="GET" action="${pageContext.servletContext.contextPath}/mvc/administrador/reporte/reporte-servlet">
                <input name="reporte-buses" value="reporteBuses" type="hidden"/>
                <input name="sucursal" value="${usuarioLogeado.sucursal}" type="hidden"/>
                <div>
                    <button class="boton" >
                        Buscar Registros
                    </button>
                </div>
            </form>-->
            <p class="cuadro-texto">Buses de la Sucursal ${usuarioLogeado.sucursal}
            
            <p class="error">${error}</p>
            
            <table class="reporte" >
                <tr>
                    <th class="verde">Numero de Placa</th>
                    <th class="azul">Marca</th>
                    <th class="verde">Modelo</th>
                    <th class="azul">Capacidad</th>
                    <th class="verde">Estado Operativo</th>
                    <th class="azul">Kilometraje</th>
                    <th class="verde">Viajes Realizados</th>
                    
                    <c:forEach items="${buses}" var="bus">
                    <tr class="celeste">
                        <td > ${bus.numeroPlaca} </td>
                        <td > ${bus.marca} </td>
                        <td > ${bus.modelo} </td>
                        <td > ${bus.capacidadPasajeros} </td>
                        <c:if test="${bus.estadoOperativo == true}">
                        <td > Activo </td>
                        </c:if>
                        <c:if test="${bus.estadoOperativo == false}">
                        <td > Desactivado </td>
                        </c:if>
                        <td > ${bus.kilometraje} </td>
                        <td > ${bus.viajesCompletados} </td>
                    </tr>
                    </c:forEach>
                    <c:if test="${buses != null && empty buses}">
                        <p class="cuadro-texto"> Esta sucural aun no tiene registros sus buses</p>
                    </c:if>
                </tr>
            </table>
        </c:if>
        <c:if test="${usuarioLogeado == null}">
            <jsp:include page="/error-log.jsp" />
        </c:if>
    </body>
</html>
