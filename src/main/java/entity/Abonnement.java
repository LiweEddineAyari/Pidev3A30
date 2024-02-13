package entity;

public class Abonnement {
    int id ;
    int iduser;

    int idcategory;
    String nom;
    int duree;
    float prix;
    boolean fidelite;

    public Abonnement(int id, int iduser, int idcategory, String nom, int duree, float prix, boolean fidelite) {
        this.id = id;
        this.iduser = iduser;
        this.idcategory = idcategory;
        this.nom = nom;
        this.duree = duree;
        this.prix = prix;
        this.fidelite = fidelite;
    }

    public int getId() {
        return id;
    }

    public int getIduser() {
        return iduser;
    }

    public int getIdcategory() {
        return idcategory;
    }

    public String getNom() {
        return nom;
    }

    public int getDuree() {
        return duree;
    }

    public float getPrix() {
        return prix;
    }

    public boolean isFidelite() {
        return fidelite;
    }
}
