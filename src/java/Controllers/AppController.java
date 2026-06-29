package Controllers;

import Dao.PedidoDaoImpl;
import Dao.ProductoDaoImpl;
import Enums.EstadoPedido;
import Enums.MetodoPago;
import Interface.IPedido;
import Interface.IProducto;
import Model.Carrito;
import Model.DetallePedido;
import Model.Pedido;
import Model.Producto;
import Model.Usuario;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "AppController", urlPatterns = {"/AppController"})
public class AppController extends HttpServlet {

    private final IProducto pDao = new ProductoDaoImpl();
    private final IPedido peDao = new PedidoDaoImpl();
    private final Gson gson = new Gson();

    protected void processRequest(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");

        JsonObject jsonResponse = new JsonObject();

        HttpSession session = request.getSession();

        List<Carrito> carrito
                = (List<Carrito>) session.getAttribute("carrito");

        if (carrito == null) {

            carrito = new ArrayList<>();

            session.setAttribute("carrito", carrito);

        }

        try (PrintWriter out = response.getWriter()) {

            if (action == null || action.trim().isEmpty()) {

                jsonResponse.addProperty("success", false);
                jsonResponse.addProperty("message", "Acción no válida");

                out.print(jsonResponse.toString());

                return;

            }

            switch (action) {

                case "listarProductos":
                    listarProductos(request, response);
                    break;

                case "agregarCarrito":
                    agregarCarrito(request, response, carrito);
                    break;

                case "listarCarrito":
                    listarCarrito(request, response, carrito);
                    break;

                case "actualizarCantidad":
                    actualizarCantidad(request, response, carrito);
                    break;

                case "quitarProducto":
                    quitarProducto(request, response, carrito);
                    break;

                case "limpiarCarrito":
                    limpiarCarrito(request, response, carrito);
                    break;

                case "generarPedido":

                    generarPedido(request, response, carrito, session);

                    break;
                case "historialPedidos":

                    historialPedidos(request, response, session);

                    break;
                case "detallePedido":

                    detallePedido(request, response);

                    break;

                default:
                    jsonResponse.addProperty("success", false);
                    jsonResponse.addProperty("message", "Acción no encontrada");
                    out.print(jsonResponse.toString());
                    break;
            }
        }

    }

    private void listarProductos(HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        JsonObject jsonResponse = new JsonObject();

        try {

            List<Producto> lista = pDao.listar();

            jsonResponse.addProperty("success", true);

            jsonResponse.add("data",
                    gson.toJsonTree(lista));

        } catch (Exception e) {

            jsonResponse.addProperty("success", false);
            jsonResponse.addProperty("message",
                    "Error al listar productos");

        }

        response.getWriter().print(jsonResponse.toString());

    }

    private void agregarCarrito(HttpServletRequest request,
            HttpServletResponse response,
            List<Carrito> carrito) throws IOException {

        JsonObject jsonResponse = new JsonObject();

        try {

            int idProducto
                    = Integer.parseInt(request.getParameter("idProducto"));

            Producto producto
                    = pDao.buscarPorId(idProducto);

            if (producto == null) {

                jsonResponse.addProperty("success", false);
                jsonResponse.addProperty("message",
                        "Producto no encontrado");

                response.getWriter().print(jsonResponse.toString());

                return;

            }

            boolean existe = false;

            for (Carrito item : carrito) {

                if (item.getProducto().getIdProducto()
                        == idProducto) {

                    item.setCantidad(item.getCantidad() + 1);

                    item.setSubTotal(
                            item.getCantidad()
                            * item.getPrecioCompra());

                    existe = true;

                    break;

                }

            }

            if (!existe) {

                Carrito item = new Carrito();

                item.setProducto(producto);
                item.setCantidad(1);
                item.setPrecioCompra(producto.getPrecio());
                item.setSubTotal(producto.getPrecio());

                carrito.add(item);

            }

            jsonResponse.addProperty("success", true);
            jsonResponse.addProperty("message",
                    "Producto agregado al carrito");

            jsonResponse.addProperty("cantidadCarrito",
                    carrito.size());

        } catch (Exception e) {

            jsonResponse.addProperty("success", false);
            jsonResponse.addProperty("message",
                    "Error: " + e.getMessage());

        }

        response.getWriter().print(jsonResponse.toString());

    }

