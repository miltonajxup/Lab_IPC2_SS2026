<%-- 
    Document   : crear-usuario
    Created on : 7 sept 2026, 19:55:33
    Author     : milton
--%>

<%@page import="com.mycompany.proyecto1ss2026.Modelos.DataBase.UsuarioDB"%>
<%@page import="com.mycompany.proyecto1ss2026.Exeptions.ValorInvalidoException"%>
<%@page import="com.mycompany.proyecto1ss2026.Modelos.DataBase.SucursalDB"%>
<%@page import="java.util.List"%>
<%@page import="com.mycompany.proyecto1ss2026.DAOs.SucursalDAO"%>
<%@page import="com.mycompany.proyecto1ss2026.Exeptions.ValorExistenteException"%>
<%@page import="com.mycompany.proyecto1ss2026.Exeptions.AccesoALaDataException"%>
<%@page import="com.mycompany.proyecto1ss2026.Servicios.ServicioUsuario"%>
<%@page import="com.mycompany.proyecto1ss2026.Respuesta.Respuesta"%>
<%@page import="com.mycompany.proyecto1ss2026.Constantes.RolUsuario"%>
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
        <h1>Crer un Usuario</h1>
        <jsp:include page="/mvc/menu-regreso/regreso-menu-administrador.jsp" />
        <%
            Respuesta respuesta = null;
            ServicioUsuario servicio = new ServicioUsuario();
            String dpi = request.getParameter("dpi");
            String nombre = request.getParameter("nombre");
            String nit = request.getParameter("nit");
            String telefono = request.getParameter("telefono");
            String direccion = request.getParameter("direccion");
            String rol = request.getParameter("rol");
            String sucursal = request.getParameter("sucursal");
            
            try {
                respuesta = servicio.agregarUsuarioPorAdmin(dpi, nombre, nit, telefono, direccion, rol, sucursal);
            } catch (AccesoALaDataException | ValorExistenteException | ValorInvalidoException e) {
                %> <p class="error"><%=e.getMessage()%></p> <%
            }
            if (respuesta != null && respuesta.isCorrecto()) {
                %> <p class="correcto"><%=respuesta.getMensaje()%></p> <%
            }
            SucursalDAO sucursaldao = new SucursalDAO();
            List<SucursalDB> sucursales = null;
            try {
                sucursales = sucursaldao.todasLasSucursales();
            } catch (AccesoALaDataException e) {
                %> <p class="error"><%=e.getMessage()%></p> <%
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
                    Direccion
                    <input name="direccion" />
                </label>
                <select name="rol" onchange="mostrarSucursalesEnUsuario(this)"> 
                    <option value="">Elige el tipo de Usuario</option>
                    <option value="<%=RolUsuario.ADMINISTRADOR.name()%>">ADMINISTRADOR</option>
                    <option value="<%=RolUsuario.ADMINISTRADOR_SUCURSAL.name()%>">ADMINISTRADOR DE SUCURSAL</option>
                    <option value="<%=RolUsuario.CLIENTE.name()%>">CLIENTE</option>
                </select>
                <select id="sucursales-en-usuario" name="sucursal">
                    <option value="">Selecciona una sucursal</option>
                    <%
                        for (SucursalDB sucursalLista : sucursales) {
                    %>
                    <option value="<%=sucursalLista.getCodigo()%>"><%=sucursalLista.getNombre()%></option>
                    <%
                        }
                    %>
                </select>
                <button class="boton" type="submit">Guardar</button>
            </form>
        </div>
        <% } else { %>
        <jsp:include page="/includes/resources.jsp" />
        <% } %>
    </body>
</html>
