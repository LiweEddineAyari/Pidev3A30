package services;

import entity.Planning;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.MyDataBase;

import java.sql.*;

public class PlanningService implements IPlanningService<Planning>{
    private Connection connection; // Assuming you have a valid database connection

    public PlanningService() {
        this.connection = MyDataBase.getInstance().getConnection();
    }

    @Override
    public int ajouter(Planning planning) throws SQLException {
        String query = "INSERT INTO planning (id_coach, date_debut, date_fin, heure_debut, heure_fin, titre, description) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, planning.getId_coach());
            preparedStatement.setString(2, planning.getDate_debut());
            preparedStatement.setString(3, planning.getDate_fin());
            preparedStatement.setString(4, planning.getHeure_debut());
            preparedStatement.setString(5, planning.getHeure_fin());
            preparedStatement.setString(6, planning.getTitre());
            preparedStatement.setString(7, planning.getDescription());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating planning failed, no rows affected.");
            }

            // Retrieve the generated keys (in this case, the ID of the newly added planning)
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int newPlanningId = generatedKeys.getInt(1);
                    System.out.println("Newly added planning ID: " + newPlanningId);
                    return newPlanningId; // Return the ID
                } else {
                    throw new SQLException("Creating planning failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error adding planning: " + e.getMessage());
        }
    }


    @Override
    public void modifier(Planning planning) throws SQLException {
        String query = "UPDATE planning SET id_coach = ?, date_debut = ?, date_fin = ?, " +
                "heure_debut = ?, heure_fin = ?, titre = ?, description = ? " +
                "WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, planning.getId_coach());
            preparedStatement.setString(2, planning.getDate_debut());
            preparedStatement.setString(3, planning.getDate_fin());
            preparedStatement.setString(4, planning.getHeure_debut());
            preparedStatement.setString(5, planning.getHeure_fin());
            preparedStatement.setString(6, planning.getTitre());
            preparedStatement.setString(7, planning.getDescription());
            preparedStatement.setInt(8, planning.getId());

            System.out.println(planning);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error modifying planning: " + e.getMessage());
        }
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String query = "DELETE FROM planning WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error deleting planning: " + e.getMessage());
        }
    }

    @Override
    public ObservableList<Planning> afficher() throws SQLException {
        ObservableList<Planning> planningList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM planning";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int id_coach = resultSet.getInt("id_coach");
                String date_debut = resultSet.getString("date_debut");
                String date_fin = resultSet.getString("date_fin");
                String heure_debut = resultSet.getString("heure_debut");
                String heure_fin = resultSet.getString("heure_fin");
                String titre = resultSet.getString("titre");
                String description = resultSet.getString("description");

                Planning planning = new Planning(id, id_coach, date_debut, date_fin, heure_debut, heure_fin, titre, description);
                planningList.add(planning);
            }
        } catch (SQLException e) {
            throw new SQLException("Error fetching planning data: " + e.getMessage());
        }

        return planningList;
    }



}
