/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import Interface.IIngrediente;
import Model.Ingrediente;
import Util.ConexionSingleton;
import java.util.List;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author kinve
 */
public class IngredienteDaoImpl implements IIngrediente {

    private Connection cn;

    @Override
    public List<Ingrediente> listar() {
        List<Ingrediente> lista = null;
        Ingrediente ing;

        PreparedStatement st;
        ResultSet rs;

        String query = null;

        try {

            query = "SELECT idIngrediente, nombre, precioExtra "
                    + "FROM INGREDIENTE";

            lista = new ArrayList<>();

            cn = ConexionSingleton.getConnection();

            st = cn.prepareStatement(query);

            rs = st.executeQuery();

            while (rs.next()) {

                ing = new Ingrediente();

                ing.setIdIngrediente(rs.getInt("idIngrediente"));
                ing.setNombre(rs.getString("nombre"));
                ing.setPrecioExtra(rs.getDouble("precioExtra"));

                lista.add(ing);

            }

        } catch (Exception e) {

            System.out.println("Error al listar ingredientes " + e.getMessage());

            try {
                cn.rollback();
            } catch (Exception ex) {
            }

            System.out.println("No se pudieron listar los ingredientes");

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
    public boolean insertar(Ingrediente ingrediente) {
         boolean flag = false;

    PreparedStatement st;

    String query = null;

    try {

        query = "INSERT INTO INGREDIENTE(nombre, precioExtra) "
                + "VALUES(?,?)";

        cn = ConexionSingleton.getConnection();

        st = cn.prepareStatement(query);

        st.setString(1, ingrediente.getNombre());
        st.setDouble(2, ingrediente.getPrecioExtra());

        st.executeUpdate();

        flag = true;

    } catch (Exception e) {

        System.out.println("Error al insertar ingrediente " + e.getMessage());

        try {
            cn.rollback();
        } catch (Exception ex) {
        }

        flag = false;

        System.out.println("No se pudo insertar el ingrediente");

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
    public Ingrediente buscarPorId(int idIngrediente) {
         Ingrediente ing = null;

    PreparedStatement st;

    ResultSet rs;

    String query = null;

    try {

        query = "SELECT * FROM INGREDIENTE WHERE idIngrediente=?";

        cn = ConexionSingleton.getConnection();

        st = cn.prepareStatement(query);

        st.setInt(1, idIngrediente);

        rs = st.executeQuery();

        if (rs.next()) {

            ing = new Ingrediente();

            ing.setIdIngrediente(rs.getInt("idIngrediente"));
            ing.setNombre(rs.getString("nombre"));
            ing.setPrecioExtra(rs.getDouble("precioExtra"));

        }

    } catch (Exception e) {

        System.out.println("Error al buscar ingrediente " + e.getMessage());

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

    return ing;
    }

}
