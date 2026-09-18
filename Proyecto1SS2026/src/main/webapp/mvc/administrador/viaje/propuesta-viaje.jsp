<%-- 
    Document   : revisar-propuestas
    Created on : 16 sept 2026, 21:22:16
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
            <jsp:include page="/mvc/menu-regreso/regreso-menu-administrador.jsp" />
            
            <p class="error">${error}</p>
            <p class="correcto">${mensaje}</p>
            
            
            <div class="contenedor-principal">
                
                <div class="contenedor-opciones">
                    <c:forEach items="${listaPropuestas}" var="propuestaL" >
                        <a class="opcion" 
                           href="${pageContext.servletContext.contextPath}/mvc/viaje/viaje-privado-servlet?todo-propuestas=todoPropuestas&id-propuesta=${propuestaL.idPropuesta}&id-sucursal=${usuarioLogeado.sucursal}">
                            Ver Informacion de <br>
                            Propuesta: ${propuestaL.idPropuesta}
                        </a>
                    </c:forEach>
                </div>
                
                <c:if test="${propuesta != null}" > 
                    <form >
                        <div class="carta">
                            <div class="borde" >Id: ${propuesta.idPropuesta}</div>
                            Cantidad de Pasajeros: ${propuesta.cantidadPasajeros} <br>
                            Lugar de Salida: ${propuesta.origen}
                            Lugar Destino: ${propuesta.destino}
                            Distancia Aproximada: ${propuesta.distanciaAProximada}
                            Hora de Salida: ${propuesta.horaSalida}
                            Hora aproximada de Llegada: ${propuesta.horaLlegada}
                            Fecha de Salida: ${propuesta.fechaSalida}
                            Costo: ${propuesta.costo}

                        </div>
                    </form>
                
                
                <form class="contenedor-principal" method="POST" 
                      action="${pageContext.servletContext.contextPath}/mvc/viaje/viaje-privado-servlet?parametro-viaje-privado=aceptarPropuesta&id-propuesta=${propuesta.idPropuesta}&sucursal=${usuarioLogeado.sucursal}">
                    
                    <div class="contenedor-opciones" >
                        <input class="head" id="chofer-vista" />
                        <input id="chofer-valor" name="chofer" type="hidden"/>
                        <c:forEach items="${choferes}" var="chofer">
                            <div class="borde">
                                Numero de Licencia: ${chofer.numeroLicencia} <br> 
                                Nombre: ${chofer.nombre} <br>  <br> 
                                <button class="boton" type="button" onclick="agregarElemento('${chofer.numeroLicencia}', 'chofer-valor', '${chofer.nombre}', 'chofer-vista')">
                                    Elegir
                                </button>
                            </div>
                        </c:forEach>
                    </div>
                    
                    <div class="contenedor-opciones" >
                        <input class="head" id="bus-vista" />
                        <input id="bus-valor" name="bus" type="hidden" />
                        <c:forEach items="${buses}" var="bus">
                            <div class="borde">
                                Numero de Licencia: ${bus.numeroPlaca} <br> 
                                Nombre: ${bus.marca} <br>  <br> 
                                <button class="boton" type="button" onclick="agregarElemento('${bus.numeroPlaca}', 'bus-valor', '${bus.numeroPlaca}', 'bus-vista')">
                                    Elegir
                                </button>
                            </div>
                        </c:forEach>
                    </div>
                    
                    <div >
                        <button class="boton">Aceptar Propuesta</button>
                    </div>
                    
                </form>
                </c:if>
                
                
                <c:if test="${empty listaPropuestas}" >
                    <div class="cuadro-texto" >
                        Por ahora no hay propuestas de viajes privados
                    </div>
                </c:if>
            </div>
        </c:if>
        <c:if test="${usuarioLogeado == null}">
            <jsp:include page="/error-log.jsp" />
        </c:if>
    </body>
</html>
