package main.projet;

import entity.Product;
import interfaces.ProductListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class ProductItemCard {

    @FXML
    private VBox productCardItem;

    @FXML
    private ImageView productimg;

    @FXML
    private Label productname;

    @FXML
    private Label productprice;


    Product product;
    private ProductListener listener;
    private ShoppingCart shoppingCart;



    public void setData(Product product, ProductListener listener) {
        this.product = product;
        this.listener = listener;
        productname.setText(product.getName());
        productprice.setText(String.valueOf(product.getPrice()) + " TND");
    }

    @FXML
    private void handleViewDetailsButton() {
        if (listener != null) {
            listener.onViewDetails(product);
        }
    }



    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    @FXML
    private void handleShopButton() {
        if (shoppingCart != null && product != null) {
            shoppingCart.addToCart(product);
        }
    }


}
