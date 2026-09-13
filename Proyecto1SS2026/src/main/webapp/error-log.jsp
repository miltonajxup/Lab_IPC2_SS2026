<%-- 
    Document   : error-log
    Created on : 11 sept 2026, 11:00:22
    Author     : milton
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <jsp:include page="/includes/resources.jsp"/>
    </head>
    <body>
        <p class="cuadro-texto">Parece ser que aun no se ha logeado</p>
        <a class="boton" href="${pageContext.servletContext.contextPath}/index.jsp">Regresar</a>
    </body>
</html>
