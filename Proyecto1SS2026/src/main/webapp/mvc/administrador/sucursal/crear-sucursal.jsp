<%-- 
    Document   : crear-sucursal
    Created on : 5 sept 2026, 11:55:53
    Author     : milton
--%>

<%@page import="com.mycompany.proyecto1ss2026.Respuesta.Respuesta"%>
<%@page import="com.mycompany.proyecto1ss2026.Modelos.DataBase.UsuarioDB"%>
<%@page import="com.mycompany.proyecto1ss2026.Exeptions.ValorInvalidoException"%>
<%@page import="com.mycompany.proyecto1ss2026.Exeptions.ValorExistenteException"%>
<%@page import="com.mycompany.proyecto1ss2026.Exeptions.AccesoALaDataException"%>
<%@page import="com.mycompany.proyecto1ss2026.Servicios.ServicioSucursal"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <jsp:include page="/includes/resources.jsp"/>
    </head>
    <body>
        <%
            UsuarioDB usuario = (UsuarioDB) session.getAttribute("usuarioLogeado");
            if (usuario != null) {
        %>
        <h1>Crear Sucursal</h1>
        <div class="menuBotones">
            <a class="boton" href="../menu-administrador.jsp">Regresar</a>
        </div>
        <%
            String codigo = request.getParameter("codigo");
            String nombre = request.getParameter("nombre");
            String ciudad = request.getParameter("ciudad");
            Respuesta respuesta = null;
            if (codigo != null && nombre != null && ciudad != null 
                    && !codigo.isEmpty() && !nombre.isEmpty() && !ciudad.isEmpty())  {
                ServicioSucursal servicio = new ServicioSucursal();
                try {
                    respuesta = servicio.agregarSucursal(codigo, nombre, ciudad);
                } catch (AccesoALaDataException | ValorExistenteException | ValorInvalidoException e) {
                    %> <p class="error"><%=e.getMessage()%></p> <%
                }
            }
            if (respuesta != null && respuesta.isCorrecto()) {
                %> <p class="correcto"> <%=respuesta.getMensaje()%> </p> <%
            }
        %>
        <div class="contenedor-principal">
            <form method="POST" action="crear-sucursal.jsp">
                <label>
                    Codigo de Sucursal:
                    <input name="codigo" />
                </label>
                <label>
                    Nombre de la Sucursal:
                    <input name="nombre"/>
                </label>
                <label>
                    Ciudad de la Sede (O referencia): 
                    <input name="ciudad"/>
                </label>
                <button class="boton" type="submit">Crear Sucursal</button>
            </form>
        </div>
        <% } else { %>
        <jsp:include page="/includes/resources.jsp" />
        <% } %>
    </body>
</html>
