package entity;

public class Account {
    private int id;
    private String name;
    private String prenom;
    private int age;
    private String mail;
    private String password;
    private Title title;



    public static int Currentid;

    public static String codemail;

    public static String getCodemail() {
        return codemail;
    }

    public static void setCodemail(String codemail) {
        Account.codemail = codemail;
    }

    public Account() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

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

    public static int getCurrentid() {
        return Currentid;
    }

    public static void setCurrentid(int currentid) {
        Currentid = currentid;
    }

    public enum Title {
        user, admin, coach;

        private String stringValue;

        static {
            user.stringValue = "user";
            admin.stringValue = "admin";
            coach.stringValue = "coach";
        }

        public static Title fromString(String titleString) {
            for (Title title : Title.values()) {
                if (title.stringValue.equalsIgnoreCase(titleString)) {
                    return title;
                }
            }
            throw new IllegalArgumentException("No enum constant for string: " + titleString);
        }
    }
}
