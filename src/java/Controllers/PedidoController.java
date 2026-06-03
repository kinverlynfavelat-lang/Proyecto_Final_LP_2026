/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import Dao.PedidoDaoImpl;
import Interface.IPedido;
import Model.EstadoPedido;
import Model.Pedido;
import com.google.gson.Gson;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @author kinve
 */
@WebServlet(name = "PedidoController", urlPatterns = {"/PedidoController"})
public class PedidoController extends HttpServlet {

    private final IPedido pDao = new PedidoDaoImpl();
    private final Gson gson = new Gson();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");

        if (action == null) {
            action = "listar";
        }

        switch (action) {

            case "buscar":
                buscarPedido(request, response);
                break;

            case "actualizarEstado":
                actualizarEstado(request, response);
                break;

            default:
                listarPedidos(request, response);
                break;
        }
    }

    private void listarPedidos(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        List<Pedido> lista = pDao.lista();

        response.getWriter().print(gson.toJson(lista));
    }

    private void buscarPedido(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        int id = Integer.parseInt(request.getParameter("id"));

        Pedido pedido = pDao.SearchById(id);

        response.getWriter().print(gson.toJson(pedido));

    }

    private void actualizarEstado(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        Pedido p = new Pedido();
        p.setId_pedido(Integer.parseInt(request.getParameter("id_pedido")));
        p.setEstadoPedido(EstadoPedido.valueOf(request.getParameter("estado").toUpperCase()));

        boolean result = pDao.updateEstado(p);

        response.getWriter().print(gson.toJson(result));

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
