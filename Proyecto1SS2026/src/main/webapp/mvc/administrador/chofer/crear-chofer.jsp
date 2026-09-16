<%-- 
    Document   : crear-chofer
    Created on : 7 sept 2026, 19:34:57
    Author     : milton
--%>

<%@page import="com.mycompany.proyecto1ss2026.Constantes.RolUsuario"%>
<%@page import="com.mycompany.proyecto1ss2026.Modelos.DataBase.UsuarioDB"%>
<%@page import="com.mycompany.proyecto1ss2026.Exeptions.ValorInvalidoException"%>
<%@page import="com.mycompany.proyecto1ss2026.Exeptions.ValorExistenteException"%>
<%@page import="com.mycompany.proyecto1ss2026.Exeptions.AccesoALaDataException"%>
<%@page import="com.mycompany.proyecto1ss2026.Modelos.DataBase.SucursalDB"%>
<%@page import="java.util.List"%>
<%@page import="com.mycompany.proyecto1ss2026.DAOs.SucursalDAO"%>
<%@page import="com.mycompany.proyecto1ss2026.Servicios.ServicioChofer"%>
<%@page import="java.io.InputStream"%>
<%@page import="com.mycompany.proyecto1ss2026.Constantes.TipoLicencia"%>
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
        <h1>Crear Chofer</h1>
        <jsp:include page="/mvc/menu-regreso/regreso-menu-administrador.jsp" />
        <%
            String nombre = request.getParameter("nombre");
            //Part foto = request.getPart("foto");
            //InputStream imagen = foto.getInputStream();
            String numeroLicencia = request.getParameter("numero-licencia");
            String tipoLicencia = request.getParameter("tipo-licencia");
            String fechaVencimiento = request.getParameter("fecha-vencimiento");
            String telefono = request.getParameter("telefono");
            String salario = request.getParameter("salario");
            String sucursal = request.getParameter("sucursal");
            if (usuario.getRol() == RolUsuario.ADMINISTRADOR_SUCURSAL) {
                sucursal = usuario.getSucursal();
            }
            ServicioChofer servicio = new ServicioChofer();
            String mensajeCreacion = null;
            try {
                mensajeCreacion = servicio.agregarChofer(nombre, numeroLicencia, tipoLicencia, fechaVencimiento, telefono, salario, sucursal);
            } catch (AccesoALaDataException | ValorExistenteException | ValorInvalidoException e) {
                %> <p class="error"><%=e.getMessage()%></p> <%
            }
            SucursalDAO sucursaldao = new SucursalDAO();
            List<SucursalDB> sucursales = null;
            try {
                sucursales = sucursaldao.todasLasSucursales();
            } catch (AccesoALaDataException e) {
                %> <p class="error"><%=e.getMessage()%></p> <%
            }
            if (mensajeCreacion != null) {
                %> <p class="correcto"> <%=mensajeCreacion%> </p> <%
            }
        %>
        <div class="contenedor-principal">
            <form method="POST" action="crear-chofer.jsp" >
                <label>
                    Numero de Licencia
                    <input name="numero-licencia"/>
                </label>
                <label>
                    Nombre
                    <input name="nombre"/>
                </label>
<!--                <label>
                    Foto
                    <input name="foto" type="file"/>
                </label>-->
                <select name="tipo-licencia">
                    <option>Selecciona un tipo de Licencia</option>
                    <option value="<%=TipoLicencia.A.name()%>"><%=TipoLicencia.A%></option>
                    <option value="<%=TipoLicencia.B.name()%>"><%=TipoLicencia.B%></option>
                    <option value="<%=TipoLicencia.C.name()%>"><%=TipoLicencia.C%></option>
                </select>
                <label>
                    Fecha de Vencimiento de la Licencia
                    <input name="fecha-vencimiento" type="date"/>
                </label>
                <label>
                    Numero de Telefono
                    <input name="telefono"/>
                </label>
                <label>
                    Salario Por Viaje
                    <input name="salario" type="number"/>
                </label>

                <% if (usuario.getRol() == RolUsuario.ADMINISTRADOR) {  %>

                <select name="sucursal">
                    <option>Selecciona la sucursal a la que pertenece el Chofer</option>
                    <%
                        if (sucursales != null) {
                            for (SucursalDB sucursalLista : sucursales) {
                    %>
                    <option value="<%=sucursalLista.getCodigo()%>"><%=sucursalLista.getNombre()%></option>
                    <%
                            }
                        }
                    %>
                </select>

                <% } %>

                <button class="boton">
                    Crear Chofer
                </button>
            </form>
        </div>
        
        <% } else { %>
        <jsp:include page="/error-log.jsp"/>
        <% } %>
        
    </body>
</html>
