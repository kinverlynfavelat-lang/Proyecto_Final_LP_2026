package Controllers;

import Dao.ProductoDaoImpl;
import Enums.EstadoProducto;
import Interface.IProducto;
import Model.Producto;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.File;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@MultipartConfig
@WebServlet(name = "ProductoController", urlPatterns = {"/ProductoController"})
public class ProductoController extends HttpServlet {

    private final IProducto pDao = new ProductoDaoImpl();
    private final Gson gson = new Gson();

    protected void processRequest(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");

        if (action == null || action.trim().isEmpty()) {
            action = "listar";
        }

        switch (action) {

            case "guardar":
                guardarProducto(request, response);
                break;

            case "editar":
                editarProducto(request, response);
                break;

            case "buscar":
                buscarProducto(request, response);
                break;

            case "cambiarEstado":
                cambiarEstado(request, response);
                break;

            default:
                listarProductos(request, response);
                break;
        }
    }

    //=========================================
    // LISTAR PRODUCTOS
    //=========================================
    private void listarProductos(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        JsonObject jsonResponse = new JsonObject();

        try {

            List<Producto> lista = pDao.listar();

            jsonResponse.addProperty("success", true);
            jsonResponse.add("data", gson.toJsonTree(lista));

        } catch (Exception e) {

            jsonResponse.addProperty("success", false);
            jsonResponse.addProperty("message",
                    "Error al listar productos.");

        }

        response.getWriter().print(jsonResponse.toString());

    }
        //=========================================
    // GUARDAR PRODUCTO
    //=========================================
    private void guardarProducto(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        JsonObject jsonResponse = new JsonObject();

        try {

            Producto producto = new Producto();

            String nombre = request.getParameter("nombre");
            String descripcion = request.getParameter("descripcion");
            String precio = request.getParameter("precio");

            //==============================
            // VALIDACIONES
            //==============================

            if (nombre == null || nombre.trim().isEmpty()) {

                jsonResponse.addProperty("success", false);
                jsonResponse.addProperty("message", "Ingrese el nombre del producto.");

                response.getWriter().print(jsonResponse.toString());
                return;

            }

            if (descripcion == null || descripcion.trim().isEmpty()) {

                jsonResponse.addProperty("success", false);
                jsonResponse.addProperty("message", "Ingrese la descripción.");

                response.getWriter().print(jsonResponse.toString());
                return;

            }

            if (precio == null || precio.trim().isEmpty()) {

                jsonResponse.addProperty("success", false);
                jsonResponse.addProperty("message", "Ingrese el precio.");

                response.getWriter().print(jsonResponse.toString());
                return;

            }

            double precioProducto = Double.parseDouble(precio);

            if (precioProducto <= 0) {

                jsonResponse.addProperty("success", false);
                jsonResponse.addProperty("message", "El precio debe ser mayor a cero.");

                response.getWriter().print(jsonResponse.toString());
                return;

            }

            producto.setNombre(nombre.trim());
            producto.setDescripcion(descripcion.trim());
            producto.setPrecio(precioProducto);

            //==============================
            // IMAGEN
            //==============================

            Part part = request.getPart("imagen");

            if (part == null || part.getSize() == 0) {

                jsonResponse.addProperty("success", false);
                jsonResponse.addProperty("message", "Debe seleccionar una imagen.");

                response.getWriter().print(jsonResponse.toString());
                return;

            }

            String fileName = part.getSubmittedFileName();

            String uploadPath = getServletContext().getRealPath("/")
                    + "assets/img/productos";

            File carpeta = new File(uploadPath);

            if (!carpeta.exists()) {
                carpeta.mkdirs();
            }

            part.write(uploadPath + File.separator + fileName);

            producto.setImagen("assets/img/productos/" + fileName);

            // Al registrar siempre inicia ACTIVO
            producto.setEstadoProducto(EstadoProducto.ACTIVO);

            boolean registrado = pDao.insertar(producto);

            jsonResponse.addProperty("success", registrado);

            if (registrado) {

                jsonResponse.addProperty("message",
                        "Producto registrado correctamente.");

            } else {

                jsonResponse.addProperty("message",
                        "No fue posible registrar el producto.");

            }

        } catch (NumberFormatException e) {

            jsonResponse.addProperty("success", false);
            jsonResponse.addProperty("message",
                    "El precio debe ser un número válido.");

        } catch (Exception e) {

            jsonResponse.addProperty("success", false);
            jsonResponse.addProperty("message",
                    "Error: " + e.getMessage());

        }

        response.getWriter().print(jsonResponse.toString());

    }
    private void editarProducto(HttpServletRequest request,
        HttpServletResponse response) throws IOException {

    JsonObject jsonResponse = new JsonObject();

    try {

        int idProducto = Integer.parseInt(request.getParameter("idProducto"));

        Producto producto = pDao.buscarPorId(idProducto);

        if (producto == null) {

            jsonResponse.addProperty("success", false);
            jsonResponse.addProperty("message", "Producto no encontrado");

            response.getWriter().print(jsonResponse.toString());
            return;

        }

        String nombre = request.getParameter("nombre");
        String descripcion = request.getParameter("descripcion");
        String precio = request.getParameter("precio");

        if (nombre == null || nombre.trim().isEmpty()) {

            jsonResponse.addProperty("success", false);
            jsonResponse.addProperty("message", "El nombre es obligatorio");

            response.getWriter().print(jsonResponse.toString());
            return;

        }

        if (descripcion == null || descripcion.trim().isEmpty()) {

            jsonResponse.addProperty("success", false);
            jsonResponse.addProperty("message", "La descripción es obligatoria");

            response.getWriter().print(jsonResponse.toString());
            return;

        }

        if (precio == null || precio.trim().isEmpty()) {

            jsonResponse.addProperty("success", false);
            jsonResponse.addProperty("message", "El precio es obligatorio");

            response.getWriter().print(jsonResponse.toString());
            return;

        }

        double precioProducto = Double.parseDouble(precio);

        if (precioProducto <= 0) {

            jsonResponse.addProperty("success", false);
            jsonResponse.addProperty("message", "El precio debe ser mayor que cero");

            response.getWriter().print(jsonResponse.toString());
            return;

        }

        producto.setNombre(nombre.trim());
        producto.setDescripcion(descripcion.trim());
        producto.setPrecio(precioProducto);

        Part part = request.getPart("imagen");

        if (part != null && part.getSize() > 0) {

            String fileName = part.getSubmittedFileName();

            String uploadPath = getServletContext().getRealPath("/")
                    + "assets/img/productos";

            File carpeta = new File(uploadPath);

            if (!carpeta.exists()) {
                carpeta.mkdirs();
            }

            part.write(uploadPath + File.separator + fileName);

            producto.setImagen("assets/img/productos/" + fileName);

        }

        boolean actualizado = pDao.actualizar(producto);

        jsonResponse.addProperty("success", actualizado);
        jsonResponse.addProperty("message",
                actualizado
                ? "Producto actualizado correctamente"
                : "No fue posible actualizar el producto");

    } catch (NumberFormatException e) {

        jsonResponse.addProperty("success", false);
        jsonResponse.addProperty("message", "Precio inválido");

    } catch (Exception e) {

        jsonResponse.addProperty("success", false);
        jsonResponse.addProperty("message", "Error: " + e.getMessage());

    }

    response.getWriter().print(jsonResponse.toString());

}
        private void buscarProducto(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        JsonObject jsonResponse = new JsonObject();

        try {

            int idProducto = Integer.parseInt(request.getParameter("idProducto"));

            Producto producto = pDao.buscarPorId(idProducto);

            if (producto != null) {

                response.getWriter().print(gson.toJson(producto));

            } else {

                jsonResponse.addProperty("success", false);
                jsonResponse.addProperty("message", "Producto no encontrado");

                response.getWriter().print(jsonResponse.toString());

            }

        } catch (Exception e) {

            jsonResponse.addProperty("success", false);
            jsonResponse.addProperty("message",
                    "Error: " + e.getMessage());

            response.getWriter().print(jsonResponse.toString());

        }

    }

    private void cambiarEstado(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        JsonObject jsonResponse = new JsonObject();

        try {

            int idProducto = Integer.parseInt(request.getParameter("idProducto"));

            EstadoProducto estado = EstadoProducto.valueOf(
                    request.getParameter("estadoProducto").toUpperCase()
            );

            boolean cambiado = pDao.cambiarEstado(idProducto, estado);

            jsonResponse.addProperty("success", cambiado);

            if (cambiado) {

                jsonResponse.addProperty("message",
                        "Estado del producto actualizado correctamente");

            } else {

                jsonResponse.addProperty("message",
                        "No fue posible actualizar el estado");

            }

        } catch (Exception e) {

            jsonResponse.addProperty("success", false);
            jsonResponse.addProperty("message",
                    "Error: " + e.getMessage());

        }

        response.getWriter().print(jsonResponse.toString());

    }

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        processRequest(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        processRequest(request, response);

    }

    @Override
    public String getServletInfo() {

        return "ProductoController BurgerBuilder";

    }

}