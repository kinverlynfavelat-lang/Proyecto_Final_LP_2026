/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Test;

import Dao.PedidoDaoImpl;
import Enums.EstadoPedido;
import Enums.MetodoPago;
import Interface.IPedido;
import Model.Carrito;
import Model.Ingrediente;
import Model.Pedido;
import Model.Producto;
import Model.Usuario;
import java.util.ArrayList;
import java.util.List;

public class TestPedido {

    IPedido dao = new PedidoDaoImpl();

    public static void main(String[] args) {

        TestPedido t = new TestPedido();

        t.generarPedido();
        //t.listar();
        //t.buscarPorId();
        //t.actualizarEstado();
        //t.historial();
    }
    
    public void generarPedido() {

    Pedido pedido = new Pedido();

    pedido.setCodigo("PED-00029");

    pedido.setEstadoPedido(EstadoPedido.ENTREGADO);

    pedido.setMetodoPago(MetodoPago.YAPE);

    pedido.setNombreCliente("Juan Perez");

    pedido.setDni("12345678");

    pedido.setTelefono("987654321");

    pedido.setDireccionEntrega("Av. Perú 123");

    Usuario u = new Usuario();

    u.setIdUsuario(1);

    pedido.setUsuario(u);

    //---------------------------------------
    // Producto 1
    //---------------------------------------

    Producto p1 = new Producto();

    p1.setIdProducto(1);

    Carrito item1 = new Carrito();

    item1.setProducto(p1);

    item1.setCantidad(2);

    item1.setPrecioCompra(18.50);

    item1.setSubTotal(37.00);

    //---------------------------------------
    // Ingredientes del producto 1
    //---------------------------------------

    Ingrediente i1 = new Ingrediente();

    i1.setIdIngrediente(1);

    Ingrediente i2 = new Ingrediente();

    i2.setIdIngrediente(2);

    List<Ingrediente> ingredientes = new ArrayList<>();

    ingredientes.add(i1);

    ingredientes.add(i2);

    item1.setIngredientes(ingredientes);

    

    //---------------------------------------
    // Carrito
    //---------------------------------------

    List<Carrito> carrito = new ArrayList<>();

    carrito.add(item1);


    pedido.setDetallePedido(carrito);

    //---------------------------------------

    pedido.setTotal(45.00);

    int id = dao.generarPedido(pedido);

    if (id > 0) {

        System.out.println("Pedido registrado correctamente");

        System.out.println("ID generado: " + id);

    } else {

        System.out.println("Error al registrar pedido");

    }

}

    public void listar() {

        List<Pedido> lista = dao.listar();

        if (lista != null && !lista.isEmpty()) {

            for (Pedido p : lista) {

                System.out.println("---------------------");

                System.out.println("ID: " + p.getIdPedido());

                System.out.println("Código: " + p.getCodigo());

                System.out.println("Cliente: " + p.getNombreCliente());

                System.out.println("Estado: " + p.getEstadoPedido());

                System.out.println("Método: " + p.getMetodoPago());

                System.out.println("Total: " + p.getTotal());

            }

        } else {

            System.out.println("No existen pedidos.");

        }

    }

    public void buscarPorId() {

        Pedido p = dao.buscarPorId(1);

        if (p != null) {

            System.out.println("Pedido encontrado");

            System.out.println("Código: " + p.getCodigo());

            System.out.println("Cliente: " + p.getNombreCliente());

            System.out.println("Estado: " + p.getEstadoPedido());

        } else {

            System.out.println("Pedido no encontrado");

        }

    }

    public void actualizarEstado() {

        boolean r = dao.actualizarEstado(1, EstadoPedido.EN_PREPARACION);

        if (r) {

            System.out.println("Estado actualizado");

        } else {

            System.out.println("No se pudo actualizar");

        }
    }

    public void historial() {

        List<Pedido> lista = dao.historialCliente(1);

        if (lista != null) {

            for (Pedido p : lista) {

                System.out.println(p.getCodigo());

                System.out.println(p.getEstadoPedido());

                System.out.println(p.getTotal());

                System.out.println("----------------");

            }
            System.out.println("No hay pedidos");
        }

    }
}
