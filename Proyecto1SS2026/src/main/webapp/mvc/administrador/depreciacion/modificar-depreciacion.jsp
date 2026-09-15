<%-- 
    Document   : modificar-depreciacion
    Created on : 12 sept 2026, 22:03:19
    Author     : milton
--%>

<%@page import="com.mycompany.proyecto1ss2026.Exeptions.ValorInvalidoException"%>
<%@page import="com.mycompany.proyecto1ss2026.Exeptions.AccesoALaDataException"%>
<%@page import="com.mycompany.proyecto1ss2026.Modelos.DataBase.DepreciacionDB"%>
<%@page import="com.mycompany.proyecto1ss2026.DAOs.DepreciacionDAO"%>
<%@page import="com.mycompany.proyecto1ss2026.Servicios.ServicioDepreciacion"%>
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
        <h1>Modificar Depreciacion</h1>
        <jsp:include page="/mvc/menu-regreso/regreso-menu-administrador.jsp" />
        <% 
            DepreciacionDAO depreciaciondao = new DepreciacionDAO();
            ServicioDepreciacion servicio = new ServicioDepreciacion();
            DepreciacionDB depreciacion = null;
            String textoMonto = request.getParameter("monto");
            try {
                servicio.modificarDepreciacion(textoMonto);
            } catch (AccesoALaDataException | ValorInvalidoException e) {
                %> <p class="error"> <%=e.getMessage()%> </p><%
            }
            try {
                depreciacion = depreciaciondao.ultimoValorDepreciacion();
            } catch (AccesoALaDataException e) {
                %> <p class="error"> <%=e.getMessage()%> </p> <%
            }
            if (depreciacion != null) { %>
            <p class="cuadro-texto">
                Depreciación Actual: <%=depreciacion.getMontoDepreciacion()%>Q por Kilometro Recorrido
            </p>
            <div class="contenedor-principal">
                <form method="POST" action="${pageContext.servletContext.contextPath}/mvc/administrador/depreciacion/modificar-depreciacion.jsp">
                    <label>
                        Agrega el Nuevo Monto
                        <input name="monto" type="number" />
                    </label>
                    <button class="boton">Actualizar Monto</button>
                </form>
            </div>
        <%  }  } else { %>
        <jsp:include page="/error-log.jsp" />
        <% } %>
    </body>
</html>
