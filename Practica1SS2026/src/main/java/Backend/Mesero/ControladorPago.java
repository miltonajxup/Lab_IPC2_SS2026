/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend.Mesero;

import DAOs.DetalleCuentaDAO;
import DAOs.InsumoDAO;
import DAOs.MesaDAO;
import DAOs.PedidoDAO;
import Exceptions.AccesoALaDataException;
import Exceptions.ErrorIngresarDatosException;
import Frontent.Mesero.ServicioPagoCuenta;
import Modelos.Insumo;
import Modelos.InsumoPedido;
import Modelos.Mesa;
import Modelos.Pedido;
import Modelos.Personal;
import Modelos.ProductoMenu;
import java.util.ArrayList;
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
    private final DetalleCuentaDAO detalledao;
    private Mesa mesa;
    private Personal mesero;
    private Pedido pedido;
    
    public ControladorPago(ServicioPagoCuenta servicioPago, ControladorOrden controladorOrden, MesaDAO mesasdao, InsumoDAO insumodao) {
        this.servicioPago = servicioPago;
        this.controladorOrden = controladorOrden;
        this.pedidodao = new PedidoDAO();
        this.mesasdao = mesasdao;
        this.insumodao = insumodao;
        this.detalledao = new DetalleCuentaDAO();
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
    }
    
    public void generarPedido() throws AccesoALaDataException, ErrorIngresarDatosException {
        if (controladorOrden.getProductoPedido().size() <= 0) {
            throw new ErrorIngresarDatosException("No hay productos seleccionados para generar una orden");
        }
        double pagoTotal = calcularPagoTotal();
        try {
            List<InsumoPedido> insumosPedido = controladorOrden.getInsumosPedido();
            
            for (int i = 0; i < insumosPedido.size(); i++) {
                InsumoPedido actual = insumosPedido.get(i);
                Insumo insumoLista = controladorOrden.buscarInsumo(actual.getCodigo());
                double stockActual = insumoLista.getCantidadStock();
                double cantidadAUtilizar = actual.getCantidad();
                stockActual -= cantidadAUtilizar;
                insumodao.actualizarInsumo(stockActual, actual.getCodigo());
            }
            
            mesa.setEstado(true);
            mesasdao.actualizarMesa(mesa.getEstado(), mesa.getNumeroMesa());
            pedidodao.agregarPedido(pagoTotal, mesero.getDpi(), mesa.getNumeroMesa());
            Pedido ultimoPedido = pedidodao.getUltimoPedido();
            generarDetallesCuenta(ultimoPedido);
        } catch (AccesoALaDataException e) {
            servicioPago.mostrarError(e.getMessage());
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
    
    private void generarDetallesCuenta(Pedido pedido) throws AccesoALaDataException {
        List<ProductoMenu> productosPedido = controladorOrden.getProductoPedido();
        List<Integer> codigoRevisados = new ArrayList<>();
        for (int i = 0; i < productosPedido.size(); i++) {
            ProductoMenu actual = productosPedido.get(i);
            if (!codigoRevisado(codigoRevisados, actual.getCodigo())) {
                int productoRepetido = 0;
                for (int j = 0; j < productosPedido.size(); j++) {
                    if (actual.getCodigo() == productosPedido.get(j).getCodigo()) {
                        productoRepetido++;
                    }
                }
                double subTotal = productoRepetido * actual.getPrecio();
                detalledao.agregarDetalles(actual.getCodigo(), actual.getPrecio(), productoRepetido, subTotal, pedido.getNumeroPedido());
            }
            codigoRevisados.add(actual.getCodigo());
        }
    }
    
    private boolean codigoRevisado(List<Integer> indices, int nuevoCodigo) {
        for (int i = 0; i < indices.size(); i++) {
            if (indices.get(i) == nuevoCodigo) {
                return true;
            }
        }
        return false;
    }
    
    private double calcularPagoTotal() {
        double pagoTotal = 0;
        List<ProductoMenu> productosPedido = controladorOrden.getProductoPedido();
        for (int i = 0; i < productosPedido.size(); i++) {
            pagoTotal += productosPedido.get(i).getPrecio();
        }
        return pagoTotal;
    }
    
}
