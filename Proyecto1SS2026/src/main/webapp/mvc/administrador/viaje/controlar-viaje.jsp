<%-- 
    Document   : controlar-viaje
    Created on : 12 sept 2026, 23:20:24
    Author     : milton
--%>

<%@page import="com.mycompany.proyecto1ss2026.Constantes.Estado"%>
<%@page import="com.mycompany.proyecto1ss2026.Exeptions.ValorInvalidoException"%>
<%@page import="com.mycompany.proyecto1ss2026.Exeptions.ValorInexistenteException"%>
<%@page import="com.mycompany.proyecto1ss2026.Servicios.ServicioEjecucionViaje"%>
<%@page import="com.mycompany.proyecto1ss2026.Exeptions.AccesoALaDataException"%>
<%@page import="com.mycompany.proyecto1ss2026.Modelos.DataBase.ViajeDB"%>
<%@page import="java.util.List"%>
<%@page import="com.mycompany.proyecto1ss2026.Servicios.ServicioViaje"%>
<%@page import="com.mycompany.proyecto1ss2026.Modelos.DataBase.ChoferDB"%>
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
            UsuarioDB usuario = (UsuarioDB) session.getAttribute("usuarioLogeado");
            ChoferDB chofer = (ChoferDB) session.getAttribute("choferLogeado");
            if (usuario != null || chofer != null) {
        %>
        <h1>Controlar Viaje</h1>
        <% if (usuario != null) { %>
        <jsp:include page="/mvc/menu-regreso/regreso-menu-administrador.jsp" />
        <% } else if (chofer != null) { %>
        <div class="menuBotones" >
            <a class="boton" href="${pageContext.servletContext.contextPath}/mvc/chofer/menu-chofer.jsp">
                Regresar
            </a>
        </div>
        <%
            }
            ServicioViaje servicioViaje = new ServicioViaje();
            ServicioEjecucionViaje servicioEjecucion = new ServicioEjecucionViaje();
            List<ViajeDB> viajesSinTerminar = null;
            ViajeDB viajeElegido = null;
            String idViaje = request.getParameter("id-viaje");
            String textoIniciar = request.getParameter("iniciar");
            String textoKilometroLlegada = request.getParameter("kilometro-llegada");
            String textoGastoCombustible = request.getParameter("gasto-combustible");
            String textoFechaRegistro = request.getParameter("fecha-registro");
            
            try {
                servicioEjecucion.generarRegistroEjecucion(idViaje, textoIniciar);
            } catch (AccesoALaDataException | ValorInexistenteException e) {
                %><p class="error"><%=e.getMessage()%></p><%
            }
            try {
                servicioEjecucion.actualizarRegistroEjecucion(textoKilometroLlegada, textoGastoCombustible, idViaje, textoFechaRegistro);
            } catch (AccesoALaDataException | ValorInvalidoException | ValorInexistenteException e) {
                %><p class="error"><%=e.getMessage()%></p><%
            }
            
            try {
                if (usuario != null) {
                    viajesSinTerminar = servicioViaje.getViajesSinTerminarSucursal(usuario.getSucursal());
                }
                if (chofer != null) {
                    viajesSinTerminar = servicioViaje.getViajesSinTerminarChofer(chofer.getNumeroLicencia());
                }
            } catch (AccesoALaDataException e) {
                %> <p class="error"> <%=e.getMessage()%> </p> <%
            }
            try {
                viajeElegido = servicioViaje.getViajeSinTerminarPorId(idViaje);
            } catch (AccesoALaDataException e) {
                %> <p class="error"> <%=e.getMessage()%> </p> <%
            }
        %>
        <div class="contenedor-principal">
            
            <% if (viajesSinTerminar != null && !viajesSinTerminar.isEmpty()) { %>
            <div class="contenedor-opciones">
                <% for(ViajeDB actual : viajesSinTerminar) { %>
                <a class="opcion" href="${pageContext.servletContext.contextPath}/mvc/administrador/viaje/controlar-viaje.jsp?id-viaje=<%=actual.getId()%>">
                    Id viaje: <%=actual.getId()%>
                </a>
                <% } %>
            </div>
            
            <% } else if (viajesSinTerminar != null && viajesSinTerminar.isEmpty()) { %>
            <p class="cuadro-texto">No hay viajes sin Terminar</p>
            
            <% }  if (viajeElegido != null && !viajeElegido.isComenzado()) { %>
            <div class="borde">
                <div class="borde">Id: <%=viajeElegido.getId()%></div>
                Numero de Licencia: <%=viajeElegido.getChofer()%> <br>
                Numero de Placa: <%=viajeElegido.getBus()%> <br> <br>
                <a class="boton" href="${pageContext.servletContext.contextPath}/mvc/administrador/viaje/controlar-viaje.jsp?id-viaje=<%=viajeElegido.getId()%>&iniciar=<%=Estado.TRUE%>">
                    Iniciar
                </a> <br> <br>
            </div>
            
            <% } else if (viajeElegido != null && viajeElegido.isComenzado()) { %>
            <form  method="POST" action="${pageContext.servletContext.contextPath}/mvc/administrador/viaje/controlar-viaje.jsp?id-viaje=<%=viajeElegido.getId()%>">
                <label>
                    Ingresa el Kilometraje actual del Autobus
                    <input name="kilometro-llegada" type="number"/>
                </label>
                <label>
                    Ingresa el Total del Gasto de Combustible 
                    <input name="gasto-combustible" type="number"/>
                </label>
                <label>
                    Ingresa la Fecha del Registro
                    <input name="fecha-registro" type="date" />
                </label>
                <button class="boton">Finalizar Viaje</button>
            </form>
            <% } %>
        </div>
        <% } else { %>
        <jsp:include page="/error-log.jsp" />
        <% } %> 
    </body>
</html>
