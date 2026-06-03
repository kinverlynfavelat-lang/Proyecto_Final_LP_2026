/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import Interface.IProducto;
import Model.EstadoProducto;
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
    public List<Producto> lista() {

        List<Producto> lista = null;
        Producto pr;
        PreparedStatement st;
        ResultSet rs;
        String query = null;

        try {
            query = "SELECT id_producto,nombre,descripcion,"
                    + " precio,imagen,estado FROM producto";

            lista = new ArrayList<>();

            cn = ConexionSingleton.getConnection();
            st = cn.prepareStatement(query);
            rs = st.executeQuery();
            while (rs.next()) {
                pr = new Producto();
                pr.setId_producto(rs.getInt("id_producto"));
                pr.setNombre(rs.getString("nombre"));
                pr.setDescripcion(rs.getString("descripcion"));
                pr.setPrecio(rs.getDouble("precio"));
                pr.setImagen(rs.getString("imagen"));
                pr.setEstado(
                        EstadoProducto.valueOf(
                                rs.getString("estado").toUpperCase()
                        )    
                );
                lista.add(pr);

            }

        } catch (Exception e) {
            System.out.println("Error al listar el producto" + e.getMessage());
            try {
                cn.rollback();
            } catch (Exception ex) {
            }
            System.out.println("No se pudo listar el producto");
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
    public boolean insert(Producto p) {
        boolean flag = false;
        PreparedStatement st;
        String query = null;

        try {

            query = "INSERT INTO producto(nombre, descripcion, precio, imagen, estado)"
                    + " VALUES(?,?,?,?,?)";

            cn = ConexionSingleton.getConnection();

            st = cn.prepareStatement(query);

            st.setString(1, p.getNombre());
            st.setString(2, p.getDescripcion());
            st.setDouble(3, p.getPrecio());
            st.setString(4, p.getImagen());
            st.setString(5, p.getEstado().name());
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
                    System.out.println("Error al cerrar conexion");
                }
            }
        }

        return flag;
    }

    @Override
    public boolean update(Producto p) {
        boolean flag = false;
        PreparedStatement st;
        String query = null;

        try {

            query = "UPDATE producto SET nombre=?, descripcion=?, precio=?, imagen=?, estado=? "
                    + "WHERE id_producto=?";

            cn = ConexionSingleton.getConnection();

            st = cn.prepareStatement(query);

            st.setString(1, p.getNombre());
            st.setString(2, p.getDescripcion());
            st.setDouble(3, p.getPrecio());
            st.setString(4, p.getImagen());
            st.setString(5, p.getEstado().name());
            st.setInt(6, p.getId_producto());

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
                    System.out.println("Error al cerrar conexion");
                }
            }
        }

        return flag;

    }

    @Override
    public Producto SearchById(int id) {
        Producto pr = null;
        PreparedStatement st;
        ResultSet rs;
        String query = null;

        try {

            query = "SELECT * FROM producto WHERE id_producto=?";

            cn = ConexionSingleton.getConnection();

            st = cn.prepareStatement(query);

            st.setInt(1, id);
            rs = st.executeQuery();
            while (rs.next()) {
                pr = new Producto();
                pr.setId_producto(rs.getInt("id_producto"));
                pr.setNombre(rs.getString("nombre"));
                pr.setDescripcion(rs.getString("descripcion"));
                pr.setPrecio(rs.getDouble("precio"));
                pr.setImagen(rs.getString("imagen"));
               pr.setEstado(
                        EstadoProducto.valueOf(
                                rs.getString("estado").toUpperCase()
                        )    
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
                    System.out.println("Error al cerrar conexion");
                }
            }
        }

        return pr;
    }

    @Override
    public boolean delete(int id) {
        boolean flag = false;
        PreparedStatement st;
        String query = null;

        try {

            query = "DELETE FROM producto WHERE id_producto=?";

            cn = ConexionSingleton.getConnection();
            st = cn.prepareStatement(query);
            st.setInt(1, id);
            st.executeUpdate();

            flag = true;

        } catch (Exception e) {

            System.out.println("Error al eliminar producto " + e.getMessage());

            try {
                cn.rollback();
            } catch (Exception ex) {
            }

            flag = false;

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
    public boolean updateEstado(int id, EstadoProducto estado) {
        boolean flag = false;
        PreparedStatement st;
        String query = null;

        try {

            query = "UPDATE producto SET estado=? WHERE id_producto=?";

            cn = ConexionSingleton.getConnection();
            st = cn.prepareStatement(query);
            st.setString(1, estado.name());
            st.setInt(2, id);

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
