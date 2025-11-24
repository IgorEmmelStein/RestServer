/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package exemplo;

import exemplo.servlet.PessoaServlet;
import jakarta.servlet.DispatcherType;
import java.util.EnumSet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlets.CrossOriginFilter;

public class WebServer {

    public static void main(String[] args) throws Exception {
        // Configura o servidor na porta 8080
        Server server = new Server(8080);

        // Configura o contexto e mapeia as rotas
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        // Configura o filtro de CORS
        FilterHolder cors = context.addFilter(CrossOriginFilter.class, "/*", EnumSet.of(DispatcherType.REQUEST));
        cors.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*"); // Permitir todas as origens
        cors.setInitParameter(CrossOriginFilter.ACCESS_CONTROL_ALLOW_ORIGIN_HEADER, "*");
        cors.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "GET,POST,PUT,DELETE,OPTIONS");
        cors.setInitParameter(CrossOriginFilter.ALLOWED_HEADERS_PARAM, "Content-Type,Authorization");
        cors.setInitParameter(CrossOriginFilter.ALLOW_CREDENTIALS_PARAM, "true");

        // Adiciona o Servlet para gerenciar o CRUD
        context.addServlet(new ServletHolder(new PessoaServlet()), "/api/pessoa/*");

        // Inicia o servidor
        try {
            server.start();
            System.out.println("Servidor rodando em: http://localhost:8080/api/pessoa");
            server.join();
        } catch (Exception e) {
        } finally {
            server.destroy();
        }
    }
}
