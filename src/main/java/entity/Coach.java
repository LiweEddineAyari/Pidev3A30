package entity;

public class Coach {
    int id;
    String nom;
    String prenom;
    int age;
    String mail;
    int password;


    public Coach(int id, String nom, String prenom, int age, String mail, int password) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.mail = mail;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public int getAge() {
        return age;
    }

    public String getMail() {
        return mail;
    }

    public int getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "Coach{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", age=" + age +
                ", mail='" + mail + '\'' +
                ", password=" + password +
                '}';
    }
}
