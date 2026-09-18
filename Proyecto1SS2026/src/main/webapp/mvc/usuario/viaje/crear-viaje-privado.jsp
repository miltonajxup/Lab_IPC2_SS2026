<%-- 
    Document   : crear-viaje-privado
    Created on : 16 sept 2026, 13:16:06
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
        <c:if test="${usuarioLogeado != null}" >
            <h1>Crear Viaje Privado</h1>
            <jsp:include page="/mvc/menu-regreso/regreso-menu-usuario.jsp" />
            <p class="correcto" >${mensajeCreacion}</p>
            <p class="error" >${error}</p>
            <div class="contenedor-principal">
                <form method="POST" action="${pageContext.servletContext.contextPath}/mvc/viaje/viaje-privado-servlet?parametro-viaje-privado=crearPropuesta&usuario=${usuarioLogeado.dpi}">
                    <label>
                        Indica la Cantidad de pasajeros 
                        <input name="cantidad-pasajeros" type="number" />
                    </label>
                    <label>
                        Indica el lugar de Origen de Viaje
                        <input name="origen" />
                    </label>
                    <label>
                        Indica el lugar de Destino del viaje
                        <input name="destino" />
                    </label>
                    <label>
                        Indica una Distancia Aproximada en Kilometros para el Viaje
                        <input name="distancia" type="number"/>
                    </label>
                    <label>
                        Indica la Hora de Salida
                        <input name="hora-salida" type="time" />
                    </label>
                    <label>
                        Indica la Hora aproximada de Llegada 
                        <input name="hora-llegada" type="time" />
                    </label>
                    <label>
                        Indica la Fecha de Salida
                        <input name="fecha-salida" type="date" />
                    </label>
                    <label>
                        Indica el Costo en Quetzales
                        <input name="costo" type="number" />
                    </label>
                    <button class="boton" >Mandar Propuesta de Viaje</button>
                </form>
            </div>
        </c:if>
        <c:if test="${usuarioLogeado == null}">
            <jsp:include page="/error-log.jsp" />
        </c:if>
    </body>
</html>
