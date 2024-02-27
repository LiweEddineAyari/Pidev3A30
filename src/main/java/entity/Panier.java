package entity;

public class Panier {
    int id;
    int iduser;

    public Panier(int id, int iduser) {
        this.id = id;
        this.iduser = iduser;
    }

    public int getId() {
        return id;
    }

    public int getIduser() {
        return iduser;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    @Override
    public String toString() {
        return "Panier{" +
                "id=" + id +
                ", iduser=" + iduser +
                '}';
    }
}
