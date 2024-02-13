package entity;

public class Admin {
    int id_admin;
    String nom;
    String prenom;
    int age;
    String mail;
    int password;


    public Admin(int id_admin, String nom, String prenom, int age, String mail, int password) {
        this.id_admin = id_admin;
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.mail = mail;
        this.password = password;
    }

    public int getId() {
        return id_admin;
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