/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import Model.Pago;
import Enums.EstadoPago;
import Enums.MetodoPago;
import Model.Pedido;
import Util.ConexionSingleton;
import java.sql.*;
import Interface.IPago;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kinve
 */
public class PagoDaoImpl implements IPago {

    private Connection cn;

    @Override
    public boolean registrarPago(Pago pago) {
        boolean flag = false;

    PreparedStatement st;
    String query = null;

    try {

        query = "INSERT INTO PAGO "
                + "(metodo, comprobante, estadoPago, idPedido) "
                + "VALUES (?,?,?,?)";

        cn = ConexionSingleton.getConnection();

        st = cn.prepareStatement(query);

        st.setString(1, pago.getMetodo().name());
        st.setString(2, pago.getComprobante());
        st.setString(3, pago.getEstadoPago().name());
        st.setInt(4, pago.getPedido().getIdPedido());

        st.executeUpdate();

        flag = true;

    } catch (Exception e) {

        System.out.println("Error al registrar pago " + e.getMessage());

        try {
            cn.rollback();
        } catch (Exception ex) {
        }

        flag = false;

    } finally {

        if (cn != null) {
            try {

            } catch (Exception ex) {
                System.out.println("Error al cerrar conexión");
            }
        }

    }

    return flag;
    }

    @Override
    public Pago buscarPorPedido(int idPedido) {
         Pago pa = null;

    PreparedStatement st;

    ResultSet rs;

    String query = null;

    try {

        query = "SELECT * FROM PAGO WHERE idPedido=?";

        cn = ConexionSingleton.getConnection();

        st = cn.prepareStatement(query);

        st.setInt(1, idPedido);

        rs = st.executeQuery();

        while (rs.next()) {

            pa = new Pago();

            pa.setIdPago(rs.getInt("idPago"));

            pa.setMetodo(
                    MetodoPago.valueOf(
                            rs.getString("metodo")
                    )
            );

            pa.setComprobante(rs.getString("comprobante"));

            pa.setEstadoPago(
                    EstadoPago.valueOf(
                            rs.getString("estadoPago")
                    )
            );

            pa.setFechaPago(
                    rs.getTimestamp("fechaPago").toLocalDateTime()
            );

            Pedido pedido = new Pedido();

            pedido.setIdPedido(rs.getInt("idPedido"));

            pa.setPedido(pedido);

        }

    } catch (Exception e) {

        System.out.println("Error al buscar pago " + e.getMessage());

        try {
            cn.rollback();
        } catch (Exception ex) {
        }

    } finally {

        if (cn != null) {
            try {

            } catch (Exception ex) {
                System.out.println("Error al cerrar conexión");
            }
        }

    }

    return pa;

    }

    @Override
    public boolean validarPago(int idPago, EstadoPago estado) {
         boolean flag = false;

    PreparedStatement st;

    String query = null;

    try {

        query = "UPDATE PAGO "
                + "SET estadoPago=? "
                + "WHERE idPago=?";

        cn = ConexionSingleton.getConnection();

        st = cn.prepareStatement(query);

        st.setString(1, estado.name());

        st.setInt(2, idPago);

        st.executeUpdate();

        flag = true;

    } catch (Exception e) {

        System.out.println("Error al actualizar estado del pago "
                + e.getMessage());

        try {
            cn.rollback();
        } catch (Exception ex) {
        }

        flag = false;

    } finally {

        if (cn != null) {
            try {

            } catch (Exception ex) {
                System.out.println("Error al cerrar conexión");
            }
        }

    }

    return flag;
    }

    @Override
    public List<Pago> listarTodos() {
        
         List<Pago> lista = new ArrayList<>();

    String sql = "SELECT * FROM PAGO";

    try {

        cn = ConexionSingleton.getConnection();
        PreparedStatement st = cn.prepareStatement(sql);
        ResultSet rs = st.executeQuery();

        while (rs.next()) {

            Pago p = new Pago();

            p.setIdPago(rs.getInt("idPago"));
            p.setMetodo(MetodoPago.valueOf(rs.getString("metodo")));
            p.setComprobante(rs.getString("comprobante"));
            p.setEstadoPago(EstadoPago.valueOf(rs.getString("estadoPago")));
            p.setFechaPago(rs.getTimestamp("fechaPago").toLocalDateTime());

            Pedido pe = new Pedido();
            pe.setIdPedido(rs.getInt("idPedido"));

            p.setPedido(pe);

            lista.add(p);
        }

    } catch (Exception e) {
        System.out.println("Error listar pagos: " + e.getMessage());
    }

    return lista;
    }

    

}