    private void listarCarrito(HttpServletRequest request,
            HttpServletResponse response,
            List<Carrito> carrito) throws IOException {

        JsonObject jsonResponse = new JsonObject();

        try {

            double total = 0;

            for (Carrito item : carrito) {

                total += item.getSubTotal();

            }

            jsonResponse.addProperty("success", true);
            jsonResponse.add("carrito", gson.toJsonTree(carrito));
            jsonResponse.addProperty("total", total);

        } catch (Exception e) {

            jsonResponse.addProperty("success", false);
            jsonResponse.addProperty("message",
                    "Error al listar el carrito");

        }

        response.getWriter().print(jsonResponse.toString());

    }

    private void actualizarCantidad(HttpServletRequest request,
            HttpServletResponse response,
            List<Carrito> carrito) throws IOException {

        JsonObject jsonResponse = new JsonObject();

        try {

            int idProducto
                    = Integer.parseInt(request.getParameter("idProducto"));

            int cantidad
                    = Integer.parseInt(request.getParameter("cantidad"));

            if (cantidad <= 0) {

                jsonResponse.addProperty("success", false);
                jsonResponse.addProperty("message",
                        "Cantidad inválida");

                response.getWriter().print(jsonResponse.toString());

                return;

            }

            for (Carrito item : carrito) {

                if (item.getProducto().getIdProducto() == idProducto) {

                    item.setCantidad(cantidad);

                    item.setSubTotal(
                            cantidad * item.getPrecioCompra());

                    break;

                }

            }

            jsonResponse.addProperty("success", true);
            jsonResponse.addProperty("message",
                    "Cantidad actualizada");

        } catch (Exception e) {

            jsonResponse.addProperty("success", false);
            jsonResponse.addProperty("message",
                    e.getMessage());

        }

        response.getWriter().print(jsonResponse.toString());

    }

    private void quitarProducto(HttpServletRequest request,
            HttpServletResponse response,
            List<Carrito> carrito) throws IOException {

        JsonObject jsonResponse = new JsonObject();

        try {

            int idProducto
                    = Integer.parseInt(request.getParameter("idProducto"));

            carrito.removeIf(item
                    -> item.getProducto().getIdProducto() == idProducto);

            jsonResponse.addProperty("success", true);
            jsonResponse.addProperty("message",
                    "Producto eliminado del carrito");

        } catch (Exception e) {

            jsonResponse.addProperty("success", false);
            jsonResponse.addProperty("message",
                    e.getMessage());

        }

        response.getWriter().print(jsonResponse.toString());

    }

    private void limpiarCarrito(HttpServletRequest request,
            HttpServletResponse response,
            List<Carrito> carrito) throws IOException {

        JsonObject jsonResponse = new JsonObject();

        carrito.clear();

        jsonResponse.addProperty("success", true);
        jsonResponse.addProperty("message",
                "Carrito limpiado correctamente");

        response.getWriter().print(jsonResponse.toString());

    }

    private void generarPedido(HttpServletRequest request,
            HttpServletResponse response,
            List<Carrito> carrito,
            HttpSession session) throws IOException {

        JsonObject jsonResponse = new JsonObject();

        try {

            Usuario usuario = (Usuario) session.getAttribute("usuario");

            if (usuario == null) {

                jsonResponse.addProperty("success", false);
                jsonResponse.addProperty("message",
                        "Debe iniciar sesión para realizar el pedido");

                response.getWriter().print(jsonResponse.toString());
                return;

            }

            if (carrito == null || carrito.isEmpty()) {

                jsonResponse.addProperty("success", false);
                jsonResponse.addProperty("message",
                        "El carrito está vacío");

                response.getWriter().print(jsonResponse.toString());
                return;

            }

            Pedido pedido = new Pedido();

            pedido.setCodigo("PED-" + System.currentTimeMillis());

            pedido.setUsuario(usuario);

            pedido.setNombreCliente(
                    request.getParameter("nombreCliente"));

            pedido.setDni(
                    request.getParameter("dni"));

            pedido.setTelefono(
                    request.getParameter("telefono"));

            pedido.setDireccionEntrega(
                    request.getParameter("direccionEntrega"));

            pedido.setMetodoPago(
                    MetodoPago.valueOf(
                            request.getParameter("metodoPago"))
            );

            pedido.setEstadoPedido(
                    EstadoPedido.LISTO
            );

            pedido.setDetallePedido(carrito);
            double total = 0;

            for (Carrito item : carrito) {

                total += item.getSubTotal();

            }

            pedido.setTotal(total);

            int idPedido = peDao.generarPedido(pedido);

            if (idPedido > 0) {

                carrito.clear();

                session.setAttribute("carrito", carrito);

                jsonResponse.addProperty("success", true);
                jsonResponse.addProperty("message",
                        "Pedido generado correctamente");

                jsonResponse.addProperty("idPedido", idPedido);

                jsonResponse.addProperty("codigo",
                        pedido.getCodigo());

            } else {

                jsonResponse.addProperty("success", false);
                jsonResponse.addProperty("message",
                        "No fue posible generar el pedido");

            }

        } catch (IllegalArgumentException e) {

            jsonResponse.addProperty("success", false);
            jsonResponse.addProperty("message",
                    "Método de pago inválido");

        } catch (Exception e) {

            jsonResponse.addProperty("success", false);
            jsonResponse.addProperty("message",
                    "Error: " + e.getMessage());

        }

        response.getWriter().print(jsonResponse.toString());

    }

