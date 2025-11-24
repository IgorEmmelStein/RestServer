package exemplo.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class FactoryConnector {

    private static Connection conn = null;

    public static Connection getConection() {

        try {
            final String strConexao = "jdbc:mysql://localhost:3306/pessoa";
            final String usuario = "root";
            final String senha = "";

            conn = DriverManager.getConnection(strConexao,
                    usuario,
                    senha);

            return conn;

        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
    }
}
