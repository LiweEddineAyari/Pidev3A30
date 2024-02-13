package services;

import entity.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.MyDataBase;

import java.sql.*;
import java.util.List;

public class ProductService implements IProductService<Product>{

    private Connection connection;

    public ProductService() {
        connection= MyDataBase.getInstance().getConnection();
    }


    @Override
    public void ajouter(Product product) throws SQLException {
        String sql = "INSERT INTO `product`(`name`, `ref`, `price`, `quantity`, `weight`, `imageUrl`, `description`) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getRef());
            preparedStatement.setFloat(3, product.getPrice());
            preparedStatement.setInt(4, product.getQuantity());
            preparedStatement.setInt(5, product.getWeight());
            preparedStatement.setString(6, product.getImageUrl());
            preparedStatement.setString(7, product.getDescription());

            preparedStatement.executeUpdate();
        }
    }


    @Override
    public void modifier(Product product) throws SQLException {
        String sql = "UPDATE `product` SET `name`=?, `ref`=?, `price`=?, `quantity`=?, `weight`=?, `imageUrl`=?, `description`=? WHERE `id`=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getRef());
            preparedStatement.setFloat(3, product.getPrice());
            preparedStatement.setInt(4, product.getQuantity());
            preparedStatement.setInt(5, product.getWeight());
            preparedStatement.setString(6, product.getImageUrl());
            preparedStatement.setString(7, product.getDescription());
            preparedStatement.setInt(8, product.getId());  // Set the ID last

            preparedStatement.executeUpdate();
        }
    }


    @Override
    public void supprimer(int id) throws SQLException {
        String sql = "DELETE FROM `product` WHERE `id`=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
    }


    @Override
    public ObservableList<Product> afficher() throws SQLException {
        ObservableList<Product> productList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM `product`";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String ref = resultSet.getString("ref");
                float price = resultSet.getFloat("price");
                int quantity = resultSet.getInt("quantity");
                int weight = resultSet.getInt("weight");
                String imageUrl = resultSet.getString("imageUrl");
                String description = resultSet.getString("description");

                Product product = new Product(id, name, ref, price, quantity, weight, imageUrl, description);
                productList.add(product);
            }
        }
        return productList;
    }

    @Override
    public ObservableList<Product> search(String name) throws SQLException {
        ObservableList<Product> searchResults = FXCollections.observableArrayList();

        // Use the '%' wildcard in the SQL query to perform a partial match
        String sql = "SELECT * FROM `product` WHERE `name` LIKE ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            // Set the parameter with the provided name and '%' wildcard
            preparedStatement.setString(1, name + "%");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String productName = resultSet.getString("name");
                String ref = resultSet.getString("ref");
                float price = resultSet.getFloat("price");
                int quantity = resultSet.getInt("quantity");
                int weight = resultSet.getInt("weight");
                String imageUrl = resultSet.getString("imageUrl");
                String description = resultSet.getString("description");

                Product product = new Product(id, productName, ref, price, quantity, weight, imageUrl, description);
                searchResults.add(product);
            }
        }

        return searchResults;
    }


    @Override
    public Product getProductById(int id) throws SQLException {
        String sql = "SELECT * FROM `product` WHERE `id` = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String ref = resultSet.getString("ref");
                float price = resultSet.getFloat("price");
                int quantity = resultSet.getInt("quantity");
                int weight = resultSet.getInt("weight");
                String imageUrl = resultSet.getString("imageUrl");
                String description = resultSet.getString("description");

                return new Product(id, name, ref, price, quantity, weight, imageUrl, description);
            }
        }

        return null; // Return null if no product with the given ID is found
    }



}
