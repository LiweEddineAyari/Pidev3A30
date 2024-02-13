package entity;

public class Participants {
    int id ;
    int idevennement;
    int iduser;

    public Participants(int id, int idevennement, int iduser) {
        this.id = id;
        this.idevennement = idevennement;
        this.iduser = iduser;
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
}
