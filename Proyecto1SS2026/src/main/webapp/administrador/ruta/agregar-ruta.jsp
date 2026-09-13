<%-- 
    Document   : agregar-ruta
    Created on : 11 sept 2026, 16:40:30
    Author     : milton
--%>

<%@page import="com.mycompany.proyecto1ss2026.DAOs.SucursalDAO"%>
<%@page import="com.mycompany.proyecto1ss2026.Modelos.DataBase.SucursalDB"%>
<%@page import="java.util.List"%>
<%@page import="com.mycompany.proyecto1ss2026.Exeptions.ValorInvalidoException"%>
<%@page import="com.mycompany.proyecto1ss2026.Exeptions.ValorInexistenteException"%>
<%@page import="com.mycompany.proyecto1ss2026.Exeptions.ValorExistenteException"%>
<%@page import="com.mycompany.proyecto1ss2026.Exeptions.AccesoALaDataException"%>
<%@page import="com.mycompany.proyecto1ss2026.Servicios.ServicioRuta"%>
<%@page import="com.mycompany.proyecto1ss2026.Modelos.DataBase.UsuarioDB"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <jsp:include page="/includes/resources.jsp" />
    </head>
    <body>
        <%
            UsuarioDB usuario = (UsuarioDB) session.getAttribute("usuario-logeado");
            if (usuario != null) {
        %>
        <h1>Agregar una Ruta</h1>
        <div class="menuBotones">
            <a class="boton" href="../menu-administrador.jsp">Regresar</a>
        </div>
        <%
            String textoDistancia = request.getParameter("distancia");
            String textoPrecio  = request.getParameter("precio");
            String sucursalRegistro = usuario.getSucursal();
            String sucursalOrigen = request.getParameter("sucursal-origen");
            String sucursalDestino = request.getParameter("sucursal-destino");
            
            String mensaje = null;
            ServicioRuta servicio = new ServicioRuta();
            if (textoDistancia != null && textoPrecio != null && sucursalRegistro != null && sucursalOrigen != null && sucursalDestino != null 
                    && !textoDistancia.isEmpty() && !textoPrecio.isEmpty() && !sucursalRegistro.isEmpty() && !sucursalOrigen.isEmpty() && !sucursalDestino.isEmpty()) {
                try {
                    mensaje = servicio.agregarRuta(textoDistancia, textoPrecio, sucursalRegistro, sucursalOrigen, sucursalDestino);
                } catch (AccesoALaDataException | ValorExistenteException | ValorInexistenteException | ValorInvalidoException e) {
        %>
        <p class="error"><%=e.getMessage()%></p>
        <%
                }
            }
            if (mensaje != null) {
        %>
        <p class="correcto"><%=mensaje%></p>
        <%
            }
            SucursalDAO sucursaldao = new SucursalDAO();
            List<SucursalDB> sucursales = null;
            try {
                sucursales = sucursaldao.todasLasSucursales();
            } catch (AccesoALaDataException e) {
        %>
        <p class="error"><%=e.getMessage()%></p>
        <%
            }
        %>
        <div class="contenedor-principal">  
            <form method="POST" action="agregar-ruta.jsp">
                <label>
                    Distancia Aproximada (En kilometros)
                    <input name="distancia" type="number"/>
                </label>
                <label>
                    Precio del Boleto (En Quetzales)
                    <input name="precio" type="number"/>
                </label>
                Selecciona la Sucursal Origen de la Ruta
                <select name    ="sucursal-origen">
                    <option></option>
                    <%
                        if (sucursales != null) {
                            for (SucursalDB sucursal : sucursales) {
                    %>
                    <option value="<%=sucursal.getCodigo()%>"><%=sucursal.getNombre()%></option>
                    <%  
                            }
                        }
                    %>SucrsalDB
                </select>
                Selecciona la Sucursal Destino de la Ruta
                <select name="sucursal-destino">
                    <option></option>
                    <%
                        if (sucursales != null) {
                            for (SucursalDB sucursal : sucursales) {
                    %>
                    <option value="<%=sucursal.getCodigo()%>"><%=sucursal.getNombre()%></option>
                    <%  
                            }
                        }
                    %>
                </select>
                <button class="boton">Registrar Ruta</button>
            </form>
        </div>
        <% } else { %>
        <jsp:include page="/error-log.jsp"/>
        <% } %>
        
    </body>
</html>
