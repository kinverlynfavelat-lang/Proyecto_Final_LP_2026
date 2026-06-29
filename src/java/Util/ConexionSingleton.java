/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Util;

/**
 *
 * @author kinve
 */
import java.sql.*;

public class ConexionSingleton {
    
    public static Connection connection;

    //metodo getConnection
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {

                Runtime.getRuntime().addShutdownHook(new getClose());

                // Driver Oracle JDBC
                Class.forName("oracle.jdbc.OracleDriver");

                // Conexion Oracle XE
                connection = DriverManager.getConnection(
                        "jdbc:oracle:thin:@localhost:1521/XE",
                        "BurgerBuilder_BD",
                        "admin1234"
                );

                System.out.println("Conectado a Oracle");
            }
            return connection;

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Conexion fallida", e);

        }
    }

    static class getClose extends Thread {

        @Override
        public void run() {
             try {

            if (connection != null && !connection.isClosed()) {

                connection.close();

                System.out.println("Conexión cerrada.");

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    

        }

    }
}
