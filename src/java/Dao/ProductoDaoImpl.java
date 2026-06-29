/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import Interface.IProducto;
import Enums.EstadoProducto;
import Model.Producto;
import Util.ConexionSingleton;
import java.util.List;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author kinve
 */
public class ProductoDaoImpl implements IProducto {

    private Connection cn;

    @Override
    public List<Producto> listar() {
        List<Producto> lista = null;
        Producto pr;

        PreparedStatement st;
        ResultSet rs;

        String query = null;

        try {

            query = "SELECT idProducto, nombre, descripcion, precio, imagen, estadoProducto "
                    + "FROM PRODUCTO";

            lista = new ArrayList<>();

            cn = ConexionSingleton.getConnection();

            st = cn.prepareStatement(query);

            rs = st.executeQuery();

            while (rs.next()) {

                pr = new Producto();

                pr.setIdProducto(rs.getInt("idProducto"));
                pr.setNombre(rs.getString("nombre"));
                pr.setDescripcion(rs.getString("descripcion"));
                pr.setPrecio(rs.getDouble("precio"));
                pr.setImagen(rs.getString("imagen"));

                pr.setEstadoProducto(
                        EstadoProducto.valueOf(
                                rs.getString("estadoProducto").toUpperCase()
                        )
                );

                lista.add(pr);

            }

        } catch (Exception e) {

            System.out.println("Error al listar productos " + e.getMessage());

            try {
                cn.rollback();
            } catch (Exception ex) {
            }

            System.out.println("No se pudieron listar los productos");

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
    public boolean insertar(Producto producto) {
        boolean flag = false;

        PreparedStatement st;
        String query = null;

        try {

            query = "INSERT INTO PRODUCTO(nombre, descripcion, precio, imagen, estadoProducto) "
                    + "VALUES(?,?,?,?,?)";

            cn = ConexionSingleton.getConnection();

            st = cn.prepareStatement(query);

            st.setString(1, producto.getNombre());
            st.setString(2, producto.getDescripcion());
            st.setDouble(3, producto.getPrecio());
            st.setString(4, producto.getImagen());
            st.setString(5, producto.getEstadoProducto().name());

            st.executeUpdate();

            flag = true;

        } catch (Exception e) {

            System.out.println("Error al insertar producto " + e.getMessage());

            try {
                cn.rollback();
            } catch (Exception ex) {
            }

            flag = false;

            System.out.println("No se pudo insertar el producto");

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
    public boolean actualizar(Producto producto) {
        boolean flag = false;

        PreparedStatement st;
        String query = null;

        try {

            query = "UPDATE PRODUCTO "
                    + "SET nombre=?, descripcion=?, precio=?, imagen=?, estadoProducto=? "
                    + "WHERE idProducto=?";

            cn = ConexionSingleton.getConnection();

            st = cn.prepareStatement(query);

            st.setString(1, producto.getNombre());
            st.setString(2, producto.getDescripcion());
            st.setDouble(3, producto.getPrecio());
            st.setString(4, producto.getImagen());
            st.setString(5, producto.getEstadoProducto().name());
            st.setInt(6, producto.getIdProducto());

            st.executeUpdate();

            flag = true;

        } catch (Exception e) {

            System.out.println("Error al actualizar producto " + e.getMessage());

            try {
                cn.rollback();
            } catch (Exception ex) {
            }

            flag = false;

            System.out.println("No se pudo actualizar el producto");

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
    public Producto buscarPorId(int idProducto) {
        Producto pr = null;

        PreparedStatement st;
        ResultSet rs;
        String query = null;

        try {

            query = "SELECT * FROM PRODUCTO WHERE idProducto=?";

            cn = ConexionSingleton.getConnection();

            st = cn.prepareStatement(query);

            st.setInt(1, idProducto);

            rs = st.executeQuery();

            if (rs.next()) {

                pr = new Producto();

                pr.setIdProducto(rs.getInt("idProducto"));
                pr.setNombre(rs.getString("nombre"));
                pr.setDescripcion(rs.getString("descripcion"));
                pr.setPrecio(rs.getDouble("precio"));
                pr.setImagen(rs.getString("imagen"));
                pr.setEstadoProducto(
                        EstadoProducto.valueOf(rs.getString("estadoProducto"))
                );

            }

        } catch (Exception e) {

            System.out.println("Error al buscar producto " + e.getMessage());

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

        return pr;
    }

    @Override
    public boolean cambiarEstado(int idProducto, EstadoProducto estado) {
        boolean flag = false;

        PreparedStatement st;
        String query = null;

        try {

            query = "UPDATE PRODUCTO SET estadoProducto=? WHERE idProducto=?";

            cn = ConexionSingleton.getConnection();

            st = cn.prepareStatement(query);

            st.setString(1, estado.name());
            st.setInt(2, idProducto);

            st.executeUpdate();

            flag = true;

        } catch (Exception e) {

            System.out.println("Error al cambiar estado del producto " + e.getMessage());

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

}
