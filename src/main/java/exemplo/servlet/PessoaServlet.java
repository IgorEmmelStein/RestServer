package exemplo.servlet;

import com.google.gson.Gson;
import exemplo.dao.PessoaDao;
import exemplo.modelDomain.Pessoa;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

public class PessoaServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");

        // Lê o corpo da requisição
        String requestBody = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        System.out.println(requestBody);
        // Converte o JSON recebido em um objeto Pessoa
        Pessoa pessoa = gson.fromJson(requestBody, Pessoa.class);
        PessoaDao pessoaDao = new PessoaDao();

        if (pessoaDao.inserir(pessoa)) {
            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.getWriter().println("{\"message\": \"true\"}");
        } else {
            resp.setStatus(HttpServletResponse.SC_CONFLICT);
            resp.getWriter().println("{\"message\": \"false\"}");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");

        PessoaDao pessoaDao = new PessoaDao();

        String email = req.getParameter("email");
        if (email != null) {
            Pessoa pessoa = pessoaDao.getPessoa(email);
            resp.setStatus(HttpServletResponse.SC_OK);
            String jsonResponse = gson.toJson(pessoa);
            resp.getWriter().println(jsonResponse);
        } else if (email == null) {
            resp.setStatus(HttpServletResponse.SC_OK);
            String jsonResponse = gson.toJson(pessoaDao.listarPessoas());
            resp.getWriter().println(jsonResponse);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");

        // Lê o corpo da requisição
        String requestBody = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

        // Converte o JSON recebido em um objeto Pessoa
        Pessoa pessoa = gson.fromJson(requestBody, Pessoa.class);

        PessoaDao pessoaDao = new PessoaDao();

        // Atualiza os dados da pessoa
        if (false/*pessoaDao.verificaSeExixtePessoa(pessoa.getEmail())*/) {
            //pessoaDao.update(pessoa)
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().println("{\"message\": \"true\"}");
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().println("{\"message\": \"false\"}");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");

        String email = req.getParameter("email");
        PessoaDao pessoaDao = new PessoaDao();

        if (email != null && pessoaDao.excluir(email)) {
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().println("{\"message\": \"true\"}");
            //resp.getWriter().println("true");
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().println("{\"message\": \"false\"}");
            //resp.getWriter().println("false");
        }
    }
}
