package entity;

public class Evennement {
    int id;
    String nom;
    String date;
    String type;
    String description;


    public Evennement(int id, String nom, String date, String type, String description) {
        this.id = id;
        this.nom = nom;
        this.date = date;
        this.description = description;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }
}
