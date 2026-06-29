package Test;

import Dao.ProductoDaoImpl;
import Enums.EstadoProducto;
import Interface.IProducto;
import Model.Producto;
import java.util.List;

public class TestProducto {

    public static IProducto dao = new ProductoDaoImpl();

    public static void main(String[] args) {

        TestProducto tP = new TestProducto();

        //tP.listar();
        tP.agregar();
        //tP.actualizar();
        //tP.buscarPorId();
        //tP.cambiarEstado();

    }

    public static void listar() {

        List<Producto> lista = dao.listar();

        if (lista != null && !lista.isEmpty()) {

            System.out.println("ID\tNombre\tPrecio\tEstado");

            for (Producto p : lista) {

                System.out.println(
                        p.getIdProducto()
                        + "\t" + p.getNombre()
                        + "\t" + p.getPrecio()
                        + "\t" + p.getEstadoProducto());

            }

        } else {

            System.out.println("NO HAY PRODUCTOS");

        }

    }

    public static void agregar() {

        Producto p = new Producto();

        p.setNombre("Cheese Burger");
        p.setDescripcion("Hamburguesa doble carne con queso");
        p.setPrecio(22.90);
        p.setImagen("img/cheeseburger.jpg");
        p.setEstadoProducto(EstadoProducto.ACTIVO);

        boolean result = dao.insertar(p);

        if (result) {

            System.out.println("PRODUCTO INSERTADO");

        } else {

            System.out.println("|ERROR| No se logró registrar");

        }

    }

    public static void actualizar() {

        Producto p = new Producto();

        p.setIdProducto(1);
        p.setNombre("Big Burger XL");
        p.setDescripcion("Hamburguesa doble carne especial");
        p.setPrecio(24.90);
        p.setImagen("img/bigburgerxl.jpg");
        p.setEstadoProducto(EstadoProducto.ACTIVO);

        boolean result = dao.actualizar(p);

        if (result) {

            System.out.println("PRODUCTO ACTUALIZADO");

        } else {

            System.out.println("|ERROR| No se logró actualizar");

        }

    }

    public static void buscarPorId() {

        Producto p = dao.buscarPorId(1);

        if (p != null) {

            System.out.println("PRODUCTO ENCONTRADO");
            System.out.println("ID: " + p.getIdProducto());
            System.out.println("Nombre: " + p.getNombre());
            System.out.println("Descripción: " + p.getDescripcion());
            System.out.println("Precio: " + p.getPrecio());
            System.out.println("Imagen: " + p.getImagen());
            System.out.println("Estado: " + p.getEstadoProducto());

        } else {

            System.out.println("|ERROR| No existe el producto.");

        }

    }

    public static void cambiarEstado() {

        boolean result = dao.cambiarEstado(1, EstadoProducto.INACTIVO);

        if (result) {

            System.out.println("ESTADO ACTUALIZADO");

        } else {

            System.out.println("|ERROR| No se logró actualizar el estado.");

        }

    }

}