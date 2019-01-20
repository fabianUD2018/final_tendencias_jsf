package com.modelo.gestion;

import com.cliente.Horario;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <ol>
 * <li></li>
 * </ol> @author fcher
 * @version 1.0
 * @created 16-oct.-2018 4:11:59 p. m.
 */
public class GestorBaseDatos {

    /**
     * @version 1.0
     * @created 16-oct.-2018 4:11:59 p. m.
     */
    private static GestorBaseDatos gestor;

    public GestorBaseDatos m_GestorBaseDatos;
    private Statement st;
    Connection myConn = null;

    private void GestorBaseDatos() {

    }

    public static GestorBaseDatos obtenerGestor() {
        if (gestor == null) {
            gestor = new GestorBaseDatos();

        }
        return gestor;

    }

    public void realizaConexion() {

        String urlDatabase = "jdbc:postgresql://127.0.0.1/tend";
        try {
            Class.forName("org.postgresql.Driver");
            myConn = DriverManager.getConnection(urlDatabase, "postgres", "Hmdemo123");
            st = myConn.createStatement();

        } catch (Exception e) {
            System.out.println("Ocurrio un error : " + e.getMessage());
        }
        System.out.println("La conexión se realizo sin problemas! =) ");

    }

    public void executeQ(String sql) {
        

        try {

            st.executeQuery(sql);
            
            st.close();
            myConn.close();
        } catch (SQLException ex) {
            Logger.getLogger(GestorBaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     *
     * @param sql
     * @return
     */
    public ResultSet read(String sql) {
        ResultSet rs = null;
        try {
            rs = st.executeQuery(sql);

        } catch (SQLException ex) {
            Logger.getLogger(GestorBaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        }

        return rs;
    }

}
