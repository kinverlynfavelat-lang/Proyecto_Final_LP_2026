/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Test;

import Dao.ProductoDaoImpl;
import Interface.IProducto;
import Model.EstadoProducto;
import Model.Producto;
import java.util.List;

/**
 *
 * @author kinve
 */
public class TestProducto {

    public static IProducto dao = new ProductoDaoImpl();

    public static void main(String[] args) {
        TestProducto tP = new TestProducto();

        //tP.listar();
        //tP.agregar();
        //tP.actualizar();
        //tP.SearchByID();
        //tP.eliminar();
        tP.cambiarEstado();
    }

    public static void listar() {
        List<Producto> Lista = dao.lista();

        if (Lista != null && !Lista.isEmpty()) {
            System.out.println("ID\tNombre\tPrecio\tStock");
            for (Producto ps : Lista) {
                System.out.println(ps.getId_producto()
                        + "\t" + ps.getNombre() + "\t"
                        + ps.getPrecio() + "\t" + ps.getEstado());

            }
        } else {
            System.out.println(" NO HAY PRODUCTOS ");
        }

    }

    public static void agregar() {
        Producto p = new Producto();

        p.setNombre("Big Burger");
        p.setDescripcion("Hamburguesa doble carne con queso");
        p.setPrecio(22.90);
        p.setImagen("img/bigburger.jpg");
        p.setEstado(EstadoProducto.ACTIVO);

        boolean result = dao.insert(p);

        if (result) {
            System.out.println(" PRODUCTO INSERTADO");
        } else {
            System.out.println(" |ERROR| No sé logró registrar");

        }
    }

    public static void actualizar() {
        Producto p = new Producto();
        
         p.setNombre("Big Burger XL");
        p.setDescripcion("Hamburguesa doble carne especial");
        p.setPrecio(24.90);
        p.setImagen("img/bigburgerxl.jpg");
        p.setEstado(EstadoProducto.ACTIVO);
        p.setId_producto(1);

        boolean result = dao.update(p);
        if (result) {
            System.out.println(" PRODUCTO ACTUALIZADO");
        } else {
            System.out.println(" |ERROR| No sé logró actualizar");

        }
    }

    public static void SearchByID() {
        Producto pr = dao.SearchById(1);

        if (pr != null) {
            System.out.println("PRODUCTOS ENCONTRADOS");
            System.out.println("ID:" + pr.getId_producto());
            System.out.println("Nombre:" + pr.getNombre());
            System.out.println("Descripcion:" + pr.getDescripcion());
            System.out.println("Precio:" + pr.getPrecio());
            System.out.println("Ruta Img:" + pr.getImagen());
            System.out.println("Estado:" + pr.getEstado());
        } else {
            System.out.println("|ERROR| No hay registros");
        }

    }

    public static void eliminar() {
        boolean result = dao.delete(1);
        if (result) {
            System.out.println("PRODUCTO ELIMINADO");
        } else {
            System.out.println(" |ERROR| No se logró eliminar");
        }
    }

    public static void cambiarEstado() {
        boolean result = dao.updateEstado(1, EstadoProducto.INACTIVO);
        if (result) {
            System.out.println(" ESTADO ACTUALIZADO");
        } else {
            System.out.println(" |ERROR| No sé logró actualizar el estado");
        }
    }
}
