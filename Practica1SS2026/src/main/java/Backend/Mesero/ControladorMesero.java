/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend.Mesero;

import DAOs.DetalleCuentaDAO;
import DAOs.MesaDAO;
import DAOs.PedidoDAO;
import DAOs.PersonalDAO;
import Exceptions.AccesoALaDataException;
import Frontent.JavaBeansCafe;
import Frontent.Mesero.OpcionSMMesero;
import Frontent.Mesero.PlantillaMesa;
import Frontent.Mesero.ServicioMesero;
import Frontent.Mesero.ServicioPagoCuenta;
import Frontent.Mesero.SubMenuMeseros;
import Modelos.DetalleCuenta;
import Modelos.Mesa;
import Modelos.Pedido;
import Modelos.Personal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author milton
 */
public class ControladorMesero {
    
    private final PersonalDAO personaldao;
    private List<Personal> meseros;
    private List<PlantillaMesa> plantillasMesa;
    private final MesaDAO mesadao;
    private List<Mesa> mesas;
    private final PedidoDAO pedidodao;
    private List<Pedido> pedidos;
    private final ServicioMesero servicioMesero;
    private final SubMenuMeseros subMenuMeseros;
    private final JavaBeansCafe jbcafe;
    private final ServicioPagoCuenta servicioCuenta;
    private final DetalleCuentaDAO detalleCuentadao;
    private ControladorPago controladorPago;
    private ControladorOrden controladorOrden;
    
    public ControladorMesero(ServicioMesero servicioMesero, SubMenuMeseros subMenuMeseros, JavaBeansCafe jbcafe, ServicioPagoCuenta servicioCuenta, List<Mesa> mesas) {
        this.personaldao = new PersonalDAO();
        this.mesadao = new MesaDAO();
        this.mesas = mesas;
        this.pedidodao = new PedidoDAO();
        this.servicioMesero = servicioMesero;
        this.subMenuMeseros = subMenuMeseros;
        this.jbcafe = jbcafe;
        this.servicioCuenta = servicioCuenta;
        this.detalleCuentadao = new DetalleCuentaDAO();
    }
    
    public void setControladroPago(ControladorPago controldorPago) {
        this.controladorPago = controldorPago;
    }
    
    public void setControladorOrden(ControladorOrden controladorOrden) {
        this.controladorOrden = controladorOrden;
    }
    
    public void traerCambiosMesa() throws AccesoALaDataException {
        mesas = mesadao.getMesas();
        pedidos = pedidodao.getMesasOcupadas();
        colocarMesas();
    }
    
    public void colocarMesas() throws AccesoALaDataException {
        String mesero;
        String estado;
        servicioMesero.limpiar();
        servicioMesero.setCuadricula(mesas.size() / 2 + 1);
        pedidos = pedidodao.getMesasOcupadas();
        plantillasMesa = new ArrayList<>();
        for (int i = 0; i < mesas.size(); i++) {
            Mesa actual = mesas.get(i);
            Pedido pedido = mesaOcupada(actual.getNumeroMesa());
            if (pedido != null) {
                mesero = pedido.getNombreMesero();
                estado = "OCUPADO";
            } else {
                mesero = "Ninguno";
                estado = "LIBRE";
            }
            PlantillaMesa plantillaMesa = new PlantillaMesa(this, actual, mesero, estado);
            plantillaMesa.habilitarBoton(false);
            plantillasMesa.add(plantillaMesa);
            servicioMesero.agregarMesa(plantillaMesa);
        }
    }
    
    private Pedido mesaOcupada(int numeroMesa) {
        for (int i = 0; i < pedidos.size(); i++) {
            Pedido actual = pedidos.get(i);
            if (actual.getNumeroMesa() == numeroMesa) {
                return actual;
            }
        }
        return null;
    }
    
    public void colocarMeseros() throws AccesoALaDataException {
        subMenuMeseros.limpiar();
        subMenuMeseros.setVisible(true);
        meseros = personaldao.getMeseros();
        subMenuMeseros.setCuadricula(meseros.size());
        for (int i = 0; i < meseros.size(); i++) {
            OpcionSMMesero opcion = new OpcionSMMesero(this, subMenuMeseros, meseros.get(i));
            subMenuMeseros.agregarMesero(opcion);
        }
    }
    
    public void elegirMesero(Personal mesero) throws AccesoALaDataException {
        subMenuMeseros.mostrar(false);
        servicioMesero.setMesero(mesero.getNombre());
        controladorPago.setMesero(mesero);
        Pedido pedido = meseroOcupado(mesero.getDpi());
        if (pedido != null) {
            controladorPago.setPedido(pedido);
        }
        for (int i = 0; i < mesas.size(); i++) {
            PlantillaMesa plantilla = plantillasMesa.get(i);
            if (pedido == null) {
                if (!plantilla.estaOcupado()) {
                    plantilla.habilitarBoton(true);
                } else {
                    plantilla.habilitarBoton(false);
                }
            } else if (pedido.getNumeroMesa() == plantilla.getNumeroMesa()) {
                plantilla.habilitarBoton(true);
            } else {
                plantilla.habilitarBoton(false);
            }
        }
    }
    
    private Pedido meseroOcupado(String dpi) throws AccesoALaDataException {
        pedidos = pedidodao.getMesasOcupadas();
        for (int i = 0; i < pedidos.size(); i++) {
            Pedido actual = pedidos.get(i);
            if (actual.getMesero().equals(dpi)) {
                return actual;
            }
        }
        return null;
    }
    
    public void bloquearMesas() {
        servicioMesero.setMesero("Ninguno");
        for (int i = 0; i < plantillasMesa.size(); i++) {
            plantillasMesa.get(i).habilitarBoton(false);
        }
    }
    
    public void desicionMesero(Mesa mesa) throws AccesoALaDataException {
        controladorPago.setMesa(mesa);
        if (mesa.getEstado()) {
            jbcafe.cambiarAPago();
            List<DetalleCuenta> detallesCuenta = detalleCuentadao.getDetallesCuenta(controladorPago.getNumeroPedido());
            servicioCuenta.setNumeroDetalles(detallesCuenta.size());
            double pagoTotal = 0;
            for (int i = 0; i < detallesCuenta.size(); i++) {
                DetalleCuenta detalle = detallesCuenta.get(i);
                servicioCuenta.agregarDetalle(detalle.getNombreProducto(), detalle.getPrecio(), detalle.getUnidades(), detalle.getSubTotal());
                pagoTotal += detalle.getSubTotal();
            }
            servicioCuenta.setTotal(pagoTotal);
            servicioCuenta.setDetallesMesa(controladorPago.getNumeroMesa(), controladorPago.getHoraPedido());
        } else {
            jbcafe.cambiarAOrden();
            controladorOrden.colocarProductos();
        }
    }
    
}
