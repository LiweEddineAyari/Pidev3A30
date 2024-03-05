package entity;

public class notif {

    private int id;
    private String nom;
    private String description;

    private String title;

    public notif() {

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public notif(int id, String nom, String description, String title) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.title = title;
    }

    @Override
    public String toString() {
        return "notif{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
