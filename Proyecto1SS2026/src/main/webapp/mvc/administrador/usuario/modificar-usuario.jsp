<%-- 
    Document   : modificar-usuario
    Created on : 7 sept 2026, 19:56:05
    Author     : milton
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <c:if test="${usuarioLogeado != null}">
        <h1>Modificar Usuario</h1>
        <jsp:include page="/mvc/menu-regreso/regreso-menu-administrador.jsp" />
        <div class="contenedor-principal">
            <div class="contenedor-opciones">
                Admiminstradores
                <c:forEach items="${administradores}" var="administrador">
                    <div class="borde">
                        <div class="borde">DPI: ${administrador.dpi}</div>
                        Nombre: ${administrador.nombre} <br> <br>
                        <a class="boton" href="${pageContext.servletContext.contextPath}/mvc/usuario/usuario-servlet?modificar-usuario=todos&dpi-usuario=${administrador.dpi}">
                            Elegir
                        </a> <br><br>
                    </div>
                </c:forEach>
            </div>
            <div class="contenedor-opciones">
                Admiminstradores de Sucursales
                <c:forEach items="${adminsSucursales}" var="administrador">
                    <div class="borde">
                        <div class="borde">DPI: ${administrador.dpi}</div>
                        Nombre: ${administrador.nombre} <br> <br>
                        <a class="boton" href="${pageContext.servletContext.contextPath}/mvc/usuario/usuario-servlet?modificar-usuario=todos&dpi-usuario=${administrador.dpi}">
                            Elegir
                        </a> <br><br>
                    </div>
                </c:forEach>
            </div>
            <div class="contenedor-opciones">
                Clientes
                <c:forEach items="${clientes}" var="cliente">
                    <div class="borde">
                        <div class="borde">DPI: ${cliente.dpi}</div>
                        Nombre: ${cliente.nombre} <br> <br>
                        <a class="boton" href="${pageContext.servletContext.contextPath}/mvc/usuario/usuario-servlet?modificar-usuario=todos&dpi-usuario=${cliente.dpi}">
                            Elegir
                        </a> <br><br>
                    </div>
                </c:forEach>
            </div>
            <p class="error">${error}</p>
            <c:if test="${usuarioElegido != null}">
            <div class="contenedor-principal">
                <form method="POST" action="${pageContext.servletContext.contextPath}/mvc/usuario/usuario-servlet?modificar-usuario=todos&dpi-usuario=${usuarioElegido.dpi}">
                    <label>
                        DPI :${usuarioElegido.dpi} <br> <br>
                    </label>
                    <label>
                        Nombre
                        <input name="nombre" value="${usuarioElegido.nombre}"/>
                    </label>
                    <label>
                        NIT
                        <input name="nit" value="${usuarioElegido.nit}"/>
                    </label>
                    <label>
                        Telefono
                        <input name="telefono" value="${usuarioElegido.telefono}"/>
                    </label>
                    <label>
                        Direccion
                        <input name="direccion" value="${usuarioElegido.direccion}"/>
                    </label>
                    <input name="rol" value="${usuarioElegido.rol}" type="hidden" />
                    <input name="sucursal" value="${usuarioElegido.sucursal}" type="hidden" />
                    <button class="boton" type="submit">Guardar</button>
                </form>
                <div>
                Cambia El estado el usuario <br> <br>
                <c:if test="${usuarioElegido.estado == true}">
                    <a class="boton activo" href="${pageContext.servletContext.contextPath}/mvc/usuario/usuario-servlet?modificar-usuario=todos&dpi-usuario=${usuarioElegido.dpi}&estado=${!usuarioElegido.estado}">
                        ACTIVO
                    </a>
                </c:if>
                <c:if test="${usuarioElegido.estado == false}">
                    <a class="boton inactivo" href="${pageContext.servletContext.contextPath}/mvc/usuario/usuario-servlet?modificar-usuario=todos&dpi-usuario=${usuarioElegido.dpi}&estado=${!usuarioElegido.estado}">
                        DESACTIVADO
                    </a>
                </c:if>
                </div>
            </div>
            </c:if>
        </div>
        </c:if>
        
        <c:if test="${usuarioLogeado == null}">
        <jsp:include page="/error-log.jsp" />
        </c:if>
    </body>
</html>
