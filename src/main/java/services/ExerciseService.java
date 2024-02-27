package services;

import entity.Exercice;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.MyDataBase;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class ExerciseService implements IExerciceService<Exercice>{

    private Connection connection;

    public ExerciseService() {
        connection= MyDataBase.getInstance().getConnection();
    }


    @Override
    public void ajouter(Exercice exercice) throws SQLException {
        String query = "INSERT INTO exercises (productid, name, target, type, description, img, intensity, EquipmentNeeded) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, exercice.getProductid());
            preparedStatement.setString(2, exercice.getName());
            preparedStatement.setString(3, exercice.getTarget());
            preparedStatement.setString(4, exercice.getType());
            preparedStatement.setString(5, exercice.getDescription());
            preparedStatement.setString(6, exercice.getImg());
            preparedStatement.setString(7, exercice.getIntensity());
            preparedStatement.setString(8, exercice.getEquipmentNeeded());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error adding exercise: " + e.getMessage());
        }
    }


    @Override
    public void modifier(Exercice exercice) throws SQLException {
        String query = "UPDATE exercises SET productid=?, name=?, target=?, type=?, description=?, img=?, intensity=?, EquipmentNeeded=? WHERE id=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, exercice.getProductid());
            preparedStatement.setString(2, exercice.getName());
            preparedStatement.setString(3, exercice.getTarget());
            preparedStatement.setString(4, exercice.getType());
            preparedStatement.setString(5, exercice.getDescription());
            preparedStatement.setString(6, exercice.getImg());
            preparedStatement.setString(7, exercice.getIntensity());
            preparedStatement.setString(8, exercice.getEquipmentNeeded());
            preparedStatement.setInt(9, exercice.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error modifying exercise: " + e.getMessage());
        }
    }


    @Override
    public void supprimer(int id) throws SQLException {
        String query = "DELETE FROM exercises WHERE id=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error deleting exercise: " + e.getMessage());
        }
    }


    @Override
    public ObservableList<Exercice> afficher() throws SQLException {
        ObservableList<Exercice> exerciceList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM `exercises`";

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int productId = resultSet.getInt("productid");
                String name = resultSet.getString("name");
                String target = resultSet.getString("target");
                String type = resultSet.getString("type");
                String description = resultSet.getString("description");
                String img = resultSet.getString("img");
                String intensity = resultSet.getString("intensity");
                String equipmentNeeded = resultSet.getString("EquipmentNeeded");

                Exercice exercice = new Exercice(id, productId, name, target, type, description, img, intensity, equipmentNeeded);
                exerciceList.add(exercice);
            }
        }
        return exerciceList;
    }



    @Override
    public ObservableList<Exercice> search(String name) throws SQLException {
        ObservableList<Exercice> exerciseList = FXCollections.observableArrayList();
        String query = "SELECT * FROM exercises WHERE name LIKE ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, name + "%");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int productId = resultSet.getInt("productid");
                String exerciseName = resultSet.getString("name");
                String target = resultSet.getString("target");
                String type = resultSet.getString("type");
                String description = resultSet.getString("description");
                String img = resultSet.getString("img");
                String intensity = resultSet.getString("intensity");
                String equipmentNeeded = resultSet.getString("EquipmentNeeded");

                Exercice exercice = new Exercice(id, productId, exerciseName, target, type, description, img, intensity, equipmentNeeded);
                exerciseList.add(exercice);
            }
        } catch (SQLException e) {
            throw new SQLException("Error searching for exercise: " + e.getMessage());
        }

        return exerciseList;
    }

    @Override
    public ObservableList<Exercice> searchU(String name, String target, String type) throws SQLException {
        ObservableList<Exercice> searchResults = FXCollections.observableArrayList();

        // Base SQL query
        String sql = "SELECT * FROM `exercises` WHERE `name` LIKE ?";

        // If target is not null, add it to the WHERE clause
        if (target != null) {
            sql += " AND `target` = '" + target + "'";
        }

        // If type is not null, add it to the WHERE clause
        if (type != null) {
            sql += " AND `type` = '" + type + "'";
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            // Set the parameter for name with the provided name and '%' wildcard
            preparedStatement.setString(1, name + "%");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int productId = resultSet.getInt("productid");
                String exerciseName = resultSet.getString("name");
                String targett = resultSet.getString("target");
                String typee = resultSet.getString("type");
                String description = resultSet.getString("description");
                String img = resultSet.getString("img");
                String intensity = resultSet.getString("intensity");
                String equipmentNeeded = resultSet.getString("EquipmentNeeded");

                Exercice exercice = new Exercice(id, productId, exerciseName, targett, typee, description, img, intensity, equipmentNeeded);
                searchResults.add(exercice);
            }
        }

        return searchResults;
    }



    @Override
    public ObservableList<Exercice> getExercisesByProductId(int productId) throws SQLException {
        ObservableList<Exercice> exercisesByProductId = FXCollections.observableArrayList();
        String sql = "SELECT * FROM exercises WHERE productid = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, productId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                // Extract data and create Exercice objects
                int exerciseId = resultSet.getInt("id");
                int productid = resultSet.getInt("productid");
                String name = resultSet.getString("name");
                String target = resultSet.getString("target");
                String type = resultSet.getString("type");
                String description = resultSet.getString("description");
                String img = resultSet.getString("img");
                String intensity = resultSet.getString("intensity");
                String equipmentNeeded = resultSet.getString("EquipmentNeeded");

                Exercice exercice = new Exercice(exerciseId, productid, name, target, type, description, img, intensity, equipmentNeeded);
                exercisesByProductId.add(exercice);
            }
        }

        return exercisesByProductId;
    }

    @Override
    public ObservableList<Exercice> getExercisesByCriteria(String bodyPart, String type, String intensity, String equipmentNeeded, int count) throws SQLException {
        String sql = "SELECT * FROM `exercises` WHERE `target` = ? AND `type` = ? AND `intensity` = ? AND `EquipmentNeeded` = ? LIMIT ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, bodyPart);
            preparedStatement.setString(2, type);
            preparedStatement.setString(3, intensity);
            preparedStatement.setString(4, equipmentNeeded);
            preparedStatement.setInt(5, count);

            ResultSet resultSet = preparedStatement.executeQuery();

            ObservableList<Exercice> exercises = FXCollections.observableArrayList();

            while (resultSet.next()) {
                int exerciseId = resultSet.getInt("id");
                int productid = resultSet.getInt("productid");
                String name = resultSet.getString("name");
                String target = resultSet.getString("target");
                String typee = resultSet.getString("type");
                String description = resultSet.getString("description");
                String img = resultSet.getString("img");
                String intensityy = resultSet.getString("intensity");
                String equipmentNeededd = resultSet.getString("EquipmentNeeded");

                Exercice exercice = new Exercice(exerciseId, productid, name, target, typee, description, img, intensityy, equipmentNeededd);
                exercises.add(exercice);
            }

            return exercises;
        }
    }

    @Override
    public void ajouterFavoriteExercice(int idUser, int idExercice, String type) throws SQLException {
        String query = "INSERT INTO favoriteexercices (iduser, idexercice, type) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, idUser);
            preparedStatement.setInt(2, idExercice);
            preparedStatement.setString(3, type);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error adding favorite exercice: " + e.getMessage());
        }
    }
    @Override
    public int getAidFromFavoriteEx(int idExercice, int iduser) throws SQLException {
        String query = "SELECT idexercice FROM favoriteexercices WHERE idexercice = ? and iduser = ? ";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, idExercice);
            preparedStatement.setInt(2, iduser);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("idexercice");


                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error getting aid from favoriteexercices: " + e.getMessage());
        }

        return -1; // Return -1 if no matching record is found
    }

    @Override
    public void supprimerFavoriteExercice(int idUser, int idExercice) throws SQLException {
        String query = "DELETE FROM favoriteexercices WHERE iduser = ? AND idexercice = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, idUser);
            preparedStatement.setInt(2, idExercice);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error deleting favorite exercice: " + e.getMessage());
        }
    }
    @Override
    public void updateScore(String type, int delta) throws SQLException {
        String query = "UPDATE exercicesastistics SET score = score + ? WHERE type = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, delta);
            preparedStatement.setString(2, type);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error updating score: " + e.getMessage());
        }
    }
   @Override
    public Map<String, Integer> getExercicesWithGreatestScoret() throws SQLException {
        Map<String, Integer> exerciceStatistics = new HashMap<>();
        String query = "SELECT type, score FROM exercicesastistics";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String type = resultSet.getString("type");
                int score = resultSet.getInt("score");

                exerciceStatistics.put(type, score);
            }
        }

        return exerciceStatistics;
    }

}
