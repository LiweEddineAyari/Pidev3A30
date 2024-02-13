package entity;

public class Exercice {
    int id ;
    int productid;
    String name;
    String target;
    String type;
    String description;
    String img;
    String intensity;
    String EquipmentNeeded;


    public Exercice(int id, int productid, String name, String target, String type, String description,String img,String intensity, String EquipmentNeeded) {
        this.id = id;
        this.productid = productid;
        this.name = name;
        this.target = target;   //(Chest,Shoulder,Back,Leg,Abs,Arm)
        this.type = type;  //(Growth,Strength,Resilience)
        this.description = description;
        this.img=img;      //file:/C:/Users/JAXIM/IdeaProjects/projet/src/main/resources/main/projet/images/ex.jpeg
        this.intensity=intensity; //(Easy,Medium,Advanced)
        this.EquipmentNeeded=EquipmentNeeded;  //(Yes,No)
    }

    public String getImg() {
        return img;
    }

    public int getId() {
        return id;
    }

    public int getProductid() {
        return productid;
    }

    public String getName() {
        return name;
    }

    public String getTarget() {
        return target;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public String getIntensity() {
        return intensity;
    }

    public String getEquipmentNeeded() {
        return EquipmentNeeded;
    }

    @Override
    public String toString() {
        return "Exercice{" +
                "id=" + id +
                ", productid=" + productid +
                ", name='" + name + '\'' +
                ", target='" + target + '\'' +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", img='" + img + '\'' +
                '}';
    }
}
