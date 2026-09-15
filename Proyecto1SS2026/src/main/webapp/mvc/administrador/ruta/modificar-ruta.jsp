<%-- 
    Document   : modidficar-ruta
    Created on : 11 sept 2026, 21:55:03
    Author     : milton
--%>

<%@page import="com.mycompany.proyecto1ss2026.Modelos.DataBase.HorarioDB"%>
<%@page import="com.mycompany.proyecto1ss2026.Servicios.ServicioHorario"%>
<%@page import="com.mycompany.proyecto1ss2026.Modelos.DataBase.RutaDB"%>
<%@page import="com.mycompany.proyecto1ss2026.DAOs.RutaDAO"%>
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
            UsuarioDB usuario = (UsuarioDB) session.getAttribute("usuarioLogeado");
            if (usuario != null) {
        %>
        <h1>Modificar Ruta</h1>
        <div class="menuBotones">
            <a class="boton" href="../menu-administrador.jsp">Regresar</a>
        </div>
        <%
            String idRuta = request.getParameter("id-ruta");
            String textoDistancia = request.getParameter("distancia");
            String textoPrecio  = request.getParameter("precio");
            
            String mensaje = null;
            ServicioRuta servicio = new ServicioRuta();
            try {
                mensaje = servicio.modificarRuta(idRuta, textoDistancia, textoPrecio);
            } catch (AccesoALaDataException | ValorInexistenteException | ValorInvalidoException e) {
                %> <p class="error"><%=e.getMessage()%></p> <%
            }
            String textoHoraSalida = request.getParameter("hora-salida");
            String textoHoraLlegada = request.getParameter("hora-llegada");
            ServicioHorario servicioHora = new ServicioHorario();
            if (textoHoraLlegada != null && textoHoraSalida != null && idRuta != null 
                    && !textoHoraLlegada.isEmpty() && !textoHoraSalida.isEmpty() && !idRuta.isEmpty()) {
                try {
                    mensaje = servicioHora.agregarHorario(textoHoraSalida, textoHoraLlegada, idRuta);
                } catch (AccesoALaDataException | ValorExistenteException | ValorInvalidoException e) {
                    %> <p class="error"> <%=e.getMessage()%> </p><%
                }
            }
            if (mensaje != null) {
                %> <p class="correcto"><%=mensaje%></p> <%
            }
            RutaDAO rutadao = new RutaDAO();
            List<RutaDB> rutas = null;
            try {
                rutas = rutadao.getRutasSucursalOrigen(usuario.getSucursal());
            } catch (AccesoALaDataException e) {
                %> <p class="error"><%=e.getMessage()%></p> <%
            }
            RutaDB ruta = null;
            List<HorarioDB> horarios = null;
            try {
                ruta = servicio.getRuta(idRuta);
                horarios = servicioHora.getHorariosRuta(idRuta);
            } catch (AccesoALaDataException | ValorInexistenteException e) {
                %> <p class="error"> <%=e.getMessage()%> </p> <%
            }
        %>
        <div class="contenedor-principal">  
            <% if (rutas != null) { %>
            <div class="contenedor-opciones">
                <% for (RutaDB rutaLista : rutas) { %>
                <a class="opcion" href="${pageContext.servletContext.contextPath}/mvc/administrador/ruta/modificar-ruta.jsp?id-ruta=<%=rutaLista.getId()%>">
                    Ruta: <%=rutaLista.getId()%> <br>
                    De Sucursal: <%=rutaLista.getSucursalOrigen()%> <br>
                    hacia la Sucursal: <%=rutaLista.getSucursalDestino()%>
                </a>
                <% } %>
            </div>
            <% } if (ruta != null) { %>
            <form method="POST" action="modificar-ruta.jsp?id-ruta=<%=ruta.getId()%>">
                <label>
                    Distancia Aproximada (En kilometros)
                    <input name="distancia" type="number" value="<%=ruta.getDistanciaAproximada()%>"/>
                </label>
                <label>
                    Precio del Boleto (En Quetzales)
                    <input name="precio" type="number" value="<%=ruta.getPrecioBoleto()%>"/>
                </label>
                <button class="boton">Modificar Ruta</button>
            </form>
                <div >
                    Horarios de la Ruta
                    <% if (horarios != null) { %>
                    <div class="contenedor-opciones">
                        <% for (HorarioDB horario : horarios) { %>
                        <div class="borde"> 
                            <div class="borde"> Id: <%=horario.getId()%>  </div>
                            Horario Salida: <%=horario.getHoraSalida()%> <br>
                            Horario Salida: <%=horario.getHoraLlegada()%> <br> 
                        </div>
                        <br>
                        <% } %>
                    </div>
                    <% } %>
                </div>
                <form method="POST" action="modificar-ruta.jsp?id-ruta=<%=ruta.getId()%>">
                    Agregar horarios a la ruta
                    <label>
                        Hora de Salida
                        <input name="hora-salida" type="time"/>
                    </label>
                    <label>
                        Hora de Llegada (Aproximada)
                        <input name="hora-llegada" type="time"/>
                    </label>
                    <button class="boton">Agregar Horario</button>
                </form>
            <% } %>
        </div>
        
        <% } else { %>
        <jsp:include page="/error-log.jsp"/>
        <% } %>
        
    </body>
</html>
