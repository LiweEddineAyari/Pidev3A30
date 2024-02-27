package services;

import entity.Paiment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.MyDataBase;

import java.sql.*;

public class PaimentService implements IPaimentService<Paiment>{
    private Connection connection;

    public PaimentService() {
        connection= MyDataBase.getInstance().getConnection();
    }

    @Override
    public void ajouter(Paiment paiment) throws SQLException {
        String sql = "INSERT INTO Paiment (iduser, montant, cartname, CartCode) VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, paiment.getIduser());
            statement.setFloat(2, paiment.getMontant());
            statement.setString(3, paiment.getCartname());
            statement.setString(4, paiment.getCartCode());

            statement.executeUpdate();

        }
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String sql = "DELETE FROM Paiment WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            statement.executeUpdate();

        }
    }

    @Override
    public ObservableList<Paiment> afficher() throws SQLException {
        ObservableList<Paiment> paiments = FXCollections.observableArrayList();
        String sql = "SELECT * FROM Paiment";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    int iduser = resultSet.getInt("iduser");
                    float montant = resultSet.getFloat("montant");
                    String cartname = resultSet.getString("cartname");
                    String cartCode = resultSet.getString("CartCode");

                    Paiment paiment = new Paiment(id, iduser, montant, cartname, cartCode);
                    paiments.add(paiment);
                }
            }
        }

        return paiments;
    }

    @Override
    public void modifier(Paiment paiment) throws SQLException {
        String sql = "UPDATE Paiment SET montant = ?, cartname = ?, cartCode = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setFloat(1, paiment.getMontant());
            statement.setString(2, paiment.getCartname());
            statement.setString(3, paiment.getCartCode());
            statement.setInt(4, paiment.getId());

            statement.executeUpdate();
        }
    }



}
