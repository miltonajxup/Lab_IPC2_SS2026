<%-- 
    Document   : menu-administrador
    Created on : 5 sept 2026, 17:19:59
    Author     : milton
--%>

<%@page import="com.mycompany.proyecto1ss2026.Constantes.RolUsuario"%>
<%@page import="com.mycompany.proyecto1ss2026.Modelos.DataBase.UsuarioDB"%>
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
        <h1>Menu de Adminstrador</h1>
        <% if (usuario.getRol() == RolUsuario.ADMINISTRADOR) { %>
        Opciones de Sucursal
        <div class="menuBotones">
            <a class="boton" href="${pageContext.servletContext.contextPath}/mvc/administrador/sucursal/crear-sucursal.jsp">Crear Sucursal</a>
            <a class="boton" href="${pageContext.servletContext.contextPath}/mvc/administrador/sucursal/modificar-sucursal.jsp">Modificar Sucursal</a>
        </div>
        Opciones de Usuario
        <div class="menuBotones">
            <a class="boton" href="${pageContext.servletContext.contextPath}/mvc/administrador/usuario/crear-usuario.jsp">Crear Usuario</a>
            <a class="boton" href="${pageContext.servletContext.contextPath}/mvc/usuario/usuario-servlet?modificar-usuario=todos">Modificar Usuario</a>
        </div>
        Mas opciones
        <div class="menuBotones">
            <a class="boton" href="${pageContext.servletContext.contextPath}/mvc/administrador/depreciacion/modificar-depreciacion.jsp">Modificar Depreciacion</a>
        </div>
        <% } %>
        
        Opciones en Para Administradores de Sucursal
        <div class="menuBotones">
            <a class="boton" href="${pageContext.servletContext.contextPath}/mvc/administrador/chofer/crear-chofer.jsp">Crear Chofer</a>
            <a class="boton" href="${pageContext.servletContext.contextPath}/mvc/administrador/chofer/modificar-chofer.jsp">Modificar Chofer</a>
            <a class="boton" href="${pageContext.servletContext.contextPath}/mvc/administrador/bus/registrar-bus.jsp">Registrar Bus</a>
            <a class="boton" href="${pageContext.servletContext.contextPath}/mvc/administrador/bus/modificar-bus.jsp">Modificar Bus</a>
            
            <% if (usuario.getRol() == RolUsuario.ADMINISTRADOR_SUCURSAL) { %>
            
            <a class="boton" href="${pageContext.servletContext.contextPath}/mvc/administrador/ruta/agregar-ruta.jsp">Agregar Ruta</a>
            <a class="boton" href="${pageContext.servletContext.contextPath}/mvc/administrador/ruta/modificar-ruta.jsp">Modificar Ruta</a>
            <a class="boton" href="${pageContext.servletContext.contextPath}/mvc/administrador/viaje/crear-viaje-publico.jsp">Crear Viaje Publico</a>
            <a class="boton" href="${pageContext.servletContext.contextPath}/mvc/administrador/viaje/controlar-viaje.jsp">Controlar Viaje</a>
            <a class="boton" href=""></a>
            
            <% } %>
            
            <a class="boton" href=""></a>
            <a class="boton" href="${pageContext.servletContext.contextPath}/index.jsp">Regresar</a>
        </div>
        
        Reportes
        <div class="menuBotones">
            <a class="boton" href="${pageContext.servletContext.contextPath}/mvc/administrador/reporte/reporte-ganancias.jsp">Reporte de Ganancias</a>
            <a class="boton" href="${pageContext.servletContext.contextPath}/mvc/administrador/reporte/reporte-ruta.jsp">Reporte de Rutas</a>
            <a class="boton" href="${pageContext.servletContext.contextPath}/mvc/administrador/reporte/reporte-gastos.jsp">Reporte de Gastos</a>
            <a class="boton" href="${pageContext.servletContext.contextPath}/mvc/administrador/reporte/reporte-servlet?reporte-buses=reporteBuses&sucursal=${usuarioLogeado.sucursal}">Reporte de Buses</a>
            <a class="boton" href="${pageContext.servletContext.contextPath}/mvc/administrador/reporte/reporte-servlet?reporte-choferes=reporteChoferes&sucursal=${usuarioLogeado.sucursal}">Reporte de Choferes</a>
            <a class="boton" href="${pageContext.servletContext.contextPath}/mvc/administrador/reporte/reporte-boletos.jsp">Reporte de Boletos</a>
            <a class="boton" href="${pageContext.servletContext.contextPath}/mvc/administrador/reporte/reporte-servlet?reporte-depreciacion=reporteDepreciacion&sucursal=${usuarioLogeado.sucursal}">Reporte de Depreciacion</a>
        </div>
        
        <% } else { %>
        <jsp:include page="/error-log.jsp"/>
        <% } %>
        
    </body>
</html>
<!--        <h1>Menu de Adminstrador</h1>
        <div class="menuBotones">
            <% //if (usuario.getRol() == RolUsuario.ADMINISTRADOR) { %>
            <a class="boton" href="${pageContext.servletContext.contextPath}/mvc/administrador/sucursal/crear-sucursal.jsp">Crear Sucursal</a>
            <a class="boton" href="${pageContext.servletContext.contextPath}/mvc/administrador/sucursal/modificar-sucursal.jsp">Modificar Sucursal</a>
            <a class="boton" href="${pageContext.servletContext.contextPath}/mvc/administrador/usuario/crear-usuario.jsp">Crear Usuario</a>
            <a class="boton" href="${pageContext.servletContext.contextPath}/mvc/administrador/depreciacion/modificar-depreciacion.jsp">Modificar Depreciacion</a>
            <a class="boton" href=""></a>
            
            <% //} %>
            
            <a class="boton" href="${pageContext.servletContext.contextPath}/mvc/administrador/chofer/crear-chofer.jsp">Crear Chofer</a>
            <a class="boton" href="${pageContext.servletContext.contextPath}/mvc/administrador/chofer/modificar-chofer.jsp">Modificar Chofer</a>
            <a class="boton" href="${pageContext.servletContext.contextPath}/mvc/administrador/bus/registrar-bus.jsp">Registrar Bus</a>
            <a class="boton" href="${pageContext.servletContext.contextPath}/mvc/administrador/bus/modificar-bus.jsp">Modificar Bus</a>
            
            <% //if (usuario.getRol() == RolUsuario.ADMINISTRADOR_SUCURSAL) { %>
            
            <a class="boton" href="${pageContext.servletContext.contextPath}/mvc/administrador/ruta/agregar-ruta.jsp">Agregar Ruta</a>
            <a class="boton" href="${pageContext.servletContext.contextPath}/mvc/administrador/ruta/modificar-ruta.jsp">Modificar Ruta</a>
            <a class="boton" href="${pageContext.servletContext.contextPath}/mvc/administrador/viaje/crear-viaje-publico.jsp">Crear Viaje Publico</a>
            <a class="boton" href="${pageContext.servletContext.contextPath}/mvc/administrador/viaje/controlar-viaje.jsp">Controlar Viaje</a>
            <a class="boton" href=""></a>
            
            <% //} %>
            
            <a class="boton" href=""></a>
            <a class="boton" href="${pageContext.servletContext.contextPath}/index.jsp">Regresar</a>
        </div>-->