package exemplo.dao;

import exemplo.factory.FactoryConnector;
import exemplo.modelDomain.Comida;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ComidaDaoJDBC { // Renomeado

    private final Connection conn;

    public ComidaDaoJDBC() {
        // Conexão JDBC
        conn = FactoryConnector.getConection();
    }

    // Altera a assinatura para buscar por nome (String) e usa Comida
    public Comida getComida(String nome) {
        PreparedStatement stmt = null;
        Comida comida = null;

        try {
            // SQL para buscar na tabela 'comida' por nome
            String sql = " SELECT id, nome, preco FROM comida WHERE nome = ? ";

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, nome); // Busca por nome

            ResultSet res = stmt.executeQuery();

            while (res.next()) {
                // Cria o objeto Comida com os novos campos
                comida = new Comida(res.getInt("id"),
                        res.getString("nome"),
                        res.getDouble("preco")); // Usa getDouble para preco
            }
            res.close();
            stmt.close();
            return comida;

        } catch (SQLException e) {
            System.out.println(e.getErrorCode() + "-" + e.getMessage());
            return comida;
        } finally {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(ComidaDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public boolean inserir(Comida c) throws SQLException { // Inserir Comida

        // SQL para inserir na tabela 'comida'
        String sql = "INSERT INTO comida (nome, preco) "
                + "         VALUES (?,?)";

        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, c.getNome());
        stmt.setDouble(2, c.getPreco()); // Preço é double

        return JDBCHelper.execute(stmt);
    }

    public boolean atualizar(Comida c) { // Atualizar Comida
        PreparedStatement stmt = null;

        try {
            // SQL para atualizar na tabela 'comida'
            String sql = "UPDATE comida SET nome = ?, preco = ? WHERE id = ?";

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, c.getNome());
            stmt.setDouble(2, c.getPreco());
            stmt.setInt(3, c.getId()); // Atualiza pelo ID

            return JDBCHelper.execute(stmt);
        } catch (SQLException e) {
            System.out.println(e.getErrorCode() + "-" + e.getMessage());
            return false;
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ComidaDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public boolean excluir(String nome) throws SQLException { // Excluir por Nome

        // SQL para deletar da tabela 'comida' por nome
        String sql = "DELETE FROM comida where nome = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, nome);

        return JDBCHelper.execute(stmt);
    }

    public ArrayList<Comida> listarComidas() { // Listar todas as Comidas
        Statement stmt = null;
        ArrayList<Comida> listaComida = new ArrayList<>();

        try {
            // SQL para listar todas as comidas
            String sql = "SELECT id, nome, preco from comida";

            stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(sql);

            while (res.next()) {
                Comida comida;

                // Cria o objeto Comida
                comida = new Comida(res.getInt("id"),
                        res.getString("nome"),
                        res.getDouble("preco"));

                listaComida.add(comida);
            }
            res.close();
            stmt.close();
            return listaComida;

        } catch (SQLException e) {
            System.out.println(e.getErrorCode() + "-" + e.getMessage());
            return listaComida;
        } finally {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(ComidaDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    // Métodos antigos que não se aplicam ao novo modelo/API foram omitidos.
}
