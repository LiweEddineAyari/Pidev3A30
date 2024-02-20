package entity;

public class Account {
    int id;
    String name;
    String prenom;
    int age;
    String mail;
    String password;

    public Account() {
    }

    // Declare an Enum for the title
    public enum Title {
        user, admin, coach;

        private String stringValue;

        public static Title fromString(String titleString) {
            for (Title title : Title.values()) {
                if (title.stringValue.equalsIgnoreCase(titleString)) {
                    return title;
                }
            }
            throw new IllegalArgumentException("No enum constant for string: " + titleString);
        }
    }

    private Title title;

    public Account(int id, String name, String prenom, int age, String mail, String password, Title title) {
        this.id = id;
        this.name = name;
        this.prenom = prenom;
        this.age = age;
        this.mail = mail;
        this.password = password;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return name;
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

    public String getPassword() {
        return password;
    }

    public Title getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
