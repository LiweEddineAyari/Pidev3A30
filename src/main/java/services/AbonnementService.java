package services;
import entity.Abonnement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.projet.AbonnementUserConroller;
import utils.MyDataBase;

import java.sql.*;

public class AbonnementService implements IAbonnementService<Abonnement> {

    private Connection connection;

    public AbonnementService() {
        connection= MyDataBase.getInstance().getConnection();
    }
    AbonnementUserConroller abonnementUserConroller = AbonnementUserConroller.getInstance();



    @Override
    public void ajouter(Abonnement abonnement) throws SQLException {
        String query = "INSERT INTO abonnement (id_category, nom, duree, prix, fidelite, members) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, abonnement.getIdcategory());
            preparedStatement.setString(2, abonnement.getNom());
            preparedStatement.setInt(3, abonnement.getDuree());
            preparedStatement.setFloat(4, abonnement.getPrix());
            preparedStatement.setBoolean(5, abonnement.isFidelite());
            preparedStatement.setString(6, "");

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error adding account: " + e.getMessage());
        }
    }


    @Override
    public void modifier(Abonnement abonnement) throws SQLException {
        String query = "UPDATE abonnement SET  id_category=?, nom=?, duree=?, prix=?, fidelite=? WHERE id=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, abonnement.getIdcategory());
            preparedStatement.setString(2, abonnement.getNom());
            preparedStatement.setInt(3, abonnement.getDuree());
            preparedStatement.setFloat(4, abonnement.getPrix());
            preparedStatement.setBoolean(5, abonnement.isFidelite());
            preparedStatement.setInt(6, abonnement.getId());


            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error editing sub: " + e.getMessage());
        }
    }
    public boolean isUserInMembers(int userId, String members) {
        if (members == null || members.isEmpty()) {
            return false;
        }

        String[] memberIds = members.split(",");
        for (String memberId : memberIds) {
            if (Integer.parseInt(memberId) == userId) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void addnewMember(Abonnement abonnement, int iduser) throws SQLException {

        String query = "UPDATE abonnement SET members=? WHERE id=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            if (abonnement.getMembres() != null) {
                // Check if the user already exists in members
                System.out.println("check dos");

                if (!isUserInMembers(iduser, abonnement.getMembres())) {
                    System.out.println("check dos1");

                    preparedStatement.setString(1, abonnement.getMembres() + iduser + ",");
                    abonnementUserConroller.selectedAbonnement.setMembres(abonnement.getMembres() + iduser + ",");

                } else {
                    // User already exists, do not update members
                    System.out.println("check dos2");
                    preparedStatement.setString(1, abonnement.getMembres());
                }
            } else {
                // No existing members, add the user
                System.out.println("check dos3");

                preparedStatement.setString(1, iduser + ",");
            }

            preparedStatement.setInt(2, abonnement.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error editing sub: " + e.getMessage());
        }
    }


    @Override
    public void supprimer(int id) throws SQLException {
        String query = "DELETE FROM abonnement WHERE id=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error deleting sub: " + e.getMessage());
        }
    }

    @Override
    public ObservableList<Abonnement> afficher() throws SQLException {
        ObservableList<Abonnement> abonnementsList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM `abonnement`";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int id_category = resultSet.getInt("id_category");
                String nom = resultSet.getString("nom");
                int duree = resultSet.getInt("duree");
                float prix = resultSet.getFloat("prix");
                boolean fidelite = resultSet.getBoolean("fidelite");

                // Assuming there's a constructor that includes the 'members' attribute
                // If not, you can add a setter for the 'members' attribute and set it separately.
                String members = resultSet.getString("members");

                Abonnement abonnement = new Abonnement(id, id_category, nom, duree, prix, fidelite, members);
                abonnementsList.add(abonnement);
            }
        }
        return abonnementsList;
    }


    @Override
    public ObservableList<Abonnement> search(String name) throws SQLException {
        return null;
    }

    @Override
    public ObservableList<Abonnement> searchU(String name) throws SQLException {
        return null;
    }

    @Override
    public ObservableList<Abonnement> getAccountByAccountId(int AccountId) throws SQLException {
        return null;
    }

    @Override
    public ObservableList<Abonnement> getAccountByRole(Enum role, int count) throws SQLException {
        return null;
    }

    @Override
    public Abonnement authenticate(String email, String password) throws SQLException {
        return null;
    }

    public String getMembersByCategoryName(String categoryName) throws SQLException {
        ObservableList<String> membersList = FXCollections.observableArrayList();

        // Retrieve the category ID based on the given category name
        int categoryId = getCategoryIdByName(categoryName);

        if (categoryId != -1) {
            // If the category exists, fetch the corresponding members strings
            String sql = "SELECT members FROM `abonnement` WHERE id_category = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, categoryId);

                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    // Retrieve the 'members' string from the result set
                    String members = resultSet.getString("members");
                     return  members;
                }
            }
        }

        return "none";
    }


    // Helper method to get the category ID based on the category name
    public int getCategoryIdByName(String categoryName) throws SQLException {
        int categoryId = -1;
        String categorySql = "SELECT id FROM `category` WHERE nom = ?";
        try (PreparedStatement categoryStatement = connection.prepareStatement(categorySql)) {
            categoryStatement.setString(1, categoryName);

            ResultSet categoryResultSet = categoryStatement.executeQuery();
            if (categoryResultSet.next()) {
                categoryId = categoryResultSet.getInt("id");
            }
        }
        return categoryId;
    }
}
