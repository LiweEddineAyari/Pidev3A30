package entity;

public class gym {
    private int id;
    private String nom;

    private int client_number;

    private int coach_number;

    private int user_id;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public gym(int id, int user_id, String nom, int client_number, int coach_number) {
        this.id = id;
        this.nom = nom;
        this.client_number = client_number;
        this.coach_number = coach_number;
        this.user_id = user_id;
    }

    public gym(int id, String nom, int client_number, int coach_number) {
        this.id = id;
        this.nom = nom;
        this.client_number = client_number;
        this.coach_number = coach_number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getClient_number() {
        return client_number;
    }

    public void setClient_number(int client_number) {
        this.client_number = client_number;
    }

    public int getCoach_number() {
        return coach_number;
    }

    public void setCoach_number(int coach_number) {
        this.coach_number = coach_number;
    }
}
