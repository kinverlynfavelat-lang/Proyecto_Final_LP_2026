/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import Interface.IUsuario;
import Enums.Rol;
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
    public boolean registrar(Usuario usuario) {

        boolean registrado = false;

        PreparedStatement st = null;
        ResultSet rs = null;

        try {

            cn = ConexionSingleton.getConnection();

            // Verificar si el correo ya existe
            String verificar = "SELECT COUNT(*) FROM USUARIO WHERE correo = ?";

            st = cn.prepareStatement(verificar);
            st.setString(1, usuario.getCorreo());

            rs = st.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {

                System.out.println("El correo ya se encuentra registrado.");
                return false;

            }

            String sql = "INSERT INTO USUARIO(nombreCompleto, correo, password, rol) "
                    + "VALUES (?,?,?,?)";

            st = cn.prepareStatement(sql);

            st.setString(1, usuario.getNombreCompleto());
            st.setString(2, usuario.getCorreo());
            st.setString(3, usuario.HashPassword(usuario.getPassword()));
            st.setString(4, usuario.getRol().name());

            registrado = st.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println("Error al registrar usuario: " + e.getMessage());

        } finally {

            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
            } catch (SQLException e) {
            }

        }

        return registrado;
    }

    @Override
    public Usuario iniciarSesion(String correo, String password) {
        Usuario usuario = null;

        PreparedStatement st = null;
        ResultSet rs = null;

        try {

            cn = ConexionSingleton.getConnection();

            String sql = "SELECT * FROM USUARIO WHERE correo=? AND password=?";

            st = cn.prepareStatement(sql);

            st.setString(1, correo);

            Usuario aux = new Usuario();
            st.setString(2, aux.HashPassword(password));

            rs = st.executeQuery();

            if (rs.next()) {

                usuario = new Usuario();

                usuario.setIdUsuario(rs.getInt("idUsuario"));
                usuario.setNombreCompleto(rs.getString("nombreCompleto"));
                usuario.setCorreo(rs.getString("correo"));
                usuario.setRol(Rol.valueOf(rs.getString("rol")));

            }

        } catch (SQLException e) {

            System.out.println("Error al iniciar sesión: " + e.getMessage());

        } finally {

            try {
                if (rs != null) {
                    rs.close();
                }

                if (st != null) {
                    st.close();
                }

            } catch (SQLException e) {
            }

        }

        return usuario;
    }

}
