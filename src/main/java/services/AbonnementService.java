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

    @Override
    public void addnewMember(Abonnement abonnement, int iduser) throws SQLException {
        String query = "UPDATE abonnement SET members=? WHERE id=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            if (abonnement.getMembres() != null) {
                // Check if the user already exists in members
                System.out.println("check dos");

                if (!isUserInMembers(iduser, abonnement.getMembres())) {
                    System.out.println("check dos1");

                    preparedStatement.setString(1, abonnement.getMembres()+iduser + ",");
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
    public Abonnement getAbonnementById(int id) throws SQLException {
        String query = "SELECT * FROM abonnement WHERE id = ?";
        Abonnement abonnement = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // Retrieve data from the result set and create an Abonnement object
                int categoryId = resultSet.getInt("id_category");
                String nom = resultSet.getString("nom");
                int duree = resultSet.getInt("duree");
                float prix = resultSet.getFloat("prix");
                boolean fidelite = resultSet.getBoolean("fidelite");
                String members = resultSet.getString("members");

                abonnement = new Abonnement(id, categoryId, nom, duree, prix, fidelite, members);
            }
        } catch (SQLException e) {
            throw new SQLException("Error getting abonnement by ID: " + e.getMessage());
        }

        return abonnement;
    }


    @Override
    public void removeMember(int idabonnement, int iduser) throws SQLException {
        String query = "UPDATE abonnement SET members=? WHERE id=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            Abonnement abonnement = getAbonnementById(idabonnement);

            if (abonnement != null && abonnement.getMembres() != null) {
                // Remove the user ID from members if it exists
                String updatedMembers = removeUserIdFromMembers(iduser, abonnement.getMembres());

                preparedStatement.setString(1, updatedMembers);
                preparedStatement.setInt(2, idabonnement);
                preparedStatement.executeUpdate();

                // Update the abonnement object with the modified members
                abonnement.setMembres(updatedMembers);
            } else {
                throw new SQLException("Abonnement not found or has no members.");
            }
        } catch (SQLException e) {
            throw new SQLException("Error removing member from abonnement: " + e.getMessage());
        }
    }

    private String removeUserIdFromMembers(int userId, String members) {
        if (members != null) {
            String[] userIds = members.split(",");
            StringBuilder updatedMembers = new StringBuilder();

            for (String id : userIds) {
                if (!id.equals(String.valueOf(userId))) {
                    updatedMembers.append(id).append(",");
                }
            }

            return updatedMembers.toString();
        }

        return members;
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
    public ObservableList<Abonnement> searchAbonnements(String minPriceText, String maxPriceText, String minDureeText, String maxDureeText,String nameText) throws SQLException {
        ObservableList<Abonnement> abonnementsList = FXCollections.observableArrayList();

        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM `abonnement` WHERE 1");

        if (!minPriceText.isEmpty()) {
            float minPrice = Float.parseFloat(minPriceText);
            queryBuilder.append(" AND prix >= ").append(minPrice);
        }

        if (!maxPriceText.isEmpty()) {
            float maxPrice = Float.parseFloat(maxPriceText);
            queryBuilder.append(" AND prix <= ").append(maxPrice);
        }

        if (!minDureeText.isEmpty()) {
            int minDuree = Integer.parseInt(minDureeText);
            queryBuilder.append(" AND duree >= ").append(minDuree);
        }

        if (!maxDureeText.isEmpty()) {
            int maxDuree = Integer.parseInt(maxDureeText);
            queryBuilder.append(" AND duree <= ").append(maxDuree);
        }
        if (!nameText.isEmpty()) {
            queryBuilder.append(" AND nom LIKE '%").append(nameText).append("%'");
        }

        // Use the dynamic query in your SQL statement
        String sql = queryBuilder.toString();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                // Parse the result set and populate Abonnement objects
                int id = resultSet.getInt("id");
                int idCategory = resultSet.getInt("id_category");
                String nom = resultSet.getString("nom");
                int duree = resultSet.getInt("duree");
                float prix = resultSet.getFloat("prix");
                boolean fidelite = resultSet.getBoolean("fidelite");
                String members = resultSet.getString("members");

                Abonnement abonnement = new Abonnement(id, idCategory, nom, duree, prix, fidelite, members);
                abonnementsList.add(abonnement);
            }
        }

        return abonnementsList;
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


    public String getCategoryName(int id ) throws SQLException {
        String categoryName = "none";
        String categorySql = "SELECT nom FROM `category` WHERE id = ?";
        try (PreparedStatement categoryStatement = connection.prepareStatement(categorySql)) {
            categoryStatement.setInt(1, id);

            ResultSet categoryResultSet = categoryStatement.executeQuery();
            if (categoryResultSet.next()) {
                categoryName = categoryResultSet.getString("nom");
            }
        }
        catch (SQLException e){
            e.getMessage();
        }
        return categoryName;
    }
}
