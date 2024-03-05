package services;

import entity.Commande;
import entity.Paiment;
import entity.Panier;
import entity.PanierProduct;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.MyDataBase;

import java.sql.*;

public class CommandeService implements ICommandeService<Commande, Panier,PanierProduct> {

    private Connection connection;

    public CommandeService() {
        connection= MyDataBase.getInstance().getConnection();
    }






    //panier
    @Override
    public int ajouterPanier(Panier panier) throws SQLException {
        String sql = "INSERT INTO Panier (iduser) VALUES (?)";

        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, panier.getIduser());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating panier failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1); // Return the ID of the newly added panier
                } else {
                    throw new SQLException("Creating panier failed, no ID obtained.");
                }
            }
        }
    }


    @Override
    public void supprimerPanier(int id) throws SQLException {
        String sql = "DELETE FROM Panier WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

           statement.executeUpdate();
        }
    }


    //panier produit
    @Override
    public void ajouterPanierProduit(PanierProduct panierProduct) throws SQLException {
        String sql = "INSERT INTO PanierProduct (idpanier, idproduct, quantite, prix) VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, panierProduct.getIdpanier());
            statement.setInt(2, panierProduct.getIdproduct());
            statement.setInt(3, panierProduct.getQuantite());
            statement.setFloat(4, panierProduct.getPrix());

            statement.executeUpdate();
        }
    }

    @Override
    public void supprimerPanierProduit(int id) throws SQLException {
        String sql = "DELETE FROM PanierProduct WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    public ObservableList<PanierProduct> afficherPanierProductList() throws SQLException {
        ObservableList<PanierProduct> panierProducts = FXCollections.observableArrayList();
        String sql = "SELECT * FROM PanierProduct";

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int idpanier = resultSet.getInt("idpanier");
                int idproduct = resultSet.getInt("idproduct");
                int quantite = resultSet.getInt("quantite");
                float prix = resultSet.getFloat("prix");

                PanierProduct panierProduct = new PanierProduct(id, idpanier, idproduct, quantite, prix);
                panierProducts.add(panierProduct);
            }
        }

        return panierProducts;
    }

    //commande

    @Override
    public void ajouterCommande(Commande commande) throws SQLException {
        String sql = "INSERT INTO Commande (iduser, idpanier, montant, statut) VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, commande.getIduser());
            statement.setInt(2, commande.getIdpanier());
            statement.setFloat(3, commande.getMontant());
            statement.setString(4, commande.getStatut());
            statement.executeUpdate();
        }
    }

    @Override
    public void supprimerCommande(int id) throws SQLException {
        String sql = "DELETE FROM Commande WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    public ObservableList<Commande> afficher() throws SQLException {
        ObservableList<Commande> commandes = FXCollections.observableArrayList();
        String sql = "SELECT * FROM commande";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    int iduser = resultSet.getInt("iduser");
                    int idpanier = resultSet.getInt("idpanier");
                    float montant = resultSet.getFloat("montant");
                    String statut = resultSet.getString("statut");

                    Commande commande = new Commande(id, iduser, idpanier, montant, statut);
                    commandes.add(commande);
                }
            }
        }

        return commandes;
    }

    public void modifier(Commande commande) throws SQLException {
        String sql = "UPDATE commande SET  montant=?, statut=? WHERE id=?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setFloat(1, commande.getMontant());
            statement.setString(2, commande.getStatut());
            statement.setInt(3, commande.getId());

            statement.executeUpdate();
        }
    }






    @Override

    public ObservableList<Commande> searchCommande(String status, String minMontantText, String maxMontantText) throws SQLException {
        ObservableList<Commande> commandes = FXCollections.observableArrayList();

        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM `commande` WHERE 1");

        if (!status.isEmpty()) {
            queryBuilder.append(" AND status = '").append(status).append("'");
        }

        if (!minMontantText.isEmpty()) {
            float minMontant = Float.parseFloat(minMontantText);
            queryBuilder.append(" AND montant >= ").append(minMontant);
        }

        if (!maxMontantText.isEmpty()) {
            float maxMontant = Float.parseFloat(maxMontantText);
            queryBuilder.append(" AND montant <= ").append(maxMontant);
        }

        // Use the dynamic query in your SQL statement
        String sql = queryBuilder.toString();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int iduser = resultSet.getInt("iduser");
                int idpanier = resultSet.getInt("idpanier");
                float montant = resultSet.getFloat("montant");
                String statut = resultSet.getString("statut");

                Commande commande = new Commande(id, iduser, idpanier, montant, statut);
                commandes.add(commande);
            }
        }

        return commandes;
    }

}
