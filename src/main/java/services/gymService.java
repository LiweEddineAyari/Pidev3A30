package services;

import entity.Abonnement;
import entity.gym;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.MyDataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class gymService implements igymService<gym>{
    private Connection connection;

    public gymService() {
        connection= MyDataBase.getInstance().getConnection();
    }

    @Override
    public void ajouter(gym g) throws SQLException {


        String query = "INSERT INTO gym ( id_user,nom,client_number,coach_number ) VALUES (?,?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, g.getUser_id());
            preparedStatement.setString(2, g.getNom());
            preparedStatement.setInt(3, g.getClient_number());
            preparedStatement.setInt(4, g.getCoach_number());



            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error adding gym: " + e.getMessage());
        }
    }

    @Override
    public void modifier(gym g) throws SQLException {
        String query = "UPDATE gym SET  id_user=? ,nom=?, client_number=?, coach_number=? WHERE id=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, g.getUser_id());
            preparedStatement.setString(2, g.getNom());
            preparedStatement.setInt(3, g.getClient_number());
            preparedStatement.setInt(4, g.getCoach_number());
            preparedStatement.setInt(5, g.getId());


            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error editing gym: " + e.getMessage());
        }
    }



    @Override
    public void supprimer(int id) throws SQLException {
        String query = "DELETE FROM gym WHERE id=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error deleting gym: " + e.getMessage());
        }
    }

    @Override
    public ObservableList<gym> afficher() throws SQLException {
        ObservableList<gym> abonnementsList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM `gym`";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int user_id =resultSet.getInt("id_user");
                String nom = resultSet.getString("nom");
                int client_number = resultSet.getInt("client_number");
                int coach_number = resultSet.getInt("coach_number");



                gym g = new gym(id,user_id, nom, client_number, coach_number);
                abonnementsList.add(g);
            }
        }
        return abonnementsList;
    }

    @Override
    public ObservableList<gym> search(String name) throws SQLException {
        return null;
    }
}
