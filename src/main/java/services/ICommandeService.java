package services;

import entity.Commande;
import entity.Paiment;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface ICommandeService<Commande,Panier,PanierProduct> {

    //panier crud
    int ajouterPanier(Panier panier) throws SQLException;
    void supprimerPanier(int id) throws SQLException;


    //panier crud
    void ajouterPanierProduit(PanierProduct panierProduct) throws SQLException;
    void supprimerPanierProduit(int id) throws SQLException;

    ObservableList<PanierProduct> afficherPanierProductList() throws  SQLException ;



    //commande crud
    void ajouterCommande(Commande commande) throws SQLException;
    void supprimerCommande(int id) throws SQLException;

    ObservableList<Commande> afficher() throws  SQLException ;
    void modifier(Commande commande)throws SQLException;


    ObservableList<entity.Commande> searchCommande(String status, String minMontantText, String maxMontantText) throws SQLException;
}
