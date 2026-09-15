<%-- 
    Document   : modificar-bus
    Created on : 10 sept 2026, 17:03:18
    Author     : milton
--%>

<%@page import="com.mycompany.proyecto1ss2026.Respuesta.Respuesta"%>
<%@page import="com.mycompany.proyecto1ss2026.Constantes.RolUsuario"%>
<%@page import="com.mycompany.proyecto1ss2026.Modelos.DataBase.UsuarioDB"%>
<%@page import="com.mycompany.proyecto1ss2026.DAOs.BusDAO"%>
<%@page import="com.mycompany.proyecto1ss2026.Exeptions.ValorInexistenteException"%>
<%@page import="com.mycompany.proyecto1ss2026.Modelos.DataBase.BusDB"%>
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
            UsuarioDB usuario = (UsuarioDB) session.getAttribute("usuario-logeado");
            if (usuario != null) {
        %>
        <h1>Registrar Bus</h1>
        <div class="menuBotones" >
            <a class="boton" href="../menu-administrador.jsp">Regresar</a>
        </div>
        
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
                mensajeCreacion = servicio.modificarBus(numeroPlaca, marca, modelo, fechaFabricacion, textoCantidadPasajeros, textoKilometraje, sucursalBase);
            } catch (AccesoALaDataException | ValorInvalidoException | ValorInexistenteException e) {
                %> <p class="error"><%=e.getMessage()%></p> <%
            }
            SucursalDAO sucursaldao = new SucursalDAO();
            BusDAO busdao = new BusDAO();
            List<BusDB> buses = null;
            List<SucursalDB> sucursales = null;
            try {
                sucursales = sucursaldao.todasLasSucursales();
                if (usuario.getRol() == RolUsuario.ADMINISTRADOR) {
                    buses = busdao.getTodosLosBuses();
                } else {
                    buses = servicio.getBusesSucursalBase(usuario.getSucursal());
                }
            } catch (AccesoALaDataException | ValorInexistenteException e) {
                %> <p class="error"><%=e.getMessage()%></p> <%
            }
            String estadoBus = request.getParameter("estado-bus");
            Respuesta respuestaEstado = null;
            try {
                respuestaEstado = servicio.modificarEstado(numeroPlaca, estadoBus);
            } catch (AccesoALaDataException | ValorInexistenteException | ValorInvalidoException e) {
                %> <p class="error"><%=e.getMessage()%></p> <%
            }
            if (respuestaEstado != null && !respuestaEstado.isCorrecto()) {
                %> <p class="cuadro-texto"> <%=respuestaEstado.getMensaje()%> </p> <%
            }
            BusDB busActual = null;
            if (numeroPlaca != null && !numeroPlaca.isEmpty()) {
                try {
                    busActual = servicio.getBus(numeroPlaca);
                } catch (AccesoALaDataException | ValorInexistenteException e) {
                    %> <p class="error"><%=e.getMessage()%></p> <%
                }
            }
            if (mensajeCreacion != null) {
                %> <p class="correcto"><%=mensajeCreacion%></p> <%
            }
        %>
        <div class="contenedor-principal">
            <div class="contenedor-opciones">
            <% if (buses != null) {
                    for (BusDB bus : buses) { %>
                <a class="opcion" href="modificar-bus.jsp?numero-placa=<%=bus.getNumeroPlaca()%>">
                    <%=bus.getNumeroPlaca()%>
                </a>
                
            <% }  } %>
                
            </div>
            <% if (busActual != null) { %>
            <form method="POST" action="modificar-bus.jsp?numero-placa=<%=busActual.getNumeroPlaca()%>">
                <div class="correcto">
                    Este bus se encuentra en la sucursal <%=busActual.getSucursalActual()%> 
                </div>
                <br>
                <label>
                    Número de Placa
                    <input name="numero-placa" value="<%=busActual.getNumeroPlaca()%>"/>
                </label>
                <label>
                    Foto
                    <input name="foto" type="file" />
                </label>
                <label>
                    Marca
                    <input name="marca" value="<%=busActual.getMarca()%>"/>
                </label>
                <label>
                    Modelo
                    <input name="modelo" value="<%=busActual.getModelo()%>"/>
                </label>
                <label>
                    Fecha de Fabricacion
                    <input name="fecha-fabricacion" type="date" value="<%=busActual.getFechaFabricacion()%>"/>
                </label>
                <label>
                    Cantidad de Pasajeros que Soporta
                    <input name="cantidad-pasajeros" type="number" value="<%=busActual.getCapacidadPasajeros()%>"/>
                </label>
                <label>
                    Kilometraje
                    <input name="kilometraje" type="number" value="<%=busActual.getKilometraje()%>"/>
                </label>
                
                <% if (usuario.getRol() == RolUsuario.ADMINISTRADOR) { %>
                
                Sucursal a la que pertenece
                <select name="sucursal-base" >
                    <option value="<%=busActual.getSucursalBase()%>"><%=busActual.getSucursalBase()%></option>
                    <%
                        if (sucursales != null) {
                            for (SucursalDB sucursalLista : sucursales) {
                    %>
                    <option value="<%=sucursalLista.getCodigo()%>"><%=sucursalLista.getNombre()%></option>
                    <%      }
                        } %>
                </select>
                
                <% } %>
                
                <button class="boton">Registrar</button>
            </form>
            <div >
                Cambiar el estado del Bus <br> <br>
                
                <% if (busActual.isEstadoOperativo()) { %>
                
                <a class="boton activo" href="modificar-bus.jsp?numero-placa=<%=busActual.getNumeroPlaca()%>&estado-bus=<%=!busActual.isEstadoOperativo()%>">
                    ACTIVO
                </a>
                    
                <% } else { %>
                
                <a class="boton inactivo" href="modificar-bus.jsp?numero-placa=<%=busActual.getNumeroPlaca()%>&estado-bus=<%=!busActual.isEstadoOperativo()%>">
                    DESHABILITADO
                </a>
                    
                <% } %>
                
            </div>
                
            <% } %>
            
        </div>
        
        <% } else { %>
        <jsp:include page="/error-log.jsp"/>
        <% } %>
        
    </body>
</html>
