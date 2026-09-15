<%-- 
    Document   : regreso-menu-usuario
    Created on : 13 sept 2026, 20:17:15
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
        <div class="menuBotones">
            <a class="boton" href="${pageContext.servletContext.contextPath}/mvc/usuario/menu-usuario.jsp">Regresar</a>
        </div>
    </body>
</html>
