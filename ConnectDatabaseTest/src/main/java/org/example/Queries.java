package org.example;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Queries {
    public static void showData() {
        try {
            Connection connection = DatabaseConnection.getConnection();
            Statement statement = connection.createStatement();

            // mostrar todos los empleados
            System.out.println("=== Lista de empleados ===");
            ResultSet empleados = statement.executeQuery("SELECT * FROM empleados");
            while (empleados.next()) {
                System.out.println(empleados.getString("nombre") + " " + empleados.getString("apellidos"));
            }
            System.out.println();

            // mostrar todos los producto
            System.out.println("=== Lista de producto ===");
            ResultSet productos = statement.executeQuery("SELECT nombre, precio FROM productos");
            while (productos.next()) {
                System.out.println(productos.getString("nombre") + " - " + productos.getDouble("precio"));
            }
            System.out.println();

            // precio menor que 600€
            System.out.println("=== producto que su precio menor que 600€ ===");
            ResultSet cheapProducts = statement.executeQuery("SELECT nombre, precio FROM productos WHERE precio < 600");
            while (cheapProducts.next()) {
                System.out.println(cheapProducts.getString("nombre") + " - " + cheapProducts.getDouble("precio"));
            }
            System.out.println();

            // mostrar todos los pedidos
            System.out.println("=== lista de pedido ===");
            ResultSet pedidos = statement.executeQuery("SELECT * FROM pedidos");
            while (pedidos.next()) {
                System.out.println("订单 ID: " + pedidos.getInt("id") +
                        ", 产品 ID: " + pedidos.getInt("id_producto") +
                        ", 描述: " + pedidos.getString("descripcion") +
                        ", 总价: " + pedidos.getDouble("precio_total"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static void insertProductosFav() {
        try {
            Connection connection = DatabaseConnection.getConnection();
            Statement statement = connection.createStatement();

            String insertQuery = """
                INSERT INTO productos_fav (id_producto)
                SELECT id
                FROM productos
                WHERE precio > 1000
            """;

            int rowsInserted = statement.executeUpdate(insertQuery);
            System.out.println("inserta " + rowsInserted + " productos a la tabla productos_fav。");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void showProductosFav() {
        try {
            Connection connection = DatabaseConnection.getConnection();
            Statement statement = connection.createStatement();

            System.out.println("=== Productos Favoritos ===");
            ResultSet productosFav = statement.executeQuery("""
            SELECT pf.id, p.nombre, p.precio
            FROM productos_fav pf
            INNER JOIN productos p ON pf.id_producto = p.id
        """);

            while (productosFav.next()) {
                int id = productosFav.getInt("id");
                String nombre = productosFav.getString("nombre");
                double precio = productosFav.getDouble("precio");
                System.out.println("ID: " + id + ", 名称: " + nombre + ", 价格: " + precio);
            }

            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
