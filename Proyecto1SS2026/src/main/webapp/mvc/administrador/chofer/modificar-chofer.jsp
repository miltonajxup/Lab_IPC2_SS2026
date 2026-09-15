<%-- 
    Document   : modificar-chofer
    Created on : 7 sept 2026, 19:34:47
    Author     : milton
--%>

<%@page import="com.mycompany.proyecto1ss2026.Respuesta.Respuesta"%>
<%@page import="com.mycompany.proyecto1ss2026.Constantes.RolUsuario"%>
<%@page import="com.mycompany.proyecto1ss2026.Modelos.DataBase.UsuarioDB"%>
<%@page import="com.mycompany.proyecto1ss2026.Exeptions.ValorInexistenteException"%>
<%@page import="com.mycompany.proyecto1ss2026.Modelos.DataBase.ChoferDB"%>
<%@page import="com.mycompany.proyecto1ss2026.DAOs.ChoferDAO"%>
<%@page import="com.mycompany.proyecto1ss2026.Exeptions.ValorInvalidoException"%>
<%@page import="com.mycompany.proyecto1ss2026.Exeptions.ValorExistenteException"%>
<%@page import="com.mycompany.proyecto1ss2026.Exeptions.AccesoALaDataException"%>
<%@page import="com.mycompany.proyecto1ss2026.Servicios.ServicioChofer"%>
<%@page import="com.mycompany.proyecto1ss2026.DAOs.SucursalDAO"%>
<%@page import="java.util.List"%>
<%@page import="com.mycompany.proyecto1ss2026.Constantes.TipoLicencia"%>
<%@page import="com.mycompany.proyecto1ss2026.Modelos.DataBase.SucursalDB"%>
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
            UsuarioDB usuario = (UsuarioDB) session.getAttribute("usuario-logeado");
            if (usuario != null) {
        %>
        <h1>Modificar Chofer</h1>
        <div class="menuBotones">
            <a class="boton" href="../menu-administrador.jsp">Regresar</a>
        </div>
        <%
            String dpi = request.getParameter("dpi");
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
            
            try {
                servicio.editarChofer(dpi, nombre, numeroLicencia, tipoLicencia, fechaVencimiento, telefono, salario, sucursal);
            } catch (AccesoALaDataException | ValorInvalidoException | ValorInexistenteException e) {
                %> <p class="error"><%=e.getMessage()%></p> <%
            }
            String dpiChofer = request.getParameter("dpi-chofer");
            ChoferDB choferdb = null;
            SucursalDAO sucursaldao = new SucursalDAO();
            List<SucursalDB> sucursales = null;
            ChoferDAO choferdao = new ChoferDAO();
            List<ChoferDB> choferes = null;
            String estadoChofer = request.getParameter("estado-chofer");
            Respuesta respuestaEstado = null;
            try {
                respuestaEstado = servicio.modificarEstadoChofer(dpiChofer, estadoChofer);
            } catch (AccesoALaDataException | ValorInexistenteException | ValorInvalidoException e) {
                %> <p class="error"> <%=e.getMessage()%> </p> <%
            }
            try {
                choferdb = choferdao.getChoferId(dpiChofer);
                sucursales = sucursaldao.todasLasSucursales();
                if (usuario.getRol() == RolUsuario.ADMINISTRADOR) {
                    choferes = choferdao.todosLosChoferes();
                } else {
                    choferes = servicio.getChoferesSucursalBase(usuario.getSucursal());
                }
            } catch (AccesoALaDataException | ValorInexistenteException e) {
                %> <p class="error"><%=e.getMessage()%><%=usuario.getSucursal()%></p> <%
            } if (respuestaEstado != null && !respuestaEstado.isCorrecto()) { 
                %> <p class="cuadro-texto"> <%=respuestaEstado.getMensaje()%> </p> <% 
            }
        %>
        <div class="contenedor-principal">
            <div class="contenedor-opciones">
                <%
                    if (choferes != null) {
                        for (ChoferDB chofer : choferes) {
                %>
                <a class="opcion" href="modificar-chofer.jsp?dpi-chofer=<%=chofer.getNumeroLicencia()%>">
                    <%=chofer.getNombre()%>
                </a>
                <% }  } %>
            </div>
            <% if (choferdb != null) { %>
            <form method="POST" action="modificar-chofer.jsp?dpi-chofer=<%=choferdb.getNumeroLicencia()%>" >
                <label>
                    DPI
                    <input name="dpi" value="<%=choferdb.getNumeroLicencia()%>"/>
                </label>
                <label>
                    Nombre
                    <input name="nombre" value="<%=choferdb.getNombre()%>"/>
                </label>
                <label>
                    Foto
                    <input name="foto" type="file"/>
                </label>
                <label>
                    Numero de Licencia
                    <input name="numero-licencia" value="<%=choferdb.getNumeroLicencia()%>"/>
                </label>
                Tipo de Licencia
                <select name="tipo-licencia" >
                    <option value="<%=choferdb.getTipoLicencia()%>"><%=choferdb.getTipoLicencia().name()%></option>
                    <option value="<%=TipoLicencia.A.name()%>"><%=TipoLicencia.A%></option>
                    <option value="<%=TipoLicencia.B.name()%>"><%=TipoLicencia.B%></option>
                    <option value="<%=TipoLicencia.C.name()%>"><%=TipoLicencia.C%></option>
                </select>
                <label>
                    Fecha de Vencimiento de la Licencia
                    <input name="fecha-vencimiento" value="<%=choferdb.getFechaVencimiento()%>" type="date"/>
                </label>
                <label>
                    Numero de Telefono
                    <input name="telefono" value="<%=choferdb.getNumeroTelefono()%>"/>
                </label>
                <label>
                    Salario Por Viaje
                    <input name="salario" value="<%=choferdb.getSalarioPorViaje()%>" type="number"/>
                </label>
            
                <% if (usuario.getRol() == RolUsuario.ADMINISTRADOR) {  %>
            
                Selecciona la sucursal a la que pertenece el Chofer
                <select name="sucursal" >
                    <option value="<%=choferdb.getSucursalBase()%>"><%=choferdb.getSucursalBase()%></option>
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
                    Modificar Chofer
                </button>
            </form>
                <div >
                    Cambiar el Estado del Chofer <br> <br>
                    <% if (choferdb.isEstadoOperativo()) { %>
                    <a class="boton activo" 
                       href="${pageContext.servletContext.contextPath}/administrador/chofer/modificar-chofer.jsp?dpi-chofer=<%=choferdb.getNumeroLicencia()%>&estado-chofer=<%=!choferdb.isEstadoOperativo()%>">
                        Activo</a>
                    <% } else { %>
                    <a class="boton inactivo" 
                       href="${pageContext.servletContext.contextPath}/administrador/chofer/modificar-chofer.jsp?dpi-chofer=<%=choferdb.getNumeroLicencia()%>&estado-chofer=<%=!choferdb.isEstadoOperativo()%>">
                        Desactivado</a>
                    <% } %>
                </div>
            <%
                }
            %>
        </div>
        
        <% } else { %>
        <jsp:include page="/error-log.jsp"/>
        <% } %>
        
    </body>
</html>
