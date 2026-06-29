/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import Interface.IPedido;
import Enums.EstadoPedido;
import Enums.EstadoProducto;
import Enums.MetodoPago;
import Model.Carrito;
import Model.DetallePedido;
import Model.Ingrediente;
import Model.Pedido;
import Model.Producto;
import Util.ConexionSingleton;
import java.util.List;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author kinve
 */
public class PedidoDaoImpl implements IPedido {

    private Connection cn;

    @Override
    public int generarPedido(Pedido pedido) {

        int idPedido = 0;

    PreparedStatement st;
    ResultSet rs;
    String query = null;

    try {

        //-------------------------
        // INSERTAR PEDIDO
        //-------------------------

        query = "INSERT INTO PEDIDO "
                + "(codigo, estadoPedido, metodoPago, nombreCliente, "
                + "dni, telefono, direccionEntrega, total, idUsuario) "
                + "VALUES (?,?,?,?,?,?,?,?,?)";

        cn = ConexionSingleton.getConnection();

        st = cn.prepareStatement(query);

        st.setString(1, pedido.getCodigo());
        st.setString(2, pedido.getEstadoPedido().name());
        st.setString(3, pedido.getMetodoPago().name());
        st.setString(4, pedido.getNombreCliente());
        st.setString(5, pedido.getDni());
        st.setString(6, pedido.getTelefono());
        st.setString(7, pedido.getDireccionEntrega());
        st.setDouble(8, pedido.getTotal());
        st.setInt(9, pedido.getUsuario().getIdUsuario());

        st.executeUpdate();

        //-------------------------
        // OBTENER ID DEL PEDIDO
        //-------------------------

        query = "SELECT idPedido FROM PEDIDO WHERE codigo=?";

        st = cn.prepareStatement(query);

        st.setString(1, pedido.getCodigo());

        rs = st.executeQuery();

        if (rs.next()) {

            idPedido = rs.getInt("idPedido");

        }

        //-------------------------
        // INSERTAR DETALLE
        //-------------------------

        if (idPedido > 0) {

            for (Carrito item : pedido.getDetallePedido()) {

                int idDetallePedido = 0;

                query = "INSERT INTO DETALLE_PEDIDO "
                        + "(cantidad, precioUnitario, subTotal, idPedido, idProducto) "
                        + "VALUES (?,?,?,?,?)";

                st = cn.prepareStatement(query);

                st.setInt(1, item.getCantidad());
                st.setDouble(2, item.getPrecioCompra());
                st.setDouble(3, item.getSubTotal());
                st.setInt(4, idPedido);
                st.setInt(5, item.getProducto().getIdProducto());

                st.executeUpdate();

                //-------------------------
                // OBTENER ID DEL DETALLE
                //-------------------------

                query = "SELECT MAX(idDetalleP) idDetalleP "
                        + "FROM DETALLE_PEDIDO "
                        + "WHERE idPedido=?";

                st = cn.prepareStatement(query);

                st.setInt(1, idPedido);

                rs = st.executeQuery();

                if (rs.next()) {

                    idDetallePedido = rs.getInt("idDetalleP");

                }

                //-------------------------
                // INSERTAR INGREDIENTES
                //-------------------------

                if (item.getIngredientes() != null
                        && !item.getIngredientes().isEmpty()) {

                    for (Ingrediente ingrediente : item.getIngredientes()) {

                        query = "INSERT INTO DETALLE_PEDIDO_INGR "
                                + "(idIngrediente,idDetalleP,cantidad) "
                                + "VALUES(?,?,?)";

                        st = cn.prepareStatement(query);

                        st.setInt(1, ingrediente.getIdIngrediente());
                        st.setInt(2, idDetallePedido);
                        st.setInt(3, 1);

                        st.executeUpdate();

                    }

                }

            }

        }

    } catch (Exception e) {

        System.out.println("Error al generar pedido " + e.getMessage());

        try {
            cn.rollback();
        } catch (Exception ex) {
        }

    } finally {

        if (cn != null) {
            try {
                cn.close();
            } catch (Exception ex) {
                System.out.println("Error al cerrar conexión");
            }
        }

    }

    return idPedido;
    }

