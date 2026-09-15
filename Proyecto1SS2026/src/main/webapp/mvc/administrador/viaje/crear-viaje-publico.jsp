<%-- 
    Document   : crear-viaje-publico
    Created on : 12 sept 2026, 11:15:54
    Author     : milton
--%>

<%@page import="com.mycompany.proyecto1ss2026.Modelos.DataBase.RutaDB"%>
<%@page import="com.mycompany.proyecto1ss2026.Servicios.ServicioRuta"%>
<%@page import="com.mycompany.proyecto1ss2026.Servicios.ServicioHorario"%>
<%@page import="com.mycompany.proyecto1ss2026.Servicios.ServicioBus"%>
<%@page import="com.mycompany.proyecto1ss2026.Servicios.ServicioChofer"%>
<%@page import="com.mycompany.proyecto1ss2026.DAOs.SucursalDAO"%>
<%@page import="com.mycompany.proyecto1ss2026.Modelos.DataBase.SucursalDB"%>
<%@page import="com.mycompany.proyecto1ss2026.Modelos.DataBase.HorarioDB"%>
<%@page import="com.mycompany.proyecto1ss2026.Modelos.DataBase.BusDB"%>
<%@page import="com.mycompany.proyecto1ss2026.Modelos.DataBase.ChoferDB"%>
<%@page import="java.util.List"%>
<%@page import="com.mycompany.proyecto1ss2026.Respuesta.Respuesta"%>
<%@page import="com.mycompany.proyecto1ss2026.Exeptions.ValorInvalidoException"%>
<%@page import="com.mycompany.proyecto1ss2026.Exeptions.ValorInexistenteException"%>
<%@page import="com.mycompany.proyecto1ss2026.Exeptions.AccesoALaDataException"%>
<%@page import="com.mycompany.proyecto1ss2026.Servicios.ServicioViaje"%>
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
            if (usuario !=null) {
        %>
        <h1>Crear Viaje Publico</h1>
        <jsp:include page="/mvc/menu-regreso/regreso-menu-administrador.jsp" />
        <%
            String chofer = request.getParameter("chofer");
            String bus = request.getParameter("bus");
            String textoFecha = request.getParameter("fecha");
            String horario = request.getParameter("horario");
            
            ServicioViaje servicioViaje = new ServicioViaje();
            Respuesta respuesta = null;
            try {
                respuesta = servicioViaje.agregarViajePublico(chofer, bus, textoFecha, horario);
            } catch (AccesoALaDataException | ValorInexistenteException | ValorInvalidoException e) {
                %> <p class="error"> <%=e.getMessage()%> </p> <%
            }
         if (respuesta != null && respuesta.isCorrecto()) {
                %> <p class="correcto"><%=respuesta.getMensaje()%></p> <%
            }
            SucursalDAO sucursaldao = new SucursalDAO();
            ServicioChofer servicioChofer = new ServicioChofer();
            ServicioBus servicioBus = new ServicioBus();
            ServicioRuta servicioRuta = new ServicioRuta();
            ServicioHorario servHorario = new ServicioHorario();

            List<SucursalDB> sucursales = null;
            List<ChoferDB> choferes = null;
            List<BusDB> buses = null;
            List<HorarioDB> horariosDestinos = null;
            RutaDB ruta = null;

            String idRuta = null;
                    
            String origen = request.getParameter("origen");
            String destino = request.getParameter("destino");
            try {
                ruta = servicioRuta.getRutaPorSucursales(origen, destino);
                if (ruta != null) {
                    idRuta = String.valueOf(ruta.getId());
                }
                sucursales = sucursaldao.todasLasSucursales();
                choferes = servicioChofer.getChoferesSucursalActual(usuario.getSucursal());
                buses = servicioBus.getBusesSucursalActual(usuario.getSucursal());
                horariosDestinos = servHorario.getHorariosRuta(idRuta);
            } catch (AccesoALaDataException | ValorInexistenteException | ValorInvalidoException e) {
                %> <p class="error"> <%=e.getMessage()%> </p> <%
            }
        %>
        <div class="contenedor-principal">
            <form method="POST" action="${pageContext.servletContext.contextPath}/mvc/administrador/viaje/crear-viaje-publico.jsp">
                Seleciona las Sucursales <br>
                Origen y Destino del viaje <br> <br>
                
                Sucursal de origen
                <select name="origen">
                    <option></option>
                    <% 
                        if (sucursales != null) {
                            for (SucursalDB sucursalLista : sucursales) {
                     %>
                     <option value="<%=sucursalLista.getCodigo()%>"><%=sucursalLista.getNombre()%></option>
                    <% } } %>
                </select>
                <select name="destino">
                    <option></option>
                    <% 
                        if (sucursales != null) {
                            for (SucursalDB sucursalLista : sucursales) {
                     %>
                     <option value="<%=sucursalLista.getCodigo()%>"><%=sucursalLista.getNombre()%></option>
                    <% } } %>
                </select>
                <button class="boton">Buscar</button>
            </form>
                
            <% if (ruta != null) { %>
            <form class="contenedor-principal" method="POST" action="crear-viaje-publico.jsp">
                <div>
                Asigna un chofer para el viaje
                <input id="chofer-viaje" name="chofer" type="hidden" readonly="" />
                <div class="contenedor-opciones">
                    <div class="head">
                        Chofer Elegido
                        <input id="chofer-identificador" readonly/>
                    </div>
                    <% if (choferes != null) {
                            for (ChoferDB choferLista : choferes) { %>
                            <div class="borde">
                                Nombre: <%=choferLista.getNombre()%> <br>
                                Tipo de Licencia: <%=choferLista.getTipoLicencia()%> <br>
                                Esta asignado a la Sucursal: <%=choferLista.getSucursalBase()%> <br> <br>
                                <button type="button" class="boton" 
                                        onclick="agregarElemento('<%=choferLista.getNumeroLicencia()%>', 'chofer-viaje', '<%=choferLista.getNombre()%>', 'chofer-identificador')">
                                    Asignar
                                </button>
                            </div>
                    <% } } %>
                </div>
                </div>
                
                <div>
                Asigna un Bus para el Viaje
                <input id="bus-viaje" name="bus" type="hidden" readonly />
                <div class="contenedor-opciones">
                    <div class="head">
                        Bus Elegido
                        <input id="bus-identificador" readonly/>
                    </div>
                    <% if (buses != null) {
                            for (BusDB busLista : buses) { %>
                            <div class="borde">
                                Numero de Placa: <%=busLista.getNumeroPlaca()%> <br>
                                Marca: <%=busLista.getMarca()%> <br>
                                Modelo: <%=busLista.getModelo()%> <br> <br>
                                <button class="boton" type="button" 
                                        onclick="agregarElemento('<%=busLista.getNumeroPlaca()%>', 'bus-viaje', '<%=busLista.getNumeroPlaca()%>', 'bus-identificador')">
                                    Asignar
                                </button>
                            </div>
                    <% } } %>
                </div>
                </div>
                
                <label>
                    Fecha de Salida
                    <input name="fecha" type="date"/>
                </label>
                
                <div>
                Elige el horario del viaje
                <input id="horario-viaje" name="horario" type="hidden" readonly/>
                <div class="contenedor-opciones">
                    <div class="head">
                        Selecciona el horario del Viaje
                        <input id="horario-identificador" readonly />
                    </div>
                    <% if (horariosDestinos != null) { 
                            for (HorarioDB horarioLista : horariosDestinos) { %>
                            <div class="borde">
                                id: <%=horarioLista.getId()%> <br>
                                Hora de Salida: <%=horarioLista.getHoraSalida()%> <br>
                                Hora de Llegada: <%=horarioLista.getHoraLlegada()%> <br> <br>
                                <button class="boton" type="button" onclick="agregarElemento('<%=horarioLista.getId()%>', 'horario-viaje', <%=horarioLista.getId()%>, 'horario-identificador')">
                                    Elegir
                                </button>
                            </div>
                    <% } } %>
                </div>
                    <button class="boton">Crear Viaje</button>
                </div>
                
            </form>
            <% } else if (ruta == null && origen != null && destino != null) { %>
            <p class="cuadro-texto">No se encotró una ruta <br> de la sucursal <%=origen%> hacia <%=destino%> </p>
            <% } %>
        </div>
        <% } else { %>
        <jsp:include page="/error-log.jsp"/>
        <% } %>
    </body>
</html>
