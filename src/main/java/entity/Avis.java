package entity;

public class Avis {
    int id ;
    int idevennement;
    int iduser;
    String commentaire;

    public Avis(int id, int idevennement, int iduser, String commentaire) {
        this.id = id;
        this.idevennement = idevennement;
        this.iduser = iduser;
        this.commentaire = commentaire;
    }

    public int getId() {
        return id;
    }

    public int getIdevennement() {
        return idevennement;
    }

    public int getIduser() {
        return iduser;
    }

    public String getCommentaire() {
        return commentaire;
    }
}
