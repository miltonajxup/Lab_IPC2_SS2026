<%-- 
    Document   : comprar-boleto
    Created on : 13 sept 2026, 20:08:51
    Author     : milton
--%>

<%@page import="com.mycompany.proyecto1ss2026.Modelos.DataBase.BusDB"%>
<%@page import="java.util.List"%>
<%@page import="com.mycompany.proyecto1ss2026.Modelos.Request.Boleto"%>
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
        <c:if test="${usuarioLogeado != null}">
            <h1>Comprar Boleto</h1>
            <jsp:include page="/mvc/menu-regreso/regreso-menu-usuario.jsp" />
            
            <p class="cuadro-texto">Credito Disponible: ${usuarioLogeado.creditoDisponible}Q</p>
            <p class="error">${error}</p>
            <div class="contenedor-principal">
                <div>
                    Selecciona Una ruta para el viaje 
                    <div class="contenedor-opciones">
                        <c:forEach items="${rutas}" var="ruta">
                            <div class="borde">
                                <div class="borde">Ruta Id: ${ruta.id}</div>
                                Sucursal Origen: ${ruta.nombreSucOrigen} <br>
                                Ciudad Origen: ${ruta.nombreCiudadOrigen} <br> <br>
                                Sucursal Destino: ${ruta.nombreSucDestino} <br>
                                Ciudad Destino: ${ruta.nombreCiudadDestino} <br> <br>
                                Precio Boleto: ${ruta.precioBoleto}Q <br> 
                                Distancia. ${ruta.distanciaAproximada}km <br> <br>
                                <a class="boton" href="${pageContext.servletContext.contextPath}/mvc/usuario/usuario-servlet?rutas=todos&id-ruta=${ruta.id}">
                                    Buscar
                                </a> <br> <br>
                            </div>
                        </c:forEach>
                    </div>
                </div>
                <c:if test="${viajesDisponibles != null}">
                <div>
                    Viajes Disponibles para la ruta Seleccionada
                    <div class="contenedor-opciones">
                        <c:forEach items="${viajesDisponibles}" var="viaje">
                            <div class="borde">
                                <div class="borde">Viaje Id: ${viaje.id}</div>
                                Fecha de Salida: ${viaje.fechaSalida} <br>
                                Hora Salida. ${viaje.horaSalida} <br>
                                Hora Aprox Llegada: ${viaje.horaLlegada} <br> <br>
                                <a class="boton" 
                                   href="${pageContext.servletContext.contextPath}/mvc/usuario/usuario-servlet?rutas=todos&id-ruta=${idRuta}&id-viaje=${viaje.id}">
                                    Seleccionar
                                </a> <br><br>
                            </div>
                        </c:forEach>
                        <c:if test="${empty viajesDisponibles}">
                            No hay viajes disponibles para esta ruta
                        </c:if>
                    </div>
                </div>
                </c:if>
                <c:if test="${asientos != null}">
                <div>
                    Elige un asiento para el viaje
                    <c:if test="${respuesta.correcto == true}">
                        <p class="correcto">${respuesta.mensaje}</p>
                    </c:if>
                    <c:if test="${respuesta.correcto == false}">
                        <p class="error">${respuesta.mensaje}</p>
                    </c:if>
                    <form method="POST" action="${pageContext.servletContext.contextPath}/mvc/usuario/usuario-servlet?compra-boleto=compraBoleto&id-ruta=${idRuta}">
                        <input type="hidden" name="dpi" value="${usuarioLogeado.dpi}" readonly/>
                        <input type="hidden" name="id-viaje" value="${idViaje}" readonly/>
                        <input id="asiento-identificador" readonly/>
                        <input id="asiento" type="hidden" name="asiento" readonly/>
                        <div class="bus">
                            <c:forEach items="${asientos}" var="asiento">
                                <c:if test="${asiento.ocupado == false}" >
                                    <button class="boton activo" type="button" onclick="agregarElemento('${asiento.numAsiento}', 'asiento', 'Asiento Elegido ${asiento.numAsiento}', 'asiento-identificador')">${asiento.numAsiento}</button>
                                </c:if>
                                <c:if test="${asiento.ocupado == true}">
                                    <button class="boton inactivo" type="button">${asiento.numAsiento}</button>
                                </c:if>
                            </c:forEach>
                        </div>
                        <button class="boton">Compra Boleto</button>
                    </form>
                </div>
                </c:if>
            </div>
        </c:if>
        <c:if test="${usuarioLogeado == null}">
            <jsp:include page="/error-log.jsp" />
        </c:if>
    </body>
</html>
