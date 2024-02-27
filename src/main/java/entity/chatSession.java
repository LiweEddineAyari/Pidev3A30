package entity;

public class chatSession {
    int id ;
    int id_user;
    int id_user2;

    public chatSession(int id, int id_user, int id_user2) {
        this.id = id;
        this.id_user = id_user;
        this.id_user2 = id_user2;
    }

    public int getId() {
        return id;
    }

    public int getId_user() {
        return id_user;
    }

    public int getId_user2() {
        return id_user2;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public void setId_user2(int id_user2) {
        this.id_user2 = id_user2;
    }

    @Override
    public String toString() {
        return "chatSession{" +
                "id=" + id +
                ", id_user=" + id_user +
                ", id_user2=" + id_user2 +
                '}';
    }
}
