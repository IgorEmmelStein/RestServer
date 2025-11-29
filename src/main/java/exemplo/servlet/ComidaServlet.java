package exemplo.servlet;

import com.google.gson.Gson;
import exemplo.dao.ComidaDao; // Novo DAO
import exemplo.modelDomain.Comida; // Novo Modelo
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

public class ComidaServlet extends HttpServlet { // Renomeado

    private static final long serialVersionUID = 1L;
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");

        String requestBody = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        Comida comida = gson.fromJson(requestBody, Comida.class);
        ComidaDao comidaDao = new ComidaDao();

        if (comidaDao.inserir(comida)) {
            resp.setStatus(HttpServletResponse.SC_CREATED); // 201 Created
            resp.getWriter().println("{\"message\": \"true\"}");
        } else {
            resp.setStatus(HttpServletResponse.SC_CONFLICT); // 409 Conflict
            resp.getWriter().println("{\"message\": \"false\"}");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");

        ComidaDao comidaDao = new ComidaDao();

        String nome = req.getParameter("nome"); // Busca por Nome
        if (nome != null) {
            Comida comida = comidaDao.getComida(nome);
            resp.setStatus(HttpServletResponse.SC_OK);
            String jsonResponse = gson.toJson(comida);
            resp.getWriter().println(jsonResponse);
        } else { // Lista todos se o parâmetro 'nome' não for fornecido
            resp.setStatus(HttpServletResponse.SC_OK);
            String jsonResponse = gson.toJson(comidaDao.listarComidas());
            resp.getWriter().println(jsonResponse);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");

        String requestBody = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        Comida comida = gson.fromJson(requestBody, Comida.class);

        ComidaDao comidaDao = new ComidaDao();

        if (comidaDao.atualizar(comida)) {
            resp.setStatus(HttpServletResponse.SC_OK); // 200 OK
            resp.getWriter().println("{\"message\": \"true\"}");
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND); // 404
            resp.getWriter().println("{\"message\": \"false\"}");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");

        String nome = req.getParameter("nome"); // Deletar por Nome
        ComidaDao comidaDao = new ComidaDao();

        if (nome != null && comidaDao.excluir(nome)) {
            resp.setStatus(HttpServletResponse.SC_OK); // 200 OK
            resp.getWriter().println("{\"message\": \"true\"}");
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().println("{\"message\": \"false\"}");
        }
    }
}
