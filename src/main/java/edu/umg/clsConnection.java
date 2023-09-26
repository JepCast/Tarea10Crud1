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

//    public  void readData() throws SQLException {
//        connect();
//        String query = "SELECT * FROM tb_personas";
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//
//        try {
//            ps = conn.prepareStatement(query);
//            rs = ps.executeQuery();
//
//            while(rs.next()) {
//                System.out.println("Nombre: " + rs.getString("nombre") + " Apellido: " + rs.getString("apellido"));
//            }
//        } finally {
//            if (rs == null) {
//                rs.close();
//            }
//
//            if (ps == null) {
//                ps.close();
//            }
//            conn.close();
//        }
//    }
//
//    public void insertData() throws SQLException {
//        connect();
//        Scanner sc = new Scanner(System.in);
//        System.out.println("Ingrese el nombre: ");
//        String nombre = sc.nextLine();
//        System.out.println("Ingrese el apellido: ");
//        String apellido = sc.nextLine();
//
//        String query = "INSERT INTO tb_personas (nombre, apellido) VALUES (?, ?)";
//        PreparedStatement ps = null;
//
//        try {
//            ps = conn.prepareStatement(query);
//            ps.setString(1, nombre);
//            ps.setString(2, apellido);
//            ps.executeUpdate();
//            System.out.println("Datos insertados correctamente");
//        } finally {
//            if (ps == null) {
//                ps.close();
//            }
//            conn.close();
//        }
//    }
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
