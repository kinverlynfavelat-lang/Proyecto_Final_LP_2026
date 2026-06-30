package Controllers;

import Dao.UsuarioDaoImpl;
import Enums.Rol;
import Interface.IUsuario;
import Model.Usuario;
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

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>AuthController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AuthController</h1>");
            out.println("</body>");
            out.println("</html>");

        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        processRequest(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");

        JsonObject jsonResponse = new JsonObject();

        try (PrintWriter out = response.getWriter()) {

            // Validar acción

            if (action == null || action.trim().isEmpty()) {

                jsonResponse.addProperty("success", false);
                jsonResponse.addProperty("message", "Acción no válida");

                out.print(jsonResponse.toString());

                return;

            }

            // ==========================
            // LOGIN
            // ==========================

            if ("login".equals(action)) {

                String correo = request.getParameter("correo");
                String password = request.getParameter("password");

                // Validación de campos

                if (correo == null || correo.trim().isEmpty()
                        || password == null || password.trim().isEmpty()) {

                    jsonResponse.addProperty("success", false);
                    jsonResponse.addProperty("message", "Correo y contraseña son obligatorios");

                    out.print(jsonResponse.toString());

                    return;

                }

                Usuario usuario = uDao.iniciarSesion(correo.trim(), password);

                if (usuario != null) {

                    HttpSession sesion = request.getSession(true);

                    sesion.setAttribute("usuario", usuario);

                    JsonObject userData = new JsonObject();

                    userData.addProperty("idUsuario", usuario.getIdUsuario());
                    userData.addProperty("nombreCompleto", usuario.getNombreCompleto());
                    userData.addProperty("correo", usuario.getCorreo());
                    userData.addProperty("rol", usuario.getRol().toString());

                    jsonResponse.addProperty("success", true);
                    jsonResponse.addProperty("message", "Inicio de sesión exitoso");

                    jsonResponse.add("userData", userData);

                } else {

                    jsonResponse.addProperty("success", false);
                    jsonResponse.addProperty("message", "Correo o contraseña incorrectos");

                }

                out.print(jsonResponse.toString());

            }
                        // ==========================
            // REGISTER
            // ==========================

            else if ("register".equals(action)) {

                String nombreCompleto = request.getParameter("nombreCompleto");
                String correo = request.getParameter("correo");
                String password = request.getParameter("password");

                // Validaciones

                if (nombreCompleto == null || nombreCompleto.trim().isEmpty()
                        || correo == null || correo.trim().isEmpty()
                        || password == null || password.trim().isEmpty()) {

                    jsonResponse.addProperty("success", false);
                    jsonResponse.addProperty("message", "Todos los campos son obligatorios");

                    out.print(jsonResponse.toString());

                    return;

                }

                // Validar correo

                if (!correo.matches("^[\\w.-]+@[\\w.-]+\\.[A-Za-z]{2,}$")) {

                    jsonResponse.addProperty("success", false);
                    jsonResponse.addProperty("message", "Correo electrónico inválido");

                    out.print(jsonResponse.toString());

                    return;

                }

                // Validar contraseña

                if (password.length() < 6) {

                    jsonResponse.addProperty("success", false);
                    jsonResponse.addProperty("message", "La contraseña debe tener al menos 6 caracteres");

                    out.print(jsonResponse.toString());

                    return;

                }

                Usuario usuario = new Usuario();

                usuario.setNombreCompleto(nombreCompleto.trim());
                usuario.setCorreo(correo.trim());
                usuario.setPassword(password);

                // Todo usuario registrado desde la web será CLIENTE

                usuario.setRol(Rol.CLIENTE);

                boolean registrado = uDao.registrar(usuario);

                if (registrado) {

                    jsonResponse.addProperty("success", true);
                    jsonResponse.addProperty("message", "Usuario registrado correctamente");

                } else {

                    jsonResponse.addProperty("success", false);
                    jsonResponse.addProperty("message", "No fue posible registrar el usuario");

                }

                out.print(jsonResponse.toString());

            }

            // ==========================
            // LOGOUT
            // ==========================

            else if ("logout".equals(action)) {

    HttpSession session = request.getSession(false);

    if (session != null) {
        session.invalidate();
    }

    jsonResponse.addProperty("success", true);
    jsonResponse.addProperty("message", "Sesión cerrada correctamente");

    out.print(jsonResponse.toString());

}
else if ("check".equals(action)) {

    HttpSession session = request.getSession(false);
    Usuario usuario = (session != null)
            ? (Usuario) session.getAttribute("usuario")
            : null;

    if (usuario != null) {

        JsonObject userData = new JsonObject();
        userData.addProperty("nombreCompleto", usuario.getNombreCompleto());
        userData.addProperty("rol", usuario.getRol().toString());

        jsonResponse.addProperty("success", true);
        jsonResponse.add("userData", userData);

    } else {
        jsonResponse.addProperty("success", false);
    }

    out.print(jsonResponse.toString());
}
else {   // 🔴 ESTE ES EL ÚLTIMO SIEMPRE

    jsonResponse.addProperty("success", false);
    jsonResponse.addProperty("message", "Acción no reconocida");

    out.print(jsonResponse.toString());
}
        }
    }

    @Override
    public String getServletInfo() {

        return "AuthController BurgerBuilder";

    }

}