<%-- 
    Document   : CrearUsuario
    Created on : 3 sept 2026, 11:20:24
    Author     : milton
--%>

<%@page import="com.mycompany.proyecto1ss2026.Exeptions.ValorInvalidoException"%>
<%@page import="com.mycompany.proyecto1ss2026.Respuesta.Respuesta"%>
<%@page import="com.mycompany.proyecto1ss2026.Exeptions.ValorExistenteException"%>
<%@page import="com.mycompany.proyecto1ss2026.Exeptions.AccesoALaDataException"%>
<%@page import="com.mycompany.proyecto1ss2026.Constantes.RolUsuario"%>
<%@page import="com.mycompany.proyecto1ss2026.Servicios.ServicioUsuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <jsp:include page="/includes/resources.jsp"/>
        <script src="${pageContext.servletContext.contextPath}/resources/js/index-javascript.js"></script>
    </head>
    <body>
        <h1>Crer un Usuario</h1>
        <div class="menuBotones">
            <a class="boton" href="${pageContext.servletContext.contextPath}/index.jsp">Regresar</a>
        </div>
        <%
            Respuesta respuesta = null;
            ServicioUsuario servicio = new ServicioUsuario();
            String dpi = request.getParameter("dpi");
            String nombre = request.getParameter("nombre");
            String nit = request.getParameter("nit");
            String telefono = request.getParameter("telefono");
            String direccion = request.getParameter("direccion");
            try {
                    respuesta = servicio.agregarUsuario(dpi, nombre, nit, telefono, direccion);
            } catch (AccesoALaDataException | ValorExistenteException | ValorInvalidoException e) {
                %> <p class="error"><%=e.getMessage()%></p> <%
            }
            if (respuesta != null && respuesta.isCorrecto()) {
                %> <p class="correcto"><%=respuesta.getMensaje()%></p> <%
            }
        %>
        <div class="contenedor-principal">
            <form method="POST" action="crear-usuario.jsp">
                <label>
                    DPI
                    <input name="dpi" />
                </label>
                <label>
                    Nombre
                    <input name="nombre" />
                </label>
                <label>
                    NIT
                    <input name="nit" />
                </label>
                <label>
                    Telefono
                    <input name="telefono" />
                </label>
                <label>
                    direccion
                    <input name="direccion" />
                </label>
                <button class="boton" type="submit">Guardar</button>
            </form>
        </div>
</html>
