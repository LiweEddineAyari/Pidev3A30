package entity;

public class Abonnement {
    int id ;

    int idcategory;
    String nom;
    int duree;
    float prix;
    boolean fidelite;
    String 	membres;

    public Abonnement(int id,  int idcategory, String nom, int duree, float prix, boolean fidelite) {
        this.id = id;
        this.idcategory = idcategory;
        this.nom = nom;
        this.duree = duree;
        this.prix = prix;
        this.fidelite = fidelite;
    }

    public Abonnement(int id,  int idcategory, String nom, int duree, float prix, boolean fidelite, String membres) {
        this.id = id;
        this.idcategory = idcategory;
        this.nom = nom;
        this.duree = duree;
        this.prix = prix;
        this.fidelite = fidelite;
        this.membres = membres;
    }

    public void setMembres(String membres) {
        this.membres = membres;
    }

    public String getMembres() {
        return membres;
    }

    public int getId() {
        return id;
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

    @Override
    public String toString() {
        return "Abonnement{" +
                "id=" + id +
                ", idcategory=" + idcategory +
                ", nom='" + nom + '\'' +
                ", duree=" + duree +
                ", prix=" + prix +
                ", fidelite=" + fidelite +
                '}';
    }
}
