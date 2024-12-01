package org.example;

import java.sql.Connection;
import java.sql.Statement;

public class AddData {
    public static void addEmployeesAndOrders() {
        try {
            Connection connection = DatabaseConnection.getConnection();
            Statement statement = connection.createStatement();


            statement.executeUpdate("INSERT INTO empleados (nombre, apellidos, correo) VALUES " +
                    "('Juan', 'Pérez', 'juan.perez@correo.com')," +
                    "('María', 'López', 'maria.lopez@correo.com');");


            statement.executeUpdate("INSERT INTO pedidos (id_producto, descripcion, precio_total) VALUES " +
                    "(1, 'Pedido 1', 150.00)," +
                    "(2, 'Pedido 2', 300.00);");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
