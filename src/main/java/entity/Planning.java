package entity;

public class Planning {
    int id ;
    int id_coach;
    String date_debut;
    String date_fin;
    String  heure_debut;
    String  heure_fin;
    String titre;
    String description;

    public Planning(int id, int id_coach, String date_debut, String date_fin, String heure_debut, String heure_fin, String titre, String description) {
        this.id = id;
        this.id_coach = id_coach;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.heure_debut = heure_debut;
        this.heure_fin = heure_fin;
        this.titre = titre;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public int getId_coach() {
        return id_coach;
    }

    public String getDate_debut() {
        return date_debut;
    }

    public String getDate_fin() {
        return date_fin;
    }

    public String getHeure_debut() {
        return heure_debut;
    }

    public String getHeure_fin() {
        return heure_fin;
    }

    public String getTitre() {
        return titre;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Planning{" +
                "id=" + id +
                ", id_coach=" + id_coach +
                ", date_debut='" + date_debut + '\'' +
                ", date_fin='" + date_fin + '\'' +
                ", heure_debut='" + heure_debut + '\'' +
                ", heure_fin='" + heure_fin + '\'' +
                ", titre='" + titre + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
