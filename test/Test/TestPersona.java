/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Test;

import Dao.PersonaDaoImpl;
import Dao.UsuarioDaoImpl;
import Interface.IPersona;
import Interface.IUsuario;
import Model.Persona;
import Model.Usuario;

/**
 *
 * @author kinve
 */
public class TestPersona {

    IPersona dao = new PersonaDaoImpl();
    IUsuario Udao = new UsuarioDaoImpl();

    public static void main(String[] args) {
        TestPersona test = new TestPersona();
        //test.insert();
        test.valiUser();
    }

    public void insert() {
        Persona p = new Persona();

        p.setNombre("Favela Segura");
        p.setDni("60270510");
        p.setTelefono("901400779");
        p.setEmail("favela@gmail.com");
        p.setDireccion("Por la plaza");

        Usuario u = new Usuario();
        u.setPassword("favela123");
        
        int result = dao.insert(p, u);
        if (result > 0) {
            System.out.println("Persona y usuario creada");
            System.out.println("Usuario:" + p.getEmail());
            System.out.println("Rol asignado:" + u.getRol());
        } else {
            System.out.println("No se pudo realizar el registro");
        }
    }

    public void valiUser() {
        Usuario u = Udao.validate("favela@gmail.com", "favela123");
        if (u != null && u.getPersona() != null) {
            System.out.println("Bienvenido " + u.getPersona().getNombre());
            System.out.println("Rol:" + u.getRol());
            System.out.println("Usuario:" + u.getUsuario());
            System.out.println("User_id:" + u.getId_usuario());
            System.out.println("Persona_id:" + u.getPersona().getId_persona());
        } else {
            System.out.println("Credenciales incorrectas");
        }
    }
}
