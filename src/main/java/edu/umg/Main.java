package edu.umg;

import java.sql.*;
import java.util.Scanner;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Main {
    private static Connection conn = null;

    private static void connect(){
        try {
            //Get connection to database
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_crud1", "root", "3(ME*N7h56c)");
        } catch (Exception e) {
            System.out.println("Ups, hubo un error en la conexion: " + e.getMessage());
        }
    }

    private static void closeConnection() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws SQLException {
        connect();
        clsConnection connection = new clsConnection();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nOpciones:");
            System.out.println("1. Agregar personas");
            System.out.println("2. Consultar personas");
            System.out.println("3. Actualizar información del personas");
            System.out.println("4. Eliminar personas");
            System.out.println("5. Salir");
            System.out.println("\nIngrese una opción: ");

            String option = scanner.nextLine();

            if (option.equals("1"))
            {
                System.out.println("Nombre: ");
                String nombre = scanner.nextLine();
                System.out.println("Apellido: ");
                String apellido = scanner.nextLine();
                System.out.println("Fecha de registro (YYYY-MM-DD): ");
                String fechaRegistro = scanner.nextLine();
                System.out.println("Sueldo base: ");
                double sueldoBase = Double.parseDouble(scanner.nextLine());
                System.out.println("Sexo (Masculino/Femenino/Otro): ");
                String sexo = scanner.nextLine();
                System.out.println("Edad: ");
                int edad = Integer.parseInt(scanner.nextLine());
                System.out.println("Longitud de casa: ");
                double longitud = Double.parseDouble(scanner.nextLine());
                System.out.println("Latitud de casa: ");
                double latitud = Double.parseDouble(scanner.nextLine());
                System.out.println("Comentarios: ");
                String comentarios = scanner.nextLine();
                insertData(nombre, apellido, fechaRegistro, sueldoBase, sexo, edad, longitud, latitud, comentarios);
            } else if (option.equals("2")) {
                consultPeople();
            } else if (option.equals("3")) {

                String query = "SELECT * FROM tb_personas";
                PreparedStatement ps = conn.prepareStatement(query);
                ResultSet rs = ps.executeQuery();
                System.out.println("\n");

                while (rs.next()) {
                    System.out.println("ID:"+rs.getInt("id")+" Nombre: " + rs.getString("nombre") + " Apellido: " + rs.getString("apellido"));
                }

                System.out.println("\nIngrese el codigo de la persona: ");
                int codigo = Integer.parseInt(scanner.nextLine());
                System.out.println("Ingrese el nuevo sueldo base: ");
                double sueldoBase = Double.parseDouble(scanner.nextLine());
                System.out.println("Ingrese los nuevos comentarios: ");
                String comentarios = scanner.nextLine();
                updateData(codigo, sueldoBase, comentarios);
            } else if (option.equals("4")) {

                String query = "SELECT * FROM tb_personas";
                PreparedStatement ps = conn.prepareStatement(query);
                ResultSet rs = ps.executeQuery();
                System.out.println("\n");

                while (rs.next()) {
                    System.out.println("ID:"+rs.getInt("id")+" Nombre: " + rs.getString("nombre") + " Apellido: " + rs.getString("apellido"));
                }
                System.out.println("\nIngrese el codigo de la persona: ");
                int id = Integer.parseInt(scanner.nextLine());
                deletePerson(id);
            } else if (option.equals("5")) {
                System.out.println("Saliendo...");
                break;
            } else {
                System.out.println("Opción no válida");
            }
        }
        connection.closeConnection();
    }
    private static void  consultPeople() throws SQLException {
        if (conn == null) {
            connect();
        }
        String query = "SELECT * FROM tb_personas";
        PreparedStatement ps = conn.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        System.out.println("\n");

        while (rs.next()) {
            System.out.println("ID:"+rs.getInt("id")+" Nombre: " + rs.getString("nombre") + " Apellido: " + rs.getString("apellido"));
        }

        // Close the resources (ResultSet and PreparedStatement)
        System.out.print("\nIngrese el ID de la persona que desea consultar (0 para volver al menú principal): ");
        Scanner scanner = new Scanner(System.in);
        int selectedId = scanner.nextInt();
        scanner.nextLine();  // Consumir la nueva línea después del número

        if (selectedId != 0)  {
            String query1 = "SELECT * FROM tb_personas WHERE id = ?";
            ps = conn.prepareStatement(query1);
            ps.setInt(1,  selectedId);
            rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println("\nDetalles de la persona:");
                System.out.println("Nombre: " + rs.getString("nombre"));
                System.out.println("Apellido: " + rs.getString("apellido"));
                System.out.println("Fecha de Registro: " + rs.getDate("fechaRegistro"));
                System.out.println("Sueldo Base: " + rs.getDouble("sueldoBase"));
                System.out.println("Sexo: " + rs.getString("sexo"));
                System.out.println("Edad: " + rs.getInt("edad"));
                System.out.println("Longitud: " + rs.getDouble("longitud"));
                System.out.println("Latitud: " + rs.getDouble("latitud"));
                System.out.println("Comentarios: " + rs.getString("comentarios"));
            } else {
                System.out.println("Persona no encontrada.");
            }
        }

        rs.close();
        ps.close();
        }
    private static void insertData(String nombre, String apellido, String fechaRegistro, double sueldoBase, String sexo, int edad, double longitud, double latitud, String comentarios) throws SQLException {
        if (conn== null) {
            connect();
        }
        String query = "INSERT INTO tb_personas (nombre, apellido, fechaRegistro, sueldoBase, sexo, edad, longitud, latitud, comentarios) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, nombre);
        ps.setString(2, apellido);
        ps.setString(3, fechaRegistro);
        ps.setDouble(4, sueldoBase);
        ps.setString(5, sexo);
        ps.setInt(6, edad);
        ps.setDouble(7, longitud);
        ps.setDouble(8, latitud);
        ps.setString(9, comentarios);
        ps.executeUpdate();
        System.out.println("\nDatos insertados y actualizados correctamente");

    }

    private static void updateData(int id, double sueldoBase, String commentarios) throws SQLException {
        if (conn == null) {
            connect();
        }

        String query = "UPDATE tb_personas SET sueldo_base = ?, comentarios = ? WHERE codigo = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setDouble(1, sueldoBase);
        ps.setString(2, commentarios);
        ps.setInt(3, id);
        ps.executeUpdate();
        System.out.println("Datos insertados y actualizados correctamente");
    }

    private static void  deletePerson(int id) throws SQLException {
        if (conn == null) {
            connect();
        }
        String query = "DELETE FROM tb_personas WHERE codigo = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, id);
        ps.executeUpdate();
        System.out.println("Datos insertados y actualizados correctamente");
    }
}