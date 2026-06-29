package Controllers;

import Dao.PagoDaoImpl;
import Enums.EstadoPago;
import Enums.MetodoPago;
import Interface.IPago;
import Model.Pago;
import Model.Pedido;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
@MultipartConfig
@WebServlet(name = "PagoController", urlPatterns = {"/PagoController"})
public class PagoController extends HttpServlet {

    private final IPago paDao = new PagoDaoImpl();
    private final Gson gson = new Gson();

    protected void processRequest(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");

        JsonObject jsonResponse = new JsonObject();

        try (PrintWriter out = response.getWriter()) {

            if (action == null || action.trim().isEmpty()) {

                jsonResponse.addProperty("success", false);
                jsonResponse.addProperty("message", "Acción no válida");

                out.print(jsonResponse.toString());

                return;

            }

            switch (action) {

                case "registrarPago":

                    registrarPago(request, response);

                    break;

                case "buscarPago":

                    buscarPago(request, response);

                    break;

                case "validarPago":

                    validarPago(request, response);

                    break;

                default:

                    jsonResponse.addProperty("success", false);
                    jsonResponse.addProperty("message", "Acción no encontrada");

                    out.print(jsonResponse.toString());

                    break;

            }

        }

    }

    private void registrarPago(HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        JsonObject jsonResponse = new JsonObject();

        try {

            Pago pago = new Pago();

            pago.setMetodo(
                    MetodoPago.valueOf(
                            request.getParameter("metodo"))
            );

            Part part = request.getPart("comprobante");

            if (part == null || part.getSize() == 0) {

                jsonResponse.addProperty("success", false);
                jsonResponse.addProperty("message",
                        "Debe seleccionar un comprobante.");

                response.getWriter().print(jsonResponse.toString());
                return;

            }

            String fileName = part.getSubmittedFileName();

            String uploadPath = getServletContext().getRealPath("/")
                    + "assets/img/comprobantes";

            File carpeta = new File(uploadPath);

            if (!carpeta.exists()) {

                carpeta.mkdirs();

            }

            part.write(uploadPath + File.separator + fileName);

            pago.setComprobante("assets/img/comprobantes/" + fileName);

            pago.setEstadoPago(
                    EstadoPago.PENDIENTE);

            Pedido pedido = new Pedido();

            pedido.setIdPedido(
                    Integer.parseInt(
                            request.getParameter("idPedido")));

            pago.setPedido(pedido);

            boolean ok = paDao.registrarPago(pago);

            jsonResponse.addProperty("success", ok);

            jsonResponse.addProperty("message",
                    ok
                            ? "Pago registrado correctamente"
                            : "No fue posible registrar el pago");

        } catch (Exception e) {

            jsonResponse.addProperty("success", false);

            jsonResponse.addProperty("message",
                    e.getMessage());

        }

        response.getWriter().print(jsonResponse.toString());

    }

    private void buscarPago(HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        JsonObject jsonResponse = new JsonObject();

        try {

            int idPedido = Integer.parseInt(
                    request.getParameter("idPedido"));

            Pago pago = paDao.buscarPorPedido(idPedido);

            if (pago != null) {

                JsonObject data = new JsonObject();

                data.addProperty("idPago", pago.getIdPago());
                data.addProperty("metodo", pago.getMetodo().name());
                data.addProperty("comprobante", pago.getComprobante());
                data.addProperty("estadoPago", pago.getEstadoPago().name());

                if (pago.getFechaPago() != null) {
                    data.addProperty("fechaPago",
                            pago.getFechaPago().toString());
                }

                data.addProperty("idPedido",
                        pago.getPedido().getIdPedido());

                jsonResponse.addProperty("success", true);
                jsonResponse.add("data", data);

            } else {

                jsonResponse.addProperty("success", false);
                jsonResponse.addProperty("message",
                        "No existe un pago para este pedido");

            }

        } catch (Exception e) {

            jsonResponse.addProperty("success", false);
            jsonResponse.addProperty("message",
                    e.getMessage());

        }

        response.getWriter().print(jsonResponse.toString());

    }

    private void validarPago(HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        JsonObject jsonResponse = new JsonObject();

        try {

            int idPago = Integer.parseInt(
                    request.getParameter("idPago"));

            EstadoPago estado = EstadoPago.valueOf(
                    request.getParameter("estado"));

            boolean ok = paDao.validarPago(idPago, estado);

            jsonResponse.addProperty("success", ok);

            jsonResponse.addProperty("message",
                    ok
                            ? "Estado del pago actualizado correctamente"
                            : "No fue posible actualizar el pago");

        } catch (Exception e) {

            jsonResponse.addProperty("success", false);

            jsonResponse.addProperty("message",
                    e.getMessage());

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

}
