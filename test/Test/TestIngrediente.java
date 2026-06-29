package Test;

import Dao.IngredienteDaoImpl;
import Interface.IIngrediente;
import Model.Ingrediente;
import java.util.List;

public class TestIngrediente {

    public static IIngrediente dao = new IngredienteDaoImpl();

    public static void main(String[] args) {

        TestIngrediente tI = new TestIngrediente();

        tI.listar();
        //tI.agregar();
        //tI.buscarPorId();

    }

    public static void listar() {

        List<Ingrediente> lista = dao.listar();

        if (lista != null && !lista.isEmpty()) {

            System.out.println("ID\tNombre\tPrecio Extra");

            for (Ingrediente i : lista) {

                System.out.println(
                        i.getIdIngrediente()
                        + "\t" + i.getNombre()
                        + "\t" + i.getPrecioExtra());

            }

        } else {

            System.out.println("NO HAY INGREDIENTES");

        }

    }

    public static void agregar() {

        Ingrediente i = new Ingrediente();

        i.setNombre("Queso");
        i.setPrecioExtra(2.50);

        boolean result = dao.insertar(i);

        if (result) {

            System.out.println("INGREDIENTE INSERTADO");

        } else {

            System.out.println("|ERROR| No se logró registrar.");

        }

    }

    public static void buscarPorId() {

        Ingrediente i = dao.buscarPorId(1);

        if (i != null) {

            System.out.println("INGREDIENTE ENCONTRADO");
            System.out.println("ID: " + i.getIdIngrediente());
            System.out.println("Nombre: " + i.getNombre());
            System.out.println("Precio Extra: " + i.getPrecioExtra());

        } else {

            System.out.println("|ERROR| No existe el ingrediente.");

        }

    }

}