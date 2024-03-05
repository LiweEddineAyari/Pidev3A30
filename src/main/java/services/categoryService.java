package services;

import entity.Category;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.MyDataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;

import java.sql.ResultSet;
import java.sql.SQLException;
public class categoryService implements icategoryService <Category>{

    private Connection connection;

    public categoryService() {
        connection= MyDataBase.getInstance().getConnection();
    }


    @Override
    public void ajouter(Category category) throws SQLException {
        String query = "INSERT INTO category ( nom ) VALUES (?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, category.getNom());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error adding account: " + e.getMessage());
        }
    }

    @Override
    public void modifier(Category category) throws SQLException {
        String query = "UPDATE category SET nom=? WHERE id=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, category.getNom());
            preparedStatement.setInt(2, category.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error editing sub: " + e.getMessage());
        }
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String query = "DELETE FROM category WHERE id=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error deleting category: " + e.getMessage());
        }
    }

    @Override
    public ObservableList<Category> afficher() throws SQLException {
        ObservableList<Category> categoriesList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM `category`";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nom = resultSet.getString("nom");


                Category category = new Category(id, nom);
                categoriesList.add(category);
            }

        }
        return categoriesList;
    }

    @Override
    public ObservableList<String> GetCategoriesNames() throws SQLException {
        ObservableList<String> categoriesNames = FXCollections.observableArrayList();
        String sql = "SELECT  nom FROM `category`";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String nom = resultSet.getString("nom");
                categoriesNames.add(nom);
            }

        }
        return categoriesNames;
    }

    @Override
    public int getCategoryIdByName(String categoryName) throws SQLException {
        int categoryId = -1; // Default value if not found
        String sql = "SELECT id FROM category WHERE nom = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, categoryName);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    categoryId = resultSet.getInt("id");
                }
            }
        }
        return categoryId;
    }



























    @Override
    public ObservableList<Category> search(String name) throws SQLException {
        return null;
    }

    @Override
    public ObservableList<Category> searchU(String name) throws SQLException {
        return null;
    }

    @Override
    public ObservableList<Category> getAccountByAccountId(int AccountId) throws SQLException {
        return null;
    }

    @Override
    public ObservableList<Category> getAccountByRole(Enum role, int count) throws SQLException {
        return null;
    }

    @Override
    public Category authenticate(String email, String password) throws SQLException {
        return null;
    }
}
