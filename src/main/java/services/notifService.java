package services;

import entity.notif;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.MyDataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class notifService implements inotifService<notif>{
    private Connection connection;

    public notifService() {
        connection= MyDataBase.getInstance().getConnection();
    }

    public void ajouter() throws SQLException {

    }

    @Override
    public void ajouter(notif notif) throws SQLException {
        String query = "INSERT INTO notif (nom, description ,title) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, notif.getNom());
            preparedStatement.setString(2,notif.getDescription());
            preparedStatement.setString(3,notif.getTitle());



            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error adding notif: " + e.getMessage());
        }
    }

    @Override
    public ObservableList<notif> afficher() throws SQLException {
        ObservableList<notif> notifList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM `notif`";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("nom");
                String description = resultSet.getString("description");
                String title = resultSet.getString("title");


                notif notif= new notif();
                if(title.equals("user")){
                    notif = new notif(id, name, description, title);
                }
                if(title.equals("admin")){
                    notif = new notif(id, name, description, title);

                }
                if(title.equals("coach")){
                    notif = new notif(id, name, description, title);

                }

                notifList.add(notif);
            }
        }
        return notifList;
    }


}
