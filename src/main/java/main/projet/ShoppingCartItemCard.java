package main.projet;

import entity.Product;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ShoppingCartItemCard implements Initializable {

    @FXML
     Label Productname;

    @FXML
     Label quantity;

    @FXML
     HBox shoppingcartitemcard;
    int Quantity;
    Product product;
    private ShoppingCart shoppingCart; // Add this reference

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        shoppingcartitemcard.getProperties().put("controller", this);

    }

    public void setData(Product product){
         this.product=product;
         this.Quantity=1;
        Productname.setText(product.getName());
        quantity.setText(String.valueOf(Quantity));
    }


    @FXML
    void increaseQuantity(){
        Quantity++;
        quantity.setText(String.valueOf(Quantity));
        shoppingCart.updateTotalLabel();
    }
    @FXML
    void decreaseQuantity(){
        if(Quantity>1){
            Quantity --;
            quantity.setText(String.valueOf(Quantity));
            shoppingCart.updateTotalLabel();
        }
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return Quantity;
    }


}
