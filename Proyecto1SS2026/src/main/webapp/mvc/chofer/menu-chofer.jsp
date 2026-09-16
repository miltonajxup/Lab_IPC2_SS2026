<%-- 
    Document   : menu-chofer
    Created on : 15 sept 2026, 23:01:17
    Author     : milton
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <jsp:include page="/includes/resources.jsp" />
    </head>
    <body>
        <h1>Menu de Chofer</h1>
        <div class="menuBotones">
            <a class="boton" href="${pageContext.servletContext.contextPath}/mvc/administrador/viaje/controlar-viaje.jsp" >Controlar Viaje Publico</a>
            <a class="boton" href="${pageContext.servletContext.contextPath}/index.jsp">Cerrar Sesion</a>
        </div>
    </body>
</html>
