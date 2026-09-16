<%-- 
    Document   : reporte-choferes
    Created on : 15 sept 2026, 20:30:35
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
            <h1>Reporte de Choferes</h1>
            <jsp:include page="/mvc/menu-regreso/regreso-menu-administrador.jsp" />
            
            <p class="cuadro-texto">Choferes de la Sucursal ${usuarioLogeado.sucursal}
            
            <p class="error">${error}</p>
            
            <table class="reporte" >
                <tr>
                    <th class="verde">Numero de Licencia</th>
                    <th class="azul">Nombre</th>
                    <th class="verde">Tipo de Licencia</th>
                    <th class="azul">Fecha de Vencimiento</th>
                    <th class="verde">Estado</th>
                    <th class="verde">Viajes Realizados</th>
                    
                    <c:forEach items="${choferes}" var="chofer">
                    <tr class="celeste">
                        <td > ${chofer.numeroLicencia} </td>
                        <td > ${chofer.nombre} </td>
                        <td > ${chofer.tipoLicencia} </td>
                        <td > ${chofer.fechaVencimiento} </td>
                        <c:if test="${chofer.estadoOperativo == true}">
                        <td > Activo </td>
                        </c:if>
                        <c:if test="${chofer.estadoOperativo == false}">
                        <td > Desactivado </td>
                        </c:if>
                        <td > ${chofer.viajesCompletados} </td>
                    </tr>
                    </c:forEach>
                    <c:if test="${choferes != null && empty choferes}">
                        <p class="cuadro-texto"> Esta sucural aun no tiene registros de sus choferes</p>
                    </c:if>
                </tr>
            </table>
        </c:if>
        <c:if test="${usuarioLogeado == null}">
            <jsp:include page="/error-log.jsp" />
        </c:if>
    </body>
</html>
