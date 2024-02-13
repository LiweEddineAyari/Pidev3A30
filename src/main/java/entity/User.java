package entity;

public class User {
    int id_user;
    String nom;
    String prenom;
    int age;
    String mail;
    int password;


    public User(int id_user, String nom, String prenom, int age, String mail, int password) {
        this.id_user = id_user;
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.mail = mail;
        this.password = password;
    }

    public int getId() {
        return id_user;
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
}