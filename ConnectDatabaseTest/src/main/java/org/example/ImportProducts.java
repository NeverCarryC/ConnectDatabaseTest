package org.example;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class ImportProducts {
    public static void importFromJson() {
        try {
            URL url = new URL("https://dummyjson.com/products");
            InputStreamReader reader = new InputStreamReader(url.openStream());
            JsonArray products = JsonParser.parseReader(reader)
                    .getAsJsonObject()
                    .getAsJsonArray("products");

            Connection connection = DatabaseConnection.getConnection();
            String sql = "INSERT INTO productos (nombre, descripcion, cantidad, precio) VALUES (?, ?, ?, ?)";

            for (JsonElement product : products) {
                String nombre = product.getAsJsonObject().get("title").getAsString();
                String descripcion = product.getAsJsonObject().get("description").getAsString();
                int cantidad = product.getAsJsonObject().get("stock").getAsInt();
                double precio = product.getAsJsonObject().get("price").getAsDouble();

                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, nombre);
                statement.setString(2, descripcion);
                statement.setInt(3, cantidad);
                statement.setDouble(4, precio);
                statement.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
