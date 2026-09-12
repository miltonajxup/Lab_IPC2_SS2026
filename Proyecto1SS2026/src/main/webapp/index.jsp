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
            <a class="boton" href="usuario/crear-usuario.jsp">Registrarse como Usuario</a>
        </div>
        <%  
            session.setAttribute("usuario-logeado", null);
            ServicioLogin servicio = new ServicioLogin();
            String idUsuario = request.getParameter("id-usuario");
            String nombre = request.getParameter("nombre");
            if (idUsuario != null && nombre != null && !idUsuario.isEmpty() && !nombre.isEmpty()) {
                try {
                    UsuarioDB usuario = servicio.loogearUsuario(idUsuario, nombre);
                    session.setAttribute("usuario-logeado", usuario);
                    if (usuario.getRol() == RolUsuario.CLIENTE) {
                        
                    } else if (usuario.getRol() == RolUsuario.ADMINISTRADOR || usuario.getRol() == RolUsuario.ADMINISTRADOR_SUCURSAL) {
                        response.sendRedirect("administrador/menu-administrador.jsp");
                    }
                } catch (AccesoALaDataException | LoginException e) {
        %>
        <p class="error"><%=e.getMessage()%></p>
        <%
                }
            }
        %>
        <div class="contenedor-principal">
            <form method="POST" action="index.jsp">
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
