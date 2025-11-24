/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exemplo.dao;

import exemplo.factory.FactoryConnector;
import exemplo.modelDomain.Pessoa;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PessoaDaoJDBC {

    private final Connection conn;

    public PessoaDaoJDBC() {
        conn = FactoryConnector.getConection();
    }

    public boolean inserir(Pessoa p) throws SQLException {

        String sql = "INSERT INTO pessoa (nome, idade, email, endereco) "
                + "         VALUES (?,?,?,?)";

        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, p.getNome());
        stmt.setInt(2, p.getIdade());
        stmt.setString(3, p.getEmail());
        stmt.setString(4, p.getEndereco());

        return JDBCHelper.execute(stmt);

    }

    public boolean inserirNormal(Pessoa p) {
        PreparedStatement stmt = null;

        try {
            String sql = "INSERT INTO pessoa (nome, idade, email, endereco) "
                    + "         VALUES (?,?,?,?)";

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, p.getNome());
            stmt.setInt(2, p.getIdade());
            stmt.setString(3, p.getEmail());
            stmt.setString(4, p.getEndereco());

            stmt.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getErrorCode() + "-" + e.getMessage());
            return false;
        } finally {
            try {
                stmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(PessoaDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public boolean atualizar(Pessoa p) {
        PreparedStatement stmt = null;

        try {
            String sql = "-- UPDATE;";

            stmt.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getErrorCode() + "-" + e.getMessage());
            return false;
        } finally {
            try {
                stmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(PessoaDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public Pessoa getPessoa(String email) {
        PreparedStatement stmt = null;
        Pessoa pessoa = null;

        try {
            String sql = " SELECT * FROM pessoa WHERE email = ? ";

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);

            ResultSet res = stmt.executeQuery();

            while (res.next()) {

                pessoa = new Pessoa(res.getInt("id"),
                        res.getString("nome"),
                        res.getString("email"),
                        res.getInt("idade"),
                        res.getString("endereco"));
            }

            res.close();
            stmt.close();

            return pessoa;

        } catch (SQLException e) {
            System.out.println(e.getErrorCode() + "-" + e.getMessage());
            return pessoa;
        } finally {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(PessoaDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public Pessoa getPessoaAntigo(String email) {
        PreparedStatement stmt = null;
        Pessoa pessoa = null;

        try {
            String sql = " SELECT * FROM pessoa WHERE email = ? ";

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);

            ResultSet res = stmt.executeQuery();

            while (res.next()) {

                pessoa = new Pessoa(res.getInt("id"),
                        res.getString("nome"),
                        res.getString("email"),
                        res.getInt("idade"),
                        res.getString("endereco"));
            }

            res.close();
            stmt.close();

            return pessoa;

        } catch (SQLException e) {
            System.out.println(e.getErrorCode() + "-" + e.getMessage());
            return pessoa;
        } finally {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(PessoaDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public boolean excluir(String email) throws SQLException {

        String sql = "DELETE FROM pessoa where email = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, email);

        return JDBCHelper.execute(stmt);

    }

    public boolean excluirAntigo(String email) {

        PreparedStatement stmt = null;

        try {

            String sql = "DELETE FROM pessoa where email = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.execute();
            stmt.close();

            return true;
        } catch (SQLException ex) {
            Logger.getLogger(PessoaDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(PessoaDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public ArrayList<Pessoa> listarPessoas() {
        Statement stmt = null;
        ArrayList<Pessoa> listaPessoa = new ArrayList<>();

        try {
            String sql = "SELECT * from pessoa";

            stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(sql);

            while (res.next()) {
                Pessoa pessoa;

                pessoa = new Pessoa(res.getInt("id"),
                        res.getString("nome"),
                        res.getString("email"),
                        res.getInt("idade"),
                        res.getString("endereco"));

                listaPessoa.add(pessoa);

            }

            res.close();
            stmt.close();

            return listaPessoa;
        } catch (SQLException e) {
            System.out.println(e.getErrorCode() + "-" + e.getMessage());
            return listaPessoa;
        } finally {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(PessoaDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
