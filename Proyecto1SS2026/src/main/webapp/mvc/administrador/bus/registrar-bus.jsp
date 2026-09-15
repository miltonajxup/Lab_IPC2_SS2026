<%-- 
    Document   : registrar-bus
    Created on : 10 sept 2026, 8:51:59
    Author     : milton
--%>

<%@page import="com.mycompany.proyecto1ss2026.Constantes.RolUsuario"%>
<%@page import="com.mycompany.proyecto1ss2026.Modelos.DataBase.UsuarioDB"%>
<%@page import="com.mycompany.proyecto1ss2026.Modelos.DataBase.SucursalDB"%>
<%@page import="java.util.List"%>
<%@page import="com.mycompany.proyecto1ss2026.DAOs.SucursalDAO"%>
<%@page import="com.mycompany.proyecto1ss2026.Exeptions.ValorInvalidoException"%>
<%@page import="com.mycompany.proyecto1ss2026.Exeptions.ValorExistenteException"%>
<%@page import="com.mycompany.proyecto1ss2026.Exeptions.AccesoALaDataException"%>
<%@page import="com.mycompany.proyecto1ss2026.Servicios.ServicioBus"%>
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
            UsuarioDB usuario = (UsuarioDB) session.getAttribute("usuarioLogeado");
            if (usuario != null) {
        %>
        <h1>Registrar Bus</h1>
        <jsp:include page="/mvc/menu-regreso/regreso-menu-administrador.jsp" />
        <%
            String numeroPlaca = request.getParameter("numero-placa");
            String marca = request.getParameter("marca");
            String modelo = request.getParameter("modelo");
            String fechaFabricacion = request.getParameter("fecha-fabricacion");
            String textoCantidadPasajeros = request.getParameter("cantidad-pasajeros");
            String textoKilometraje = request.getParameter("kilometraje");
            String sucursalBase = request.getParameter("sucursal-base");
            if (usuario.getRol() == RolUsuario.ADMINISTRADOR_SUCURSAL) {
                sucursalBase = usuario.getSucursal();
            }
            ServicioBus servicio = new ServicioBus();
            String mensajeCreacion = null;
            
            try {
                mensajeCreacion = servicio.agregarBus(numeroPlaca, marca, modelo, fechaFabricacion, textoCantidadPasajeros, textoKilometraje, sucursalBase);
            } catch (AccesoALaDataException | ValorExistenteException | ValorInvalidoException e) {
                %> <p class="error"><%=e.getMessage()%></p> <%
            }
            SucursalDAO sucursaldao = new SucursalDAO();
            List<SucursalDB> sucursales = null;
            try {
                sucursales = sucursaldao.todasLasSucursales();
            } catch (AccesoALaDataException e) {
                %> <p class="error">e.getMessage()</p> <%
            }
            if (mensajeCreacion != null) {
                %> <p class="correcto"><%=mensajeCreacion%></p> <% 
            } %>
        
        <div class="contenedor-principal">
            <form method="POST" action="registrar-bus.jsp">
                <label>
                    Número de Placa
                    <input name="numero-placa"/>
                </label>
                <label>
                    Foto
                    <input name="foto" type="file"/>
                </label>
                <label>
                    Marca
                    <input name="marca"/>
                </label>
                <label>
                    Modelo
                    <input name="modelo"/>
                </label>
                <label>
                    Fecha de Fabricacion
                    <input name="fecha-fabricacion" type="date"/>
                </label>
                <label>
                    Cantidad de Pasajeros que Soporta
                    <input name="cantidad-pasajeros" type="number"/>
                </label>
                <label>
                    Kilometraje
                    <input name="kilometraje" type="number"/>
                </label>
                
                <% if (usuario.getRol() == RolUsuario.ADMINISTRADOR) { %>
                
                Sucursal a la que pertenece
                <select name="sucursal-base">
                    <option></option>
                    <%
                        if (sucursales != null) {
                            for (SucursalDB sucursalLista : sucursales) {
                    %>
                    <option value="<%=sucursalLista.getCodigo()%>"><%=sucursalLista.getNombre()%></option>
                    <%
                            }
                        }
                    %>
                </select>
                
                <% } %>
                
                <button class="boton">Registrar</button>
            </form>
        </div>
        
        <% } else { %>
        <jsp:include page="/error-log.jsp"/>
        <% } %>
        
    </body>
</html>
