<%-- 
    Document   : index
    Created on : 1 sept 2026, 22:52:27
    Author     : milton
--%>

<%@page import="com.mycompany.proyecto1ss2026.Exeptions.LoginException"%>
<%@page import="com.mycompany.proyecto1ss2026.Exeptions.AccesoALaDataException"%>
<%@page import="com.mycompany.proyecto1ss2026.Modelos.DataBase.UsuarioDB"%>
<%@page import="com.mycompany.proyecto1ss2026.Constantes.RolUsuario"%>
<%@page import="com.mycompany.proyecto1ss2026.Servicios.ServicioLogin"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <jsp:include page="/includes/resources.jsp"/>
    </head>
    <body>
        <h1>Proyecto 1 IPC2</h1>
        <div class="menuBotones">
            <a class="boton" href="${pageContext.servletContext.contextPath}/mvc/usuario/perfil/crear-usuario.jsp">Registrarse como Usuario</a>
        </div>
        
        <% session.setAttribute("usuarioLogeado", null); %>
        
        <p class="error" >${error}</p>
        <div class="contenedor-principal">
            <form method="GET" action="${pageContext.servletContext.contextPath}/login/log-servlet">
                <label>
                    Nombre
                    <input name="nombre"/>
                </label>
                <label>
                    Contraseña (dpi o numero de licencia)
                    <input name="id-usuario"/>
                </label>
                <button class="boton">Ingresar</button>
            </form>
        </div>
    </body>
</html>
