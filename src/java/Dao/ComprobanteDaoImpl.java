/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import Interface.IComprobante;
import Model.Comprobante;
import Model.EstadoComprobante;
import Model.Pedido;
import Util.ConexionSingleton;
import java.sql.*;

/**
 *
 * @author kinve
 */
public class ComprobanteDaoImpl implements IComprobante {

    private Connection cn;

    @Override
    public boolean insert(Comprobante c) {

        boolean flag = false;

        PreparedStatement st;
        String query;

        try {

            query = "INSERT INTO comprobante "
                    + "(archivo,estado,id_pedido) "
                    + "VALUES (?,?,?)";

            cn = ConexionSingleton.getConnection();

            st = cn.prepareStatement(query);

            st.setString(1, c.getArchivo());
            st.setString(2, c.getEstado().name());
            st.setInt(3, c.getPedido().getId_pedido());

            st.executeUpdate();

            flag = true;

        } catch (Exception e) {

            System.out.println("Error al registrar comprobante "
                    + e.getMessage());

            try {
                cn.rollback();
            } catch (Exception ex) {
            }

            flag = false;
        }

        return flag;
    }

    @Override
    public Comprobante SearchById(int id) {
        Comprobante co = null;
        PreparedStatement st;
        ResultSet rs;
        String query = null;

        try {

            query = "SELECT * FROM comprobante WHERE id_comprobante=?";

            cn = ConexionSingleton.getConnection();

            st = cn.prepareStatement(query);

            st.setInt(1, id);
            rs = st.executeQuery();
            while (rs.next()) {
                co = new Comprobante();
                co.setId_comprobante(rs.getInt("id_comprobante"));
                co.setArchivo(rs.getString("archivo"));
                co.setEstado(EstadoComprobante.valueOf(rs.getString("estado")));
                
                Pedido p = new Pedido();
                p.setId_pedido(rs.getInt("id_pedido"));
                co.setPedido(p);

            }

        } catch (Exception e) {

            System.out.println("Error al buscar comprobante " + e.getMessage());

            try {
                cn.rollback();
            } catch (Exception ex) {
            }

        } finally {

            if (cn != null) {
                try {

                } catch (Exception ex) {
                    System.out.println("Error al cerrar conexion");
                }
            }
        }

        return co;
    }

    @Override
    public boolean updateEstado(Comprobante c) {
        boolean flag = false;
        PreparedStatement st;
        String query = null;

        try {

            query = "UPDATE comprobante SET estado=? WHERE id_comprobante=?";

            cn = ConexionSingleton.getConnection();
            st = cn.prepareStatement(query);
            st.setString(1, c.getEstado().name());
            st.setInt(2, c.getId_comprobante());

            st.executeUpdate();

            flag = true;

        } catch (Exception e) {

            System.out.println("Error al actualizar estado " + e.getMessage());

            try {
                cn.rollback();
            } catch (Exception ex) {
            }

            flag = false;

        } finally {

            if (cn != null) {
                try {
                    cn.close();
                } catch (Exception ex) {
                    System.out.println("Error al cerrar conexion");
                }
            }
        }

        return flag;
    
    
    }

}
