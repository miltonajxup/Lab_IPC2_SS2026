<%-- 
    Document   : gastos-taller
    Created on : 16 sept 2026, 0:18:31
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
        <h1>Gastos de Taller</h1>
        <jsp:include page="/mvc/menu-regreso/regreso-menu-administrador.jsp" />
        <p class="error" >${error}</p>
        <div class="contenedor-principal">
            <div class="contenedor-opciones" >
                <c:forEach items="${buses}" var="bus">
                    <a class="opcion" href="${pageContext.servletContext.contextPath}/mvc/administrador/taller/taller-servlet?id-sucursal=${usuarioLogeado.sucursal}&id-bus=${bus.numeroPlaca}">
                        Numero de Placa: ${bus.numeroPlaca}
                    </a>
                </c:forEach>
                <c:if test="${empty buses}">
                    <p class="cuadro-texto">Esta sucursal no tiene buses registrados</p>
                </c:if>
            </div>
            <c:if test="${bus != null}" >
            <form method="POST" action="${pageContext.servletContext.contextPath}/mvc/administrador/taller/taller-servlet?id-sucursal=${usuarioLogeado.sucursal}&id-bus=${bus.numeroPlaca}" >
                <label>
                    Monto de Mano de Obra 
                    <input name="monto-mano-obra" type="number" />
                </label>
                <label>
                    Monto de Repuestos
                    <input name="monto-repuestos" type="number" />
                </label>
                <label>
                    Fecha de Registro
                    <input name="fecha" type="date" />
                </label>
                <button class="boton">Registrar Gasto</button>
            </form>
            </c:if>
        </div>
        </c:if>
        
        <c:if test="${usuarioLogeado == null}">
        <jsp:include page="/error-log.jsp" />
        </c:if>
    </body>
</html>
