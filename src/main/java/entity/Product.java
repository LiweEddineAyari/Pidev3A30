package entity;


import java.util.Objects;

public class Product {
    int id;
    String name;
    String ref;
    float price;
    int quantity;
    int weight;
    String imageUrl;
    String description;


    public Product(int id, String name, String ref, float price, int quantity, int weight, String imageUrl, String description) {
        this.id = id;
        this.name = name;
        this.ref = ref;
        this.price = price;
        this.quantity = quantity;
        this.weight = weight;
        this.imageUrl = imageUrl;
        this.description = description;
    }
// Getters...

    public int getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getRef() {
        return ref;
    }

    public float getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getWeight() {
        return weight;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ref='" + ref + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", weight=" + weight +
                ", imageUrl='" + imageUrl + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}