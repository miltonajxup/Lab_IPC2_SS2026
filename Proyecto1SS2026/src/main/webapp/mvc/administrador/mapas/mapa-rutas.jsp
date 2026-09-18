<%-- 
    Document   : mapa-rutas
    Created on : 17 sept 2026, 22:10:30
    Author     : milton
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <jsp:include page="/includes/resources.jsp" />
        <link rel="stylesheet"href="https://unpkg.com/leaflet@1.9.4/dist/leaflet.css"/>
        <script src="https://unpkg.com/leaflet@1.9.4/dist/leaflet.js"></script>
        <style>
            #map {
                height: 600px;
                width: 100%;
            }
        </style>
        <script>
            
            
            
            document.addEventListener("DOMContentLoaded", function () {
                mostrarRuta();
            });
            
            function mostrarRuta(){
                const map = L.map('map').setView([14.6349, -90.5069], 15);

                L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
                    attribution: '&copy; OpenStreetMap contributors'
                }).addTo(map);

                const ruta = [
                    [14.6349, -90.5069],
                    [14.6355, -90.5080],
                    [14.6370, -90.5100],
                    [14.6400, -90.5120], 
                    [14.6400, -90.5110]
                ];

                L.polyline(ruta, {
                    color: "blue",
                    weight: 5
                }).addTo(map);
            }
        </script>
    </head>
    <body>
        <c:if test="${usuarioLogeado != null}" >
            <h1>Mapa de Rutas</h1>
            <jsp:include page="/mvc/menu-regreso/regreso-menu-administrador.jsp" />
            
            <div id="map"></div>
            
            
        </c:if>
        <c:if test="${usuarioLogeado == null}">
            <jsp:include page="/error-log.jsp" />
        </c:if>
    </body>
</html>
