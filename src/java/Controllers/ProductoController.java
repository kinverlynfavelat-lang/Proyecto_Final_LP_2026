/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import Dao.ProductoDaoImpl;
import Interface.IProducto;
import Model.EstadoProducto;
import Model.Producto;
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
@WebServlet(name = "ProductoController", urlPatterns = {"/ProductoController"})
public class ProductoController extends HttpServlet {

    private final IProducto pDao = new ProductoDaoImpl();

    private final Gson gson = new Gson();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");

        if (action == null) {
            action = request.getQueryString();
        }

        if (action != null) {
            action = action.trim().toLowerCase();
        }

        switch (action) {
            case "guardar":
                guardarProductos(request, response);

                break;
            case "editar":
                editarProductos(request, response);

                break;
            case "eliminar":
                eliminarProductos(request, response);

                break;
            case "buscar":
                buscarProductos(request, response);

                break;
            default:
                listarProductos(request, response);
                break;
        }

    }

    private void listarProductos(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        List<Producto> productos = pDao.lista();
        response.getWriter().print(gson.toJson(productos));

    }

    private void guardarProductos(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        Producto p = new Producto();

        p.setNombre(request.getParameter("nombre"));
        p.setDescripcion(request.getParameter("descripcion"));
        p.setPrecio(Double.parseDouble(request.getParameter("precio")));
        p.setImagen(request.getParameter("imagen"));
        p.setEstado(EstadoProducto.valueOf(request.getParameter("estado").toUpperCase()));

        boolean result = pDao.insert(p);

        response.getWriter().print(gson.toJson(result));
    }

    private void editarProductos(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        Producto p = new Producto();

        p.setId_producto(Integer.parseInt(request.getParameter("id_producto")));

        p.setNombre(request.getParameter("nombre"));
        p.setDescripcion(request.getParameter("descripcion"));
        p.setPrecio(Double.parseDouble(request.getParameter("precio")));
        p.setImagen(request.getParameter("imagen"));
        p.setEstado(EstadoProducto.valueOf(request.getParameter("estado").toUpperCase()));

        boolean result = pDao.update(p);

        response.getWriter().print(gson.toJson(result));
    }

    private void eliminarProductos(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        int id = Integer.parseInt(request.getParameter("id"));

        boolean result = pDao.updateEstado(id, EstadoProducto.INACTIVO);

        response.getWriter().print(gson.toJson(result));

    }

    private void buscarProductos(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        int id = Integer.parseInt(request.getParameter("id"));

        Producto p = pDao.SearchById(id);

        response.getWriter().print(gson.toJson(p));

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
