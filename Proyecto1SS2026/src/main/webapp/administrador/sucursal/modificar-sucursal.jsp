<%-- 
    Document   : modificar-sucursal
    Created on : 6 sept 2026, 17:06:40
    Author     : milton
--%>

<%@page import="com.mycompany.proyecto1ss2026.DAOs.SucursalDAO"%>
<%@page import="com.mycompany.proyecto1ss2026.Exeptions.ValorInvalidoException"%>
<%@page import="com.mycompany.proyecto1ss2026.Exeptions.ValorExistenteException"%>
<%@page import="com.mycompany.proyecto1ss2026.Exeptions.AccesoALaDataException"%>
<%@page import="com.mycompany.proyecto1ss2026.Servicios.ServicioSucursal"%>
<%@page import="com.mycompany.proyecto1ss2026.Modelos.DataBase.SucursalDB"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>modificar-sucursal</title>
        <jsp:include page="/includes/resources.jsp"/>
    </head>
    <body>
        <h1>Modificar Sucursal</h1>
        <div class="menuBotones">
            <a class="boton" href="../menu-administrador.jsp">Regresar</a>
        </div>
        <%
            
            String codigo = request.getParameter("codigo");
            String nombre = request.getParameter("nombre");
            String ciudad = request.getParameter("ciudad");
            if (codigo != null && nombre != null && ciudad != null 
                    && !codigo.isEmpty() && !nombre.isEmpty() && !ciudad.isEmpty())  {
                ServicioSucursal servicio = new ServicioSucursal();
                try {
                    servicio.modificarSucursal(codigo, nombre, ciudad);
                } catch (AccesoALaDataException | ValorExistenteException | ValorInvalidoException e) {
        %>
        <p class="error"><%=e.getMessage()%></p>
        <%
                }
            }
            SucursalDAO sucursaldao = new SucursalDAO();
            List<SucursalDB> sucursales = null;
            String codigoSucursal = request.getParameter("codigo");
            SucursalDB sucursalCodigo = null;
            try {
                sucursales = sucursaldao.todasLasSucursales();
                sucursalCodigo = sucursaldao.buscarSucursal(codigoSucursal);
            } catch (AccesoALaDataException e) {
        %>
        <p class="error"><%=e.getMessage()%></p>
        <%
            }
        %>
        <div class="contenedor-principal">
            <div class="contenedor-opciones">
            <%
                if (sucursales != null) {
                    for (SucursalDB sucursal : sucursales) {
            %>
                <a class="opcion" href="modificar-sucursal.jsp?&codigo=<%=sucursal.getCodigo()%>">
                    <%=sucursal.getCodigo()%> <br>
                    <%=sucursal.getNombre()%>
                </a>
            <% 
                    }
                }
            %>
            </div>
            <%
                if (sucursalCodigo != null) {
            %>
            <form method="POST" action="modificar-sucursal.jsp?codigo=<%=sucursalCodigo.getCodigo()%>">
                <label>
                    Nombre: 
                    <input name="nombre" value="<%=sucursalCodigo.getNombre()%>"/>
                </label>
                <label>
                    Ciudad:
                    <input name="ciudad" value="<%=sucursalCodigo.getCiudad()%>"/>
                </label>
                <button type="submit" class="boton">
                    Guardar Cambios
                </button>
            </form>
            <%
                }
            %>
        </div>
    </body>
</html>
