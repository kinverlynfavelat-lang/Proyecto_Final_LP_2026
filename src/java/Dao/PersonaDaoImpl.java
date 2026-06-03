/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import Interface.IPersona;
import Model.Persona;
import Model.Rol;
import Model.Usuario;
import Util.ConexionSingleton;
import java.util.List;
import java.sql.*;

/**
 *
 * @author kinve
 */
public class PersonaDaoImpl implements IPersona {

    private Connection cn;

    @Override
    public List<Persona> lista() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int insert(Persona p, Usuario u) {
        PreparedStatement st;
        String query = null;
        ResultSet rs;
        int id_persona = 0;
        int r = 0;

        try {
            query = "INSERT INTO persona(nombre,dni,telefono,email,direccion) "
                    + "VALUES (?, ?, ?, ?, ?)";
            cn = ConexionSingleton.getConnection();
            st = cn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            st.setString(1, p.getNombre());
            st.setString(2, p.getDni());
            st.setString(3, p.getTelefono());
            st.setString(4, p.getEmail());
            st.setString(5, p.getDireccion());
            r = st.executeUpdate();
            if (r != 0) {
                rs = st.getGeneratedKeys();
                if (rs.next()) {
                    // linea que devuelve el id de la persona creada
                    id_persona = rs.getInt(1);
                    System.out.println("id_recuperado" + id_persona);
                }
                if (id_persona > 0) {
                    u.setRol(Rol.CLIENTE);
                    String hashedPassword = u.HashPassword(u.getPassword());
                    query = "INSERT INTO usuario(usuario,password,rol,id_persona) "
                            + "VALUES (?,?,?,?)";
                    st = cn.prepareStatement(query);
                    st.setString(1, p.getEmail());
                    st.setString(2, hashedPassword);
                    st.setString(3, u.getRol().name());
                    st.setInt(4, id_persona);
                    r = st.executeUpdate();
                } else {
                    System.out.println("Error al agregar una persona");
                }
            }
        } catch (Exception e) {
            System.out.println("Error al agregar" + e.getMessage());
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
        return r;
    }

    @Override
    public boolean update(Persona p) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Persona SearchById(int id) {

        Persona p = null;

        PreparedStatement st;
        ResultSet rs;
        String query = null;
        try {

            query = "SELECT * FROM persona WHERE id_persona=?";

            cn = ConexionSingleton.getConnection();

            st = cn.prepareStatement(query);

            st.setInt(1, id);

            rs = st.executeQuery();

            while (rs.next()) {

                p = new Persona();

                p.setId_persona(rs.getInt("id_persona"));
                p.setNombre(rs.getString("nombre"));
                p.setDni(rs.getString("dni"));
                p.setTelefono(rs.getString("telefono"));
                p.setEmail(rs.getString("email"));
                p.setDireccion(rs.getString("direccion"));

            }

        } catch (Exception e) {
            System.out.println("Error al agregar" + e.getMessage());
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
        return p;
    }

    @Override
    public boolean delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
