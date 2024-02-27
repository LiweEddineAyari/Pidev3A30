package entity;

public class PanierProduct {
    int id;
    int idpanier;
    int idproduct;
    int quantite;
    float prix;

    public PanierProduct(int id, int idpanier, int idproduct, int quantite, float prix) {
        this.id = id;
        this.idpanier = idpanier;
        this.idproduct = idproduct;
        this.quantite = quantite;
        this.prix = prix;
    }

    public int getId() {
        return id;
    }

    public int getIdpanier() {
        return idpanier;
    }

    public int getIdproduct() {
        return idproduct;
    }

    public int getQuantite() {
        return quantite;
    }

    public float getPrix() {
        return prix;
    }
}
