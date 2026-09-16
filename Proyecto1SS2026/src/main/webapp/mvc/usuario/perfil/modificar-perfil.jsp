<%-- 
    Document   : modificar-perfil
    Created on : 13 sept 2026, 20:32:29
    Author     : milton
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <jsp:include page="/includes/resources.jsp" />
    </head>
    <body>
        <h1>Editar Perfil</h1>
        <jsp:include page="/mvc/menu-regreso/regreso-menu-usuario.jsp" />
        <p class="error">${error}</p>
        <p class="correcto">${mensaje}</p>
        <div class="contenedor-principal" >
            <form method="POST" action="${pageContext.servletContext.contextPath}/mvc/usuario/usuario-servlet?cliente=editar-perfil&dpi=${usuarioLogeado.dpi}">
                
                DPI: ${usuarioLogeado.dpi} <br> <br>
                Creditos Disponibles: ${usuarioLogeado.creditoDisponible} <br> <br>
                
                <label>
                    Nombre
                    <input name="nombre" value="${usuarioLogeado.nombre}"/>
                </label>
                <label>
                    NIT
                    <input name="nit" value="${usuarioLogeado.nit}"/>
                </label>
                <label>
                    Telefono
                    <input name="telefono" value="${usuarioLogeado.telefono}"/>
                </label>
                <label>
                    direccion
                    <input name="direccion" value="${usuarioLogeado.direccion}"/>
                </label>
                <button class="boton" type="submit">Guardar</button>
            </form>
            <form method="POST" action="${pageContext.servletContext.contextPath}/mvc/usuario/usuario-servlet?cliente=agregar-creditos&dpi=${usuarioLogeado.dpi}">
                <label>
                    Agregar Creditos
                    <input name="creditos" />
                </label>
                <button class="boton" >Agregar</button>
            </form>
        </div>
    </body>
</html>
