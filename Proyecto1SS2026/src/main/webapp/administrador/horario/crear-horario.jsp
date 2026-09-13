<%-- 
    Document   : crear-horario
    Created on : 12 sept 2026, 0:30:58
    Author     : milton
--%>

<%@page import="com.mycompany.proyecto1ss2026.Exeptions.AccesoALaDataException"%>
<%@page import="com.mycompany.proyecto1ss2026.DAOs.RutaDAO"%>
<%@page import="java.util.List"%>
<%@page import="com.mycompany.proyecto1ss2026.Modelos.DataBase.RutaDB"%>
<%@page import="com.mycompany.proyecto1ss2026.Exeptions.ValorInvalidoException"%>
<%@page import="com.mycompany.proyecto1ss2026.Exeptions.ValorExistenteException"%>
<%@page import="com.mycompany.proyecto1ss2026.Servicios.ServicioHorario"%>
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
        <h1>Crear un Horario</h1>
        <div class="menuBotones" >
            <a class="boton" href="../menu-administrador.jsp">Regresar</a>
        </div>
        
        <%
            String textoHoraSalida = request.getParameter("hora-salida");
            String textoHoraLlegada = request.getParameter("hora-llegada");
            String ruta = request.getParameter("ruta");
            
            String mensaje = null;
            ServicioHorario servicio = new ServicioHorario();
            if (textoHoraSalida != null && textoHoraLlegada != null && ruta != null 
                    && !textoHoraSalida.isEmpty() && !textoHoraLlegada.isEmpty() && !ruta.isEmpty()) {
                try {
                    mensaje = servicio.agregarHorario(textoHoraSalida, textoHoraLlegada, ruta);
                } catch (AccesoALaDataException | ValorExistenteException | ValorInvalidoException e) {
                    %> <p class="error"> <%=e.getMessage()%> </p> <%
                }
            }
            
            if (mensaje != null) {
                %> <p class="error"> <%=mensaje%> </p> <%
            }
            RutaDAO rutadao = new RutaDAO();
            
        %>
        
        <% } else { %>
        <jsp:include page="/error-log.jsp"/>
        <% } %>
        
    </body>
</html>