    @Override
    public List<Pedido> listar() {
        List<Pedido> lista = null;
        Pedido pe;

        PreparedStatement st;
        ResultSet rs;
        String query = null;

        try {

            query = "SELECT * FROM PEDIDO";

            lista = new ArrayList<>();

            cn = ConexionSingleton.getConnection();

            st = cn.prepareStatement(query);

            rs = st.executeQuery();

            while (rs.next()) {

                pe = new Pedido();

                pe.setIdPedido(rs.getInt("idPedido"));
                pe.setCodigo(rs.getString("codigo"));
                pe.setFecha(rs.getTimestamp("fecha").toLocalDateTime());
                pe.setEstadoPedido(
                        EstadoPedido.valueOf(rs.getString("estadoPedido"))
                );
                pe.setMetodoPago(
                        MetodoPago.valueOf(rs.getString("metodoPago"))
                );
                pe.setNombreCliente(rs.getString("nombreCliente"));
                pe.setTotal(rs.getDouble("total"));

                lista.add(pe);

            }

        } catch (Exception e) {

            System.out.println("Error al listar pedidos " + e.getMessage());

            try {
                cn.rollback();
            } catch (Exception ex) {
            }

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
    public Pedido buscarPorId(int idPedido) {

        Pedido pe = null;

    PreparedStatement st;
    ResultSet rs;
    String query = null;

    try {

        //-------------------------
        // CABECERA DEL PEDIDO
        //-------------------------

        query = "SELECT * FROM PEDIDO WHERE idPedido=?";

        cn = ConexionSingleton.getConnection();

        st = cn.prepareStatement(query);

        st.setInt(1, idPedido);

        rs = st.executeQuery();

        if (rs.next()) {

            pe = new Pedido();

            pe.setIdPedido(rs.getInt("idPedido"));
            pe.setCodigo(rs.getString("codigo"));
            pe.setFecha(rs.getTimestamp("fecha").toLocalDateTime());

            pe.setEstadoPedido(
                    EstadoPedido.valueOf(
                            rs.getString("estadoPedido"))
            );

            pe.setMetodoPago(
                    MetodoPago.valueOf(
                            rs.getString("metodoPago"))
            );

            pe.setNombreCliente(rs.getString("nombreCliente"));
            pe.setDni(rs.getString("dni"));
            pe.setTelefono(rs.getString("telefono"));
            pe.setDireccionEntrega(rs.getString("direccionEntrega"));
            pe.setTotal(rs.getDouble("total"));

            //-------------------------
            // DETALLE DEL PEDIDO
            //-------------------------

            List<DetallePedido> detalles = new ArrayList<>();

            query = "SELECT dp.idDetalleP, dp.cantidad, "
                    + "dp.precioUnitario, dp.subTotal, "
                    + "p.idProducto, p.nombre, p.descripcion, "
                    + "p.precio, p.imagen, p.estadoProducto "
                    + "FROM DETALLE_PEDIDO dp "
                    + "INNER JOIN PRODUCTO p "
                    + "ON dp.idProducto = p.idProducto "
                    + "WHERE dp.idPedido=?";

            st = cn.prepareStatement(query);

            st.setInt(1, idPedido);

            rs = st.executeQuery();

            while (rs.next()) {

                DetallePedido detalle = new DetallePedido();

                detalle.setIdDetalleP(rs.getInt("idDetalleP"));
                detalle.setCantidad(rs.getInt("cantidad"));
                detalle.setPrecioUnitario(rs.getDouble("precioUnitario"));
                detalle.setSubTotal(rs.getDouble("subTotal"));

                Producto producto = new Producto();

                producto.setIdProducto(rs.getInt("idProducto"));
                producto.setNombre(rs.getString("nombre"));
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setPrecio(rs.getDouble("precio"));
                producto.setImagen(rs.getString("imagen"));

                producto.setEstadoProducto(
                        EstadoProducto.valueOf(
                                rs.getString("estadoProducto"))
                );

                detalle.setProducto(producto);

                //-------------------------
                // INGREDIENTES DEL DETALLE
                //-------------------------

                List<Ingrediente> ingredientes = new ArrayList<>();

                PreparedStatement stIng = cn.prepareStatement(
                        "SELECT i.idIngrediente, i.nombre, i.precioExtra "
                        + "FROM DETALLE_PEDIDO_INGR di "
                        + "INNER JOIN INGREDIENTE i "
                        + "ON di.idIngrediente=i.idIngrediente "
                        + "WHERE di.idDetalleP=?"
                );

                stIng.setInt(1, detalle.getIdDetalleP());

                ResultSet rsIng = stIng.executeQuery();

                while (rsIng.next()) {

                    Ingrediente ing = new Ingrediente();

                    ing.setIdIngrediente(rsIng.getInt("idIngrediente"));
                    ing.setNombre(rsIng.getString("nombre"));
                    ing.setPrecioExtra(rsIng.getDouble("precioExtra"));

                    ingredientes.add(ing);

                }

                detalle.setIngredientes(ingredientes);

                detalles.add(detalle);

            }

            pe.setDetalles(detalles);

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
                cn.close();
            } catch (Exception ex) {
                System.out.println("Error al cerrar conexión");
            }
        }

    }

    return pe;
    }

    @Override
    public boolean actualizarEstado(int idPedido, EstadoPedido estado) {
        boolean flag = false;

        PreparedStatement st;

        String query = null;

        try {

            query = "UPDATE PEDIDO SET estadoPedido=? WHERE idPedido=?";

            cn = ConexionSingleton.getConnection();

            st = cn.prepareStatement(query);

            st.setString(1, estado.name());

            st.setInt(2, idPedido);

            st.executeUpdate();

            flag = true;

        } catch (Exception e) {

            System.out.println("Error al actualizar estado " + e.getMessage());

            try {
                cn.rollback();
            } catch (Exception ex) {
            }

        } finally {

            if (cn != null) {
                try {

                } catch (Exception ex) {
                }

            }

        }

        return flag;
    }

    @Override
    public List<Pedido> historialCliente(int idUsuario) {
        List<Pedido> lista = null;

        Pedido pe;

        PreparedStatement st;

        ResultSet rs;

        String query = null;

        try {

            query = "SELECT * FROM PEDIDO "
                    + "WHERE idUsuario=? "
                    + "ORDER BY fecha DESC";

            lista = new ArrayList<>();

            cn = ConexionSingleton.getConnection();

            st = cn.prepareStatement(query);

            st.setInt(1, idUsuario);

            rs = st.executeQuery();

            while (rs.next()) {

                pe = new Pedido();

                pe.setIdPedido(rs.getInt("idPedido"));
                pe.setCodigo(rs.getString("codigo"));
                pe.setFecha(rs.getTimestamp("fecha").toLocalDateTime());
                pe.setEstadoPedido(
                        EstadoPedido.valueOf(rs.getString("estadoPedido"))
                );
                pe.setMetodoPago(
                        MetodoPago.valueOf(rs.getString("metodoPago"))
                );
                pe.setTotal(rs.getDouble("total"));

                lista.add(pe);

            }

        } catch (Exception e) {

            System.out.println("Error al listar historial " + e.getMessage());

            try {
                cn.rollback();
            } catch (Exception ex) {
            }

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
