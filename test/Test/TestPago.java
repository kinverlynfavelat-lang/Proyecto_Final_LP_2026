package Test;

import Dao.PagoDaoImpl;
import Enums.EstadoPago;
import Enums.MetodoPago;
import Interface.IPago;
import Model.Pago;
import Model.Pedido;

public class TestPago {

    public static IPago dao = new PagoDaoImpl();

    public static void main(String[] args) {

        TestPago tp = new TestPago();

        //tp.registrarPago();

        //tp.buscarPorPedido();

        tp.validarPago();

    }

    public static void registrarPago() {

        Pago pago = new Pago();

        Pedido pedido = new Pedido();

        // Debe existir previamente un pedido con ese ID
        pedido.setIdPedido(1);

        pago.setPedido(pedido);
        pago.setMetodo(MetodoPago.YAPE);
        pago.setComprobante("YAPE-20260628-001");
        pago.setEstadoPago(EstadoPago.PENDIENTE);

        boolean result = dao.registrarPago(pago);

        if (result) {

            System.out.println("PAGO REGISTRADO");

        } else {

            System.out.println("ERROR AL REGISTRAR EL PAGO");

        }

    }

    public static void buscarPorPedido() {

        Pago pago = dao.buscarPorPedido(1);

        if (pago != null) {

            System.out.println("PAGO ENCONTRADO");

            System.out.println("ID Pago: " + pago.getIdPago());

            System.out.println("Método: " + pago.getMetodo());

            System.out.println("Comprobante: " + pago.getComprobante());

            System.out.println("Estado: " + pago.getEstadoPago());

            System.out.println("Fecha: " + pago.getFechaPago());

            System.out.println("Pedido: " + pago.getPedido().getIdPedido());

        } else {

            System.out.println("NO EXISTE PAGO PARA ESE PEDIDO");

        }

    }

    public static void validarPago() {

        boolean result = dao.validarPago(1, EstadoPago.VALIDADO);

        if (result) {

            System.out.println("PAGO VALIDADO");

        } else {

            System.out.println("NO SE PUDO VALIDAR EL PAGO");

        }

    }

}