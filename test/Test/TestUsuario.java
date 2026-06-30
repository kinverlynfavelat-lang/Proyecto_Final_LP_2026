package Test;

import Dao.UsuarioDaoImpl;
import Enums.Rol;
import Interface.IUsuario;
import Model.Usuario;

public class TestUsuario {

    IUsuario dao = new UsuarioDaoImpl();

    public static void main(String[] args) {

        TestUsuario test = new TestUsuario();

        // Descomentar una prueba a la vez

        //test.registrar();

        test.iniciarSesion();

    }

    public void registrar() {

        Usuario usuario = new Usuario();

        usuario.setNombreCompleto("Mishel Goñas");
        usuario.setCorreo("mishel20@gmail.com");
        usuario.setPassword("admin123");
        usuario.setRol(Rol.ADMIN);

        boolean registrado = dao.registrar(usuario);

        if (registrado) {

            System.out.println("=================================");
            System.out.println("Usuario registrado correctamente");
            System.out.println("Nombre : " + usuario.getNombreCompleto());
            System.out.println("Correo : " + usuario.getCorreo());
            System.out.println("Rol    : " + usuario.getRol());
            System.out.println("=================================");

        } else {

            System.out.println("No se pudo registrar el usuario.");

        }

    }

    public void iniciarSesion() {

        Usuario usuario = dao.iniciarSesion(
                "mishel20@gmail.com",
                "admin123"
        );

        if (usuario != null) {

            System.out.println("=================================");
            System.out.println("Inicio de sesión correcto");
            System.out.println("ID      : " + usuario.getIdUsuario());
            System.out.println("Nombre  : " + usuario.getNombreCompleto());
            System.out.println("Correo  : " + usuario.getCorreo());
            System.out.println("Rol     : " + usuario.getRol());
            System.out.println("=================================");

        } else {

            System.out.println("Correo o contraseña incorrectos.");

        }

    }

}