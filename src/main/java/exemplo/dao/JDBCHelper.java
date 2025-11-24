/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exemplo.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Geison Quevedo
 */
public class JDBCHelper {

    public static boolean execute(PreparedStatement stmt) {
        try {
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

    public static ResultSet executeQuery(PreparedStatement stmt) {

        try {

            ResultSet res = stmt.executeQuery();

            res.close();
            stmt.close();

            return res;

        } catch (SQLException e) {
            System.out.println(e.getErrorCode() + "-" + e.getMessage());
        } finally {
        }
        return null;
    }

}
