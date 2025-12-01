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

public class ComidaDaoJDBC {

    private final Connection conn;

    public ComidaDaoJDBC() {

        conn = FactoryConnector.getConection();
    }

    public Comida getComida(String nome) {
        PreparedStatement stmt = null;
        Comida comida = null;

        try {

            String sql = " SELECT id, nome, preco FROM comida WHERE nome = ? ";

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, nome);

            ResultSet res = stmt.executeQuery();

            while (res.next()) {

                comida = new Comida(res.getInt("id"),
                        res.getString("nome"),
                        res.getDouble("preco"));
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

    public boolean inserir(Comida c) throws SQLException {

        String sql = "INSERT INTO comida (nome, preco) "
                + "         VALUES (?,?)";

        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, c.getNome());
        stmt.setDouble(2, c.getPreco());

        return JDBCHelper.execute(stmt);
    }

    public boolean atualizar(Comida c) {
        PreparedStatement stmt = null;

        try {

            String sql = "UPDATE comida SET nome = ?, preco = ? WHERE id = ?";

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, c.getNome());
            stmt.setDouble(2, c.getPreco());
            stmt.setInt(3, c.getId());

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

    public boolean excluir(String nome) throws SQLException {

        String sql = "DELETE FROM comida where nome = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, nome);

        return JDBCHelper.execute(stmt);
    }

    public ArrayList<Comida> listarComidas() {
        Statement stmt = null;
        ArrayList<Comida> listaComida = new ArrayList<>();

        try {

            String sql = "SELECT id, nome, preco from comida";

            stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(sql);

            while (res.next()) {
                Comida comida;

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

}
