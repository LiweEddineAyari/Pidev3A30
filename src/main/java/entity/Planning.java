package entity;

public class Planning {
    int id ;
    int id_coach;
    String date;
    String  heure_debut;
    String  heure_fin;

    public Planning(int id, int id_coach, String date, String heure_debut, String heure_fin) {
        this.id = id;
        this.id_coach = id_coach;
        this.date = date;
        this.heure_debut = heure_debut;
        this.heure_fin = heure_fin;
    }

    public int getId() {
        return id;
    }

    public int getId_coach() {
        return id_coach;
    }

    public String getDate() {
        return date;
    }

    public String getHeure_debut() {
        return heure_debut;
    }

    public String getHeure_fin() {
        return heure_fin;
    }
}
