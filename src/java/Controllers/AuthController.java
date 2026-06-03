package Controllers;

import Dao.PersonaDaoImpl;
import Dao.UsuarioDaoImpl;
import Interface.IPersona;
import Interface.IUsuario;
import Model.Persona;
import Model.Usuario;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "AuthController", urlPatterns = {"/AuthController"})
public class AuthController extends HttpServlet {

    private final IUsuario uDao = new UsuarioDaoImpl();
    private final IPersona pDao = new PersonaDaoImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("logout".equals(action)) {

            HttpSession session = request.getSession(false);

            if (session != null) {
                session.invalidate();
            }

            response.sendRedirect("login.jsp");

        } else {
            response.sendRedirect("login.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");

        JsonObject jsonResponse = new JsonObject();
        Gson gson = new Gson();

        try (PrintWriter out = response.getWriter()) {

            if ("validar".equals(action)) {

                String usuario = request.getParameter("usuario");
                String password = request.getParameter("password");

                Usuario us = uDao.validate(usuario, password);

                if (us != null && us.getUsuario() != null) {

                    HttpSession session = request.getSession(true);

                    session.setAttribute("usuario", us);
                    session.setAttribute("rol", us.getRol());

                    jsonResponse.addProperty("success", true);
                    jsonResponse.addProperty("message", "Inicio de sesión exitoso");
                    jsonResponse.addProperty("rol", us.getRol().name());

                    jsonResponse.add(
                            "userData",
                            gson.toJsonTree(us)
                    );

                } else {

                    jsonResponse.addProperty("success", false);
                    jsonResponse.addProperty("message",
                            "Usuario o contraseña incorrecta");
                }

                out.print(jsonResponse.toString());

            } else if ("register".equals(action)) {

                Persona p = new Persona();
                Usuario u = new Usuario();

                p.setNombre(request.getParameter("nombre"));
                p.setDni(request.getParameter("dni"));
                p.setTelefono(request.getParameter("telefono"));
                p.setEmail(request.getParameter("email"));
                p.setDireccion(request.getParameter("direccion"));

                u.setPassword(request.getParameter("password"));

                int resultado = pDao.insert(p, u);

                if (resultado > 0) {

                    jsonResponse.addProperty("success", true);
                    jsonResponse.addProperty("message",
                            "Registro realizado correctamente");

                } else {

                    jsonResponse.addProperty("success", false);
                    jsonResponse.addProperty("message",
                            "No se pudo registrar el usuario");
                }

                out.print(jsonResponse.toString());

            } else if ("logout".equals(action)) {

                HttpSession session = request.getSession(false);

                if (session != null) {
                    session.invalidate();
                }

                jsonResponse.addProperty("success", true);
                jsonResponse.addProperty("message", "Sesión cerrada");

                out.print(jsonResponse.toString());

            } else {

                jsonResponse.addProperty("success", false);
                jsonResponse.addProperty("message", "Acción no válida");

                out.print(jsonResponse.toString());
            }

        } catch (Exception e) {

            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

            jsonResponse.addProperty("success", false);
            jsonResponse.addProperty("message", e.getMessage());

            response.getWriter().print(jsonResponse.toString());

            e.printStackTrace();
        }
    }

    @Override
    public String getServletInfo() {
        return "Controlador de autenticación";
    }
}