<%-- 
    Document   : ReporteDepreciacion
    Created on : 15 sept 2026, 21:21:36
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
            
            <form class="contenedor-principal" method="GET" action="${pageContext.servletContext.contextPath}/mvc/administrador/reporte/reporte-servlet">
                <input name="reporte-depreciacion" value="reporteDepreciacion" type="hidden"/>
                <input name="sucursal" value="${usuarioLogeado.sucursal}" type="hidden"/>
                <label>
                    Indica la ruta de la carpeta donde se guardará el archivo
                    <input name="ruta">
                <div>
                    <button class="boton" >
                        GuardarReporte
                    </button>
                </div>
                </label>
                <div >
                    <c:if test="${rutaArchivo != null}">
                        La carpeta del archivo es: <strong> ${rutaArchivo} </strong>
                    </c:if>
                </div>
            </form>
                
            <p class="cuadro-texto">Depreciaciones de la Sucursal ${usuarioLogeado.sucursal}
                
            <p class="error">${error}</p>
            
            <table class="reporte" >
                <tr>
                    <th class="verde">Numero de Placa del Bus</th>
                    <th class="azul">Monto de Depreciacion</th>
                    <th class="verde">Kilometros Recorridos</th>
                </tr>
                    
                    <c:forEach items="${depreciacionBuses}" var="depreciacion">
                    <tr class="celeste">
                        <td > ${depreciacion.bus} </td>
                        <td > ${depreciacion.montoDepreciacion} </td>
                        <td > ${depreciacion.kilometrosRecorridos} </td>
                    </tr>
                    </c:forEach>
            </table>
        </c:if>
        <c:if test="${usuarioLogeado == null}">
            <jsp:include page="/error-log.jsp" />
        </c:if>
    </body>
</html>
