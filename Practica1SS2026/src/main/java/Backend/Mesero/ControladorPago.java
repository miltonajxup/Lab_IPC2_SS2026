/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend.Mesero;

import DAOs.InsumoDAO;
import DAOs.MesaDAO;
import DAOs.PedidoDAO;
import Exceptions.AccesoALaDataException;
import Frontent.Mesero.ServicioPagoCuenta;
import Modelos.Insumo;
import Modelos.InsumoPedido;
import Modelos.Mesa;
import Modelos.Pedido;
import Modelos.Personal;
import com.mycompany.practica1ss2026.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author milton
 */
public class ControladorPago {
    
    private final ServicioPagoCuenta servicioPago;
    private final ControladorOrden controladorOrden;
    private final PedidoDAO pedidodao;
    private final MesaDAO mesasdao;
    private final InsumoDAO insumodao;
    private Mesa mesa;
    private Personal mesero;
    private Pedido pedido;
    private double pagoTotal;
    
    public ControladorPago(ServicioPagoCuenta servicioPago, ControladorOrden controladorOrden, MesaDAO mesasdao, InsumoDAO insumodao) {
        this.servicioPago = servicioPago;
        this.controladorOrden = controladorOrden;
        this.pedidodao = new PedidoDAO();
        this.mesasdao = mesasdao;
        this.insumodao = insumodao;
    }
    
    public void setMesa(Mesa mesa) {
        this.mesa = mesa;
    }
    
    public void setMesero(Personal mesero) {
        this.mesero = mesero;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }
    
    public void setPagoTotal(double pagoTotal) {
        this.pagoTotal = pagoTotal;
    }
    
    public String getdpiMesero() {
        return mesero.getDpi();
    }
    
    public int getNumeroPedido() {
        return pedido.getNumeroPedido();
    }
    
    public void eliminarPedidoMesero() {
        mesa = null;
        mesero = null;
        pedido = null;
        pagoTotal = 0;
    }
    
    public void generarPedido() throws AccesoALaDataException {
        Connection connection = DBConnection.getConnection();
        try {
            connection.setAutoCommit(false);
            List<InsumoPedido> insumosPedido = controladorOrden.getInsumosPedido();
            
            for (int i = 0; i < insumosPedido.size(); i++) {
                InsumoPedido actual = insumosPedido.get(i);
                Insumo insumoLista = controladorOrden.buscarInsumo(actual.getCodigo());
                double stockActual = insumoLista.getCantidadStock();
                double cantidadAUtilizar = actual.getCantidad();
                stockActual -= cantidadAUtilizar;
                insumodao.actualizarInsumo(stockActual, actual.getCodigo());
            }
            
            pedidodao.agregarPedido(pagoTotal, mesero.getDpi(), mesa.getNumeroMesa());
            
            mesa.setEstado(true);
            mesasdao.actualizarMesa(mesa.getEstado(), mesa.getNumeroMesa());
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                servicioPago.mostrarError(e.getMessage());
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                servicioPago.mostrarError(ex.getMessage());
            }
        }
    }

    public void pagarCuenta(String propinaTexto) throws AccesoALaDataException {
        double propina = 0;
        if (!propinaTexto.equals("")) {
            try {
                propina = Double.parseDouble(propinaTexto);
            } catch (NumberFormatException e) {
                servicioPago.mostrarError("La propina debe ser ingresada en numeros");
                return;
            }
        }
        pedidodao.actualizarPedido(pedido.getNumeroPedido(), propina);
        mesa.setEstado(false);
        mesasdao.actualizarMesa(mesa.getEstado(), mesa.getNumeroMesa());
    }
    
}
