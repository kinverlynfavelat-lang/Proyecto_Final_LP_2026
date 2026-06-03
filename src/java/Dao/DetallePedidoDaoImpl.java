/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import Interface.IDetallePedido;
import Model.DetallePedido;
import Util.ConexionSingleton;
import java.util.List;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author kinve
 */
public class DetallePedidoDaoImpl implements IDetallePedido{

            private Connection cn;

    @Override
    public boolean insert(DetallePedido detalle) {
        
        boolean flag = false;
        PreparedStatement st;
        String query = null;

        try {

            query = "INSERT INTO detalle_pedido(cantidad, precio_unitario, personalizacion, id_pedido, id_producto)"
                    + " VALUES(?,?,?,?,?)";

            cn = ConexionSingleton.getConnection();

            st = cn.prepareStatement(query);

            st.setInt(1, detalle.getCantidad());
            st.setDouble(2, detalle.getPrecio_unitario());
            st.setString(3, detalle.getPersonalizacion());
            st.setInt(4, detalle.getPedido().getId_pedido());
            st.setInt(5, detalle.getProducto().getId_producto());
            st.executeUpdate();

            flag = true;

        } catch (Exception e) {

            System.out.println("Error al insertar detalle " + e.getMessage());

            try {
                cn.rollback();
            } catch (Exception ex) {
            }

            flag = false;
            System.out.println("No se pudo insertar el detalle");

        } finally {

            if (cn != null) {
                try {

                } catch (Exception ex) {
                    System.out.println("Error al cerrar conexion");
                }
            }
        }

        return flag;
    }

    @Override
    public List<DetallePedido> listaByPedido(int idPedido) {
        List<DetallePedido> lista = null;
        DetallePedido dp;
        PreparedStatement st;
        ResultSet rs;
        String query = null;

        try {
            query = "SELECT * FROM detalle_pedido "
                    + "WHERE id_pedido=?";

            lista = new ArrayList<>();

            cn = ConexionSingleton.getConnection();
            st = cn.prepareStatement(query);
            
            st.setInt(1, idPedido);
            rs = st.executeQuery();
            while (rs.next()) {
                dp = new DetallePedido();
                dp.setId_detalle(rs.getInt("id_detalle"));
                dp.setCantidad(rs.getInt("cantidad"));
                dp.setPrecio_unitario(rs.getDouble("precio_unitario"));
                dp.setPersonalizacion(rs.getString("personalizacion"));
                
                lista.add(dp);

            }

        } catch (Exception e) {
            System.out.println("Error al listar el detalle" + e.getMessage());
            try {
                cn.rollback();
            } catch (Exception ex) {
            }
            System.out.println("No se pudo listar el detalle");
        } finally {
            if (cn != null) {
                try {

                } catch (Exception ex) {
                }
            }
        }
        return lista;

    }
    
}
