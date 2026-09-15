<%-- 
    Document   : menu-usuario
    Created on : 13 sept 2026, 20:02:51
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
            <h1>Menu Usuario</h1>
            <div class="menuBotones">
                <a class="boton" href="${pageContext.servletContext.contextPath}/mvc/usuario/usuario-servlet?rutas=todos">Comprar Boleto</a>
                <a class="boton" href="${pageContext.servletContext.contextPath}/mvc/usuario/viaje/crear-viaje-privado.jsp">Crear Viaje Privado</a>
                <a class="boton" href="${pageContext.servletContext.contextPath}/mvc/usuario/viaje/consultar-viajes.jsp">Consultar Viajes Comprados</a>
                <a class="boton" href="${pageContext.servletContext.contextPath}/mvc/usuario/perfil/modificar-perfil.jsp">Modificar Perfil</a>
                <a class="boton" href="${pageContext.servletContext.contextPath}/index.jsp">Regresar</a>
            </div>
            
        </c:if>
            <p class="error">${error}</p>
        <c:if test="${usuarioLogeado == null}">
            <jsp:include page="/error-log.jsp" />
        </c:if>
    </body>
</html>
