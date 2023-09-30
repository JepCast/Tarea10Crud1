package edu.umg;

import java.sql.*;
import java.util.Scanner;

public class clsConnection {
    private Connection conn = null;
    private void connect(){
        try {
            //Get connection to database
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_crud1", "root", "3(ME*N7h56c)");
        } catch (Exception e) {
            System.out.println("Ups, hubo un error en la conexion: " + e.getMessage());
        }
    }

    public void closeConnection() {
        try {
            if (conn != null) {
                conn.close();
                System.out.println("La conexion de la Base de Datos ha sido cerrada.");
            }
        } catch (SQLException e) {
            System.err.println("Erros cerrando la conexion de la Base de Datos" + e.getMessage());
        }
    }
}