    private void historialPedidos(HttpServletRequest request,
            HttpServletResponse response,
            HttpSession session) throws IOException {

        JsonObject jsonResponse = new JsonObject();

        try {

            Usuario usuario = (Usuario) session.getAttribute("usuario");

            if (usuario == null) {

                jsonResponse.addProperty("success", false);
                jsonResponse.addProperty("message",
                        "Debe iniciar sesión");

                response.getWriter().print(jsonResponse.toString());

                return;

            }

            List<Pedido> lista = peDao.historialCliente(
                    usuario.getIdUsuario());

            JsonArray pedidos = new JsonArray();

            for (Pedido p : lista) {

                JsonObject obj = new JsonObject();

                obj.addProperty("idPedido", p.getIdPedido());
                obj.addProperty("codigo", p.getCodigo());
                obj.addProperty("fecha", p.getFecha().toString());
                obj.addProperty("estadoPedido", p.getEstadoPedido().name());
                obj.addProperty("metodoPago", p.getMetodoPago().name());
                obj.addProperty("total", p.getTotal());

                pedidos.add(obj);

            }

            jsonResponse.addProperty("success", true);
            jsonResponse.add("data", pedidos);

        } catch (Exception e) {

            jsonResponse.addProperty("success", false);

            jsonResponse.addProperty("message",
                    e.getMessage());

        }

        response.getWriter().print(jsonResponse.toString());

    }

    private void detallePedido(HttpServletRequest request,
        HttpServletResponse response) throws IOException {

    JsonObject jsonResponse = new JsonObject();

    try {

        int idPedido = Integer.parseInt(
                request.getParameter("idPedido"));

        Pedido pedido = peDao.buscarPorId(idPedido);

        if (pedido != null) {

            JsonObject data = new JsonObject();

            //========================
            // Datos del pedido
            //========================
            data.addProperty("idPedido", pedido.getIdPedido());
            data.addProperty("codigo", pedido.getCodigo());
            data.addProperty("fecha", pedido.getFecha().toString());
            data.addProperty("estadoPedido",
                    pedido.getEstadoPedido().name());
            data.addProperty("metodoPago",
                    pedido.getMetodoPago().name());
            data.addProperty("nombreCliente",
                    pedido.getNombreCliente());
            data.addProperty("dni",
                    pedido.getDni());
            data.addProperty("telefono",
                    pedido.getTelefono());
            data.addProperty("direccionEntrega",
                    pedido.getDireccionEntrega());
            data.addProperty("total",
                    pedido.getTotal());

            //========================
            // Detalle del pedido
            //========================
            JsonArray detalles = new JsonArray();

            for (DetallePedido d : pedido.getDetalles()) {

                JsonObject det = new JsonObject();

                det.addProperty("idDetalle",
                        d.getIdDetalleP());

                det.addProperty("producto",
                        d.getProducto().getNombre());

                det.addProperty("cantidad",
                        d.getCantidad());

                det.addProperty("precioUnitario",
                        d.getPrecioUnitario());

                det.addProperty("subTotal",
                        d.getSubTotal());

                detalles.add(det);

            }

            data.add("detalles", detalles);

            jsonResponse.addProperty("success", true);
            jsonResponse.add("data", data);

        } else {

            jsonResponse.addProperty("success", false);
            jsonResponse.addProperty("message",
                    "Pedido no encontrado");

        }

    } catch (NumberFormatException e) {

        jsonResponse.addProperty("success", false);
        jsonResponse.addProperty("message",
                "ID de pedido inválido");

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

        return "AppController BurgerBuilder";

    }
}
