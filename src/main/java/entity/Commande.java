package entity;

import javafx.beans.Observable;
import javafx.collections.ObservableList;

public class Commande {
  int id ;
  int iduser;
  int idpanier;
  float montant;
  String statut;
  ObservableList<Product> productspanier;

    public Commande(int id, int iduser, int idpanier, float montant,String statut) {
        this.id = id;
        this.iduser = iduser;
        this.idpanier = idpanier;
        this.montant = montant;
        this.statut=statut;
    }

    public int getId() {
        return id;
    }

    public int getIduser() {
        return iduser;
    }

    public int getIdpanier() {
        return idpanier;
    }

    public float getMontant() {
        return montant;
    }

    public String getStatut() {
        return statut;
    }

    public ObservableList<Product> getProductspanier() {
        return productspanier;
    }
}
