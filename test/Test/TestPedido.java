/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Test;

import Dao.PedidoDaoImpl;
import Interface.IPedido;
import Model.DetallePedido;
import Model.EstadoPedido;
import Model.MetodoPago;
import Model.Pedido;
import Model.Persona;
import Model.Producto;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kinve
 */
public class TestPedido {

    /**
     * @param args the command line arguments
     */
    IPedido dao = new PedidoDaoImpl();

    public static void main(String[] args) {
        TestPedido t = new TestPedido();
        t.generarPedido();
        //t.listar();
        //t.buscarPedido();
        //t.actualizarEstado();
    }

    public void generarPedido() {

        Persona persona = new Persona();
        persona.setId_persona(8); 

        Producto prod1 = new Producto();
        prod1.setId_producto(3);

        Producto prod2 = new Producto();
        prod2.setId_producto(1);

        List<DetallePedido> detalles = new ArrayList<>();

        DetallePedido d1 = new DetallePedido();
        d1.setCantidad(2);
        d1.setPrecio_unitario(25);
        d1.setPersonalizacion("Sin cebolla");
        d1.setProducto(prod1);
        detalles.add(d1);

        DetallePedido d2 = new DetallePedido();
        d2.setCantidad(1);
        d2.setPrecio_unitario(18);
        d2.setPersonalizacion("Extra queso");
        d2.setProducto(prod2);
        detalles.add(d2);

        Pedido pedido = new Pedido();

        pedido.setCodigo("PED003");
        pedido.setTotal(68);
        pedido.setEstadoPedido(EstadoPedido.PENDIENTE);
        pedido.setMetodoPago(MetodoPago.YAPE);
        pedido.setPersona(persona);
        pedido.setDetallePedido(detalles);

        int idGenerado = dao.generarPedido(pedido);

        if (idGenerado > 0) {
            System.out.println("Pedido registrado");
            System.out.println("ID Pedido: " + idGenerado);

        } else {
            System.out.println("Error al registrar pedido");
        }

    }

    public void listar() {

        List<Pedido> lista = dao.lista();

        if (lista != null && !lista.isEmpty()) {

            for (Pedido p : lista) {

                System.out.println("ID: " + p.getId_pedido());
                System.out.println("Codigo: " + p.getCodigo());
                System.out.println("Total: " + p.getTotal());
                System.out.println("Estado: " + p.getEstadoPedido());
                System.out.println("Metodo Pago: " + p.getMetodoPago());
                System.out.println("----------------------");

            }

        } else {
            System.out.println("No hay pedidos");
        }
    }

    public void buscarPedido() {

        Pedido p = dao.SearchById(1);

        if (p != null) {

            System.out.println("Pedido encontrado");
            System.out.println("ID: " + p.getId_pedido());
            System.out.println("Codigo: " + p.getCodigo());
            System.out.println("Total: " + p.getTotal());
            System.out.println("Estado: " + p.getEstadoPedido());

        } else {
            System.out.println("Pedido no encontrado");
        }
    }

    public void actualizarEstado() {

        Pedido p = new Pedido();

        p.setId_pedido(1);
        p.setEstadoPedido(EstadoPedido.EN_PREPARACION);

        boolean r = dao.updateEstado(p);

        if (r) {
            System.out.println("Estado actualizado");
        } else {
            System.out.println("Error al actualizar");
        }
    }
}
