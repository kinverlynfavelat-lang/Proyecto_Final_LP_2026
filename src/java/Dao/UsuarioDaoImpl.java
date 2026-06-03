/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import Interface.IUsuario;
import Model.Persona;
import Model.Rol;
import Model.Usuario;
import Util.ConexionSingleton;
import java.sql.*;

/**
 *
 * @author kinve
 */
public class UsuarioDaoImpl implements IUsuario {

    private Connection cn;

    @Override
    public Usuario validate(String user, String passw) {
        Usuario u = null;
        Persona p = null;

        PreparedStatement st;
        ResultSet rs;
        String query = null;

        try {
            u = new Usuario();
            p = new Persona();
            String hashedPassword = u.HashPassword(passw);
            query = "SELECT u.id_usuario, u.usuario, u.rol, "
                    + "p.id_persona, p.nombre, p.email "
                    + "FROM persona p, usuario u "
                    + "WHERE p.id_persona = u.id_persona "
                    + "AND u.usuario = ? "
                    + "AND u.password = ?";
            cn = ConexionSingleton.getConnection();
            st = cn.prepareStatement(query);
            st.setString(1, user);
            st.setString(2, hashedPassword);
            rs = st.executeQuery();
            while (rs.next()) {
                u = new Usuario();
                u.setId_usuario(rs.getInt("id_usuario"));
                u.setUsuario(rs.getString("usuario"));
                u.setRol(Rol.valueOf(rs.getString("rol").toUpperCase()));
                p.setId_persona(rs.getInt("id_persona"));
                p.setNombre(rs.getString("nombre"));
                p.setEmail(rs.getString("email"));
                u.setPersona(p);
            }

        } catch (Exception e) {
            System.out.println("Error al validar usuario" + e.getMessage());
            try {
                cn.rollback();
            } catch (Exception ex) {
            }
            System.out.println("No se pudo validar el usuario");
        } finally {
            if (cn != null) {
                try {

                } catch (Exception ex) {
                }
            }
        }
        return u;
    }

}
