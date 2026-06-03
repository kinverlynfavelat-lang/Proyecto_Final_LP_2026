/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import Interface.IPedido;
import Model.DetallePedido;
import Model.EstadoPedido;
import Model.MetodoPago;
import Model.Pedido;
import Util.ConexionSingleton;
import java.util.List;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author kinve
 */
public class PedidoDaoImpl implements IPedido{
        private Connection cn;

    @Override
    public int generarPedido(Pedido pedido) {
        int id_pedido=0;
        int r=0;
        PreparedStatement st;
        String query = null;
        ResultSet rs;
       //primero se inserta lo de la tabla pedido y luego el detalle 
        try {
            
            query="INSERT INTO pedido"
                    + "(codigo,total,estado,metodo_pago,id_persona) "
                    + "VALUES(?,?,?,?,?)";
            cn=ConexionSingleton.getConnection();
            st=cn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            
            st.setString(1, pedido.getCodigo());
            st.setDouble(2, pedido.getTotal());
            st.setString(3, pedido.getEstadoPedido().name());
            st.setString(4, pedido.getMetodoPago().name());
            st.setInt(5, pedido.getPersona().getId_persona());
            
            r=st.executeUpdate();
            if (r!=0) {
                rs=st.getGeneratedKeys();
                if (rs.next()) {
                    id_pedido=rs.getInt(1);
                }
            }
            if (id_pedido>0) {
                for (DetallePedido detalle : pedido.getDetallePedido()) {
                    query="INSERT INTO detalle_pedido(cantidad,precio_unitario,personalizacion,"
                            + "id_pedido,id_producto) "
                            + "VALUES(?,?,?,?,?)";
                    st=cn.prepareStatement(query);
                    st.setInt(1, detalle.getCantidad());
                    st.setDouble(2, detalle.getPrecio_unitario());
                    st.setString(3, detalle.getPersonalizacion());
                    st.setInt(4, id_pedido);
                    st.setInt(5, detalle.getProducto().getId_producto());
                    
                    r=st.executeUpdate();
                }
            }else{
                System.out.println("Error al agregar detalle");
            }
            
        } catch (Exception e) {
            System.out.println("Error al agregar un pedido" + e.getMessage());
            try {
                cn.rollback();
            } catch (Exception ex) {
                System.out.println("Error de rollback" + e.getMessage());

            }
        } finally {
            if (cn != null) {
                try {

                } catch (Exception ex) {
                }

            }
        }
        return id_pedido;
    }

    @Override
    public List<Pedido> lista() {
         List<Pedido> lista = null;
        Pedido pe;
        PreparedStatement st;
        ResultSet rs;
        String query = null;

        try {
            query = "SELECT * FROM pedido";

            lista = new ArrayList<>();

            cn = ConexionSingleton.getConnection();
            st = cn.prepareStatement(query);
            rs = st.executeQuery();
            while (rs.next()) {
                pe = new Pedido();
                pe.setId_pedido(rs.getInt("id_pedido"));
                pe.setCodigo(rs.getString("codigo"));
                pe.setTotal(rs.getDouble("total"));
                 pe.setEstadoPedido(
                         EstadoPedido.valueOf(
                                rs.getString("estado").toUpperCase() 
                         )
                );
                
                 pe.setMetodoPago(
                         MetodoPago.valueOf(
                                rs.getString("metodo_pago").toUpperCase() 
                         )
                );
                
                lista.add(pe);

            }

        } catch (Exception e) {
            System.out.println("Error al listar pedidos" + e.getMessage());
            try {
                cn.rollback();
            } catch (Exception ex) {
            }
            System.out.println("No se pudo listar los pedidos");
        } finally {
            if (cn != null) {
                try {

                } catch (Exception ex) {
                }
            }
        }
        return lista;

    }

    @Override
    public Pedido SearchById(int id) {
        Pedido pe = null;
        PreparedStatement st;
        ResultSet rs;
        String query = null;

        try {

            query = "SELECT * FROM pedido WHERE id_pedido=?";

            cn = ConexionSingleton.getConnection();

            st = cn.prepareStatement(query);

            st.setInt(1, id);
            rs = st.executeQuery();
            while (rs.next()) {
                pe = new Pedido();
                pe.setId_pedido(rs.getInt("id_pedido"));
                pe.setCodigo(rs.getString("codigo"));
                pe.setTotal(rs.getDouble("total"));
                pe.setEstadoPedido(
                        EstadoPedido.valueOf(
                                rs.getString("estado").toUpperCase()
                        )    
                );
               pe.setMetodoPago(
                        MetodoPago.valueOf(
                                rs.getString("metodo_pago").toUpperCase()
                        )    
                );
               

            }

        } catch (Exception e) {

            System.out.println("Error al buscar pedido " + e.getMessage());

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

        return pe;
    }

    @Override
    public boolean updateEstado(Pedido p) {
        boolean flag = false;
        PreparedStatement st;
        String query = null;

        try {

            query = "UPDATE pedido SET estado=? WHERE id_pedido=?";

            cn = ConexionSingleton.getConnection();
            st = cn.prepareStatement(query);
            st.setString(1, p.getEstadoPedido().name());
            st.setInt(2, p.getId_pedido());

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
